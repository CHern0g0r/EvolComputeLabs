package lab03;

import lab03.MyAlg;
import lab03.ExpSetup;

public class RunTests {

    public static void main(String[] args) {

        ExpSetup e = new ExpSetup();

        single_test(e);
    }

    public static void single_test(ExpSetup e) {
        MyAlg alg = new MyAlg();
        alg.algo(e.dimension, e.populationSize, e.generations);
    }
}
