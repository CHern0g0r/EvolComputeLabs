package lab05;

import org.uncommons.watchmaker.framework.operators.AbstractCrossover;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Crossover extends AbstractCrossover<Solution> {
    protected Crossover() {
        super(1);
    }

    protected List<Solution> mate(Solution p1,
                                     Solution p2,
                                     int numberOfCrossoverPoints,
                                     Random random) {
        ArrayList<Solution> children = new ArrayList<Solution>();
        // your implementation:

        List<Integer> valList = IntStream.range(0, p1.dim).boxed().collect(Collectors.toList());
        Collections.shuffle(valList);

        int[] ints = valList.stream().mapToInt(i->i).toArray();

        // int[] ints = IntStream.rangeClosed(1, p1.dim).toArray();
        int[] c1 = new int[p1.dim];
        int[] c2 = new int[p1.dim];

        // Collections.shuffle(Arrays.asList(ints), random);
        int l = Math.min(ints[0], ints[1]);
        int r = Math.max(ints[0], ints[1]);
        HashSet<Integer> m1 = new HashSet<>();
        HashSet<Integer> m2 = new HashSet<>();
        
        for (int j = 0; j < p1.dim; j++) {
            if (l <= j && r > j) {
                c1[j] = p1.get(j);
                c2[j] = p2.get(j);
                m1.add(c1[j]);
                m2.add(c2[j]);
            }
        }

        int i1 = r, i2 = r;
        int idx;
        for (int j = 0; j < p1.dim; j++) {
            idx = (j + r) % p1.dim;
            int val1 = p1.get(idx);
            int val2 = p2.get(idx);
            if (!m1.contains(val2)) {
                c1[i1] = val2;
                i1 = (i1 + 1) % p1.dim;
            }
            if (!m2.contains(val1)) {
                c2[i2] = val1;
                i2 = (i2 + 1) % p1.dim;
            }
        }
        children.add(new Solution(c1));
        children.add(new Solution(c2));

        return children;
    }
}
