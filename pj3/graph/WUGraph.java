package graph;
/**
 * The WUGraph class represents a weighted, undirected graph.  Self-edges are
 * permitted.
 */
import dict.*;
import list.*;

public class WUGraph {
	
	protected DList vertices;
	protected int numOfVertices;
	protected int numOfEdges;
	protected HashTableChained vhash;
	protected HashTableChained ehash;
	
  /**
   * WUGraph() constructs a graph having no vertices or edges.
   *
   * Running time:  O(1).
   */
  public WUGraph(){
	  vertices = new DList();
	  numOfVertices = 0;
	  numOfEdges = 0;
	  vhash = new HashTableChained();
	  ehash = new HashTableChained();
	  
  };

  /**
   * vertexCount() returns the number of vertices in the graph.
   *
   * Running time:  O(1).
   */
  public int vertexCount(){
	  return numOfVertices;
  };

  /**
   * edgeCount() returns the total number of edges in the graph.
   *
   * Running time:  O(1).
   */
  public int edgeCount(){
	  return numOfEdges;
  }

  /**
   * getVertices() returns an array containing all the objects that serve
   * as vertices of the graph.  The array's length is exactly equal to the
   * number of vertices.  If the graph has no vertices, the array has length
   * zero.
   *
   * (NOTE:  Do not return any internal data structure you use to represent
   * vertices!  Return only the same objects that were provided by the
   * calling application in calls to addVertex().)
   *
   * Running time:  O(|V|).
 * @throws InvalidNodeException 
   */
  public Object[] getVertices() throws InvalidNodeException{
	  Object[] va = new Object[numOfVertices];
	  DListNode cur = (DListNode) vertices.front();
	
	  for(int i=0; i<numOfVertices;i++) {
		  va[i] = ((Vertex) cur.item()).getItem();
		  cur = (DListNode) cur.next();
	  }
	  
	  return va;
  }

  /**
   * addVertex() adds a vertex (with no incident edges) to the graph.
   * The vertex's "name" is the object provided as the parameter "vertex".
   * If this object is already a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(1).
   */
  public void addVertex(Object vertex) {
	  if(isVertex(vertex)){
		  return;
	  }
	  Vertex v = new Vertex(vertex);
	  vertices.insertBack(v);
	  v.vnode = (DListNode) vertices.back();
	  vhash.insert(vertex, v);
	  numOfVertices++;
	  
	  
  };

  /**
   * removeVertex() removes a vertex from the graph.  All edges incident on the
   * deleted vertex are removed as well.  If the parameter "vertex" does not
   * represent a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(d), where d is the degree of "vertex".
 * @throws InvalidNodeException 
   */
  public void removeVertex(Object vertex) throws InvalidNodeException{
	  if(!isVertex(vertex)){
		  return;
	  }
	  Vertex v = (Vertex) vhash.find(vertex).value();
	  DListNode cur = (DListNode) v.adjList.front();
	  int d = v.degree;
	  for(int i =0; i<d;i++){
//		  System.out.println(v.degree);
		  Edge edge = (Edge) cur.item();
		  Object u = edge.u.getItem();
		  Object w = edge.v.getItem();
		  removeEdge(u,w);
		  cur = (DListNode) cur.next();
	  }
	  vertices.remove(v.vnode);
	  vhash.remove(vertex);
	  numOfVertices--;
  }

  /**
   * isVertex() returns true if the parameter "vertex" represents a vertex of
   * the graph.
   *
   * Running time:  O(1).
   */
  public boolean isVertex(Object vertex){
	  return vhash.find(vertex) != null;
  }

  /**
   * degree() returns the degree of a vertex.  Self-edges add only one to the
   * degree of a vertex.  If the parameter "vertex" doesn't represent a vertex
   * of the graph, zero is returned.
   *
   * Running time:  O(1).
   */
  public int degree(Object vertex){
	  if(!isVertex(vertex)){
		  return 0;
	  }
	  Vertex v = (Vertex) vhash.find(vertex).value();
	  return v.degree;
  }

  /**
   * getNeighbors() returns a new Neighbors object referencing two arrays.  The
   * Neighbors.neighborList array contains each object that is connected to the
   * input object by an edge.  The Neighbors.weightList array contains the
   * weights of the corresponding edges.  The length of both arrays is equal to
   * the number of edges incident on the input vertex.  If the vertex has
   * degree zero, or if the parameter "vertex" does not represent a vertex of
   * the graph, null is returned (instead of a Neighbors object).
   *
   * The returned Neighbors object, and the two arrays, are both newly created.
   * No previously existing Neighbors object or array is changed.
   *
   * (NOTE:  In the neighborList array, do not return any internal data
   * structure you use to represent vertices!  Return only the same objects
   * that were provided by the calling application in calls to addVertex().)
   *
   * Running time:  O(d), where d is the degree of "vertex".
 * @throws InvalidNodeException 
   */
  public Neighbors getNeighbors(Object vertex) throws InvalidNodeException{
	  if(!isVertex(vertex) || degree(vertex) == 0){
		  return null;
	  }
	  Neighbors n = new Neighbors();
	  Vertex v = (Vertex) vhash.find(vertex).value();
	  DListNode cur = (DListNode) v.adjList.front();
	  int d = degree(vertex);
	  int c = 0;
	  n.neighborList = new Object[d];
	  n.weightList = new int[d];
	  while(cur != v.adjList.front().prev()){
		  Edge edge = (Edge) cur.item();
		  Object u = edge.u.getItem();
		  Object w = edge.v.getItem();
		  if(edge.u.equals(v)){
			  n.neighborList[c] = w;
		  }else if(edge.v.equals(v)){
			  n.neighborList[c] = u;
		  }
		  n.weightList[c] = edge.weight;
		  cur = (DListNode) cur.next();
		  c++;
	  }
	  
	  return n;
  }

  /**
   * addEdge() adds an edge (u, v) to the graph.  If either of the parameters
   * u and v does not represent a vertex of the graph, the graph is unchanged.
   * The edge is assigned a weight of "weight".  If the graph already contains
   * edge (u, v), the weight is updated to reflect the new value.  Self-edges
   * (where u.equals(v)) are allowed.
   *
   * Running time:  O(1).
   */
  public void addEdge(Object u, Object v, int weight){
	  if(! isVertex(u) || ! isVertex(v)){
		  return;
	  }
	  if(isEdge(u,v)){
		  VertexPair vp = new VertexPair(u,v);
		  Edge e = (Edge) ehash.find(vp).value();
		  e.weight = weight;
	  }else{
		  Vertex uu = (Vertex) vhash.find(u).value();
		  Vertex vv = (Vertex) vhash.find(v).value();
		  Edge e = new Edge(uu,vv,weight);
		  uu.adjList.insertBack(e);
		  uu.degree++;
		  e.unode = (DListNode) uu.adjList.back();
		  if(vv != uu){
			  vv.adjList.insertBack(e);
			  vv.degree++;
			  e.vnode = (DListNode) vv.adjList.back();
		  }
		  
		  VertexPair vp = new VertexPair(u,v);
		  ehash.insert(vp, e);
		  numOfEdges++;
	  }
  }

  /**
   * removeEdge() removes an edge (u, v) from the graph.  If either of the
   * parameters u and v does not represent a vertex of the graph, the graph
   * is unchanged.  If (u, v) is not an edge of the graph, the graph is
   * unchanged.
   *
   * Running time:  O(1).
   */
  public void removeEdge(Object u, Object v){
	  if(! isVertex(u) || ! isVertex(v) || ! isEdge(u,v)){
		  return;
	  }
	  VertexPair vp = new VertexPair(u,v);
	  Edge e = (Edge) ehash.find(vp).value();
	  ehash.remove(vp);
	  Vertex uu = (Vertex) vhash.find(u).value();
	  Vertex vv = (Vertex) vhash.find(v).value();
	  uu.degree--;
	  uu.adjList.remove(e.unode);
	  if(uu != vv){
		  vv.degree--;
		  vv.adjList.remove(e.vnode);
	  }
	  numOfEdges--;
  }

  /**
   * isEdge() returns true if (u, v) is an edge of the graph.  Returns false
   * if (u, v) is not an edge (including the case where either of the
   * parameters u and v does not represent a vertex of the graph).
   *
   * Running time:  O(1).
   */
  public boolean isEdge(Object u, Object v){
	  VertexPair vp = new VertexPair(u,v);
	  return ehash.find(vp) != null;
  }

  /**
   * weight() returns the weight of (u, v).  Returns zero if (u, v) is not
   * an edge (including the case where either of the parameters u and v does
   * not represent a vertex of the graph).
   *
   * (NOTE:  A well-behaved application should try to avoid calling this
   * method for an edge that is not in the graph, and should certainly not
   * treat the result as if it actually represents an edge with weight zero.
   * However, some sort of default response is necessary for missing edges,
   * so we return zero.  An exception would be more appropriate, but also more
   * annoying.)
   *
   * Running time:  O(1).
   */
  public int weight(Object u, Object v){
	  if(!isEdge(u,v)){
		  return 0;
	  }
	  VertexPair vp = new VertexPair(u,v);
	  Edge e = (Edge) ehash.find(vp).value();
	  return e.weight;
  }

}
