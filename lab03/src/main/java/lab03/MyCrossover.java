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
        for (int j = 0; j < child.length; j++) {
            child[j] = coord_mate(p1[j], p2[j]);
        }
        children.add(child);
//        children.add(p1);
//        children.add(p2);
        return children;
    }

    private double coord_mate(double p1, double p2) {
        double result;
        // result = (p1 + p2) / 2.0;

        Random r = new Random();
        if (r.nextBoolean()) {
            result = p1;
        } else {
            result = p2;
        }

        return result;
    }
}
