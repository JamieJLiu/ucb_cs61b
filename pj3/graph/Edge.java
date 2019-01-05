/*
 * Edge.java
 */

package graph;
import list.*;

public class Edge {
	protected Vertex u;
	protected Vertex v;
	protected int weight;
	protected DListNode unode; //edge's position in u's adjlist 
	protected DListNode vnode;//edge's position in v's adjlist
	
	public Edge(Vertex u, Vertex v, int weight) {
		this.u = u;
		this.v = v;
		this.weight = weight;
		this.unode = null;
		this.vnode = null;
	}
}
