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
            //DPC dpc = new DPC(tSTN);
            //display += String.format("\nIs consistent (DPC): %s\n\n", String.valueOf(dpc.isConsistent()));
            //display += String.format("New network: %s\n\n", tSTN.toString());
        } catch (Exception e){
            System.err.println("Failed: "+ e);
        }
        display += "\n========================================================\n\n";

        System.out.println(display);
    }

    public void testJohnsons(STN tSTN){
        String display = "";

        display += String.format("Printing original network: \n%s\n\n", tSTN.toString());
        try {
            FloydWarshall fw = new FloydWarshall(tSTN);
            tSTN.setDistanceMatrix(fw.generateMatrix());
            display += "FW distance matrix: \n" + tSTN.getDistanceMatrix().toString();
            Johnsons jns = new Johnsons(tSTN);
            tSTN.setDistanceMatrix(jns.johnsonsAlgorithm());
            display += "Johnson's Distance matrix: \n" + tSTN.getDistanceMatrix().toString();
            System.out.println("sydftyg");
        } catch (Exception e){
            System.err.println("Failed: "+ e);
        }
        display += "\n========================================================\n\n";

        System.out.println(display);
    }

    public void testNaive(Edge edge, STN tSTN){
        String display = "";
        try {
            FloydWarshall fw = new FloydWarshall(tSTN);
            tSTN.setDistanceMatrix(fw.generateMatrix());
            display += "Old DM:" + tSTN.getDistanceMatrix();
            NaiveAlgorithm na = new NaiveAlgorithm(tSTN);
            tSTN.setDistanceMatrix(na.updateDistanceMatrix(edge, tSTN.getDistanceMatrix()));
            //FloydWarshall fw2 = new FloydWarshall(tSTN);
            //display += "new fw" + fw2.generateMatrix();
            display += "New distance matrix: \n" + tSTN.getDistanceMatrix().toString() + "\n";
        } catch (Exception e){
            System.err.println("Failed: "+ e);
        }
        display += "\n========================================================\n\n";

        System.out.println(display);
    }

    public void testConsistencyChecker(STN tSTN){
        String display = "";
        display += "Original graph" + tSTN.toString() + "bellford\n";
        
        try {
            BellmanFord bf = new BellmanFord(tSTN);
            display += "Bellman ford outcome:" + bf.generate_BF(0);
        } catch (Exception e){
            System.err.println("Failed: "+ e);
        }
        display += "\n========================================================\n\n";

        System.out.println(display);
    }

    public void testFwdBackPropagation(Edge edge, STN stn){
        String display = "Current STN:\n";
        display += stn.toString() + "\n";
        
        try {
            
            FloydWarshall fw = new FloydWarshall(stn);
            stn.setDistanceMatrix(fw.generateMatrix());
            display += "Old distance matrix: \n" + stn.getDistanceMatrix().toString();
            TwoPhasePropagation tpp = new TwoPhasePropagation(stn);
            DistanceMatrix outputMatrix = tpp.twoPhasePropagate(edge);
            if (outputMatrix != null){
                stn.setDistanceMatrix(outputMatrix);
            }
            display += "New distance matrix: \n" + stn.getDistanceMatrix().toString() + "\n";
        } catch (Exception e){
            System.err.println("Failed: "+ e);
        }
        display += "\n========================================================\n\n";

        System.out.println(display);
    }


    
}
