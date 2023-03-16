package lab04;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Task {
    private String PROBLEMS = "./EvolComputeLabs/lab04/data/vlsi/";
    private String path;
    private ArrayList<double[]> graph;
    private int dim;

    public Task(String problem) {
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
                    while (sc.hasNextLine()) {
                        next = sc.nextLine();
                        if ("EOF".equals(next)) {
                            break;
                        }
                        double[] vals = Arrays.stream(next.trim().split(" ")).skip(1).mapToDouble(Double::parseDouble).toArray();
                        graph.add(vals);
                    }
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Wrong problem name");
        }
        dim = graph.size();
    }

    public int getDim() {
        return dim;
    }

    public double[] get(int i) {
        return graph.get(i);
    }
}
