package java;

import java.util.*;

public class STN {

    private List<List<Integer>> successorsMatrix;   // A 2D matrix of nodes and their successors 
    private List<Integer> numSuccessors;            // Number of successor nodes for corresponding node index
    private List<List<Integer>> edgesMatrix;        // A 2D matrix of edge weights for corresponding node index pair

    // Default constructor for simple temporal network
    public STN(){
        successorsMatrix = new ArrayList<List<Integer>>();
        numSuccessors = new ArrayList<>();
    }

    // Overloaded constructor for simple temporal network
    // @params A list of edge 3-tuples (X, d, Y) where X is start node,
    //         Y is end node, and d is the edge weight.
    public STN(ArrayList<List<Integer>> graph){
        // TODO: convert a list of 3-tuples (X, d, Y) into a STN.
    }

    public void addEdge(){
        // TODO: all
    }

    public void removeEdge(){
        // TODO: all
    }
    
}