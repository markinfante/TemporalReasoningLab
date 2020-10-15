package src;

import java.util.*;

/**
 * Allows for creation of directed, weighted graph.
 * Extends TemporalNetwork.
 * @author Mark Infante
 * @author Ciara O'Donnell
*/

public class STN extends TemporalNetwork{
    
    private List<HashMap<Integer, Double>> successors;   // A 2D vector that holds information about a node's successors.
                                                     // index of the list is start, map at index contains end and delta
    private List<Integer> numSuccessors;             // Number of successor nodes for corresponding node index
    private List<ArrayList <Edge>> edgesMatrix;      // A 2D matrix of edge weights for corresponding node index pair
    private List<HashMap<Integer, Double>> predecessors; // A 2D vector that holds information about a node's preds.
                                                     // index of the list is end, map at index contains start and delta

    // Default constructor for simple temporal network
    // TODO: Figure out how to prompt if wanting a distance matrix 
    public STN(){
        setNetType(TemporalNetworks.STN);
        numSuccessors = new ArrayList<Integer>();
        successors = new ArrayList<HashMap<Integer, Double>>(); //iniitialize successors as a Vector
        predecessors = new ArrayList<HashMap<Integer, Double>>();
        edgesMatrix = new ArrayList<ArrayList<Edge>>();
    }
    
    @Override
    public void init(){
        int tps = super.getNumTimePoints(); //populate the spaces in the vector, based on the number of timepoints
        for (int i = 0; i < tps; i++) {
            successors.add(i, new HashMap<Integer, Double>()); 
            numSuccessors.add(0);
            edgesMatrix.add(i, new ArrayList<Edge>());
            for (int k = 0; k < tps; k++)
            {
                edgesMatrix.get(i).add(null);
            }
        }   
    }

    @Override 
    public void addEdge(Edge edge){ 
        Integer x = edge.getStart();
        Integer y = edge.getEnd();
        Double d = edge.getWeight();
        Double e = successors.get(x).get(y);
        
        if (e == 0.0){ //if an edge doesn't exist in successors, input the given edge argument. also increment numsuccessors, since we are adding a new edge
            successors.get(x).put(y, d);
            predecessors.get(y).put(x,d);
            numSuccessors.set(x, numSuccessors.get(x)+1);
            edgesMatrix.get(x).add(y, edge);
        } else if (e > d){ //else if new edge gives a shorter path from x->y than the old edge, replace the old edge with the new one
            successors.get(x).put(y, d);
            predecessors.get(y).put(x,d);
            edgesMatrix.get(x).set(y, edge);
        }
    }
    
    @Override
    public void removeEdge(Edge edge){
        Integer x = edge.getStart();
        Integer y = edge.getEnd();
    
        edgesMatrix.get(x).set(y, null);
        
        return;
    }
    
    @Override
    public Edge getEdge(Integer src, Integer dest){
        return edgesMatrix.get(src).get(dest);
    }

    @Override
    public void addNode() {
        // TODO: all
        return;
    }

    @Override
    public void removeNode() {
        // TODO Auto-generated method stub
        return;

    }

    @Override
    public Integer getSizeEdgesMatrix(){
        return edgesMatrix.size();
    }
    
    @Override
    public Map<Integer, Double> getSuccsOf(Integer node){
        return successors.get(node);
    }

    @Override
    public Map<Integer, Double> getPredsOf(Integer node){
        return predecessors.get(node);
    }

    @Override
    public String toString(){
        // TODO: test
        String output = "\nPrinting network: \n\n";
        Edge tEdge = null;
        for (int y = 0; y < getSizeEdgesMatrix() + 1; y++){
            for (int x = 0; x <= getSizeEdgesMatrix() + 1; x++){
                if (x == getSizeEdgesMatrix()+1){     // Create new line in matrix
                    output = output + "\n\n";
                } else if (y == 0){     // Create first row of start nodes
                    if (x == 0) {       // Skip a space for alignment at 0,0 because we use 0 as a node
                        output = output + "\t\t" + Integer.toString(x); 
                    } else if (x < getSizeEdgesMatrix()){            
                        output = output + "\t" + Integer.toString(x);
                    }
                } else if (x == 0){     // Create first column of end nodes
                    output = output + "\t" + Integer.toString(y - 1); 
                } else {
                    tEdge = this.getEdge(x-1, y-1);
                    if (tEdge == null) {
                        output = output + "\t" + "null";
                    } else {
                        output = output + "\t" + tEdge.getWeight().toString();
                    }
                }
            }
        }
        return output;
    }
}