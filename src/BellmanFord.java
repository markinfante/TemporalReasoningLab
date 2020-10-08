package src;

/* 
 * BellmanFord.java
 * Author: Jonathan Fong
 */

/** 
 * Implementation of the Bellman-Ford algorithm
 * @author Jonathan Fong
 */

public class BellmanFord {
	/*
	 * Author: Jonathan Fong
	 * 
	 * 
	 * Pseudo-code from Michael Sambol video on Bellman-Ford:
	 * 
	 * procedure shortest-paths(G,l,s)
	 * Input: 	directed graph G = (V,E);
	 * 			edge lengths {l_e : e in E} with no negative cycles;
	 * 			vertex s in V
	 * Output:	For all vertices u reachable from s, dist(u) is set to the distance from s to u.
	 * 
	 *  
	 *  for all u in V:
	 *  	dist(u) = infinity
	 *  	prev(u) = nil
	 *  
	 *  dist(s) = 0
	 *  repeat V.length-1 times
	 *  	for all e in E:
	 *  		update(e)
	 *  
	 *  
	 *  procedure update((u,v) in E)
	 *  dist(v) = min{dist(v), dist(u)+l(u,v)}
	 */
	
	/*
	 * Advice from Hunsberger:
	 * Instead of "for each Edge(X, delta, Y) in STN",
	 * 
	 * do: for each node X,
	 * 			for each (Y, delta) in succs[X] hash table...
	 */
	
	private TemporalNetwork network;
	private DistanceMatrix outputmatrix;
	
	/**
	 * Create a new instance of the Bellman-Ford algorithm and generate a distance matrix as output.
	 * @param network The local temporal network
	 * @param vertex
	 */
	public BellmanFord(TemporalNetwork network, Integer vertex)
	{
		Edge tEdge = null;	// temp edge
		
		this.network = network;
		ArrayList<Double> distVector = new ArrayList<Double>(network.getNumTimePoints());
		distVector.add(0);				// First element will be the source (or sink) node, distance is 0
		ArrayList<Double> prevVector = new ArrayList<Double>(network.getNumTimePoints());
		prevVector.add(0);
		
		for (int i = 0; i < network.getNumTimePoints(); i++)			// for each node X
		{
			distVector.add(Double.POSITIVE_INFINITY);					// initialize distances to infinity
			prevVector.add(null);								// previous values
		}
		
		for (int i = 0; i < network.getNumTimePoints(); i++)			// for each node X
		{
			for (int j = 0; j < network.getNumTimePoints(); j++)		// for each outgoing edge from node X to node Y
			{
				tEdge = network.getEdge(i, j);
				// Using i+1 so we don't interfere with index 0, which holds the source (or sink) node
				if (tEdge != null && distVector.get(i+1) > prevVector.get(i+1) + tEdge.getWeight())		// Edit distance if needed
				{
					prevVector.set(i+1, distVector.get(i+1));
					distVector.set(i+1, tEdge.getWeight());
				}
			}
		}
	}
	/*
	// copied from Mark's Floyd-Warshall algorithm
	public DistanceMatrix generateMatrix()
	{
        for (int k = 0; k < outputMatrix.size(); k++){
            for (int i = 0; i < outputMatrix.size(); i++){
                for (int j = 0; j< outputMatrix.size(); j++){
                    if (outputMatrix.get(i).get(j) > outputMatrix.get(i).get(k) + outputMatrix.get(k).get(j)){
                        outputMatrix.get(i).set(j, (outputMatrix.get(i).get(k) + outputMatrix.get(k).get(j)));
                    }
                }
            }
        }
        return outputMatrix;
	}
	*/
}
