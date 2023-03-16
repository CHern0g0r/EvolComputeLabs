package lab05;

import org.uncommons.watchmaker.framework.FitnessEvaluator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FitnessFunction implements FitnessEvaluator<Solution> {
    private Task task;

    public FitnessFunction(Task task) {
        this.task = task;
    }

    public double getFitness(Solution solution, List<? extends Solution> list) {
        double fitness = 0.0;
        for (int j = 0; j < task.getDim(); j++) {
            int a = solution.get(j);
            int b = solution.get((j+1) % task.getDim());
            fitness += getDistance(a, b);
        }
        return fitness;
    }

    public boolean isNatural() {
        return false;
    }

    public double getDistance(int a, int b) {
        
        double[] t1 = task.get(a);
        double[] t2 = task.get(b);
        
        return euc(t1[0], t1[1], t2[0], t2[1]);
    }

    public double euc(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
}
