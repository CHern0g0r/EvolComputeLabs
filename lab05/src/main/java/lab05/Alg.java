package lab05;

import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.*;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.selection.RankSelection;
import org.uncommons.watchmaker.framework.selection.RouletteWheelSelection;
import org.uncommons.watchmaker.framework.selection.TournamentSelection;
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

    public static void main(String[] args) {

        int populationSize = 100; // size of population
        int generations = 1000; // number of generations
        int dim = 16;
        Alg alg = new Alg();

        alg.run(
            dim,
            populationSize,
            generations
        );

        System.out.println(alg.best_epoch);
    }

    public Alg() {
        best_epoch = 0;
        best_fit = Double.POSITIVE_INFINITY;
    }

    public void run(int dim,
                    int populationSize,
                    int generations) {


        Random random = new Random(); // random

        FitnessEvaluator<Solution> evaluator = new FitnessFunction(); // Fitness function

        CandidateFactory<Solution> factory = new Factory(dim); // generation of solutions

        ArrayList<EvolutionaryOperator<Solution>> operators = new ArrayList<EvolutionaryOperator<Solution>>();
        operators.add(new Crossover()); // Crossover
        operators.add(new Mutation());
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
                }
                System.out.println("\tBest solution = " + best.toString());
            }
        });

        // TerminationCondition terminate = new GenerationCount(generations);
        TerminationCondition terminate = new TargetFitness(0, false);
        algorithm.evolve(populationSize, 1, terminate);
    }
}
