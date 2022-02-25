// Jose J Saldivar
// GA --> TSP
// Artificial Intelligence

package Homework4; 
import java.util.*;
import java.util.Collections;
import java.util.Scanner;
/*
 * Implement a genetic algorithm to find a solution to the traveling 
 * salesman problem for the following distance matrix below

  |0|	1	2	3	4	5	6	7	8	9	|
  |1|	-									|
  |2|	2	-								|
  |3|	11	13	-							|
  |4|	3	10	5	-						|
  |5|	18	5	19	6	-					|
  |6|	14	3	21	4	12	-				|
  |7|	20	8	2	12	6	19	-			|
  |8|	12	20	5	15	9	7	21	-		|
  |9|	5	17	8	1	7	4	13	6	-	|

 Example
 * Order: 9->2->1->3->4->5->6->7->8->9
 *  9 to 2 is 17 units
 *  2 to 1 is 2 units
 *  1 to 3 is 11 units
 *  ...........
 *  total distance = 99 units
 *  
 *  FIND BETTER SOLUTIONS TO THIS EXAMPLE


 Identify Genetic Algorithm Functions
 * fitness function (evaluation)
 * mutation function (crossover)
 * encoding scheme (terms of [x,y] 10x10 array)


 * Simple Genetic Algorithm
  {
  	initialize populationSize;
  	FITNESS(population);
  	while (TerminationCriteriaNotSatisfied)
  	{
  		SELECT parents for reproduction
  		perform MUTATION and RECOMBINATION
  		FITNESS(current population)
  	}
  }


 * Algorithm

  function GENETIC-ALGORITHM(population, FITNESS-FN) *returns* an individual
 *inputs*: PopulationSize, (a set of individuals)
  			  FITNESS-FN, (evaluates the total fitness of individual)

 *repeat*
  		new_population = null
  		FOR (i=1 : SIZE(population)) DO
  			x = RANDOM-SELECTION(population, FITNESS-FN)
  			y = RANDOM-SELECTION(population, FITNESS-FN)
  			child = REPRODUCE(x,y)
  			IF (small random probability)
  				child = MUTATE(child)
  				Add child to new_population
  		population = new_population
 *until* some individual is fit enough, or enough time has elapsed
 *return* the best individual in population, according to FITNESS-FN
  	---------------------------------------------------------------------------------
  	function REPRODUCE(x,y) *returns* an individual
 *inputs*: x, y, PARENTS

  		n = LENGTH(x)
  		c = RANDOM.NUM(1 to n)
 *return* APPEND (SUBSTRING(x,1,c), SUBSTRING(y,c+1,n))
 */
class State {

	//private String city;
	private int X;
	private int Y;
	private int value;
	//private boolean bool;

	public State(int X, int Y) {
		super();
		this.X = X;
		this.Y = Y;
		//this.value = value;
	}

	public int getX() {
		return X;
	}

	public void setX(int x) {
		this.X = x;
	}

	public int getY() {
		return Y;
	}

	public void setY(int y) {
		this.Y = y;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "State [X =" + X + "| Y =" + Y + "| value =" + value + "]";
	}
}
////////////////////////////////////////////////////////////////////////////////////

class GeneticAlgorithm
{
	Vector population = new Vector();
	Vector newPopulation = new Vector();
	Vector bestSum = new Vector();
	
	int sum = 0;

	int [][]arr = { {0,0,0,0,0,0,0,0,0},
					{2,0,0,0,0,0,0,0,0},
					{11,13,0,0,0,0,0,0,0},
					{3,10,5,0,0,0,0,0,0},
					{18,5,19,6,0,0,0,0,0},
					{14,3,21,4,12,0,0,0,0},
					{20,8,2,12,6,19,0,0,0},
					{12,20,5,15,9,7,21,0,0},
					{5,17,8,1,7,4,13,6,0} };

	public void maxPopulation(String data, String result)
	{
		if(data.length() == 0)
		{
			population.add(result);
			sum += Fitness(result);
			//sum = Fitness(result);
			System.out.println(result);
			return;
		}
		boolean List[] = new boolean[9];

		for(int i=0; i<data.length(); i++)
		{
			char character = data.charAt(i);
			String temp = data.substring(0, i) + data.substring(i+1);

			if(List[character - '1'] == false)
				maxPopulation(temp, result+character);
			List[character - '1'] = true;
		}

	}
	/*
	 * The matrixKey function will grab from the Matrix every X and Y 
	 * value of the cities
	 */
	public int matrixKey(int x, int y)
	{
		State newNode = new State(x,y);

		if(x < y)
		{
			newNode.setValue(arr[y-1][x-1]);
			return newNode.getValue();
		}
		else
		{
			newNode.setValue(arr[x-1][y-1]);
			return newNode.getValue();
		}
	}
	
	public void SP()
	{
		String x = ""; int sum;
		for(int j=0; j<2; j++)
		{
			for(int p=0; p<newPopulation.size()-1; p++)
			{
				x = (String) population.elementAt(p);
				sum = Fitness(x);
				bestSum.add(sum);
				//System.out.println(x);
			}
			if(j == 1)
			{
				// to add the city it will go back from where started
				String X1stCity = String.valueOf(x.charAt(0));
				x += X1stCity;
				
				int solution = (int) Collections.min(bestSum);
				System.out.println("\t\tSOLUTION: { " + x + "   sum: " + solution + " }");
			}
		}
	}
	
	public String availableNumbs(String str)
	{
		//String str = "532";
		String output = "";
		int []arr = {1,2,3,4,5,6,7,8,9};
		int []list = new int[3];

		// this will enter the current subString that is getting passed on to the next
		// genes into an array to be checked.
		for(int p=0; p<3; p++)
		{
			String temp = String.valueOf(str.charAt(p));
			int num = Integer.parseInt(temp);
			list[p] = num;
		}

		// When a match is found, it then takes that number out of the list
		// creating a new list
		for(int i=0; i<3; i++)
		{
			for(int j=0; j<8; j++)
			{
				if(list[i] == arr[j])
				{
					//System.out.println("J = " + j + "\tWorked");
					int count = j;
					while(count<8)
					{
						arr[count] = arr[count+1];
						//System.out.println("Updated arr -> " + arr[count]);
						count++;
					}
				}

			}
		}
		// finally this will convert the integer array into a string to insert
		// back into the reproduce function.
		for(int z=0; z<6; z++)
			output += Integer.toString(arr[z]);
		return output;
	}


	/*
	 * The fitness function runs in the while loop until reaches the end of the string.
	 * Stores position0 at x and then position1 on y, every time they will move up one 
	 * spot to get every value in the string. Then finds x and y values using the matrix
	 * key function above.
	 */
	public int Fitness(String str)
	{
		int sum = 0;
		int countX = 0;		int countY = 1;
		//String str = "123456789";


		//Need to have the first number in string at the end of the string indicating
		//that it will return to that city after visiting all cities.
		String X1stCity = String.valueOf(str.charAt(0));
		str += X1stCity;

		while(countY<10)
		{
			int x = Integer.parseInt(String.valueOf(str.charAt(countX)));
			int y = Integer.parseInt(String.valueOf(str.charAt(countY)));

			sum += matrixKey(x,y);
			countX++; countY++;
		}
		return sum;
	}

	/*
	 * The reproduce function runs by initializing a child that will be the new child node..
	 * After running the Fitness() to get the total distance traveled a winner will be named.
	 * A subset of the winner will be chosen and then the rest of the cities will be chosen to
	 * replace the missing parts of the string.
	 */
	public String Reproduce(String x, String y)
	{
		int xSum = 0;			int ySum = 0;
		String winner = ""; 	String child = "";

		xSum = Fitness(x); 
		ySum = Fitness(y);
		//System.out.println("\nReproduce()\nX = " + x + "\nY = " + y);
		//System.out.println("X sum = " + xSum + "\t Y sum = " + ySum);
		if(xSum < ySum)
			winner = x;
		else
			winner = y;

		String subSet = winner.substring(3,6);
		String key = availableNumbs(subSet);
		//System.out.println("Available numbers to input: { " + key + " }");

		int z = 0; int count = 0;
		while(z < 3)
		{
			String subKey = String.valueOf(key.charAt(count));
			child += subKey;
			count++; z++;
		}
		child += subSet;

		// now set z = 5 to start after the subset
		z = 5;
		while(z < 8)
		{
			String subKey = String.valueOf(key.charAt(count));
			child += subKey;
			count++; z++;
		}
		return child;
	}
	/*
	 * Grab the character number at index 0 and at index 6 and SWAP them
	 */
	public String Mutate(String str)
	{
		String temp1 = String.valueOf(str.charAt(2));
		String temp2 = String.valueOf(str.charAt(6));
		String swap = "";

		for(int i=0; i<9; i++)
		{
			if(i == 2)
				swap += temp2;
			else if(i == 6)
				swap += temp1;
			else
			{
				String subkey = String.valueOf(str.charAt(i));
				swap += subkey;	
			}
		}
		return swap;
	}

	///////////////////////////////////////////////////////////////////////////////////
	public void GA(int populationSize)
	{
		//String child = "986125734";  TEST
		String x; 	String y;
		String child = "";	String best = "";
		int Generations = 4;
		int keyPercent = Math.floorDiv(sum, 50); 	// 658627 is the 1-2 PERCENTILE

		for(int i=0; i<populationSize; i++)
		{
			x = (String) population.elementAt(i);
			y = (String) population.elementAt(i+1);

			child = Reproduce(x,y);
			int temp = Fitness(child);
			temp = Math.floorDiv(sum, temp);
			
			// IF the childs probability is higher then gets mutated by mutate function
			if(temp > keyPercent)
			{
				//System.out.println("ENTERED THE IF STATEMENT FOR MUTATION");
				child = Mutate(child);
				newPopulation.add(child);
			}
			else
				newPopulation.add(child);
		}
		SP();
	}
///////////////////////////////////////////////////////////////////////////////////////////////
}
public class GA_TSP {

	public static void main(String[] args) {

		GeneticAlgorithm ga = new GeneticAlgorithm();
		Scanner scan = new Scanner(System.in);
		int count = 0;
		System.out.println("\t\t\t* Genetic Algorithm TSP *");
		
		//Feel free to choose any String provided or to create your own random string from 1-9
		String inputString = "123456789";
		//String inputString = "781369452";
		//String inputString = "941736258";
		
		ga.maxPopulation(inputString, "");
		System.out.println("Max Population: " + ga.population.size() + "\n");
	
	
		while(count < 1)
		{
			System.out.println("** Population Sizes ** \n 1 = 500 \n 2 = 1000 \n 3 = 10000 \n 4 = 100000 "
								+ "\n 5 = MAX (362880) \n 6 = Random Number");
			System.out.print("\nEnter choice from above: ");
			int input = scan.nextInt();
			
			switch(input)
			{
			case 1:
				//theTree.displayTree();
				ga.GA(500);
				break;
			case 2:
				ga.GA(1000);
				break;
			case 3:
				ga.GA(10000);
				break;
			case 4:
				ga.GA(100000);
				break;
			case 5:
				ga.GA(362879);
				break;
			case 6:
				System.out.print("Enter random number (1 to 362880): ");
				input = scan.nextInt();
				if(input == 362880)
					ga.GA(input-1);
				else
					ga.GA(input);
				break;
			default:
				System.out.print("Invalid entry\n");
			}  // end switch
			count++;
		}  // end while
	}
}