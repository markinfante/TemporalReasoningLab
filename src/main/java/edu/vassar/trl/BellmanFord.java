package edu.vassar.trl;

import java.util.*;

/** 
 * Implementation of the Bellman-Ford algorithm
 * 
 * @author Jonathan Fong
 */

public class BellmanFord {
	
	private STN network;
	private ArrayList<Edge> tEdges;
	
	/**
	 * Creates a new instance of the Bellman-Ford algorithm.
	 * @param network The local temporal network
	 */
	public BellmanFord(STN network)
	{		
		this.network = network;
		this.tEdges = new ArrayList<Edge>();
		
		
		for (int i = 0; i < network.getNumTimePoints(); i++)
		{
			if (network.getEdge(i, i, true) != null)

				tEdges.add(network.getEdge(i, i, true));
			
			// Adds all edges to the ArrayList of edges
			for(Map.Entry<Integer,Double> entry : network.getSuccsOf(i).entrySet())
			{
				tEdges.add(network.getEdge(i, entry.getKey(), true));
			}
		}
	}

	
	/**
	 * Runs the Bellman-Ford algorithm
	 * @param source, the SOURCE flag
	 * @return An ArrayList of Doubles, named distVector; null if negative cycles exist
	 */
	public ArrayList<Double> generate_BF(Integer source)
	{	
		ArrayList<Double> distVector = new ArrayList<Double>(Collections.nCopies(network.getNumTimePoints(), Double.POSITIVE_INFINITY));
		distVector.set(source, 0.0);
		
		for (int i = 0; i < network.getNumTimePoints()-1; i++)
		{
			for (Edge tEdge : tEdges)
			{
				// Edit distance vector if needed
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
				distVector.get(tEdge.getStart()) + tEdge.getWeight() < distVector.get(tEdge.getEnd()))
			{
				return null;
			}
		}
		System.out.println("shgdh" + distVector);
		return distVector;
	}
}