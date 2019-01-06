package graphalg;

import graph.*;
import list.*;
import set.*;
import dict.*;


/**
 * The Kruskal class contains the method minSpanTree(), which implements
 * Kruskal's algorithm for computing a minimum spanning tree of a graph.
 */

public class Kruskal {

  /**
   * minSpanTree() returns a WUGraph that represents the minimum spanning tree
   * of the WUGraph g.  The original WUGraph g is NOT changed.
   *
   * @param g The weighted, undirected graph whose MST we want to compute.
   * @return A newly constructed WUGraph representing the MST of g.
 * @throws InvalidNodeException 
 * @throws QueueEmptyException 
   */
  public static WUGraph minSpanTree(WUGraph g) throws InvalidNodeException, QueueEmptyException{
	  WUGraph t = new WUGraph();
	  Object[] ver = g.getVertices();
	  LinkedQueue q = new LinkedQueue();
	  
	  for(int i = 0;i<g.vertexCount();i++){
		  t.addVertex(ver[i]);
		  int d = g.degree(ver[i]);
		  Neighbors n = g.getNeighbors(ver[i]);
		  for(int j=0;j<d;j++){
			  KEdge e = new KEdge(ver[i],n.neighborList[j],n.weightList[j]);
			  q.enqueue(e);
		  }
		  
	  }
	 ListSorts.quickSort(q);
	 
	 DisjointSets s = new DisjointSets(g.vertexCount());
	 HashTableChained vhash = new HashTableChained();
	 for(int i =0; i< g.vertexCount();i++){
		 vhash.insert(ver[i], i);
	 }
	 while(!q.isEmpty()){
		 KEdge e = (KEdge) q.dequeue();
		 int a = s.find((int) vhash.find(e.u).value());
		 int b = s.find((int) vhash.find(e.v).value());
		 if(a != b){
			 s.union(a, b);
			 t.addEdge(e.u, e.v, e.weight);
		 }
	 }
	 
	  
	  return t;
  }

}