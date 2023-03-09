package lab03;

import lab03.MyAlg;

import java.util.ArrayList;

import lab03.ExpSetup;

public class RunTests {

    public static void main(String[] args) {

        int n = 1000;
        int dm = 100;

        int[][] params = {
            // {10, 10},
            // {10, 50},
            // {50, 10},
            // {50, 50},
            // {10, 100},
            // {100, 10},
            // {100, 100},
            {100, 1000},
        };
        ArrayList<ExpSetup> all_exps = new ArrayList<ExpSetup>();
        ArrayList<ExpSetup> exps = new ArrayList<ExpSetup>();

        for (int[] p : params) {
            ExpSetup eg = new ExpSetup(dm, p[0], p[1]);
            for (int k = 0; k < n; k++) {
                ExpSetup e = new ExpSetup(dm, p[0], p[1]);
                single_test(e);
                all_exps.add(e);
                eg.update_mean_best(e.best, e.best_epoch, n);
            }
            exps.add(eg);
        }

        for (ExpSetup e : exps) {
            System.out.printf(
                "Experiment(dim = %d, pop = %d, gen = %d)%n%n",
                e.dimension,
                e.populationSize,
                e.generations
            );
            System.out.printf(
                "Results: mean_best = %f; mean_ep = %f %n%n",
                e.mean_best,
                e.mean_best_epoch
            );
            System.out.printf("<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>%n%n");
        }

        // System.out.println("s6");
    }

    public static void single_test(ExpSetup e) {
        MyAlg alg = new MyAlg();
        alg.algo(e.dimension, e.populationSize, e.generations);
        e.set_results(alg.best, alg.bestGen, alg.solution);
    }
}
