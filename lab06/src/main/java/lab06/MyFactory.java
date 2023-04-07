package lab06;

import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory;

import java.util.Random;

public class MyFactory extends AbstractCandidateFactory<double[]> {

    private int dimension;
    private static int generated;

    public MyFactory(int dimension) {
        this.dimension = dimension;
        generated = 0;
    }

    public double[] generateRandomCandidate(Random random) {
        double[] solution = new double[dimension];
        // x from -5.0 to 5.0

        // your implementation:

        for (int i = 0; i < dimension; i++) {
            solution[i] = -5.0 + random.nextDouble() * (10.0);
        }
        generated += 1;

        return solution;
    }
}

