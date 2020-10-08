package src;
import java.util.*;

import java.util.Collections;
import java.util.HashMap;


public class DPC {

    private List<Integer> shuffledNodes;
    private TemporalNetwork network; 

    /**
     * Creates a new instance of the algorithm and intitializes output matrix.
     * @param network The local temporal network 
     */
    public DPC(TemporalNetwork network){
        this.network = network;
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
    public boolean isConsistent(){
        List<? extends Map<Integer,Edge>> relaxed = new ArrayList<HashMap<Integer,Edge>>();
        Integer tstart = 0;
        Integer tend = 0;
        Double tweight = 0.0;
        boolean output = true;
        
        Collections.reverse(shuffledNodes); // Work from the back first

        for (Integer node: shuffledNodes){  // For all nodes
            for (Integer key: network.getSuccsOf(node).keySet()){ // For all succs
                if (key < node){

                }
            }
            for (Integer key: network.getPredsOf(node).keySet()){ // For all preds
                if (key < node){

                }
            }
        }

        return output;
    }
}
