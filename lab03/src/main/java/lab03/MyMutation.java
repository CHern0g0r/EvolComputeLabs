package lab03;

import org.uncommons.watchmaker.framework.EvolutionaryOperator;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class MyMutation implements EvolutionaryOperator<double[]> {
    public List<double[]> apply(List<double[]> population, Random random) {
        // initial population
        // need to change individuals, but not their number!

        // your implementation:
        // List<double[]> new_pop = new ArrayList<double[]>();

        // for (double[] item : population) {
        //     new_pop.add(single(item, random));
        // }

        // //result population
        // return new_pop;
        for (int j = 0; j < population.size(); j++) {
            double[] item = population.get(j);
            double[] newitem = single(item, random);
            // double[] newitem = new double[item.length];
            // int cnt = random.nextInt(item.length);
            // int idx;
            // for (int k = 0; k < cnt; k++) {
            //     newitem[idx] = random.nextDouble() * 5
            // }
            population.set(j, newitem);
        }
        return population;
    }

    private double[] single(double[] item, Random random) {
        double c = 1;

        int cnt = item.length / 2;
        // cnt = random.nextInt(item.length);
        cnt = 1;
        int idx;

        for (int j = 0; j < cnt; j++) {
            idx = random.nextInt(item.length);
            c = random.nextDouble();
            item[idx] += random.nextGaussian() * c;
            // newitem[idx] = random.nextDouble() * 10.0 - 5;
            item[idx] = Math.min(Math.max(item[idx], -5), 5);
        }

        return item;
    }
}
