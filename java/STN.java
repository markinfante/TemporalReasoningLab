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
    public STN(ArrayList<Successor> graph){
        // TODO: convert a list of 3-tuples (X, d, Y) into a STN.
    }

    @Override
    public void addNode(){
        // TODO: all
    }

    @Override
    public void removeNode(){
        // TODO: all
    }

    @Override
    public void addEdge(Successor successor){
        // TODO: all
    }

    @Override
    public void removeEdge(Successor successor){
        // TODO: all
    }

    @Override
    void addSuccessor(Successor successor) {
        // TODO: all
    }
    
}