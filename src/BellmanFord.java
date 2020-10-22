package src;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
/* 
 * BellmanFord.java
 * Author: Jonathan Fong
 */

/** 
 * Implementation of the Bellman-Ford algorithm
 * 
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
	 * 			Distance vector
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
	 * 
	 * Two versions:
	 * 		1. Source node s (distances from s to other nodes)
	 * 		2. Sink node s (distances from other nodes to s)
	 * 
	 * 
	 * Hunsberger's pseudocode
	 * for each X, d(X) = 0
	 * for i=1 to (n-1),
	 * 		for each edge (U,δ,V) in graph,
	 *			d(V) = min{d(V), d(U) + δ}
	 * for each edge (U,δ,V) in graph,
	 * 		if (d(V) > d(U) + δ) return false
	 * return true
	 */
	
	private STN network;
	private DistanceMatrix outputmatrix;
	private ArrayList<Edge> tEdges;
	
	/**
	 * Create a new instance of the Bellman-Ford algorithm and generate a distance matrix as output.
	 * @param network The local temporal network
	 */
	public BellmanFord(STN network)
	{		
		this.network = network;
		this.outputmatrix = network.getDistanceMatrix();
		this.tEdges = new ArrayList<Edge>();
		
		for (int i = 0; i < network.getNumTimePoints(); i++)
		{
			tEdges.add(network.getEdge(i, i, true));
			
			for (int j = 0; j < network.getNumTimePoints(); j++)
			{
				if (i != j)
				{
					tEdges.add(network.getEdge(i, j, true));
					tEdges.add(network.getEdge(j, i, true));
				}
			}
		}
	}
	
	/**
	 * @param source SOURCE flag
	 */
	public ArrayList<Double> generate_BF(int source)
	{	
		ArrayList<Double> distVector = new ArrayList<Double>(Collections.nCopies(network.getNumTimePoints(), Double.POSITIVE_INFINITY));
		distVector.set(source, 0.0);
		
		for (int i = 0; i < network.getNumTimePoints()-1; i++)
		{
			for (Edge tEdge : tEdges)		// for each outgoing edge in successors
			{				
				if (distVector.get(tEdge.getStart()) != Double.POSITIVE_INFINITY &&
					distVector.get(tEdge.getStart()) + tEdge.getWeight() < distVector.get(tEdge.getEnd()))
				{
					distVector.set(tEdge.getEnd(),
									distVector.get(tEdge.getStart()) + tEdge.getWeight());
				}
			}
		}
		
		
		
		// Checking for negative cycles
		for (Edge tEdge : tEdges)
		{
			if (distVector.get(tEdge.getStart()) != Double.POSITIVE_INFINITY &&
				distVector.get(tEdge.getEnd()) > distVector.get(tEdge.getStart()) + tEdge.getWeight())
			{
				return null;
			}
		}
		
		return distVector;
	}
}
