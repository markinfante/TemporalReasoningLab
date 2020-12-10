package edu.vassar.trl;

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

    public List<Integer> getPermutation(){
        return shuffledNodes;
    }
    /**
     * Checks network consistency according to Dechter's DPC algorithm.
     * Starts by shuffling list of nodes in the graph, then
     * checks the succs less than the node and the preds less than the node. 
     * 
     * @return A boolean representing network consistency
     */
    public boolean isConsistent(List<Integer> permutation){
        Edge succ = null;
        Edge pred = null;
        Edge tEdge = null;
        Double tWeight = 0.0;
        
        for (int k = permutation.size() - 1; k >= 0; k--){
            for (int j = 0; j < k; j++){
                for (int i = 0; i < k; i++){
                    pred = network.getEdge(permutation.get(i), permutation.get(k), true);
                    succ = network.getEdge(permutation.get(k), permutation.get(j), true);
                    tEdge = network.getEdge(permutation.get(i), permutation.get(j), true);
                    if (tEdge.getWeight() > pred.getWeight() + succ.getWeight()){ // relax
                        tEdge.setWeight(pred.getWeight() + succ.getWeight());
                    } 
                    if (tEdge.getWeight() + network.getEdge(j, i, true).getWeight() < 0) return false;
                }
            }
        }

        return true;
    }
}
