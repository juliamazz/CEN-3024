package runner;
import java.util.Scanner;

public class runner {

	public static void main(String[] args) {
		
		// User can decide to use either the Iterative or Recursive Function
		Scanner userInput = new Scanner(System.in);
		int selection = -1;
		
		while (selection != 3) {
			System.out.println("Select Iterative [1] or Recursive [2] by typing the desired number. Type [3] to exit.");
			selection = userInput.nextInt();
			
			if (selection == 1)
			{
				// User input the desired Fibonacci Sequence
				System.out.println("Enter the desired Fibonacci Sequence: ");
				int maxNum = userInput.nextInt();
				
				// Output the time taken for the sequence to be generated
				long startTime = System.nanoTime();
				long endTime = 0;
				
				int sum = iterativeFib(maxNum);
				endTime = System.nanoTime();
				
				for(int i = 0; i <= maxNum; i++){
					System.out.print(recursiveFib(i) + " ");
				}
				System.out.println("\nTime taken: " + (endTime - startTime) + " nanoseconds.\n");
			}
			
			else if (selection == 2)
			{
				// User input the desired Fibonacci Sequence
				System.out.println("Enter the desired Fibonacci Sequence: ");
				int maxNum = userInput.nextInt();
				
				// Output the time taken for the sequence to be generated
				long startTime = System.nanoTime();
				long endTime = 0;
				
				int sum = recursiveFib(maxNum);
				endTime = System.nanoTime();
				
				for(int i = 0; i <= maxNum; i++){
					System.out.print(recursiveFib(i) + " ");
				}
				System.out.println("\nTime taken: " + (endTime - startTime) + " nanoseconds.\n");
			}
			else if (selection == 3)
			{
				break;
			}
			
			else
			{
				System.out.println("Invalid selection. Select Iterative [1] or Recursive [2] by typing the desired number. Type [3] to exit.");
				selection = userInput.nextInt();
			}
		}
		userInput.close();
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

