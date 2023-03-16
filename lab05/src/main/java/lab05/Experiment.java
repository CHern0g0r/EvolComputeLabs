package lab05;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Experiment {

    public static void main(String[] args) {

        int nexp = 10;
        int populationSize = 500; // size of population
        int generations = 100000; // number of generations
        // String problem = "pma343"; // name of problem or path to input file
        String[] problems = {
            "xqg237",
            "pka379",
            "bcl380",
            "pbk411"
        };
        String report_path = "./EvolComputeLabs/lab04/data/report.txt";

        File f = new File(report_path);
        try {
            f.createNewFile();
        } catch (IOException e) {
            System.out.println(e);
        }
        toFile(report_path, "-----------------------------\n");

        for (String problem : problems) {
            Pair mean_results = new Pair();
            for (int j = 0; j < nexp; j++) {
                Pair result = run_single(populationSize, generations, problem);
                mean_results.epoch += result.epoch;
                mean_results.tour += result.tour;
            }
            mean_results.epoch /= nexp;
            mean_results.tour /= nexp;

            String rep = report(problem, mean_results, populationSize, generations);
            toFile(report_path, rep);
        }
    }

    public static Pair run_single(int populationSize,
                                  int generations,
                                  String problem) {
        Alg alg  = new Alg();

        alg.run(
            populationSize,
            generations,
            problem
        );

        return new Pair(alg.best_epoch, alg.best_tour);
    }

    public static String report(String problem,
                              Pair res,
                              int popsize,
                              int gens) {

        Integer size = Integer.parseInt(problem.replaceAll("[\\D]", ""));
        String template = "|%s|%d|%d ; %d|%f|%f||\n";
        String result = String.format(
            template,
            problem,
            size,
            popsize,
            gens,
            res.tour,
            res.epoch
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

class Pair {
    double epoch;
    double tour;

    Pair() {
        this(0, 0);
    }

    Pair(double e, double t) {
        epoch = e;
        tour = t;
    }
}
