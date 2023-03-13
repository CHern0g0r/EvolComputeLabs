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
    private ArrayList<String[]> graph;

    public TspFitnessFunction(String problem) {
        path = String.format(
            "%s%s.tsp",
            PROBLEMS,
            problem
        );

        graph = new ArrayList<String[]>();
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
                        graph.add(next.trim().split(" "));
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
        return 0.0;
    }

    public boolean isNatural() {
        return false;
    }
}
