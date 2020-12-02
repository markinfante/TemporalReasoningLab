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
      private int keys[];
      private PriorityQueue<Edge> pq;
      private Set<Integer> settled; //nodes  that have been visited already
      private int numOfVertices; 
      
      
      public Ramalingam(STN tSTN)
      {
          network = tSTN;
          pq = new PriorityQueue<Edge>(new EdgeSort()); //create priority queue that prioritizes based on edge weights
          settled = new HashSet<Integer>();
          this.numOfVertices = tSTN.getNumTimePoints();
          keys = new int[numOfVertices];
      }
  
      public ArrayList<Double> updatePotential(ArrayList<Double> potential, Edge newEdge, int source)
      {
          source = newEdge.getEnd(); //maybe comment this out? A = source
          network.addEdge(newEdge); //commenting this out makes it funky
          ArrayList<Double> output = new ArrayList<Double>();
          
          for (int i = 0; i < potential.size(); i++) //copy potential function into output
          {
              output.add(potential.get(i));
          }

          for(int i = 0; i < numOfVertices; i++){
            keys[i] = Integer.MAX_VALUE;
          }

          keys[source] = 0;
          //A is the thing that i'm going to be getting the preds of, so pq.getEnd
  
          pq.add(new Edge(0,source, 0.0)); 
  
          while (!pq.isEmpty())
          {
              int currNode = (int)pq.remove().getEnd();
              settled.add(currNode);
              for (Map.Entry<Integer,Double> entry : network.getPredsOf(currNode).entrySet())
              {
                  int v = entry.getKey();
                  double weight = entry.getValue();
                  if (output.get(v) < (output.get(currNode) - weight))
                  {
                      output.set(v, (output.get(currNode) - weight));
                  }

                  double newKey = potential.get(v) - output.get(v);
                  if (!settled.contains(entry.getKey()))
                  {
                    if (v == source && (newKey < 0))
                    {
                        return null;
                    }
                    else if ((v != source) || (newKey < keys[entry.getKey()]))
                    {
                        pq.add(new Edge(currNode, v, newKey));
                    }
                  }
              }
          }
          return output;
      }
  
  }