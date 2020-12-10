package edu.vassar.trl;

import java.util.*;
/**
  * Implementation of Dijkstra's Algorithm for finding the shortest paths between nodes in a graph. 
  * @author Nyala Jackson
  * @author Ciara O'Donnell
  */

class DijkstraPQ{
private double dist[];
private Set<Integer> settled;
private PriorityQueue<Edge> pq;
private int numOfVertices; 
private STN tSTN;

/***
 * Constructor for the Dijkstra class
 * @param tSTN the local temporalNetwork
 */
public DijkstraPQ(STN tSTN){
  this.tSTN = tSTN;
  this.numOfVertices = tSTN.getNumTimePoints();
  dist = new double[numOfVertices];
  settled = new HashSet<Integer>();
  pq = new PriorityQueue<Edge>(new EdgeSort()); //create priority queue that prioritizes based on edge weights
}

/***
 * This function runs Dijkstra's shortest path algorithm on a given STN using a priority queue
 * 
 * @param tSTN the graph that Dijkstra will run on
 * @param srcNode the source node
 * @return an array containing the shortest distances from a single source node to all other nodes in the graph
 */
public double[] dijkstra(int srcNode){
  // set all the weights to "inf"
  for(int i = 0; i < numOfVertices; i++){
    dist[i] = Double.POSITIVE_INFINITY;
  }
  // Add source node to the PQ
  pq.add(new Edge(0,srcNode, 0.0));

  dist[srcNode] = 0; // initialize distance to source node to 0

  while(!pq.isEmpty()){
    // remove the min distance node from the priority queue
    int minDistNode = (int)pq.remove().getEnd();
    settled.add(minDistNode);
    e_Neighbours(minDistNode);
  }
  return dist;
}
/**
 * A function for evaluating the successors ("neighbors") of a node. This function will add nodes
 * to the priority queue, as well as modify the distance array if a shorter path is found.
 * 
 * @param minDistNode the node which we are getting the successors of
 */
private void e_Neighbours(Integer minDistNode){
  double edgeDistance = -1;
  double newDistance = -1;

  // All the neighbors of edge v
  Map<Integer, Double> succNodes = tSTN.getSuccsOf(minDistNode); 
  for (Map.Entry<Integer, Double> entry : succNodes.entrySet()){
     //Double weight = entry.getValue();

    // if current node hasnt been processed
    if(!settled.contains(entry.getKey())){
      edgeDistance = entry.getValue(); // is a double, but needs to be an int
      newDistance = dist[minDistNode] + edgeDistance;

      // if new distance is cheaper in cost
      if(newDistance < dist[entry.getKey()]){
        dist[entry.getKey()] = newDistance;
   
        // add the current node to the queue
        pq.add(new Edge(minDistNode,entry.getKey(),newDistance));
      }
    }

  }
}

}