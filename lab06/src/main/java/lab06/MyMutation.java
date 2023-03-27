package lab06;

import org.uncommons.watchmaker.framework.EvolutionaryOperator;

import java.util.List;
import java.util.Random;

public class MyMutation implements EvolutionaryOperator<double[]> {
    public List<double[]> apply(List<double[]> population, Random random) {
        for (int j = 0; j < population.size(); j++) {
            double[] item = population.get(j);
            double[] newitem = single(item, random);
            population.set(j, newitem);
        }
        return population;
    }

    private double[] single(double[] item, Random random) {
        double c = 1;

        int cnt = item.length / 2;
        cnt = 1;
        int idx;

        for (int j = 0; j < cnt; j++) {
            idx = random.nextInt(item.length);
            c = random.nextDouble();
            item[idx] += random.nextGaussian() * c;
            item[idx] = Math.min(Math.max(item[idx], -5), 5);
        }

        return item;
    }
}
