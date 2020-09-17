package java;

import java.util.*;

/* STN.java
 * Author: Mark Infante
 *      A Simple Temporal Network extending TemporalNetwork
 *      Allows for creation of directed, weighted graph  
*/

public class STN extends TemporalNetwork{
    
    private List<? extends List <Integer>> successorsMatrix;   // A 2D matrix of nodes and their successors 
    private List<Integer> numSuccessors;            // Number of successor nodes for corresponding node index
    private List<? extends List <Integer>> edgesMatrix;        // A 2D matrix of edge weights for corresponding node index pair

    // Default constructor for simple temporal network
    public STN(){
        setNetType(TemporalNetworks.STN);
        successorsMatrix = new ArrayList<ArrayList<Integer>>();
        numSuccessors = new ArrayList<Integer>();
        edgesMatrix = new ArrayList<ArrayList<Integer>>();
    }

    // @params A list of edge 3-tuples (X, d, Y) where X is start node,
    //         Y is end node, and d is the edge weight.
    public STN(ArrayList<Edge> graph){
        // TODO: convert a list of 3-tuples (X, d, Y) into a STN.
    }

    @Override
    public void addEdge(Edge edge){
        // TODO: all
    }

    @Override
    public void removeEdge(Edge edge){
        // TODO: all
    }

    @Override
    void addNode() {
        // TODO: all
    }

    @Override
    void removeNode() {
        // TODO Auto-generated method stub

    }
    
}
