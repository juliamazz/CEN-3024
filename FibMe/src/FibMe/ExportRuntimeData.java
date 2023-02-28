package FibMe;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors

public class ExportRuntimeData {
	static long startTime = 0;
	
	public static void main(String[] args)
	{
		try {
		      FileWriter f = new FileWriter("fibTimes.txt");
		      for(int i = 0; i <= 30; i++)
		      {
		    	  startTime = System.nanoTime();
		    	  f.write("i\t" + i + "\t" + iterativeFib(i) + "\t" + (System.nanoTime() - startTime) + "\n");
		      }
		      
		      for(int i = 0; i <= 30; i++)
		      {
		    	  startTime = System.nanoTime();
		    	  f.write("r\t" + i + "\t" + recursiveFib(i) + "\t" + (System.nanoTime() - startTime)  + "\n");
		      }
		      
		      f.close();
		    } 
		catch (IOException e) {
	      System.out.println("An error occurred.");
	      e.printStackTrace();
	    }
		
	}
	
	public static int iterativeFib(int max) {
		int sum = 0;
		int prev = 0;
		int next = 1;
		
		if(max == 0)
			return 0;
		if(max == 1)
			return 1;
		
		for (int i = 1; i < max; i++)
		{
			sum = prev + next;
			prev = next;
			next = sum;
		}
		return sum;
	}
	
	public static int recursiveFib(int i) {
		if(i == 0) {
			return 0;
		}
		if(i == 1 || i == 2) {
			return 1;
		}
		return recursiveFib(i-2) + recursiveFib(i-1);
	}
}
