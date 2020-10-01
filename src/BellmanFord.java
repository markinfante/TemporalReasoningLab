package src;

/* 
 * BellmanFord.java
 * Author: Jonathan Fong
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
	
	
}
