/* The function of this console based code is to show the time it takes for for different list to process the same 
 * data given different various text files that differ in size. We will be first parsing the file and after each pass 
 * we will close the file and re open it for the next list to process the same data and put it into each of the four list.
 * We will be looking at the unsorted, sorted, a list that sends a word to the front of the list based on the occurrence 
 * of the word, and finally a list that shifts a node up one spot based on the occurrence of the word. Each of the list 
 * will collect 5 types of data. It will first collect the total words, then the distinct words in the list, how many 
 * comparisons that list made, how many times the reference changed, and how much time elapsed for it to put the text file 
 * into a list.
 * 
 * 
 *  Author: Samuel Butler
 *  EECS 2500
 *  Date: 11/05/2018
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ProcessTextFile
{
	public static void main(String[] args) throws FileNotFoundException
	{
		// The function of main is to call the specific list that is to be processed and to open and close the file after
		// each iteration through a different list. We will give the program a specific text file to process by using 
		// jave.io.File to import a text file. Of course there is a possibly that the file does not exist, but since we 
		// are manually typing it in and not relying on a user to type in a file name, the only time the exception could
		// occur is if we type the file name in wrong.
		
		final String FILE_NAME = "test";
		File file	= new java.io.File("F:\\2500 - Butler, Samuel\\2500-Lab2\\TestTextFiles-Lab2\\" + FILE_NAME + ".txt");
		Scanner input = null;
		try
		{
			input = new Scanner(file);
		}
		catch(FileNotFoundException e)
		{
			// let's the user (me) know if I misspelled the text file name or if the file isn't there
			System.out.println("File not Found.");
			System.exit(1);
		}
		
									FirstPassParsing(input);	input.close();
									
		//input = new Scanner(file);	SecondPassUnsorted(input);	input.close();
		
		input = new Scanner(file);	ThirdPassSorted(input);		input.close();
		
		//input = new Scanner(file);	FourthPassToFront(input);	input.close();
		
		//input = new Scanner(file);	FifthPassShift(input);		input.close();
		
		input = new Scanner(file);	SixthPassSkip(input); 		input.close();
		
	}// end main
	
	public static void FirstPassParsing (Scanner input)
	{
		// First parse will consist of just collecting the word count and how long it took to 
		// parse the file. We collect the before time before it enters the loop and the after time
		// after it exits the loop so be able to get the value of how long it took to parse the file.
		
		double beforeTime1	= System.currentTimeMillis();
		
		int wordCount1		= 0;
		while (input.hasNext())
		{
			if (!cutPunctuation(input.next().toLowerCase()).equals(""))	// gets the word, changes it to lower case, then sends it to the cutPunctuation method
				wordCount1++;
		}
		
		String pass1Time = String.format("%.3f", (System.currentTimeMillis() - beforeTime1) / 1000);
		System.out.println("First Pass: \"Parsing Text\" \n"      + " -Total Words: " + wordCount1 + 
						   " words\n" + " -Total Time Elapsed: "  + pass1Time		  + " seconds" + "\n");
	}// end FirstPassParsing
	
	public static void SecondPassUnsorted(Scanner input)
	{
		// First list is the unsorted, we create an instance of the unsorted class to be able to add
		// nodes(words) to our list. The unsorted list will add the nodes in no certain order to the list
		// We will also collect the time and before time and the total word count of the file. 
		
		ListUnsorted<String> unsorted	= new ListUnsorted<String>();
		double beforeTime	= System.currentTimeMillis();
		
		while (input.hasNext())											// Once we have the word we cans end it to the add method to process the word to see if it needs
			unsorted.add(cutPunctuation(input.next().toLowerCase()));	// to add it to the list or if it's already in the list just increment the count of the word being studied
		
		double afterTime	= System.currentTimeMillis();
		
		System.out.print("Second Pass: \"Unsorted List\"\n");
		printStats(unsorted, beforeTime, afterTime);	// once we have processed out list we can send it to the printStats method
		
	}// endSecondPassUnsorted
	
	public static void ThirdPassSorted (Scanner input)
	{
		// Next list is the sorted list, this list puts nodes in alphabetical order. 
		// Half of the time the list will only traverse half of the list because if it
		// has a word say "apple" and we reach the word "bear" we can stop cause we know it
		// is not after bear
		
		ListSorted<String> sorted	= new ListSorted<String>();
		double beforeTime	= System.currentTimeMillis();
		
		while (input.hasNext())											// Once we have the word we cans end it to the add method to process the word to see if it needs
			sorted.add(cutPunctuation(input.next().toLowerCase()));		// to add it to the list or if it's already in the list just increment the count of the word being studied
		
		double afterTime	= System.currentTimeMillis();
		
		System.out.print("Third Pass: \"Sorted List\"\n");
		printStats(sorted, beforeTime, afterTime);	// once we have processed out list we can send it to the printStats method
		
	}// endThirdPassSorted
	
	public static void FourthPassToFront(Scanner input)
	{
		// The next list is where each occurrence of the word we will move that node to the front
		// and increment it's count. This makes searching the list very fast since the words that
		// occur the most will be at the front of the list.
		
		ListAdjustableToFront<String> adjFront	= new ListAdjustableToFront<String>();
		double beforeTime	= System.currentTimeMillis();
		
		
		while (input.hasNext())											// Once we have the word we cans end it to the add method to process the word to see if it needs
			adjFront.add(cutPunctuation(input.next().toLowerCase())); 	// to add it to the list or if it's already in the list just increment the count of the word being studied
		
		double afterTime	= System.currentTimeMillis();
		
		System.out.print("Fourth Pass: \"Self-adjusting List (Move Node to Front)\"\n");
		printStats(adjFront, beforeTime, afterTime);	// once we have processed out list we can send it to the printStats method
		
	}// endFourthPassToFront
	
	public static void FifthPassShift (Scanner input)
	{
		// The next list is where we shift the node up one. We will see if the  word
		// we are studying is already in the list, if it is then we will move the node
		// up one and increment its count, if not it will add it to the front of the list
		
		ListAdjustableUpOne<String> adjShift	= new ListAdjustableUpOne<String>();
		double beforeTime	= System.currentTimeMillis();
		
		while (input.hasNext())											// Once we have the word we cans end it to the add method to process the word to see if it needs
			adjShift.add(cutPunctuation(input.next().toLowerCase()));	// to add it to the list or if it's already in the list just increment the count of the word being studied

		double afterTime	= System.currentTimeMillis();
		
		System.out.print("Fifth Pass: \"Self-adjusting List (Shift Node)\"\n");
		printStats(adjShift, beforeTime, afterTime);	// once we have processed out list we can send it to the printStats method
		
	}// endFifthPassShift
	
	public static <T> void printStats(DefaultList<T> list, double before, double after)
	{
		// the PrintStats method will display all of the required stuff needed to know about the
		// the file, the following is a list of what we need to print out.
		//	1. Total Words
		//	2. Distinct Words
		//	3. Comparisons
		//	4. Reference Changes
		//	5. Time Elapsed
		System.out.println (" -Total Words: "                       + list.TotalWordCount()							 + " words\n"+ 
	   			   			" -Total of Distinct Words: "           + list.DistinctWordCount()						 + " words\n"+ 
	   			   			" -Total Number of Comparisons: "       + list.compareNum()								 + " Comparisons\n"+
	   			   			" -Total Number of Reference Changes: " + list.refNum()									 + " changes\n"+ 
	   			   			" -Total Time Elapsed: "                + String.format("%.3f", (after - before) / 1000) + " seconds\n" );
	}
	
	public static String cutPunctuation(String readFile)
	{
		// Since all the list require we trim leading and trailing punctuation, it made
		// sense to put it in a method and just have the specific list call cutPunctuation.
		// How it works is it will first check the char at 0, if it is a punctuation it will
		// go into the loop, use substring(1 , readFile.length()) to cut it off and go back around
		// to the while loop until there are no more leading punctuation. Trailing punctuation works
		// in the same way except now we look at the end of the file and we do from 0 to readFile.length()-1
		
		while (!Character.isLetter(readFile.charAt(0)))
		{
			readFile = readFile.substring(1, readFile.length());
			if (readFile.equals("")) break;
		}
		
		if (!readFile.equals(""))
		{
			while (!Character.isLetter(readFile.charAt(readFile.length()-1)))
				readFile = readFile.substring(0, readFile.length()-1);
		}
		return readFile;
	}
	
	public static void SixthPassSkip(Scanner input)
	{
		// The final list, and by far the coolest list to work with is the skip list, this will make a list
		// where it can easily refer to specific nodes based off of a random (coin toss). 
		// when the coin toss is true it will add a whole new level to the list and add the new node
		// to that level. Once the coin toss finally becomes false it will break out and stop adding 
		// any more levels to the list
		SkipListClass skipList	= new SkipListClass();
		double beforeTime	= System.currentTimeMillis();
			
		while (input.hasNext())												// Once we have the word we cans end it to the add method to process the word to see if it needs
			skipList.insert(cutPunctuation(input.next().toLowerCase()));	// to add it to the list or if it's already in the list just increment the count of the word being studied

		double afterTime	= System.currentTimeMillis();
		//System.out.println(skipList);
		System.out.print("Sixth Pass: \"Skip List\"\n");
		printStats(skipList, beforeTime, afterTime);	// once we have processed out list we can send it to the printStats method
	}// end SixthPasSkip 

}// end class