package lab05;

import org.uncommons.watchmaker.framework.FitnessEvaluator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FitnessFunction implements FitnessEvaluator<Solution> {

    public double getFitness(Solution solution, List<? extends Solution> list) {
        double fitness = 0;  // non-attacking pairs of queens

        for (int col = 0; col < solution.dim; col++) {
            fitness += countCrosses(solution, col);
        }
        
        return fitness;
    }

    public int countCrosses(Solution sol, int col) {
        int crosses = 0;
        int cury = sol.get(col);

        for (int j = 0; j < sol.dim; j++) {
            if (j == col) continue;
            int newy = sol.get(j);
            int dif = j - col;
            int top = cury + dif;
            int bot = cury - dif;
            if (newy == top || newy == bot) crosses++;
        }

        return crosses;
    }

    public boolean isNatural() {
        return false;
    }

}
