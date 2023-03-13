package lab03;

public class ExpSetup {
    int dimension; // dimension of problem
    int populationSize; // size of population
    int generations; // number of generations
    int best_epoch;
    double best;
    double[] solution;
    double mean_best_epoch;
    double mean_best;

    ExpSetup() {
        dimension = 2;
        populationSize = 10;
        generations = 10;
        mean_best_epoch = 0;
        mean_best = 0;
    }

    ExpSetup(int dimension, int populationSize, int generations) {
        this.dimension = dimension;
        this.populationSize = populationSize;
        this.generations = generations;
        mean_best_epoch = 0;
        mean_best = 0;
    }

    public void set_results(double best, int best_epoch, double[] solution) {
        this.best = best;
        this.best_epoch = best_epoch;
        this.solution = solution.clone();
    }

    public void update_mean_best(double best, int epoch, int sum) {
        mean_best_epoch += ((double) epoch) / sum;
        mean_best += best / sum;
    }
}
