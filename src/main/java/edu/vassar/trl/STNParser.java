package edu.vassar.trl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * A parser that reads from STN files.
 * @author Mark Infante
 */
public class STNParser {
    
    private HashMap<File,String> echoMap; // A mapping of file to echo string

    /**
     * Creates new STN parser object.
     */
    public STNParser(){
        echoMap = new HashMap<File,String>();
    }

    /**
     * Takes in a STN file, parses it, and returns an STN.
     * @param file A STN file.
     * @return A STN object.
     * @throws Exception If file is empty.
     */
    public STN parseFile(File file) throws Exception{
        Scanner fileScanner = null;
        String echo = "";
        String ts = ""; // Temp string
        Integer lStart = 0;   // Local start node 
        Integer lEnd = 0;     // Local end node
        Double lWeight = 0.0; // Local edge weight
        Integer numTimePoints = 0; // Number of time points from input file
        List<String> timePointNames = new ArrayList<String>();  // Time point names from input file
        List<String> pseudoEdge = new ArrayList<String>();  // An edge from input file in the format of string list
        STN network = new STN();

        try {  // Try to read values from input file
            fileScanner = new Scanner(file);
            if (fileScanner.hasNext()){
                fileScanner.nextLine();
                ts = fileScanner.nextLine().trim(); // Get network type 
                TemporalNetworks typ = TemporalNetworks.valueOf(ts);
                echo += "Type of temporal network: " + typ.toString() + "\n";
            } else {
                throw new Exception("File is empty."); 
            }
            fileScanner.nextLine();
            ts = fileScanner.nextLine();  // Get num time points
            numTimePoints = Integer.parseInt(ts);
            echo += "Number of time points: " + ts + "\n";
            network.setNumTimePoints(numTimePoints);
            fileScanner.nextLine();
            ts = fileScanner.nextLine();
            echo += "Number of edges: " + ts + "\n";
            fileScanner.nextLine();
            ts = fileScanner.nextLine();  // Get time point names
            timePointNames = Arrays.asList(ts.split(" "));
            echo += "Time point names: \n" + ts + "\n";
            network.setTimePointNames(timePointNames);
            fileScanner.nextLine();
            network.init();
            echo += "Edges: \n";
            while (fileScanner.hasNext()){   // Get constraints
                ts = fileScanner.nextLine();
                pseudoEdge = Arrays.asList(ts.split(" "));
                lStart = timePointNames.indexOf(pseudoEdge.get(0));
                lEnd = timePointNames.indexOf(pseudoEdge.get(2));
                lWeight = Double.parseDouble(pseudoEdge.get(1));
                echo += String.format("Start: %d. End: %d. Weight: %f.\n", lStart, lEnd, lWeight);
                network.addEdge(new Edge(lStart,lEnd, lWeight));
            }
            echo += "Finished network creation.\n";
        } catch (NullPointerException np){
            System.err.println(np.getCause());
        } catch (FileNotFoundException fnf){
            System.err.println("File not found, make sure to input a valid path.");
        } catch (Exception e){
            System.err.println(e.getCause());
        } finally {
            if (fileScanner != null){ fileScanner.close(); }     
        }

        echoMap.put(file, echo); // Put echo string in map
        return network;  // Return new network
    }

    /**
     * Parses a directory and returns a list of STN objects.
     * @param dir A directory.
     * @return A list of STN objects.
     * @throws Exception If file is empty.
     */
    public ArrayList<STN> parseDirectory(File dir) throws Exception{
        File[] files = null;
        ArrayList<STN> output = new ArrayList<STN>();
        STN tNet = null;

        if (dir.isDirectory()){
            files = dir.listFiles();
        } else {
            throw new Exception("Not a parseable directory.");  
        } 

        for (int i = 0; i < files.length; i++){
            tNet = parseFile(files[i]);
            output.add(tNet);
        }

        return output;
    }

    /**
     * Echos a file after its been parsed.
     * @param file A File to be echoed.
     */
    public void echoFile(File file){
        //TODO: Wrap in try catch, file may not have been parsed.
        System.out.println(echoMap.get(file));
    }

    
}
