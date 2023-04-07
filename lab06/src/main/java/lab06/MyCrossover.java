package lab06;

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
            
            c1[j] = Math.min(Math.max(c1[j], -5), 5);
            c2[j] = Math.min(Math.max(c2[j], -5), 5);
        }

        children.add(c1);
        children.add(c2);
        return children;
    }
}
