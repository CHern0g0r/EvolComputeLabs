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
        List<double[]> new_pop = new ArrayList<double[]>();

        for (double[] item : population) {
            new_pop.add(single(item, random));
        }

        //result population
        return new_pop;
    }

    private double[] single(double[] item, Random random) {
        double[] newitem = new double[item.length];
        double newval;
        for (int j = 0; j < item.length; j++) {
            newval = item[j] + random.nextGaussian();
            if (newval <= 5 && newval >= -5){
                newitem[j] = newval;
            } else {
                newitem[j] = item[j];
            }
            // if (item[j] > 0) {
            //     newval = (5.0 - item[j]) / 2;
            // } else {
            //     newval = (-5.0 + item[j]) / 2;
            // }
            // newitem[j] = newval;
        }
        return newitem;
    }
}
