package src;
import java.util.*;
/**
  * Implementation of Johnson's Algorithm to find shortest paths between every
  * pair of vertices in a given weighted directed graph.
  * @author Nyala Jackson
  * @author Ciara O'Donnell
  */

public class Johnsons{
  private STN network; // STN to generate matrix
  private DistanceMatrix outputMatrix;
  //private Edge srcNode = new Edge(0,0,0.0);

  /**
   * Creates a new instance of the Johnsons algorithm.
   * @param network The local temporal network
   */

   /**
  Pseudo Code
  * if BellmanFord(s) = false
  * return "Input graph has neg weight cycle"
  * else:
  * for vertex v in G`.V:
  * h(v) = distance (s,v)
  * for edge (u,v) in G`.E:
  * weight`(u,v) = weight(u,v) + h(u) - h(v)
  * D = new matrix of distances intialized to POSITIVE_INFINITY
  * for vertex u in G.V:
  * run Dijkstra to compute distance for all v in G.V
  * for each vertex in G.V:
  * D_(u,v) = distance`(u,v) + h(v) - h(u)
  * return D
  */
  public Johnsons(STN network){
    this.network = network;
    this.outputMatrix = new DistanceMatrix();
    
  }

  public DistanceMatrix johnsonsAlgorithm(){ // needs to return shortestPath not matrix
    // create G` where G`.V where G`.V = G.V + {s}
    // adding a source node connected to all the other nodes with a val of 0
    int sourceNode = 0;

    //DistanceMatrix gprime = outputMatrix.makeCleanMatrixFromSTN(network);
    // gprime.add(new ArrayList<Double> (Collections.nCopies(network.getNumTimePoints(), 0.0));
    //ArrayList<Edge> tEdges = network.getEdges();
    //
  /*
  Main Steps:
  - A new vertex is added to the graph, and it is connected by edges of zero weight to all other vertices in the graph.
  - All edges go through a reweighting process that eliminates negative weight edges.
  - The added vertex from step 1 is removed and Dijkstra's algorithm is run on every node in the graph.
     get edge weight head + prev weight - edge weight tail = new weight*/

  // Bellman Ford returns a boolean indicating a negative weight cycle or not, but needs to get a vector of distances/transformed graph

    
    BellmanFord bf = new BellmanFord(network);
    ArrayList<Double> newDistances = bf.generate_BF(sourceNode); //these are our h(v)s (our potential function)

    if(newDistances == null){
      System.out.println("Input graph has a negative weight cycle");
    }
    else{

      // for(Map.Entry<Integer,Double> entry : Map.entrySet()){
     // modifiedMatrix = bf.generate_BF(sourceNode);

    /*
      for(int u = 0; u < outputMatrix.size();u++){
        for(int v = 0; v < outputMatrix.size();v++){
         outputMatrix.get(u).set(v,(outputMatrix.get(u).get(v)+ bf.generate_BF(u).get(v) - bf.generate_BF(v).get(u)));
          // original weight + h(u) - h(v), where 
        }
      } //not sure this part is right
      */
      ///******** ****************************************/
      outputMatrix.makeCleanMatrixFromSTN(network);

      //reweight graph
      for (int source = 0; source < network.getNumTimePoints(); source++)
      {
          for (int dest = 0; dest < network.getNumTimePoints(); dest++)
          {
              outputMatrix.get(source).set(dest, outputMatrix.get(source).get(dest)
              + newDistances.get(source+1) - newDistances.get(dest+1));
          } //maybe ask jonathan about deleting the src node (first) element in bellford output. thats why i put the +1s
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

    DijkstraPQ dpq = new DijkstraPQ(network);
    int [] shortestPathsFromU;
    //ArrayList <Double> shortestPathsFromU;

    for (int u = 0; u < outputMatrix.size(); u++) //for each vertex u in the graph 
    {
        shortestPathsFromU = dpq.dijkstra(newNetwork,u); //run dijkstra using u as the source node
        for (int v = 0; v < outputMatrix.size(); v++)  //for each vertex v in the graph 
        {
            outputMatrix.get(u).set(v, shortestPathsFromU[v] + newDistances.get(v+1) - newDistances.get(u+1));
            //set elements of distance matrix
        }

    }

    return outputMatrix;
    // where bf will be the modified graph
    
  /**  if(!bf){ 
      System.err.println("Input graph has a negative weight cycle");
    } **/
    // if true would return the vector
   
    /** for each node/vertex in the newly constructed graph (with the source node connecting
      * to each one)
      * calculate the distance b/w the source node to each of the vertices
      * for each edge in the new graph,
      * calculate the weight by adding the current weight (0) to the "head" and the "tail"
      * Create a new matrix of distances
      * for each vertex in the original graph
      * run Dijkstra to compute the distances for all the vertices in the original graph
      * for each vertex in the original
      * calculate the distance by adding the current distance to the "head" and "tail" vertices
      * return the new matrix of distances
      */
    
  }
}
