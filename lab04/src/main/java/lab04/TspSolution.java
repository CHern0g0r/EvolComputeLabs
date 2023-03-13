package lab04;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class TspSolution {
    // any required fields and methods
    public int dim;
    public int[] values;

    public TspSolution(int dim) {
        this.dim = dim;
        Random rand = new Random();
        values = generate(rand);
    }

    public TspSolution(int dim, Random rand) {
        this.dim = dim;
        values = generate(rand);
    }

    public TspSolution(int[] values) {
        dim = values.length;
        this.values = values.clone();
    }

    public int[] generate(Random rand) {
        int[] vals = IntStream.rangeClosed(1, dim).toArray();

        Collections.shuffle(Arrays.asList(vals), rand);
        // return valList.stream().mapToInt(i->i).toArray();
        return vals;
    }
}
