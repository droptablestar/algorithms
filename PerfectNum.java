import java.util.*;
import java.math.*;

public class PerfectNum
{
	public static void main(String[] args)
	{
		System.out.println("Enter 1 for brute force, 2 for intelligent: ");
		Scanner scanin=new Scanner(System.in);
		int i=scanin.nextInt();
		if(i==1)
			bruteForce();
		else if(i==2)
			intelligent();
		else
			System.out.println("Invalid input.");			
	}
	public static void bruteForce()
	{
	 	int numFound=0,k=2,sum,i;
		float stop,start=System.nanoTime();
		while(numFound<6)
		{
			sum=0;
			for(i=1;i<=k/2;i++)
			{
				if(k%i==0)
					sum=sum+i;
			}
			stop=System.nanoTime();
			if(sum==k)
				System.out.printf("Found perfect number: %d in %2.9f ns%n",k,stop-start);
			k++;
		}
	}
	public static void intelligent()
	{
		int numFound=0,k=2,sum,i;
		float stop,start=System.nanoTime();
		while(numFound<6)
		{
			sum=1;			
			for(i=2;i<=(int)Math.sqrt((double)k);i++)
			{
				if(k%i==0)
					sum=sum+i+k/i;
			}
			stop=System.nanoTime();
			if(sum==k)
				System.out.printf("Found perfect number: %d in %2.9f ns%n",k,stop-start);
			k++;
		}
	}
}
