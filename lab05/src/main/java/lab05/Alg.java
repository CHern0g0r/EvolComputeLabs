package lab05;

import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.*;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.selection.RankSelection;
import org.uncommons.watchmaker.framework.selection.RouletteWheelSelection;
import org.uncommons.watchmaker.framework.selection.TournamentSelection;
import org.uncommons.watchmaker.framework.termination.Stagnation;
import org.uncommons.watchmaker.framework.termination.GenerationCount;
import org.uncommons.watchmaker.framework.termination.TargetFitness;
import org.uncommons.maths.number.ConstantGenerator;
import org.uncommons.maths.number.NumberGenerator;
import org.uncommons.maths.random.PoissonGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Alg {

    double best_fit;
    int best_epoch;
    Solution best_solution;
    int fitness_eval;

    public static void main(String[] args) {

        int populationSize = 300; // size of population
        int dim = 4;
        Alg alg = new Alg();

        alg.run(
            dim,
            populationSize
        );
    }

    public Alg() {
        best_epoch = 0;
        best_fit = Double.POSITIVE_INFINITY;
    }

    public void run(int dim,
                    int populationSize) {


        Random random = new Random(); // random

        // FitnessEvaluator<Solution> evaluator = new FitnessFunction(); // Fitness function
        FitnessFunction evaluator = new FitnessFunction(); // Fitness function

        CandidateFactory<Solution> factory = new Factory(dim); // generation of solutions

        ArrayList<EvolutionaryOperator<Solution>> operators = new ArrayList<EvolutionaryOperator<Solution>>();
        operators.add(new Crossover()); // Crossover
        operators.add(new Mutation(new Ngen(), new Ngen()));
        // operators.add(new Mutation(new ConstantGenerator<Integer>(1), new PoissonGenerator(1.5, random))); // Mutation
        EvolutionPipeline<Solution> pipeline = new EvolutionPipeline<Solution>(operators);

        // Selection operator
        // SelectionStrategy<Object> selection = new RouletteWheelSelection();
        // SelectionStrategy<Object> selection = new TournamentSelection(new Probability(0.7));
        SelectionStrategy<Object> selection = new RankSelection();

        // EvolutionEngine<Solution> algorithm = new SteadyStateEvolutionEngine<Solution>(
        //         factory, pipeline, evaluator, selection, populationSize, false, random);
            
        EvolutionEngine<Solution> algorithm = new GenerationalEvolutionEngine<Solution>(
            factory,
            pipeline,
            evaluator,
            selection,
            random
        );

        algorithm.addEvolutionObserver(new EvolutionObserver() {
            public void populationUpdate(PopulationData populationData) {
                double bestFit = populationData.getBestCandidateFitness();
                System.out.println("Generation " + populationData.getGenerationNumber() + ": " + bestFit);
                Solution best = (Solution)populationData.getBestCandidate();
                if (bestFit < best_fit) {
                    best_fit = bestFit;
                    best_epoch = populationData.getGenerationNumber();
                    best_solution = best;
                }
                // System.out.println("\tBest solution = " + best.toString());
            }
        });

        // TerminationCondition terminate = new GenerationCount(generations);
        // TerminationCondition terminate = new TargetFitness(0, false);
        TerminationCondition terminate = new Stagnation(1000, false);
        algorithm.evolve(populationSize, 1, terminate);
        fitness_eval = evaluator.counter;
    }

    public void run(int dim,
                    int populationSize,
                    int mutations) {


        Random random = new Random(); // random

        // FitnessEvaluator<Solution> evaluator = new FitnessFunction(); // Fitness function
        FitnessFunction evaluator = new FitnessFunction(); // Fitness function

        CandidateFactory<Solution> factory = new Factory(dim); // generation of solutions

        ArrayList<EvolutionaryOperator<Solution>> operators = new ArrayList<EvolutionaryOperator<Solution>>();
        operators.add(new Crossover()); // Crossover
        operators.add(new Mutation(mutations, mutations));
        // operators.add(new Mutation(new ConstantGenerator<Integer>(1), new PoissonGenerator(1.5, random))); // Mutation
        EvolutionPipeline<Solution> pipeline = new EvolutionPipeline<Solution>(operators);

        // Selection operator
        // SelectionStrategy<Object> selection = new RouletteWheelSelection();
        // SelectionStrategy<Object> selection = new TournamentSelection(new Probability(0.7));
        SelectionStrategy<Object> selection = new RankSelection();

        // EvolutionEngine<Solution> algorithm = new SteadyStateEvolutionEngine<Solution>(
        //         factory, pipeline, evaluator, selection, populationSize, false, random);
            
        EvolutionEngine<Solution> algorithm = new GenerationalEvolutionEngine<Solution>(
            factory,
            pipeline,
            evaluator,
            selection,
            random
        );

        algorithm.addEvolutionObserver(new EvolutionObserver() {
            public void populationUpdate(PopulationData populationData) {
                double bestFit = populationData.getBestCandidateFitness();
                System.out.println("Generation " + populationData.getGenerationNumber() + ": " + bestFit);
                Solution best = (Solution)populationData.getBestCandidate();
                if (bestFit < best_fit) {
                    best_fit = bestFit;
                    best_epoch = populationData.getGenerationNumber();
                    best_solution = best;
                }
                // System.out.println("\tBest solution = " + best.toString());
            }
        });

        // TerminationCondition terminate = new GenerationCount(generations);
        // TerminationCondition terminate = new TargetFitness(0, false);
        TerminationCondition terminate = new Stagnation(300, false);
        algorithm.evolve(populationSize, 1, terminate);
        fitness_eval = evaluator.counter;
    }
}

class Ngen implements NumberGenerator<Integer>{
    double p1, p2;
    Random rand;

    Ngen(double p, double q) {
        p1 = p;
        p2 = q;
        rand = new Random();
    }

    Ngen() {
        this(0.8, 0.4);
    }

    @Override
    public Integer nextValue() {
        double p = rand.nextDouble();
        if (p > p1) return 0;
        p = rand.nextDouble();
        if (p > p2) return 1;
        return 2;
    }

}
