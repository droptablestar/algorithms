/*
	Josh Reese
	CSC520 - Project 3
	Implementation of graphs using adjacency lists and finding MST with
	Kruskal's algorithm.  Maximum weight of 1000000 for edge.
	Edges stored in MAX heap, MST stored in homemade tree
	
	Had to give up.  Not sure how to organize the binary tree that would 
	be the MST. 
	
	Edges are stored in a max heap, MST is right now a balanced binary tree.
	This is sloppy and unfinished.
*/
import java.util.*;

public class Proj3_reese
{
	public static void main(String[] args)
	{
		Scanner  s = new Scanner(System.in);		
		int n,i,j,e,u,v,c;		

		n=s.nextInt();
		e=s.nextInt();
		Vertex vertex[]=new Vertex[n+1];
		Heap edgeHeap=new Heap(e);			//new heap with max size = # of edges
		Edge edge[] = new Edge[e];

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
			edgeHeap.insert(new Edge(u,v,c));
//			graph.list[u].add(vertex);
		}
//		edgeHeap.displayHeap();
		Tree MST = new Tree();
		MST=Kruskal(edge,vertex,e,n);
		MST.printTree();
//		MST.displayHeap();
		MST.Path(vertex[1],vertex[5]);
	}
	public static Tree Kruskal(Edge[] edgeList,Vertex[] vertexList,int e,int n)
	{
		LinkedList<Vertex> 	tree = new LinkedList<Vertex>();
		Edge			 	edge = new Edge(-1,-1,-1);
		Vertex 				v1 	 = new Vertex();	
		Vertex 				v2 	 = new Vertex();	
		Tree				MST	 = new Tree();
//		Heap				MST = new Heap(e);
		Edge				insert;
		treeNode			root;
		ArrayList <Edge> e1=new ArrayList();
		ArrayList <Edge> e2=new ArrayList();
		Edge smallEdge;
		int smallIndex,i;

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
		MST.insert(smallEdge);
		
//		for(i=1;i<=n;i++)
//			System.out.println("parents: "+vertexList[i].parent.id);
		while(e1.size()<n-1 && e2.size()!=0)
		{
			smallEdge = findSmallest(e2);
			smallIndex = e2.indexOf(smallEdge);
			e2.remove(smallIndex);	
			v1=find(vertexList[smallEdge.start]);	
			v2=find(vertexList[smallEdge.end]);	
			if(v1!=v2)
			{
				union(v1,v2);
				e1.add(smallEdge);
				MST.insert(smallEdge);
			}
			else
			{
				makeSet(vertexList[smallEdge.start]);
				vertexList[smallEdge.end].parent=vertexList[smallEdge.start];
			}
		}
		System.out.println();
//		for(i=1;i<=n;i++)
//			System.out.println("parents: "+vertexList[i].parent.id);
//		MST.printTree(MST.root,32);
		return MST;	
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
		public void displayHeap()
		{
			int nBlanks = 32;
			int itemsPerRow = 1;
			int column = 0;
			int j = 0;                          // current item
			String dots = "...............................";
			System.out.println(dots+dots);      // dotted top line
			
			while(currentSize > 0)              // for each heap item
			{
				if(column == 0)                  // first item in row?
					for(int k=0; k<nBlanks; k++)  // preceding blanks
						System.out.print(' ');
				// display item
				System.out.print(heapArray[j].getKey().start+"-"+heapArray[j].getKey().end);
				
				if(++j == currentSize)           // done?
					break;
				
				if(++column==itemsPerRow)        // end of row?
				{
					nBlanks /= 2;                 // half the blanks
					itemsPerRow *= 2;             // twice the items
					column = 0;                   // start over on
					System.out.println();         //    new row
				}
				else                             // next item on row
					for(int k=0; k<nBlanks*2-2; k++)
						System.out.print(' ');     // interim blanks
			}  // end for
			System.out.println("\n"+dots+dots); // dotted bottom line
		}
		public void Path(Heap MST, Vertex u, Vertex v,int n)
		{
				Edge start,end,pStart,pEnd;
			start=findStart(u.id);
			end=findStart(v.id);
			System.out.println("s: "+start.start+"-"+start.end);
		}
		
		public Edge findStart(int start)
		{
			int i=0;
			while(i<maxSize)
			{
				if(heapArray[i].getKey().start==start)
					return heapArray[i].getKey();
				else
					i++;
			}
			Edge no = new Edge(0,0,0);
			return no;

		}
	}
	private static class Tree
	{
		private treeNode leftChild,rightChild,root;
		private	ArrayList<Edge> bTree=new ArrayList();
		public Tree () {bTree.add(new Edge(0,0,0));}
		public void insert(Edge e) {bTree.get(0).cost+=1; bTree.add(e);}
		public void printTree()
		{
			for(int i=1;i<=bTree.get(0).cost;i++)
			{
				System.out.print(bTree.get(i).start+"-"+bTree.get(i).end);
				System.out.print(" -> ");
				if(2*i<=bTree.get(0).cost)
					System.out.print("LEFT: "+bTree.get(2*i).start+"-"+bTree.get(2*i).end);
				if(2*i+1<=bTree.get(0).cost)
					System.out.print(" RIGHT: "+bTree.get(2*i+1).start+"-"+bTree.get(2*i+1).end);
				System.out.println();
			}
		}
		public void Path(Vertex u, Vertex v)
		{
			Edge 	start,end;
			start=findStart(u.id);
			end=findEnd(v.id);
			System.out.println(start.start+"-"+start.end);
		}
		
		public Edge findStart(int start)
		{
			for(int i=1;i<=bTree.get(0).cost;i++)
				if(bTree.get(i).start==start || bTree.get(i).end==start)
					return bTree.get(i);
			return new Edge(0,0,0);
		}
		public Edge findEnd(int end)
		{
			for(int i=1;i<=bTree.get(0).cost;i++)
				if(bTree.get(i).end==end)
					return bTree.get(i);
			return new Edge(0,0,0);
		}
	}
	static class treeNode
	{
		public int			start,end,cost;
		public treeNode 	leftChild,rightChild,parent;
		
		//2 node constructors for initial creation and insertions
		public treeNode(Edge e) {start=e.start;end=e.end;cost=e.cost;}
		public treeNode() {}
	}
}
