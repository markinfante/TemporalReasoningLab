package edu.vassar.trl.stnu;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Map.Entry;

import edu.vassar.trl.*;

public class Morris2006 {

    /**
     * Subclass Pair for use in Morris Alg
     */
    public class Pair {
        private Integer node;
        private Double weight;

        public Pair(Integer node, Double weight) {
            this.node = node;
            this.weight = weight;
        }

        public Integer getNode() {
            return node;
        }

        public Double getWeight() {
            return weight;
        }
    }

    private STNU network;
    private ArrayList<Link> links;

    /**
     * Constructor for Morris2006 Algorithm
     */
    public Morris2006(STNU network) {
        this.network = network;
        this.links = network.getLinks();
    }

    /**
     * The Morris2006 Algorithm for checking consistency in an STNU.
     * @return A boolean representing if the STNU is consistent
     */
    public boolean isConsistent(){
        boolean output = false;

        for (int i = 0; i < network.getNumCLinks(); i++) { // do k times where k is num links
            BellmanFord bf = new BellmanFord(network); // this needs to be changed to run on ordinary AND upper case edges
            ArrayList<Double> h = bf.generate_BF(0);

            if (h == null) { return output; }

            ArrayList<Edge> newOrdEdges = new ArrayList<Edge>();
            ArrayList<Edge> newUCEdges = new ArrayList<Edge>();

            for (Link link : links){ // For each lower case edge in the graph NEED TO CHANGE
                PriorityQueue<Pair> Q = new PriorityQueue<Pair>(new SortByWeight());
                Q.add(new Pair(link.getEnd(), 0.0));
                while (!Q.isEmpty()){
                    Pair min = Q.remove();
                    int U = min.getNode();
                    double nonNegCU = min.getWeight();
                    double realCU = nonNegCU - h.get(link.getEnd()) + h.get(U);
                    if (realCU < 0) {
                        // Case 1
                        double newAU = link.getX() + realCU; // lower case rule
                        if (newAU < network.getEdge(link.getStart(), U, true).getWeight()){
                            newOrdEdges.add(new Edge(link.getStart(),U,newAU));
                        }
                    } else {
                        // Case 2
                        // Prop forward from U along ordinary edges
                        for (Entry<Integer, Double> entry : network.getSuccsOf(U).entrySet()){ 
                            double nonNegCUV = nonNegCU + (entry.getValue() + h.get(U) - h.get(entry.getKey()));
                            // TODO: ADD TO Q OR UPDATE
                            for (int j = 0; j < Q.size(); j++){
                                
                            }
                        }
                    }
                }
            }
        }


        return output;
    }
}
