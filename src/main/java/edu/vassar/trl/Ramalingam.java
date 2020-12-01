package edu.vassar.trl;

/**
  * Implementation of Dijkstra's Algorithm for finding the shortest paths between nodes in a graph. 
  * @author Nyala Jackson
  * @author Ciara O'Donnell
  * @version 1.0
  */

  import java.util.*;

  public class Ramalingam
  {
      /*
       * Parameters
       * -------------
       * a graph
       * D: a feasible solution for the graph
       * a new constraint to be added
       */
      private STN network;
      private PriorityQueue<Edge> pq;
      private Set<Integer> settled; //nodes  that have been visited already
      
      public Ramalingam(STN tSTN)
      {
          network = tSTN;
          pq = new PriorityQueue<Edge>(new EdgeSort()); //create priority queue that prioritizes based on edge weights
          settled = new HashSet<Integer>();
      }
  
      public ArrayList<Double> RamalingamNN(ArrayList<Double> potential, Edge newEdge, int source)
      {
          ArrayList<Double> output = new ArrayList<Double>();
          
          for (int i = 0; i < potential.size(); i++)
          {
              output.add(potential.get(i));
          }
  
          pq.add(new Edge(0,source, 0.0));
  
          while (!pq.isEmpty())
          {
              int currNode = (int)pq.remove().getEnd();
              settled.add(currNode);
  
              for (Map.Entry<Integer,Double> entry : network.getPredsOf(currNode).entrySet())
              {
                  int v = entry.getKey();
                  double weight = entry.getValue();
                  if (output.get(v) > (output.get(currNode) - weight))
                  {
                      output.set(v, (output.get(currNode) - weight));
                  }
                  double newKey = potential.get(v) - output.get(v);
                  if (v == source && (newKey < 0))
                  {
                      return null;
                  }
                  else if (v != source)
                  {
                      pq.add(new Edge(currNode, v, newKey));
                  }
              }
          }
          return output;
      }
  
  }