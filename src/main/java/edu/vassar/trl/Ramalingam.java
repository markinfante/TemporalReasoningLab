package edu.vassar.trl;

/**
  * Implementation of Dijkstra's Algorithm for finding the shortest paths between nodes in a graph. 
  * @author Ciara O'Donnell
  * @author Nyala Jackson
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
      private PriorityQueue<Link> pq;
      private Set<Integer> settled; //nodes  that have been visited already
      private int numOfVertices; 
      
      
      public Ramalingam(STN tSTN)
      {
          network = tSTN;
          pq = new PriorityQueue<Link>(new LinkSort()); //create priority queue that prioritizes based on edge weights
          settled = new HashSet<Integer>();
          this.numOfVertices = tSTN.getNumTimePoints();
          keys = new int[numOfVertices];
      }
  
      public ArrayList<Double> updatePotential(ArrayList<Double> potential, Edge newEdge, int source)
      {
          source = newEdge.getEnd(); //A = source
          network.addEdge(newEdge); 
          ArrayList<Double> output = new ArrayList<Double>();
          HashMap<Integer, Double> qTracker = new HashMap<Integer, Double>();
          
          for (int i = 0; i < potential.size(); i++) //copy potential function into output
          {
              output.add(potential.get(i));
          }

          for(int i = 0; i < numOfVertices; i++){
            keys[i] = Integer.MAX_VALUE;
          }

          keys[source] = 0; //A is the thing that i'm going to be getting the preds of, so pq.getEnd
  
          pq.add(new Link(source, 0.0));
          //qTracker.put(source, 0.0);
  
          while (!pq.isEmpty())
          {
              int w = (int)pq.remove().getNode();
              //qTracker.remove(w);
              settled.add(w);
              for (Map.Entry<Integer,Double> entry : network.getPredsOf(w).entrySet())
              {
                  int v = entry.getKey();
                  double weight = entry.getValue();
                  if (output.get(v) < (output.get(w) - weight)) 
                  {
                      output.set(v, (output.get(w) - weight));

                      double newKey = potential.get(v) - output.get(v); //next lines update the pq

                      if (!settled.contains(v)) //it has not been popped off the queue yet
                        {
                            if (v == source && (newKey < keys[source]))
                            {
                                return null;
                            }
                            else if ((v != source) && (newKey < keys[v]))
                            {
                                keys[v] = (int) newKey;
                                pq.add(new Link(v, newKey));
                            }
                        }
                  }
              }
          }
          return output;
      }
  }