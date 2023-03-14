package lab04;

import org.uncommons.watchmaker.framework.FitnessEvaluator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TspFitnessFunction implements FitnessEvaluator<TspSolution> {

    private String PROBLEMS = "./EvolComputeLabs/lab04/data/vlsi/";
    // private String PROBLEMS = "../../../../data/vlsi/";
    private String path;
    private ArrayList<double[]> graph;

    public TspFitnessFunction(String problem) {
        path = String.format(
            "%s%s.tsp",
            PROBLEMS,
            problem
        );

        graph = new ArrayList<double[]>();
        File f = new File(path);
        
        System.out.println(path);
        System.out.println(System.getProperty("user.dir"));
        System.out.println(f.getAbsolutePath());
        try {
            Scanner sc = new Scanner(f);
            String next = null;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if ("NODE_COORD_SECTION".equals(line)) {
                    System.out.println("FOUND");
                    while (sc.hasNextLine()) {
                        next = sc.nextLine();
                        double[] vals = Arrays.stream(next.trim().split(" ")).skip(1).mapToDouble(Double::parseDouble).toArray();
                        graph.add(vals);
                    }
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Wrong problem name");
        }
        System.out.println(graph.size());
    }

    public double getFitness(TspSolution solution, List<? extends TspSolution> list) {
        double fitness = 0.0;
        for (int j = 0; j < solution.dim; j++) {
            fitness += getDistance(j, (j+1) % solution.dim);
        }
        return fitness;
    }

    public boolean isNatural() {
        return false;
    }

    public double getDistance(int a, int b) {
        b -= 1;
        a -= 1;
        
        double[] t1 = graph.get(a);
        double[] t2 = graph.get(b);
        
        return euc(t1[0], t1[1], t2[0], t2[1]);
    }

    public double euc(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
}
