package lab04;

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

public class TspAlg {

    double best_tour;
    int best_epoch;

    public static void main(String[] args) {

        int populationSize = 100; // size of population
        int generations = 10000; // number of generations
        String problem = "xqf131"; // name of problem or path to input file
        TspAlg tsp = new TspAlg();

        tsp.run(
            populationSize,
            generations,
            problem
        );
    }

    public TspAlg() {
        best_epoch = 0;
        best_tour = Double.POSITIVE_INFINITY;
    }

    public void run(int populationSize,
                    int generations,
                    String problem) {


        Random random = new Random(); // random

        Task task = new Task(problem);

        FitnessEvaluator<TspSolution> evaluator = new TspFitnessFunction(task); // Fitness function

        CandidateFactory<TspSolution> factory = new TspFactory(task.getDim()); // generation of solutions

        ArrayList<EvolutionaryOperator<TspSolution>> operators = new ArrayList<EvolutionaryOperator<TspSolution>>();
        operators.add(new TspCrossover()); // Crossover
        operators.add(new TspMutation()); // Mutation
        EvolutionPipeline<TspSolution> pipeline = new EvolutionPipeline<TspSolution>(operators);

        // Selection operator
        // SelectionStrategy<Object> selection = new RouletteWheelSelection();
        // SelectionStrategy<Object> selection = new TournamentSelection(new Probability(0.7));
        SelectionStrategy<Object> selection = new RankSelection();

        // EvolutionEngine<TspSolution> algorithm = new SteadyStateEvolutionEngine<TspSolution>(
        //         factory, pipeline, evaluator, selection, populationSize, false, random);
            
        EvolutionEngine<TspSolution> algorithm = new GenerationalEvolutionEngine<TspSolution>(
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
                TspSolution best = (TspSolution)populationData.getBestCandidate();
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
