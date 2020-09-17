package java;

/* Successor.java
 * Author: Mark Infante
 *      A successor is a 3-tuple (X, d, Y), where X is a start node, Y is an end node,
 *      and d is delta, the weight of the edge from X --> Y. 
*/

public class Successor{

    private Integer startNode;
    private Integer endNode;
    private Double weight; 

    // @params 
    // successor = (X, d, Y) = (Integer, Double, Integer)
    public Successor(Integer startNode, Double weight, Integer endNode){
        this.startNode = startNode;
        this.weight = weight;
        this.endNode = endNode; 
    }

    public Integer getStart(){
        return startNode;
    }

    public Integer getEnd(){
        return endNode;
    }

    public Double getWeight(){
        return weight;
    }
}