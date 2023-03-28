package lab06;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.time.Duration;
import java.time.Instant;

public class Experiment {

    public static void main(String[] args) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
        int[] comps = {1, 2, 3, 4, 5, 6, 7};
        int nexp = 16;
        int gens = 1000;
        int dim = 50;
        int popsize = 100;
        
        String report_path = "./EvolComputeLabs/lab06/report.txt";
        File f = new File(report_path);
        try {
            f.createNewFile();
        } catch (IOException e) {
            System.out.println(e);
        }
        toFile(report_path, "-----------------------------\n");

        for (int comp: comps) {
            Setup s = new Setup(comp, dim, nexp, gens, popsize);
            run_single(s);
            String res = report(s);
            toFile(report_path, res);
        }
    }

    public static void run_single(Setup s) {
        for (AlgoSetup as: s.algos) {
            Instant start = Instant.now();
            for (int i = 0; i < s.nexp; i++) {
                Algorithm alg = as.get();
                double fitness = alg.run(s.dim, s.comp, s.popsize, s.gens);
                as.fitness += fitness;
            }
            Instant end = Instant.now();
            Duration timeElapsed = Duration.between(start, end);
            System.out.println(timeElapsed.toMillis());
            as.time = timeElapsed.toMillis();
        }
        s.average();
    }

    public static String report(Setup res) {

        DecimalFormat df = new DecimalFormat("#.##");
        String template = (
            "<tr>\n\t<td rowspan=2>%d</td>\n\t<td>Time</td>\n%s" +
            "</tr>\n<tr>\n\t<td>Result</td>\n%s</tr>");
        StringBuilder ptimes = new StringBuilder();
        StringBuilder pfit = new StringBuilder();
        for (AlgoSetup a : res.algos) {
            ptimes.append("\t<td> ").append(df.format(a.time)).append(" </td>\n");
            pfit.append("\t<td> ").append(df.format(a.fitness)).append(" </td>\n");
        }
        String result = String.format(
            template,
            res.comp,
            ptimes.toString(),
            pfit.toString()
        );

        return result;
    }

    public static void toFile(String path, String res) {
        try {
            Files.write(
                Paths.get(path),
                res.getBytes(),
                StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}

class Setup {
    String[] names = {
        "lab06.MasterSlaveAlg",
        "lab06.MasterSlaveAlg",
        "lab06.IslandsAlg"
    };
    boolean[] args = {
        true,
        false,
        false,
    };
    ArrayList<AlgoSetup> algos;
    int comp;
    int nexp;
    int gens;
    int popsize;
    int dim;

    Setup(int comp,
          int dim,
          int nexp,
          int gens,
          int popsize) {

        this.comp = comp;
        this.dim = dim;
        this.nexp = nexp;
        this.gens = gens;
        this.popsize = popsize;

        algos = new ArrayList<AlgoSetup>();
        for (int i = 0; i < names.length; i++) {
            AlgoSetup a = new AlgoSetup(names[i], args[i]);
            algos.add(a);
        }
    }

    void average() {
        for (AlgoSetup a: algos) {
            a.time /= nexp;
            a.fitness /= nexp;
        }
    }

}

class AlgoSetup {
    Constructor<?> c;
    double fitness;
    double time;
    boolean arg;

    AlgoSetup(String name, boolean arg) {
        Class<?> cls;
        try {
            cls = Class.forName(name);
            c = cls.getConstructor(boolean.class);
        } catch (IllegalArgumentException | ClassNotFoundException
                | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
        fitness = 0;
        time = 0;
        this.arg = arg;
    }

    Algorithm get() {
        Algorithm a;
        try {
            a = (Algorithm) c.newInstance(arg);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            e.printStackTrace();
            System.out.println("FAILURE");
            a = new IslandsAlg();
        }
        return a;
    }
}
