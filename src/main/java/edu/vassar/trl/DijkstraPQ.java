package edu.vassar.trl;

import java.util.*;
/**
  * Implementation of Dijkstra's Algorithm for finding the shortest paths between nodes in a graph. 
  * @author Nyala Jackson
  * @author Ciara O'Donnell
  */

class DijkstraPQ{
private int dist[];
private Set<Integer> settled;
private PriorityQueue<Edge> pq;
private int numOfVertices; 
List<HashMap<Integer, Double>> adjacencyMatrix;
private STN tSTN;

// where numOfVertices 
public DijkstraPQ(STN tSTN){
  this.tSTN = tSTN;
  this.numOfVertices = tSTN.getNumTimePoints();
  dist = new int[numOfVertices];
  settled = new HashSet<Integer>();
  // super for call parametric constructer, comparator for method reference?  (Comparator<? super Edge>)
  // recommendation from net beans 
  //pq = new PriorityQueue<Edge>(numOfVertices, new Edge(0,0,0.0));
  pq = new PriorityQueue<Edge>(numOfVertices);
}

public int[] dijkstra(STN tSTN, int srcNode){
  this.adjacencyMatrix = tSTN.getSuccs();
  // set all the weights to "inf"
  for(int i = 0; i < numOfVertices; i++){
    dist[i] = Integer.MAX_VALUE;
  }
  // Add source node to the PQ
  pq.add(new Edge(srcNode, 0, 0.0));

  dist[srcNode] = 0;
  while(settled.size() != numOfVertices){
    // remove the min distance node from the priority queue
    //DIKSTRA IS HAVING PROBLEMS HERE!!!!
    int minDistNode = (int)pq.remove().getStart();
    settled.add(minDistNode);
    e_Neighbours(minDistNode);
  }
  ///what do we return????
  return dist;
}

private void e_Neighbours(Integer minDistNode){
  double edgeDistance = -1;
  double newDistance = -1;

  // All the neighbors of edge v
  Map<Integer, Double> succNodes = tSTN.getSuccsOf(minDistNode); 
  for (Map.Entry<Integer, Double> entry : succNodes.entrySet()){
     //Double weight = entry.getValue();

    // if current node hasnt been processed
    if(!settled.contains(entry.getKey())){ ///THIS IS WHERE I LEFT OFF
      edgeDistance = entry.getValue(); // is a double, but needs to be an int
      newDistance = dist[minDistNode] + edgeDistance;

      // if new distance is cheaper in cost
      if(newDistance < dist[entry.getKey()]){
        dist[minDistNode] = (int) newDistance;
   
        // add the current node to the queue
        pq.add(new Edge(minDistNode,entry.getKey(),newDistance));
      }
    }

  }
}

}