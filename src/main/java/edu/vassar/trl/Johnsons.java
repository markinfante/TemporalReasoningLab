package edu.vassar.trl;
import java.util.*;
/**
  * Implementation of Johnson's Algorithm to find shortest paths between every
  * pair of vertices in a given weighted directed graph.
  * @author Nyala Jackson
  * @author Ciara O'Donnell
  * @version 1.0
  */

public class Johnsons{
  private STN network; 
  private DistanceMatrix outputMatrix;

  /**
   * Creates a new instance of the Johnsons algorithm.
   * @param network The local temporal network
   */
  public Johnsons(STN network){
    this.network = network;
    this.outputMatrix = new DistanceMatrix();
    
  }

  /***
   * This method runs Johnson's algorithm on a given STN. It makes use of both the Bellman Ford Algorithm and Dijkstra's Algorithm.
   * 
   * @return the Distance Matrix for a given STN
   */
  public DistanceMatrix johnsonsAlgorithm(){
    int sourceNode = 0;

    BellmanFord bf = new BellmanFord(network); //creating bellman ford object
    ArrayList<Double> newDistances = bf.generate_BF(sourceNode); //these are our h(v)s (our potential function)

    if(newDistances == null){
      System.out.println("\nInput graph has a negative weight cycle");
      return null;
    }
    else{

      outputMatrix.makeCleanMatrixFromSTN(network); //This is an edges matrix

      //reweight graph based on Bellman Ford
      for (int source = 0; source < network.getNumTimePoints(); source++)
      {
          for (int dest = 0; dest < network.getNumTimePoints(); dest++)
          {
              outputMatrix.get(source).set(dest, outputMatrix.get(source).get(dest)
              + newDistances.get(source) - newDistances.get(dest));
          } 
      }
    }

    //create new STN with no neg weight cycles. this is for dijkstra to use
    STN newNetwork = new STN(network.getNumTimePoints());
    newNetwork.init();
    for (int i = 0; i < newNetwork.getNumTimePoints(); i++)
    {
        for (int j = 0; j < newNetwork.getNumTimePoints(); j++)
          {
            if (outputMatrix.get(i).get(j) == Double.POSITIVE_INFINITY) //skip over these, we dont want them in our new STN
                continue;
            else
            {  
                Double w = outputMatrix.get(i).get(j);
                newNetwork.addEdge(new Edge (i,j,w));
            }
          }
    }

    DijkstraPQ dpq = new DijkstraPQ(newNetwork);
    double [] shortestPathsFromU; //this will hold the values from dijkstra's algorithm

    for (int u = 0; u < outputMatrix.size(); u++) //for each vertex u in the graph 
    {
        dpq = new DijkstraPQ(newNetwork);
        shortestPathsFromU = dpq.dijkstra(u); //run dijkstra using u as the source node
        for (int v = 0; v < outputMatrix.size(); v++)  //for each vertex v in the graph 
        {
            outputMatrix.get(u).set(v, shortestPathsFromU[v] + newDistances.get(v) - newDistances.get(u));
            //set elements of distance (output) matrix
        }

    }

    return outputMatrix;
  }
}