package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class STNParser {
    
    private File file;
    private File[] files; 

    /**
     * Creates new STN parser object.
     */
    public STNParser(){}

    /**
     * Takes in a STN file, parses it, and returns an STN.
     * @param file A STN file.
     * @return A STN.
     * @throws Exception If file is empty.
     */
    public STN parseFile(File file) throws Exception{
        Scanner fileScanner = null;
        String ts = "";
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
                System.out.println("Type of temporal network: " + typ.toString());
            } else {
                throw new Exception("File is empty."); 
            }
            fileScanner.nextLine();
            ts = fileScanner.nextLine();  // Get num time points
            numTimePoints = Integer.parseInt(ts);
            System.out.println("Number of time points: " + ts);
            network.setNumTimePoints(numTimePoints);
            fileScanner.nextLine();
            ts = fileScanner.nextLine();
            System.out.println("Number of edges: " + ts);
            fileScanner.nextLine();
            ts = fileScanner.nextLine();  // Get time point names
            timePointNames = Arrays.asList(ts.split(" "));
            System.out.println("Time point names: \n" + ts);
            network.setTimePointNames(timePointNames);
            fileScanner.nextLine();
            network.init();
            System.out.println("Edges: ");
            while (fileScanner.hasNext()){   // Get constraints
                ts = fileScanner.nextLine();
                pseudoEdge = Arrays.asList(ts.split(" "));
                lStart = timePointNames.indexOf(pseudoEdge.get(0));
                lEnd = timePointNames.indexOf(pseudoEdge.get(2));
                lWeight = Double.parseDouble(pseudoEdge.get(1));
                System.out.printf("Start: %d. End: %d. Weight: %f.\n", lStart, lEnd, lWeight);
                network.addEdge(new Edge(lStart,lEnd, lWeight));
            }
            System.out.println("Finished network creation.\n\n");
            System.out.println("================================================\n\n");
        } catch (NullPointerException np){
            System.err.println("Path to file cannot be null.");
        } catch (FileNotFoundException fnf){
            System.err.println("File not found, make sure to input a valid path.");
        } catch (Exception e){
            System.err.println(e.getCause());
        } finally {
            if (fileScanner != null){ fileScanner.close(); }     
        }

        return network;
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

    
}
