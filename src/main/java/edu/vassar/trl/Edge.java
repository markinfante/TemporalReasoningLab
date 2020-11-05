package edu.vassar.trl;

import java.util.*;

/** 
 * An Edge is a 3-tuple (X, Y, d), where X is a start node, Y is an end node,
 * and d is delta, the weight of the edge from X --> Y.
 * @author Mark Infante 
*/
public class Edge  implements Comparator<Edge>{

    private int startNode;
    private int endNode;
    private Double weight; 

    /**
     * Default constructor for an Edge.
     * @param startNode An integer representing start node
     * @param endNode An integer representing end node
     * @param weight A double representing the edge weight between nodes.
     */
    public Edge(int startNode, int endNode, Double weight){
        this.startNode = startNode;
        this.endNode = endNode; 
        this.weight = weight;
    }

    @Override
    public int compare(Edge e1, Edge e2)
    {
        if (e1.getWeight()  < e2.getWeight())
            return -1;
        if (e1.getWeight()  > e2.getWeight())
            return 1;
        return 0;
    }
    /**
     * Returns the string representation of an edge.
     */
    @Override
    public String toString(){
        return startNode + "-->" + endNode + ": " + weight.toString();
    }
    
    public int getStart(){ return startNode; }
    public int getEnd(){ return endNode; }
    public Double getWeight(){ return weight; }
    public void setWeight(Double weight) { this.weight = weight; }
    
}
