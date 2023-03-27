package lab06;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.time.Duration;
import java.time.Instant;

public class Experiment {

    public static void main(String[] args) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
        // int[] comps = {1, 2, 3, 4, 5, 6, 7};
        int[] comps = {1};
        int nexp = 1;
        int gens = 100;
        int dim = 50;
        int popsize = 100;
        

        for (int comp: comps) {
            Setup s = new Setup(comp, dim, nexp, gens, popsize);
            run_single(s);
        }
    }

    public static void run_single(Setup s) {
        for (AlgoSetup as: s.algos) {
            Instant start = Instant.now();
            System.out.println("Alg");
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

    public static String report(int problem,
                                Setup res,
                                int popsize) {

        String template = (
            "<tr>\n\t<td rowspan=2>%d</td>\n\t<td>Time</td>\n\t<td> %f </td>\n\t<td> %f </td>\n\t<td> %f </td>\n" +
            "</tr>\n<tr>\n\t<td>Result</td>\n\t<td> %f </td>\n\t<td> %f </td>\n\t<td> %f </td>\n\t</tr>");
        String result = String.format(
            template,
            problem,
            popsize
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
    List<AlgoSetup> algos;
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
