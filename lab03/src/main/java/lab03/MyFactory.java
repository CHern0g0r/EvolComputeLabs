package lab03;

import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory;

import java.util.Random;

public class MyFactory extends AbstractCandidateFactory<double[]> {

    private int dimension;

    public MyFactory(int dimension) {
        this.dimension = dimension;
    }

    public double[] generateRandomCandidate(Random random) {
        double[] solution = new double[dimension];
        // x from -5.0 to 5.0

        // your implementation:
        Random r = new Random();

        for (int i = 0; i < dimension; i++) {
            // solution[i] = r.nextGaussian() * 5;
            // if (solution[i] > 5) solution[i] = 5.0;
            // if (solution[i] < -5) solution[i] = -5.0;
            solution[i] = -5.0 + r.nextDouble() * (10.0);
            // solution[i] = 0.0;
        }

        return solution;
    }
}

