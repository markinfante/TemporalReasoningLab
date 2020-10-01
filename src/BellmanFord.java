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
		ArrayList<Double> distMatrix = new ArrayList<Double>(network.getSizeEdgesMatrix());
		distMatrix.add(0);				// First element will be 0
		
		for (int i = 1; i < network.getSizeEdgesMatrix(); i++)
		{
			distMatrix.add(Double.POSITIVE_INFINITY);			// initialize entries to infinity
			for (int j = 1; j < network.getSizeEdgesMatrix(); j++)
			{
				tEdge = network.getEdge(i, j);
				if (distMatrix.get(i) > tEdge.getWeight())		// Edit distance if needed
				{
					distMatrix.set(i, tEdge.getWeight());
				}
			}
		}
			
			/*if (i != vertex)							// Only run if i != vertex: distance from vertex to vertex is 0
			{
				outputMatrix.add(new ArrayList<Double>());  // Add lists for every edge 
	            for (int j = 0; j < network.getNumTimePoints(); j++){
	                tEdge = network.getEdge(i, j);
	                if (tEdge != null){
	                    outputMatrix.get(i).add(tEdge.getWeight());
	                } else {
	                    outputMatrix.get(i).add(Double.POSITIVE_INFINITY);  
	                }
	            }
			}*/
        }
	}
	
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
}
