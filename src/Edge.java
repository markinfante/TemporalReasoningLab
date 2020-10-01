package src;

/* Edge.java
 * Author: Mark Infante
 *      A successor is a 3-tuple (X, d, Y), where X is a start node, Y is an end node,
 *      and d is delta, the weight of the edge from X --> Y. 
*/

public class Edge{

    private Integer startNode;
    private Integer endNode;
    private Double weight; 

    // @params 
    // successor = (X, d, Y) = (Integer, Integer, Double)
    public Edge(Integer startNode, Integer endNode, Double weight){
        this.startNode = startNode;
        this.endNode = endNode; 
        this.weight = weight;
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

    @Override
    public String toString(){
        return startNode.toString() + "-->" + endNode.toString() + ": " + weight.toString();
    }
}
