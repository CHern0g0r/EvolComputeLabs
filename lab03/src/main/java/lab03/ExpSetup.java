package lab03;

public class ExpSetup {
    int dimension; // dimension of problem
    int populationSize; // size of population
    int generations; // number of generations
    double best;
    double[] solution;

    ExpSetup() {
        dimension = 2;
        populationSize = 10;
        generations = 10;
    }

    ExpSetup(int dimension, int populationSize, int generations) {
        this.dimension = dimension;
        this.populationSize = populationSize;
        this.generations = generations;
    }

    public void set_results(double best, double[] solution) {
        this.best = best;
        this.solution = solution.clone();
    }
}
