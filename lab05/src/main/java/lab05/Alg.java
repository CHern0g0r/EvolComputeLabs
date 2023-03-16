package lab05;

import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.*;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.selection.RankSelection;
import org.uncommons.watchmaker.framework.selection.RouletteWheelSelection;
import org.uncommons.watchmaker.framework.selection.TournamentSelection;
import org.uncommons.watchmaker.framework.termination.GenerationCount;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Alg {

    double best_tour;
    int best_epoch;

    public static void main(String[] args) {

        int populationSize = 100; // size of population
        int generations = 10000; // number of generations
        String problem = "xqf131"; // name of problem or path to input file
        Alg alg = new Alg();

        alg.run(
            populationSize,
            generations,
            problem
        );
    }

    public Alg() {
        best_epoch = 0;
        best_tour = Double.POSITIVE_INFINITY;
    }

    public void run(int populationSize,
                    int generations,
                    String problem) {


        Random random = new Random(); // random

        Task task = new Task(problem);

        FitnessEvaluator<Solution> evaluator = new FitnessFunction(task); // Fitness function

        CandidateFactory<Solution> factory = new Factory(task.getDim()); // generation of solutions

        ArrayList<EvolutionaryOperator<Solution>> operators = new ArrayList<EvolutionaryOperator<Solution>>();
        operators.add(new Crossover()); // Crossover
        operators.add(new Mutation()); // Mutation
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
                if (bestFit < best_tour) {
                    best_tour = bestFit;
                    best_epoch = populationData.getGenerationNumber();
                }
                // System.out.println("\tBest solution = " + best.toString());
            }
        });

        TerminationCondition terminate = new GenerationCount(generations);
        algorithm.evolve(populationSize, 1, terminate);
    }
}
