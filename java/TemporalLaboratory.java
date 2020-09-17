package java;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/* TemporalLaboratory.java
 * Author: Mark Infante
 *      Main class for project.
 *      Creates specified Temporal Network from resource file and prompts user for action.
*/

public class TemporalLaboratory{

    private TemporalNetwork network;

    public TemporalLaboratory(TemporalNetworks networkType){
        switch (networkType) {
            case STN:
                network = new STN(); 
            default:  
        }
    }

    public static void main(String[] args){
        TemporalLaboratory lab = null; 
        File file = null;   // Resource file containing type of network and constraints
        Scanner scanner = null; // Scanner for file     
        String ts; // Temporary string for reading from file 
        String[] tsA; // Temporary string array
        Integer lStart = 0;   // Local start node 
        Integer lEnd = 0;     // Local end node
        Double lWeight = 0.0; // Local edge weight

        if (args.length != 1){
            System.out.println("Temporal laboratory should take in a resource file of graph constraints.");
            return;
        }
        try {
            file = new File("/" + args[0]);
            scanner = new Scanner(file);
            if (scanner.hasNext()){
                ts = scanner.nextLine(); // Get network type 
                switch (ts) {   
                    case "STN": lab = new TemporalLaboratory(TemporalNetworks.STN);
                    default: 
                        System.err.println("First argument in file should be a valid TN type.");
                } 
            } else {
                System.out.println("File is empty.");
                return; 
            }
            while (scanner.hasNext()){   // Get constraints
                ts = scanner.nextLine();
                tsA = ts.split("[ <=]+");
                ts = tsA[0];
                lStart = Integer.parseInt(ts.substring(1));
                ts = tsA[2];
                lEnd = Integer.parseInt(ts.substring(1));
                ts = tsA[3];
                lWeight = Double.parseDouble(ts.substring(1));
                lab.network.addEdge(new Edge(lStart,lWeight,lEnd));
            }
            System.out.println("Finished network creation.");
        } catch (NullPointerException np){
            System.err.println("Path to file cannot be null.");
        } catch (FileNotFoundException fnf){
            System.err.println("File not found, make sure to input a valid path.");
        } catch (Exception e){
            System.err.println(e);
        } finally {
            if (scanner != null){ scanner.close(); }     
        }
        

    }

}
