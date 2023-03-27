package lab05;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Experiment {

    public static void main(String[] args) {

        int nexp = 10;
        int populationSize = 1000; // size of population
        int[] problems = {
            // 4,
            // 8,
            // 16,
            32,
            64,
            128,
            512
        };
        int[] mutations = {
            // 10,
            50,
            100,
            200,
            500,
            1000
        };
        String report_path = "./EvolComputeLabs/lab05/report.txt";
        String solutions_path = "./EvolComputeLabs/lab05/sols.txt";

        File f = new File(report_path);
        try {
            f.createNewFile();
        } catch (IOException e) {
            System.out.println(e);
        }
        toFile(report_path, "-----------------------------\n");

        for (int mut: mutations) {
            for (int problem : problems) {
                Result mean_results = new Result();
                mean_results.mutations = mut;
                for (int j = 0; j < nexp; j++) {
                    Result result = run_single(
                        populationSize,
                        problem,
                        mut
                    );
                    mean_results.epoch += result.epoch;
                    mean_results.fitness_eval += result.fitness_eval;
                    mean_results.best += result.best;
                    toFile(solutions_path, result.solution.toString() + "\n");
                }
                mean_results.epoch /= nexp;
                mean_results.fitness_eval /= nexp;
                mean_results.best /= nexp;

                String rep = report(problem, mean_results, populationSize);
                toFile(report_path, rep);
            }
        }
    }

    public static Result run_single(int populationSize,
                                    int problem,
                                    int mutations) {
        Alg alg  = new Alg();

        alg.run(
            problem,
            populationSize,
            mutations
        );

        return new Result(alg.best_epoch, alg.best_solution, alg.fitness_eval, alg.best_fit);
    }

    public static String report(int problem,
                                Result res,
                                int popsize) {

        String template = "| %d | %d | %d | %.2f | %.2f | > %.2f\n";
        String result = String.format(
            template,
            problem,
            popsize,
            res.mutations,
            res.fitness_eval,
            res.epoch,
            res.best
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

class Result {
    double epoch;
    double fitness_eval;
    double best;
    Solution solution;
    int mutations;

    Result() {
        epoch = 0;
        fitness_eval = 0;
        best = 0;
    }

    Result(double e, Solution s, double fe, double b) {
        this(e, s, fe, b, 1);
    }

    Result(double e, Solution s, double fe, double b, int m) {
        epoch = e;
        solution = s;
        fitness_eval = fe;
        best = b;
        mutations = m;
    }
}
