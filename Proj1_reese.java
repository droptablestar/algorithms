/*
	Josh Reese Project 1
	9.22.10
	CSC 520
	Implementation of Linked List without library ADT's
*/

public class Proj1_reese
{
	public static void main(String[] args)
	{
		//create and initialize avail
		avail myLL=new avail();
		avail.initialize();

		myLL.addlast("Nalgeen",3);

		myLL.addlast("Kayla",2);
		myLL.addlast("Josh",2);
		myLL.addlast("tsivid",3);
		myLL.addfirst("giant",3);
		myLL.addfirst("xxxxx",3);
		myLL.addlast("Alton",2);
		myLL.addlast("Chicken",2);

		myLL.show(2);
		myLL.sort(2);
		myLL.show(2);

		myLL.show(3);
		myLL.sort(3);
		myLL.show(3);
/*
		System.out.println("Adding four elements to the end of the list");
		myLL.addlast("Kayla",2);
		myLL.addlast("Josh",2);
		myLL.addlast("Alton",2);
		myLL.addlast("Chicken",2);
		myLL.show(2);

		System.out.println("\nReversing the list");
		myLL.reverse(2);
		myLL.show(2);

		System.out.println("\nAdding two elements to the beginning of the list");
		myLL.addfirst("Dr. Wu",2);
		myLL.addfirst("Jeff",2);
		myLL.show(2);

		System.out.println("\nDeleting the last element of the list");
		myLL.deletelast(2);
		myLL.show(2);

		System.out.println("\nDeleting the first element of the list");
		myLL.deletefirst(2);
		myLL.show(2);

		System.out.println("\nCreating a new list to show append function");
		myLL.addfirst("This",3);
		myLL.addfirst("Is",3);
		myLL.addfirst("List",3);
		myLL.addfirst("Three",3);

		System.out.println("\nList 2:");
		myLL.show(2);

		System.out.println("\nList 3:");
		myLL.show(3);

		System.out.println("\nAppending list three on to list 2");
		myLL.append(2,3);

		System.out.println("\nNew list 2:");
		myLL.show(2);
*/
	}
	public static class avail
	{
		//create variables
		
		static String[] info = new String[100];
	 	static int[] next = new int[100];
		static int[] L = new int[4];
		static int a;

		public static void initialize()
		{
			//set a
			a=0;

			for(int i=0;i<99;i++)
				next[i]=i+1;
			next[99]=-1;

			for(int i=0;i<4;i++)
				//use -1 for null
				L[i]=-1;
		}
		//this is all essentially junk after this point
		public static void sort(int m)
		{
			int i=L[m],j=L[m],k=0,n=0,size,smallest=L[m];
			size=count(m);
			int[] sorted=new int[size];	
			//find smallest element & remove it
			//loop until only 1 element
			while(count(m)>1)
			{
				//loop through all and compare to the current smallest
				while(n<count(m))
				{
//System.out.println("Comparing: info["+i+"]: "+info[i]+" to info["+smallest+"]: "+info[smallest]);
						if(info[i].compareToIgnoreCase(info[smallest]) < 0)
							smallest=i;
					//are we at the end?
					if(next[i]!=-1)
						i=next[i];
					else
						i=L[m];
					n+=1;
				}
				//insert smallest element into sorted
				sorted[k]=smallest;
				k++;
				
				//find element before smallest
				while(next[i]!=smallest && next[i]!=-1)
					i=next[i];	
				//store this element to put in the list later
				j=i;
				//are we at the end? remove smallest
				if(next[i]!=-1)
					next[i]=next[next[i]];
				dispose(smallest);

				//reset
				i=L[m];
				smallest=i;				
				n=0;
			}
			//sorted pointers in sorted now make that L
			sorted[k]=j;
			L[m]=sorted[0];
			j=L[m];
			//loop through sorted and create new LL			
			for(i=1;i<size;i++)
			{
				next[j]=sorted[i];	
				j=next[j];
			}
			next[j]=-1;
		}
		public static int fetch()
		{
			int temp;
			temp=next[a];
			a=next[a];
			return temp;
		}
		public static void dispose(int x)
		{
			//point the value at x to the beginning
			next[x]=a;
			a=x;
		}
		public static void addfirst(String X, int m)
		{
			int temp;
			//put the info in first available location
			//point the beginning at new location and point the beginning
			//at the previous beginning
			info[a]=X;
			temp=L[m];
			L[m]=a;
			a=next[a];
			next[L[m]]=temp;
		}
		public static void addlast(String X, int m)
		{
			//is L[m] empty?
 			if(L[m]!=-1)
			{
				int i=L[m];
				info[a]=X;
				//locate last element
				while(next[i]!=-1)
					i=next[i];
				//add on last element
				next[i]=a;
				a=next[a];
				next[next[i]]=-1;
			}
			//L[m] is empty
			else
			{
				//just add the element
				info[a]=X;
				L[m]=a;
				a=next[a];
				next[L[m]]=-1;
			}
		}
		public static void deletefirst(int m)
		{
			//point L[m] at second element and release the first element
			int temp=a;
			a=L[m];
			L[m]=next[L[m]];
			next[a]=temp;
		}
		public static void deletelast(int m)
		{
			int i=L[m],j=L[m],temp=a;
			//locate last element
			while(next[i]!=-1)
				i=next[i];
			//make the next available the deleted place and point its next
			//to previous available location
			a=i;
			next[a]=temp;
			//locate last element
			while(next[j]!=i)
				j=next[j];
			next[j]=-1;

		}
		public static void show(int m)
		{
			//print out all elements with next values
			int i=L[m];
			while(i!=-1)
			{
				System.out.println(info[i] + "\tat: "+ i + " next: " + next[i]);
				i=next[i];
			}
		}
		public static void reverse(int m)
		{
			int i=L[m],prev=-1,n;

			//cycle through list and reverse all pointers but last
			while(next[i]!=-1)
			{
				n=next[i];
				next[i]=prev;
				prev=i;
				i=n;
			}
			//reverse last pointer and set L[m] to the end
			L[m]=i;
			next[L[m]]=prev;
		}
		public static void append(int j, int k)
		{
			int i=L[j];
			//find the end of the j list
			while(next[i]!=-1)
				i=next[i];
			//point the end of the j list to the beginning of k
			next[i]=L[k];
			//clear the k list
			L[k]=-1;
		}
		public static int count(int m)
		{
			int i=L[m],total;

			if(L[m]!=-1) total=1;
			else return 0;

			while(next[i]!=-1)
			{
				i=next[i];
				total++;
			}
			return total;
		}
	}
}

