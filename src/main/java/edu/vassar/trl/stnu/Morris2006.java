package edu.vassar.trl.stnu;

import java.util.ArrayList;
import java.util.PriorityQueue;

import edu.vassar.trl.*;

public class Morris2006 {
    
    /**
     * Subclass Pair for use in Morris Alg
     */
    public class Pair{
        private Integer node;
        private Double weight;

        public Pair(Integer node, Double weight){
            this.node = node;
            this.weight = weight;
        }

        public Integer getNode(){ return node; }
        public Double getWeight(){ return weight; }
    }

    private STNU network;
    private ArrayList<Link> links;

    public Morris2006(STNU network) {
        this.network = network;
        this.links = network.getLinks();
    }

    public boolean isConsistent(){
        boolean output = false;

        for (int i = 0; i < network.getNumLinks(); i++) { // do k times where k is num links
            BellmanFord bf = new BellmanFord(network); // this needs to be changed to run on ordinary AND upper case edges
            ArrayList<Double> h = bf.generate_BF(0);

            if (h == null) { return output; }

            ArrayList<Edge> newOrdEdges = new ArrayList<Edge>();
            ArrayList<Edge> newUCEdges = new ArrayList<Edge>();

            for (Link link : links){
                PriorityQueue<Pair> Q = new PriorityQueue<Pair>(new SortByWeight());
            }
        }


        return output;
    }
}
