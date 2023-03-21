package lab05;

import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import org.uncommons.maths.number.ConstantGenerator;
import org.uncommons.maths.number.NumberGenerator;
import org.uncommons.maths.random.PoissonGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mutation implements EvolutionaryOperator<Solution> {
    private final NumberGenerator<Integer> CountGen;
    private final NumberGenerator<Integer> AmountGen;

    public Mutation(NumberGenerator<Integer> countGen, NumberGenerator<Integer> amountGen) {
        CountGen = countGen;
        AmountGen = amountGen;
    }

    public Mutation() {
        Random rand = new Random();
        CountGen = new PoissonGenerator(1.5, rand);
        AmountGen = new PoissonGenerator(1.5, rand);
    }

    public Mutation(Random rand) {
        this(new PoissonGenerator(1.5, rand), new PoissonGenerator(1.5, rand));
    }

    public Mutation(int mutationCount, int mutationAmount)
    {
        this(new ConstantGenerator<Integer>(mutationCount),
             new ConstantGenerator<Integer>(mutationAmount));
    }

    public List<Solution> apply(List<Solution> population, Random random) {
        // your implementation:
        List<Solution> result = new ArrayList<Solution>(population.size());
        for (Solution candidate : population) {
            Solution newCandidate = new Solution(candidate);
            int mutationCount = Math.abs(CountGen.nextValue());
            for (int i = 0; i < mutationCount; i++) {
                int fromIndex = random.nextInt(newCandidate.dim);
                // int mutationAmount = AmountGen.nextValue();
                // int toIndex = (fromIndex + mutationAmount) % newCandidate.dim;
                // if (toIndex < 0)
                // {
                //     toIndex += newCandidate.dim;
                // }
                int toIndex;
                do {
                    toIndex = random.nextInt(newCandidate.dim);
                } while (toIndex == fromIndex);
                
                newCandidate.swap(fromIndex, toIndex);
            }
            // System.out.println(candidate.toString() + " " + newCandidate.toString());
            result.add(newCandidate);
        }

        return population;
    }
}
