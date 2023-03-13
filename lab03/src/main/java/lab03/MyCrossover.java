package lab03;

import org.uncommons.watchmaker.framework.operators.AbstractCrossover;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyCrossover extends AbstractCrossover<double[]> {
    protected MyCrossover() {
        super(1);
    }

    protected List<double[]> mate(double[] p1, double[] p2, int ncp, Random random) {
        ArrayList<double[]> children = new ArrayList<double[]>();

        // your implementation:

        double[] c1 = p1.clone();
        double[] c2 = p2.clone();
        double x1, x2, val, l, a = 3.4;
        l = random.nextDouble();

        for (int j = 0; j < c1.length; j++) {
            x1 = Math.min(p2[j], p1[j]);
            x2 = Math.max(p2[j], p1[j]);
            val = 1 - Math.pow(0.5, (x2 - x1) / 10.0);
            c1[j] = val * (x1 + x2) + Math.pow(a, l) * Math.exp(1 - l) * (1 - val) * (x2 - x1);
            c2[j] = (1 - val) * (x1 + x2) - Math.pow(a, (1 - l)) * Math.exp(l - 1.5 * l) * val * (x2 - x1);
            
            // if (random.nextBoolean()) {
            //     c1[j] = l * p1[j] + (1 - l) * p2[j];
            //     c2[j] = l * p2[j] + (1 - l) * p1[j];
            // } else {
            //     c1[j] = p1[j];
            //     c2[j] = p2[j];
            // }
            c1[j] = Math.min(Math.max(c1[j], -5), 5);
            c2[j] = Math.min(Math.max(c2[j], -5), 5);
        }

        // for (int i = 0; i < p1.length; i++) {
        //         x1 = Math.min(p1[i], p2[i]);
        //         x2 = Math.max(p1[i], p2[i]);
        //         double lb = x1 - a * (x2 - x1);
        //         double rb = x2 + a * (x2 - x1);

        //         c1[i] = random.nextDouble() * (rb - lb) - lb;
        //         c2[i] = random.nextDouble() * (rb - lb) - lb;

        //         c1[i] = Math.min(Math.max(-5, p1[i]), 5);
        //         c2[i] = Math.min(Math.max(-5, p2[i]), 5);
        // }

        children.add(c1);
        children.add(c2);
        return children;
    }

    private double coord_mate(double p1, double p2, Random random, double l) {
        double result = p1;

        if (random.nextBoolean()) {
            if (random.nextBoolean()) {
                result = l * p1 + (1 - l) * p2;
            } else {
                result = random.nextDouble() * 10 - 5;
            }
        }

        return result;
    }
}
