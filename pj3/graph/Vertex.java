/*
 * Vertex.java
 */

package graph;

import list.*;

public class Vertex {
	protected DList adjList;
	protected DListNode vnode;
	protected int degree;
	private Object item;
	
	public Vertex(Object item){
		this.item = item;
		this.adjList = new DList();
		this.vnode = null;
		this.degree = 0;
		
	}
	
	public Object getItem(){
		return item;
	}
	
}
