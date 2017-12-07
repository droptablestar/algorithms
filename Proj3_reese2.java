/*
	Josh Reese
	CSC520 - Project 3
	Implementation of graphs using adjacency lists and finding MST with
	Kruskal's algorithm.  Maximum weight of 1000000 for edge.
*/
import java.util.*;

public class Proj3_reese
{
	public static void main(String[] args)
	{
		Scanner  s = new Scanner(System.in);
//		Graph 	 graph;
		Iterator iterator;
		
		int n,i,j,e,u,v,c;		

		n=s.nextInt();
		e=s.nextInt();
		Vertex vertex[]=new Vertex[n+1];
		Edge edge[] = new Edge[e];
//		graph = new Graph(n);
/*		
		//add nodes
		for(i=1;i<=n;i++)
		{
			vertex = new Vertex(-1,i);
			graph.list[i].add(vertex);			
		}
*/
		for(i=1;i<=n;i++)
		{
			vertex[i] = new Vertex(i);
			vertex[i].parent=vertex[i];
		}
		for(i=0;i<e;i++)
		{
			u=s.nextInt();
			v=s.nextInt();
			c=s.nextInt();
			
			edge[i] = new Edge(u,v,c);
//			graph.list[u].add(vertex);
		}
//		showGraph(graph,n);
		ArrayList <Edge>MST = new ArrayList<Edge>();
		MST=Kruskal(edge,vertex,e,n);
		i=0;
		while(MST.get(i)!=null)
		{
			System.out.println("start: "+MST.get(i).start+" end: "+MST.get(i).end);
			i++;
		}
	}
	
	public static ArrayList Kruskal(Edge[] edgeList,Vertex[] vertexList,int e,int n)
	{
		LinkedList<Vertex> 	tree = new LinkedList<Vertex>();
		Edge			 	edge = new Edge(-1,-1,-1);
		Vertex 				v1 	 = new Vertex();	
		Vertex 				v2 	 = new Vertex();	
		
		ArrayList <Edge> e1;
		ArrayList <Edge> e2;
		Edge smallEdge;
		int smallIndex,i;

		e1 = new ArrayList();
		e2 = new ArrayList();
	
		//put all edges in e2
		for(i=0;i<e;i++)
			e2.add(edgeList[i]);

		//find the smallest initially and create the first disjoint-set
		smallEdge = findSmallest(e2);
		smallIndex = e2.indexOf(smallEdge);
		e2.remove(smallIndex);	
		e1.add(smallEdge);
		makeSet(vertexList[smallEdge.start]);
		vertexList[smallEdge.end].parent=vertexList[smallEdge.start];
		for(i=1;i<=n;i++)
			System.out.println("parents: "+vertexList[i].parent.id);
		while(e1.size()<n-1 && e2.size()!=0)
		{
			smallEdge = findSmallest(e2);
			smallIndex = e2.indexOf(smallEdge);
			e2.remove(smallIndex);	
			e1.add(smallEdge);

//System.out.println("Cost: "+smallEdge.cost+" start: "+smallEdge.start+" end: "+smallEdge.end);

			v1=find(vertexList[smallEdge.start]);	
			v2=find(vertexList[smallEdge.end]);	
			if(v1!=v2)
				union(v1,v2);
			else
			{
				makeSet(vertexList[smallEdge.start]);
				vertexList[smallEdge.end].parent=vertexList[smallEdge.start];
			}
		for(i=1;i<=n;i++)
			System.out.println("parents: "+vertexList[i].parent.id);
//System.out.println("parent1: "+v1.parent.id+" parent2: "+v2.parent.id);
//			while(true) ;
		}
		
		return e1;	
	}
	public static void union(Vertex x, Vertex y)
	{
		Vertex xRoot = find(x);
		Vertex yRoot = find(y);
		
		yRoot.parent=xRoot;
	}
	public static Vertex find(Vertex v)
	{
		if(v.parent == v)
			return v;
		else 
			return find(v.parent);
	}
	public static void makeSet(Vertex v)
	{
		v.parent = v;
	}
	public static Edge findSmallest(ArrayList<Edge> e)
	{
		int smallCost=1000000, size=e.size(),i;
		Edge smallEdge=new Edge(0,0,0);
		for(i=0;i<size;i++)
		{
			if(e.get(i).cost<smallCost)
			{
				smallCost=e.get(i).cost;
				smallEdge=e.get(i);	
			}
		}
//		System.out.println("Cost: "+smallEdge.cost+" start: "+smallEdge.start+" end: "+smallEdge.end);
		return smallEdge;
	}
	private static class Vertex
	{
		private int id;
		private Vertex parent;

		public Vertex(int nodeID) {id=nodeID;}
		public Vertex(){}
	}
	private static class Edge
	{
		private int start,end,cost,id;
		
		public Edge(int s,int e,int c) {start=s; end=e;cost=c;}
	}
	private static class Node
	{
		private Edge iData;
		public Node(Edge key) {iData=key;}
		public Edge getKey() {return iData;}
		public void setKey(Edge id) {iData=id;}
	}
	private static class Heap
	{
		private Node[] heapArray;
		private	int maxSize;
		private int currentSize;
		
		public Heap(int mx) {maxSize=mx; currentSize=0; heapArray=new Node[maxSize];}
		public boolean insert(Edge key)
		{
			if(currentSize==maxSize)
				return false;
			Node newNode = new Node(key);
			heapArray[currentSize]=newNode;
			trickleUp(currentSize++);
			return true;
		}
		public void trickleUp(int index)
		{
			int parent = (index-1)/2;
			Node bottom = heapArray[index];
			
			while(index>0 && heapArray[parent].getKey().cost<bottom.getKey().cost)
			{
				heapArray[index]=heapArray[parent];
				index=parent;
				parent=(parent-1)/2;
			}
			heapArray[index]=bottom;
		}
		public Node remove()
		{
			Node root = heapArray[0];
			heapArray[0]=heapArray[--currentSize];
			trickleDown(0);
			return root;
		}
		public void trickleDown(int index)
		{
			int largerChild;
			Node top = heapArray[index];       // save root
			while(index < currentSize/2)       // while node has at
			{                               //    least one child,
				int leftChild = 2*index+1;
				int rightChild = leftChild+1;
				// find larger child
				if(rightChild < currentSize &&  // (rightChild exists?)
				   heapArray[leftChild].getKey().cost < heapArray[rightChild].getKey().cost)
					largerChild = rightChild;
				else
					largerChild = leftChild;
				// top >= largerChild?
				if( top.getKey().cost >= heapArray[largerChild].getKey().cost)
					break;
				// shift child up
				heapArray[index] = heapArray[largerChild];
				index = largerChild;            // go down
			}  // end while
			heapArray[index] = top;            // root to index
		}
		public boolean change(int index, Edge newValue)
		{
			if(index<0 || index>=currentSize)
				return false;
			Edge oldValue = heapArray[index].getKey(); // remember old
			heapArray[index].setKey(newValue);  // change to new
			
			if(oldValue.cost < newValue.cost)        // if raised,
				trickleUp(index);                // trickle it up
			else                                // if lowered,
				trickleDown(index);              // trickle it down
			return true;
		}
	}
	
}
