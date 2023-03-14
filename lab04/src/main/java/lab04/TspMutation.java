package lab04;

import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import org.uncommons.maths.number.ConstantGenerator;
import org.uncommons.maths.number.NumberGenerator;
import org.uncommons.maths.random.PoissonGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TspMutation implements EvolutionaryOperator<TspSolution> {
    private final NumberGenerator<Integer> CountGen;
    private final NumberGenerator<Integer> AmountGen;

    public TspMutation(NumberGenerator<Integer> countGen, NumberGenerator<Integer> amountGen) {
        CountGen = countGen;
        AmountGen = amountGen;
    }

    public TspMutation() {
        Random rand = new Random();
        CountGen = new PoissonGenerator(1.5, rand);
        AmountGen = new PoissonGenerator(1.5, rand);
    }

    public TspMutation(Random rand) {
        this(new PoissonGenerator(1.5, rand), new PoissonGenerator(1.5, rand));
    }

    public TspMutation(int mutationCount, int mutationAmount)
    {
        this(new ConstantGenerator<Integer>(mutationCount),
             new ConstantGenerator<Integer>(mutationAmount));
    }

    public List<TspSolution> apply(List<TspSolution> population, Random random) {
        // your implementation:
        List<TspSolution> result = new ArrayList<TspSolution>(population.size());
        for (TspSolution candidate : population) {
            TspSolution newCandidate = new TspSolution(candidate);
            int mutationCount = Math.abs(CountGen.nextValue());
            for (int i = 0; i < mutationCount; i++) {
                int fromIndex = random.nextInt(newCandidate.dim);
                int mutationAmount = AmountGen.nextValue();
                int toIndex = (fromIndex + mutationAmount) % newCandidate.dim;
                if (toIndex < 0)
                {
                    toIndex += newCandidate.dim;
                }

                newCandidate.swap(fromIndex, toIndex);
            }
            result.add(newCandidate);
        }

        return population;
    }
}
