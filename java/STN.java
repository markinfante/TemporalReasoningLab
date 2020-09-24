package java;

import java.util.*;

/* STN.java
 * Author: Mark Infante
 *      A Simple Temporal Network extending TemporalNetwork
 *      Allows for creation of directed, weighted graph  
*/

public class STN extends TemporalNetwork{
    
    private List<Map<String, Object>> successors;   // A 2D vector that holds information about a node's successors.
    private List<Integer> numSuccessors;            // Number of successor nodes for corresponding node index
    private List<? extends List <Integer>> edgesMatrix;        // A 2D matrix of edge weights for corresponding node index pair

    // Default constructor for simple temporal network
    public STN(){
        setNetType(TemporalNetworks.STN);
        numSuccessors = new ArrayList<Integer>();
        successors = new Vector<Map<Integer, Edge>>(); //iniitialize successors as a Vector
        edgesMatrix = new ArrayList<ArrayList<Integer>>();
        
        int tps = super.getNumTimePoints(); //populate the spaces in the vector, based on the number of timepoints
        for (int i = 0; i < tps; i++) {
            successors.add(i, new HashMap<Integer, Edge>()); 
            numSuccessors.add(0);
            edgesMatrix.add(i, new ArrayList<Integer>);
        }
        
    }

    /* @params A list of edge 3-tuples (X, d, Y) where X is start node,
    //         Y is end node, and d is the edge weight.
    public STN(ArrayList<Edge> graph){
        // TODO: convert a list of 3-tuples (X, d, Y) into a STN.
        
        
    }*/

    @Override 
    // @params An existing STN s and an Edge object edge consisting of a source, destination, and a weight.
    public void addEdge(Edge edge){ 
        // TODO: all
        Integer x = edge.getStart();
        Integer y = edge.getEnd();
        Integer d = edge.getWeight();
        Edge e = successors.get(x).get(y);
        
        if (e == null){ //if an edge doesn't exist in successors, input the given edge argument. also increment numsuccessors, since we are adding a new edge
            successors.get(x).put(y, edge);
            numSuccessors.set(x, numSuccessors.get(x)+1);
            edgesMatrix.get(x).add(y, d);
        }
        else if (e != null && (e.getWeight() > d)){ //else if new edge gives a shorter path from x->y than the old edge, replace the old edge with the new one
            successors.get(x).put(y, edge);
            edgesMatrix.get(x).set(y, d);
        }
    }

    @Override
    public void removeEdge(Edge edge){
        Edge e = successors.get(x).get(y);
        if (edge == null){
            System.out.println("Cannot remove edge because edge does not exist.");
            return;
        }
        else {
            successors.get(x).put(y, null);
            numSuccessors.set(x, numSuccessors.get(x)-1);
            edgesMatrix.get(x).set(y, 0);
        }
        return;
    }
    
    public Edge getEdge(Integer src, Integer dest){
        return successors.get(x).get(y);
    }

    @Override
    void addNode() {
        // TODO: all
        return;
    }

    @Override
    void removeNode() {
        // TODO Auto-generated method stub
        return;

    }
    
}
