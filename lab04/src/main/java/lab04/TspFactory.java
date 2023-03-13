package lab04;

import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory;

import java.util.Random;

public class TspFactory extends AbstractCandidateFactory<TspSolution> {

    private int dimension;

    public TspFactory() {
        dimension = 10;
    }

    public TspFactory(int dimension) {
        this.dimension = dimension;
    }

    public TspSolution generateRandomCandidate(Random random) {
        TspSolution solution = new TspSolution(dimension);
        //your implementation
        return solution;
    }
}

