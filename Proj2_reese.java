/*
	Josh Reese Project 2  
	10.26.10
	CSC520
	Implementation of two binary search trees to store
	stocks.  One tree for names and one tree for prices.
*/
import java.util.*;
public class Proj2_reese
{
	public static void main(String[] args)
	{
		System.out.println("Creating a new tree with stock name Exxon and price 2.5 (insert(Exxon,2.5))");
		Tree tree = new Tree();
		tree.insert("Exxon",2.5);	
		System.out.println("Inserting multiple elements to demonstrate working functions");
		tree.insert("Shell",3.5);
		tree.insert("Abc",4.5);
		tree.insert("Zzz",1.0);
		tree.insert("bbbc",4.5);
		tree.insert("fdj",1.0);
		System.out.println("This is the current tree in alphabetical order (showAll())");
		tree.showAll(tree.nRoot);
		System.out.println();
		
		System.out.println("Running find on stock name Shell");
		Node n = tree.findName(tree.nRoot,"Shell");
		if(n != null)
		{
			System.out.println("Find Shell: " + n.name+" Price: "+n.price);
		}
		else 
			System.out.println("Stock not found"); 
		System.out.println();

		System.out.println("Deleting node with stockname Shell");
		System.out.println("Tree with deleted stock in alphabetical order:");
		tree.delete(n,tree);
		tree.showAll(tree.nRoot);
		System.out.println();

		System.out.println("Running find on stock name Abc");
		n = tree.findName(tree.nRoot,"Abc");
		if(n != null)
		{
			System.out.println("Find Abc: " + n.name+" Price: "+n.price);
		}
		else 
			System.out.println("Stock not found"); 
		System.out.println();

		System.out.println("Deleting node with stockname Abc");
		System.out.println("Tree with deleted stock in alphabetical order:");
		tree.delete(n,tree);
		tree.showAll(tree.nRoot);
		System.out.println();

		System.out.println("Finding max price");
		Node maxPrice;
		maxPrice=tree.findMaxPrice(tree.pRoot);
		System.out.println("Max price = "+maxPrice.price);
		System.out.println();

		System.out.println("Find the range of Exxon stock");
		tree.range(tree,"Exxon");
		
		System.out.println("Changing Zzz price to 1.5");
		tree.change("Zzz",1.5,tree);
		tree.showAll(tree.nRoot);
		System.out.println();
		
		System.out.println("Finding the high average of stockprices >= 1.4");
		tree.highAverage(1.4,tree.pRoot);
		System.out.println();
	
		System.out.println("Additional features:");
		System.out.println("Show price tree (breadth-first)");
		tree.showAllByPrice();
		System.out.println();
		
		System.out.println("Show name tree (breadth-first)");
		tree.showAllByName();
		System.out.println();

		System.out.println("Number of nodes in either tree: "+tree.count(tree.pRoot,1));
		System.out.println("Number of nodes with price below 1.5: "+tree.getLowRange(tree.pRoot,1.4,0));
		System.out.println("Number of nodes with price above 1.5: "+tree.getHighRange(tree.pRoot,1.4,0));
		System.out.println("Sum of prices above 1.5: "+tree.totalAbove(1.4,tree.pRoot,0));
	}
	//one type of node - has a price left/right and name left/right
	static class Node
	{
		public String 	name;
		public double 	price;
		public Node 	nLeft,nRight,pLeft,pRight;
		
		//2 node constructors for initial creation and insertions
		public Node(String stockName, double stockPrice)
		{
			name=stockName;
			price=stockPrice;
		}
		public Node()
		{
		}
	}
	static class Tree
	{
		private Node nRoot,pRoot;
		
		//upon tree creation set name and price roots to null
		public Tree()	{ nRoot = null; pRoot = null; }

		//computes the range of node associated with stockName in tree T
		public void range(Tree T,String stockName)
		{
			Node currentName = findName(T.nRoot,stockName);
			int low = getLowRange(T.pRoot,currentName.price,0);
			int high = getHighRange(T.pRoot,currentName.price,0);
			int count = low+high;
			System.out.println(low+"/"+count+"\t"+high+"/"+count);
		}
		//counts all the nodes < stockPrice
		public int getLowRange(Node n,double stockPrice,int total)
		{
			if(n.pLeft != null)
				total = getLowRange(n.pLeft,stockPrice,total);
			if(n.price < stockPrice)
				total+=1;
			if(n.pRight != null)
				total = getLowRange(n.pRight,stockPrice,total);
			return total;
		}	
		//counts all the nodes > stockPrice
		public int getHighRange(Node n,double stockPrice,int total)
		{
			if(n.pLeft != null)
				total = getHighRange(n.pLeft,stockPrice,total);
			if(n.price > stockPrice)
				total+=1;
			if(n.pRight != null)
				total = getHighRange(n.pRight,stockPrice,total);
			return total;
		}	
		//counts total nodes in price tree
		public int count(Node n,int total)
		{
			//leaf
			if(n.pLeft == null && n.pRight == null)
				return total;
			if(n.pLeft != null)
				total = count(n.pLeft,total+1);
			if(n.pRight != null)
				total = count(n.pRight,total+1);
			return total;
		}
		//compute the average >= stockPrice
		public void highAverage(double stockPrice,Node n)
		{
			double 	total = totalAbove(stockPrice,n,0);
			double 	count = countAbove(stockPrice,n,0);
			System.out.println("High average: "+total/count);
		}
		//computes the total of stockprices >= stockPrice
		public double totalAbove(double stockPrice,Node n,double total)
		{
			if(n.price >= stockPrice)
				total+=n.price;
			//leaf
			if(n.pLeft == null && n.pRight == null)
				return total;
			if(n.pLeft != null)
				total = totalAbove(stockPrice,n.pLeft,total);
			if(n.pRight != null)
				total = totalAbove(stockPrice,n.pRight,total);
			return total;
		}
		//count the number of nodes with prices >= stockPrice
		public double countAbove(double stockPrice,Node n,double total)
		{	
			if(n.price >= stockPrice)
				total+=1;
			//leaf
			if(n.pLeft == null && n.pRight == null)
				return total;
			if(n.pLeft != null)
				total = countAbove(stockPrice,n.pLeft,total);
			if(n.pRight != null)
				total = countAbove(stockPrice,n.pRight,total);
			return total;
		}
		//change price give name and new price and tree	
		public void change(String stockName, double stockPrice, Tree T)
		{
			Node n = findName(T.nRoot,stockName);
			n.price = stockPrice;
			
			deletePrice(n,T);
			insertPrice(n.name,n.price);
		}
		//delete a node - given node, and tree
		public void delete(Node n,Tree T)
		{
				deleteName(n,T);
				deletePrice(n,T);
		}
		//removes node n from name tree
		public void deleteName(Node n, Tree T)
		{
			Node current = T.nRoot;
			Node parent = T.nRoot;
			
			while(current != n)
			{
				parent = current;
				if(current.name.compareToIgnoreCase(n.name) > 0)
					current = current.nLeft;
				else
					current = current.nRight; 
			}
			//two children
			if(current.nLeft != null && current.nRight != null)
			{
				Node max = findMaxName(current.nLeft);
				//deleting root
				if(current.name == T.nRoot.name)
				{
					Node temp = T.nRoot.nRight;
					T.nRoot = T.nRoot.nLeft;
					max.nRight = temp;
					return;
				}
				if(parent.name.compareToIgnoreCase(n.name) > 0)
					parent.nLeft = current.nLeft;
				else 
					parent.nRight = current.nLeft;
				max.nRight = current.nRight;				
			}
			//no children
			if(current.nLeft == null && current.nRight == null)
			{
				if(parent.name.compareToIgnoreCase(n.name) > 0)
					parent.nLeft = null;
				else
					parent.nRight = null;
			}
			//one child
			if(parent.name.compareToIgnoreCase(n.name) > 0)
				parent.nLeft = (current.nLeft != null) ? current.nLeft:current.nRight;
			else
				parent.nRight = (current.nLeft != null) ? current.nLeft:current.nRight;
				
		}
		//deletes a node from the price tree
		public void deletePrice(Node n, Tree T)
		{
			Node current = T.pRoot;
			Node parent = T.pRoot;
			while(current.name != n.name)
			{
				parent = current;
				if(current.price > n.price)
						current = current.pLeft;
				else
					current = current.pRight; 
			}
			//two children
			if(current.pLeft != null && current.pRight != null)
			{
				Node max = findMaxPrice(current.pLeft);
			
				//deleting root
				if(current.name == T.pRoot.name)
				{
					Node temp = T.pRoot.pRight;
					T.pRoot = T.pRoot.pLeft;
					max.pRight = temp;
					return;
				}
				if(parent.price > n.price)
					parent.pLeft = current.pLeft;
				else 
					parent.pRight = current.pLeft;
				max.pRight = current.pRight;				
			}
			//no children
			if(current.pLeft == null && current.pRight == null)
			{
				if(parent.price > n.price)
					parent.pLeft = null;
				else
					parent.pRight = null;
			}
			//one child
			if(parent.price > n.price)
				parent.pLeft = (current.pLeft != null) ? current.pLeft:current.pRight;
			else
				parent.pRight = (current.pLeft != null) ? current.pLeft:current.pRight;
		}

		//find a node based on stockName, requires a starting node for searching
		public Node findName(Node n, String stockName)
		{
			if(n == null)
				return n;
			else if(n.name.compareToIgnoreCase(stockName) == 0)
				return n;
			else
			{
				if(n.name.compareToIgnoreCase(stockName) > 0)
					n = findName(n.nLeft,stockName);
				else if(n.name.compareToIgnoreCase(stockName) < 0)
					n = findName(n.nRight,stockName);
			}
			return n;
		}
		//find a node based on stockPrice, requires a starting node for searching
		public Node findPrice(Node n, double stockPrice)
		{
			if(n == null)
				return n;
			else if(n.price == stockPrice)
				return n;
			else
			{
				if(n.price > stockPrice)
					n = findPrice(n.pLeft,stockPrice);
				else if(n.price < stockPrice)
					n = findPrice(n.pRight,stockPrice);
			}
			return n;
		}
		
		//find max needs a root node to start
		public Node findMaxPrice(Node n)
		{
			if(n.pRight != null)
				return findMaxPrice(n.pRight);					
			else 
				return n;	
		}
		//find max needs a root node to start
		public Node findMaxName(Node n)
		{
			if(n.nRight != null)
				return findMaxName(n.nRight);					
			else 
				return n;	
		}
		
		//insert a name and price into the tree...will separate price and stock into 2 trees
		public void insert(String stockName, double stockPrice)
		{
			insertName(stockName,stockPrice);
			insertPrice(stockName,stockPrice);
		}
		//inserts name into name tree
		public void insertName(String stockName, double stockPrice)
		{
			//insert newNode and iterate with current
			Node newNode = new Node();
			Node current = nRoot;

			newNode.name = stockName;
			newNode.price = stockPrice;
			
			if(nRoot == null) 	
				nRoot = newNode;
			else
			{
				Node parent;
				while(true)
				{
					parent = current;
					//pick direction and set newNode eventually
					if(current.name.compareToIgnoreCase(stockName) > 0)
					{
						current = current.nLeft;
						if(current == null)
						{
							parent.nLeft = newNode;
							return;
						}
					}
					else
					{
						current = current.nRight;
						if(current == null)
						{
							parent.nRight = newNode;
							return;
						}
					}
				}
			}
		}
		//inserts price into price tree
		public void insertPrice(String stockName, double stockPrice)
		{
			//insert newNode and loop with current
			Node newNode = new Node();
			Node current = pRoot;
			
			newNode.price = stockPrice;
			newNode.name = stockName;
			
			if(pRoot == null) 	
				pRoot = newNode;
			else
			{
				Node parent;
				while(true)
				{
					parent = current;
					//pick direction and set newNode eventually
					if(current.price > stockPrice)
					{
						current = current.pLeft;
						if(current == null)
						{
							parent.pLeft = newNode;
							return;
						}
					}
					else
					{
						current = current.pRight;
						if(current == null)
						{
							parent.pRight = newNode;
							return;
						}
					}
				}
			}
		}
		//BF printing of all names
		public void showAllByName()
		{
			Node n = new Node();		

			Queue<Node> q = new LinkedList<Node>();
			q.offer(nRoot);		

			while(q.peek()!=null)
			{
				n=q.remove();
				System.out.println("Name: "+n.name+" Price: "+n.price);
				if(n.nLeft != null)
					q.offer(n.nLeft);
				if(n.nRight != null)
					q.offer(n.nRight);
			}
		}
		//BF printing of all prices
		public void showAllByPrice()
		{
			Node p = new Node();		

			Queue<Node> q = new LinkedList<Node>();
			q.offer(pRoot);

			while(q.peek()!=null)
			{
				p=q.remove();
				System.out.println("Name: "+p.name+" Price: "+p.price);
				if(p.pLeft != null)
					q.offer(p.pLeft);
				if(p.pRight != null)
					q.offer(p.pRight);
			}
		}
		//print in alphabetical order starting at n
		public void showAll(Node n)
		{
			if(n.nLeft != null)
				showAll(n.nLeft);
			System.out.println("Name: "+n.name+" Price: "+n.price);
			if(n.nRight != null)
				showAll(n.nRight);			
		}
	}
}

