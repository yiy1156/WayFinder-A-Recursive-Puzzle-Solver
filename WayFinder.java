import java.util.*;
/**
 * This program finds all solutions using recursive algorithm to the following number puzzle:
 * The puzzle uses an array of positive integers. The objective is to find a path from index zero to the last 
 * index in the array. At each step we need to know the distance to be traveled 
 * and the direction. Each entry in the array is a number indicating the distance to be traveled 
 * on this particular leg of the path. The player (your program) needs to decide the 
 * direction (if the move should be made to the right or to the left). 
 * The starting index is always 0. 
 * @author Yi Yang
 *
 */
public class WayFinder {
	public static void main(String[] args) {
		//If empty argument is passed, an error message is printed and the program terminates
		if (args.length<1) {
			System.err.println("ERROR: incorrect usage. At least one argument is required.");
			System.exit(1);
		}
		
		try {
			//Converts arguments into an integer array
			int[] array = Arrays.stream(args).mapToInt(Integer::parseInt).toArray();
			//Last element has to be 0, or print error message
			if (array[array.length-1]!=0) {
				System.err.println("ERROR: incorrect usage. The last number must be 0.");
				System.exit(1);
			}
			//If a single 0 is passed, then the only way through the puzzle is [ 0 ]
			else if (array.length==1 && array[0] == 0) {
				System.out.println("[ 0 ]");
				System.out.println("There is 1 way through the puzzle.");
				System.exit(1);
			}
			//If any number is not between 0-99, error is printed
		    int invalidNumberCounter=0;
		    for (int i=0; i<array.length; i++) {
		    	if (array[i]<0 || array[i]>99)
		    	invalidNumberCounter++;
		    }
		    if (invalidNumberCounter!=0) {
		    	System.err.println("ERROR: : the puzzle values have to be positive integers in range [0, 99].");
		    	System.exit(1);
		    }
		
		    String currentInfo[]=new String[array.length];
		    //iterate through the String array, and add the integer array elements to it
			for(int i=0;i<array.length;i++) {
				currentInfo[i]=(array[i]+""); 
			}
			
			ArrayList<String> solutions=new ArrayList<>();
			boolean visited[]=new boolean[array.length];
			solvePuzzle(array, 0, currentInfo, visited, solutions);
			
			for(int i=0;i<solutions.size();i++) {
				System.out.println(solutions.get(i));
			}
			if (solutions.size()==0) //print out message when there is zero solutions
				System.out.println("No way through this puzzle.");
			else if (solutions.size()==1) //print out message when there is just one solution
				System.out.println("There is 1 way through the puzzle.");
			else //print out message when there are many ways to solve the puzzle
				System.out.println("There are "+ solutions.size()+" ways through the puzzle.");
		}
		//If invalid arguments such as letters are passed, print error message
		catch (Exception ex) {
			System.out.println("ERROR: Invalid argument.");
			System.exit(1);
		}

	    
	}
	/**
	 * Generates all solutions to the puzzle, and store all the solutions in an ArrayList
	 * @param array the integer array that is to be solved
	 * @param index the index that is currently on
	 * @param currentInfo stores the information at which the index number is to be moved right, left, 
	 * or never touched on
	 * @param visited checks if the number has been visited
	 * @param solutions stores all the solutions 
	 */
	public static void solvePuzzle(int array[], int index, String[] currentInfo, boolean visited[], ArrayList<String> solutions) {
		
		if(index==(array.length-1)) //reached the desired element
			{
			
			StringBuilder ans= new StringBuilder("[ ");
			ans.append(currentInfo[0]);
			//add the current information to the StringBuilder
			for(int i=1;i<currentInfo.length;i++) {
				if (currentInfo[i].contains("R") || currentInfo[i].contains("L"))
					ans.append(",  "+ currentInfo[i]);
				else
					ans.append(",  "+ currentInfo[i]+" ");
			}
			ans.append("]");
			solutions.add(ans.toString());//adds StringBuilder elements to the solutions
			return;
		}
		
		
		visited[index]=true;
		
		int index1= index-array[index]; //index1 goes to the left from the current index
		int index2= index+array[index]; //index2 goes to the right from the current index
		//moving from the current index to the left
		if(index1>=0&&index1<array.length) {
			if(!visited[index1])//checks to prevent infinite loop 
				{
				currentInfo[index]= (array[index]+"L");
				solvePuzzle(array, index1, currentInfo, visited, solutions);
			}
		}
		//moving from the current index to the right
		if(index2>=0&&index2<array.length) {
			if(!visited[index2]) //checks to prevent infinite loop
				{
				currentInfo[index]= (array[index]+"R");
				solvePuzzle(array, index2, currentInfo, visited, solutions);
			}
		}
		
		currentInfo[index]= (array[index]+"");
		visited[index]=false;
		return;
		
	}
	

}
