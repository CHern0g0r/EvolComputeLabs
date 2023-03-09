package lab03;

import org.uncommons.watchmaker.framework.operators.AbstractCrossover;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyCrossover extends AbstractCrossover<double[]> {
    protected MyCrossover() {
        super(1);
    }

    protected List<double[]> mate(double[] p1, double[] p2, int i, Random random) {
        ArrayList<double[]> children = new ArrayList<double[]>();

        // your implementation:

        double[] child = new double[p1.length];
        int border = random.nextInt(p1.length);
        for (int j = 0; j < child.length; j++) {
            // child[j] = coord_mate(p1[j], p2[j], random);
            if (j < border) child[j] = p1[j];
            else child[j] = p2[j];
        }
        children.add(child);
        return children;
    }

    private double coord_mate(double p1, double p2, Random random) {
        double result;
        result = (p1 + p2) / 2.0;

        if (random.nextBoolean()) {
            if (random.nextBoolean()) {
                result = p1;
            } else {
                result = p2;
            }
        }

        // if (r.nextBoolean()) {
        //     result = p1;
        // } else {
        //     result = p2;
        // }

        return result;
    }
}
