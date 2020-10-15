package src;

import java.util.List;
import java.util.ArrayList;
import java.io.File;

/**
 * A Test Suite for testing STN's.  
 * @author Mark Infante
 */
public class TestSuite {

    public TestSuite() {}

    /**
     * Runs default network algs and prints output to the screen
     * for manual testing. 
     * @param tSTN
     */
    public void testSTN(STN tSTN){
        String display = "";

        display += String.format("Printing original network: \n%s\n\n", tSTN.toString());
        display += String.format("Successors List: %s \n\n", tSTN.getSuccs().toString());
        display += String.format("Predecessors List: %s \n\n", tSTN.getPreds().toString());
        try {
            FloydWarshall fw = new FloydWarshall(tSTN);
            tSTN.setDistanceMatrix(fw.generateMatrix());
            display += "Original distance matrix: \n" + tSTN.getDistanceMatrix().toString();
            DPC dpc = new DPC(tSTN);
            display += String.format("\nIs consistent (DPC): %s\n\n", String.valueOf(dpc.isConsistent()));
            display += String.format("New network: %s\n\n", tSTN.toString());
        } catch (Exception e){
            System.err.println("Failed: "+ e);
        }
        display += "\n========================================================\n\n";

        System.out.println(display);
    }

    public void testIncrementor(Edge edge, STN tSTN){
        String display = "";
        display += tSTN.toString() + "\n";
        
        try {
            FloydWarshall fw = new FloydWarshall(tSTN);
            tSTN.setDistanceMatrix(fw.generateMatrix());
            display += "Old distance matrix: \n" + tSTN.getDistanceMatrix().toString();
            NaiveAlgorithm na = new NaiveAlgorithm(tSTN);
            tSTN.setDistanceMatrix(na.updateDistanceMatrix(edge, tSTN.getDistanceMatrix()));
            display += "New distance matrix: \n" + tSTN.getDistanceMatrix().toString() + "\n";
        } catch (Exception e){
            System.err.println("Failed: "+ e);
        }
        display += "\n========================================================\n\n";

        System.out.println(display);
    }
    
}
