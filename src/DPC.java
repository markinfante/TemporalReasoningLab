package src;
import java.util.*;

import java.util.Collections;
import java.util.HashMap;


public class DPC {

    private List<Integer> shuffledNodes;
    private STN network; 

    /**
     * Creates a new instance of the algorithm and intitializes output matrix.
     * @param network The local temporal network 
     */
    public DPC(STN network){
        this.network = network;
        this.shuffledNodes = new ArrayList<Integer>();
        for (int i = 0; i < network.getNumTimePoints(); i++){
            shuffledNodes.add(i);
        }
        Collections.shuffle(shuffledNodes);  // Shuffle nodes in the graph
    }

    /**
     * Checks network consistency according to Dechter's DPC algorithm.
     * Starts by shuffling list of nodes in the graph, then
     * checks the succs less than the node and the preds less than the node. 
     * 
     * @return A boolean representing network consistency
     */
    // isDPC
    public boolean isConsistent(){
        List<? extends Map<Integer,Edge>> relaxed = new ArrayList<HashMap<Integer,Edge>>();
        ArrayList<Edge> edges = network.getEdges();
        Map<Integer, Double> succs = null;
        Map<Integer, Double> preds = null;
        Double tWeight1 = 0.0;
        Double tWeight2 = 0.0;
        Edge tEdge = null;
        
        for (int k = network.getNumTimePoints() - 1; k >= 0; k--){
            succs = network.getSuccsOf(shuffledNodes.get(k));
            preds = network.getPredsOf(shuffledNodes.get(k));
            for (int j = 0; j < k; j++){
                for (int i = 0; i < k; i++){
                    tWeight1 = succs.get(i);   // weight from k -> i
                    tWeight2 = preds.get(j);   // weight from j -> k 
                    // Relax
                    if (tWeight1 != 0.0 && tWeight2 != 0.0){    // if1 : both pred and succ edges exist
                        tEdge = network.getEdge(i, j);             //   edge = i -> j 
                        if (tEdge != null){                        // if2 : edge exists in the graph from i -> j
                            if (tEdge.getWeight() > tWeight1 + tWeight2) { // if3 : the weight of new is less than prev val
                                tEdge.setWeight(tWeight1 + tWeight2);
                            } else {                                        // else3 : weight of new is greater
                                continue;       
                            }
                        } else {                                    // else2 : edge doesn't exist in the graph from i -> j 
                            network.addEdge(new Edge(i, j, tWeight1 + tWeight2));
                        }
                    } else {                                    // else1 :  at lest one edge doesn't exist
                        continue;
                    } 
                }
            }
        }
        
        if (network.getEdge(shuffledNodes.get(0), shuffledNodes.get(0)).getWeight() < 0) 
            return false;

        return true;
    }
}
