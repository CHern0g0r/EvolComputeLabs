package lab04;

public class Experiment {

    public static void main(String[] args) {

        int nexp = 10;
        int populationSize = 500; // size of population
        int generations = 100000; // number of generations
        String problem = "rbx711"; // name of problem or path to input file

        Pair mean_results = new Pair();
        for (int j = 0; j < nexp; j++) {
            Pair result = run_single(populationSize, generations, problem);
            mean_results.epoch += result.epoch;
            mean_results.tour += result.tour;
        }
        mean_results.epoch /= nexp;
        mean_results.tour /= nexp;

        report(problem, mean_results, populationSize, generations);
    }

    public static Pair run_single(int populationSize,
                                  int generations,
                                  String problem) {
        TspAlg tsp = new TspAlg();

        tsp.run(
            populationSize,
            generations,
            problem
        );

        return new Pair(tsp.best_epoch, tsp.best_tour);
    }

    public static void report(String problem,
                              Pair res,
                              int popsize,
                              int gens) {

        Integer size = Integer.parseInt(problem.replaceAll("[\\D]", ""));
        String template = "|%s|%d|%d ; %d|%f|%f||";
        String result = String.format(
            template,
            problem,
            size,
            popsize,
            gens,
            res.tour,
            res.epoch
        );

        System.out.println(result);
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
