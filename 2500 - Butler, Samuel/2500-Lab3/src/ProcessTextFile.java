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
	public static void main(String[] args)
	{
		// The function of main is to call the specific list that is to be processed and to open and close the file after
		// each iteration through a different list. We will give the program a specific text file to process by using 
		// jave.io.File to import a text file. Of course there is a possibly that the file does not exist, but since we 
		// are manually typing it in and not relying on a user to type in a file name, the only time the exception could
		// occur is if we type the file name in wrong.
		
		final String FILE_NAME = "Hamlet";
		File file	= new java.io.File("F:\\2500 - Butler, Samuel\\2500-Lab3\\TestTextFiles-Lab2\\" + FILE_NAME + ".txt");

		try
		{
			// In a try catch block incase the file is not found
			FirstPassParsing(file);
			System.out.println("-------------------------------------------");	// just to separate the data from each list
																				// more readability 
			SecondPassUnsorted(file);
			System.out.println("-------------------------------------------");
			
			ThirdPassSorted(file);
			System.out.println("-------------------------------------------");
		
			FourthPassToFront(file);
			System.out.println("-----------------------------------------");
			
			FifthPassShift(file);
			System.out.println("-------------------------------------------");
			
			SixthPassSkip(file);
		}
		catch(FileNotFoundException e)
		{
			// let's the user (me) know if I misspelled the text file name or if the file isn't there
			System.out.println("File not Found.");
			System.exit(1);
		}		
		
	}// end main
	
	public static void FirstPassParsing (File file) throws FileNotFoundException
	{
		// First parse will consist of just collecting the word count and how long it took to 
		// parse the file. We collect the before time before it enters the loop and the after time
		// after it exits the loop so be able to get the value of how long it took to parse the file.
		
		Scanner input 		= new Scanner(file);
		double beforeTime1	= System.currentTimeMillis();
		int wordCount1		= 0;
		
		while (input.hasNext())
		{
			if (!cutPunctuation(input.next().toLowerCase()).equals(""))	// gets the word, changes it to lower case, then sends it to the cutPunctuation method
				wordCount1++;
		}
		
		input.close();
		String pass1Time = String.format("%.3f", (System.currentTimeMillis() - beforeTime1) / 1000);
		System.out.print("\"Parsing Text\" \n"      + " -Total Number of Words:   "	+ wordCount1	+  " words\n" 			
													  + " -Total Time Elapsed:      " + pass1Time		+ " seconds" + "\n");
	}// end FirstPassParsing
	
	public static void SecondPassUnsorted(File file) throws FileNotFoundException
	{
		// First list is the unsorted, we create an instance of the unsorted class to be able to add
		// nodes(words) to our list. The unsorted list will add the nodes in no certain order to the list
		// We will also collect the time and before time and the total word count of the file. 
		
		Scanner input = new Scanner(file);
		ListUnsorted<String> unsorted	= new ListUnsorted<String>();
		double beforeTime	= System.currentTimeMillis();
		
		while (input.hasNext())											// Once we have the word we can send it to the add method to process the word to see if it needs
			unsorted.add(cutPunctuation(input.next().toLowerCase()));	// to add it to the list or if it's already in the list just increment the count of the word being studied
		
		double afterTime	= System.currentTimeMillis();
		System.out.print("\"Unsorted List Creation\"\n");
		printStats(unsorted, beforeTime, afterTime);	// once we have processed out list we can send it to the printStats method
		
		unsorted.resetStats();
		input.close();
		input = new Scanner(file);
		
		beforeTime = System.currentTimeMillis();
		
		while (input.hasNext())											 // Once we have the word we can send it to the remove method to process the word to see if it needs
			unsorted.remove(cutPunctuation(input.next().toLowerCase())); // so the word can either be decremented or removed from the list
		
		if (!unsorted.isEmpty())	// checks to see if the list is empty
		{
			System.out.println("List not Empty.");
			System.exit(1);
		}
		
		afterTime	= System.currentTimeMillis();
		System.out.print("\"Unsorted List Removing Nodes\"\n");
		printRemoveStats(unsorted, beforeTime, afterTime);	// once we have processed out list we can send it to the printStats method
		input.close();
		
	}// endSecondPassUnsorted
	
	public static void ThirdPassSorted (File file) throws FileNotFoundException
	{
		// Next list is the sorted list, this list puts nodes in alphabetical order. 
		// Half of the time the list will only traverse half of the list because if it
		// has a word say "apple" and we reach the word "bear" we can stop cause we know it
		// is not after bear
		
		Scanner input = new Scanner(file);
		ListSorted<String> sorted				  = new ListSorted<String>();			// instance of the old sorted list
		ModifiedSortedList<String> sortedModified = new ModifiedSortedList<String>();	// instance of the modified sorted list
		double beforeTime	= System.currentTimeMillis();
		
		while (input.hasNext())	
		{
			//sorted.add(cutPunctuation(input.next().toLowerCase()));		// Once we have the word we can send it to the add method to process the word to see if it needs
			sortedModified.add(cutPunctuation(input.next().toLowerCase()));	// to add it to the list or if it's already in the list just increment the count of the word being studied
		}					
		
		double afterTime	= System.currentTimeMillis();
		//System.out.print("\"Sorted List Creation\"\n");
		//printStats(sorted, beforeTime, afterTime);		// Old sorted list add stats 
		
		System.out.print("\"Modified Sorted List Creation\"\n");
		printStats(sortedModified, beforeTime, afterTime);	// modified sorted list add stats
		
		sorted.resetStats();
		input.close();
		input = new Scanner(file);

		beforeTime = System.currentTimeMillis();
		
		while (input.hasNext())			
		{
			//sorted.remove(cutPunctuation(input.next().toLowerCase()));	   // Once we have the word we can send it to the remove method to process the word 
			sortedModified.remove(cutPunctuation(input.next().toLowerCase())); // so it can be removed from the list if the count is 0 
		}
		
		if (!sorted.isEmpty())	// checks to see if the list is empty
		{
			System.out.println("List not Empty.");
			System.exit(1);
		}

		afterTime	= System.currentTimeMillis();
		//System.out.print("\"Sorted List Removing Nodes\"\n");
		//printRemoveStats(sorted, beforeTime, afterTime);			// old sorted list remove stats
		
		System.out.print("\"Modified Sorted List Removing Nodes\"\n");
		printRemoveStats(sortedModified, beforeTime, afterTime);	// modified sorted list remove stats
		input.close();
		
	}// endThirdPassSorted
	
	public static void FourthPassToFront(File file) throws FileNotFoundException
	{
		// The next list is where each occurrence of the word we will move that node to the front
		// and increment it's count. This makes searching the list very fast since the words that
		// occur the most will be at the front of the list.
		
		Scanner input = new Scanner(file);
		HeavyHandedList<String> adjFront	= new HeavyHandedList<String>();
		
		double beforeTime	= System.currentTimeMillis();
		
		while (input.hasNext())											// Once we have the word we can send it to the add method to process the word to see if it needs
			adjFront.add(cutPunctuation(input.next().toLowerCase())); 	// to add it to the list or if it's already in the list just increment the count of the word being studied
		
		double afterTime	= System.currentTimeMillis();
		
		System.out.print("\"Heavy-Handed List Creation\"\n");
		printStats(adjFront, beforeTime, afterTime);	// once we have processed out list we can send it to the printStats method
		
		adjFront.resetStats();
		input.close();
		input = new Scanner(file);

		beforeTime = System.currentTimeMillis();
		
		while (input.hasNext())	
		{	
			//adjFront.remove(cutPunctuation(input.next().toLowerCase()));	 // Once we have the word we cans send it to the remove method to process the word 
			adjFront.remove2(cutPunctuation(input.next().toLowerCase()));	 // to either decrement the count or remove it from the list
		}																	 // two different types of removes
		
		if (!adjFront.isEmpty())	// checks to see if the list is empty
		{
			System.out.println("List not Empty.");
			System.exit(1);
		}
		
		afterTime	= System.currentTimeMillis();
		System.out.print("\"Heavy-Handed List Removing Nodes\"\n");
		printRemoveStats(adjFront, beforeTime, afterTime);	// once we have processed out list we can send it to the printStats method
		input.close();
		
	}// endFourthPassToFront
	
	
	public static void FifthPassShift (File file) throws FileNotFoundException
	{
		// The next list is where we shift the node up one. We will see if the  word
		// we are studying is already in the list, if it is then we will move the node
		// up one and increment its count, if not it will add it to the front of the list
		
		Scanner input = new Scanner(file);
		LightHandedList<String> adjShift	= new LightHandedList<String>();
		double beforeTime	= System.currentTimeMillis();
		
		while (input.hasNext())											// Once we have the word we can send it to the add method to process the word to see if it needs
			adjShift.add(cutPunctuation(input.next().toLowerCase()));	// to add it to the list or if it's already in the list just increment the count of the word being studied

		double afterTime	= System.currentTimeMillis();
		
		System.out.print("\"Shifting List Creation\"\n");
		printStats(adjShift, beforeTime, afterTime);	// once we have processed out list we can send it to the printStats method
		
		adjShift.resetStats();
		input.close();
		input = new Scanner(file);
		
		beforeTime = System.currentTimeMillis();
		
		while (input.hasNext())											 // Once we have the word we can send it to the remove method to process the word 
			adjShift.remove(cutPunctuation(input.next().toLowerCase())); // to either decrement the count or remove it from the list
		
		if (!adjShift.isEmpty())	// checks to see if the list is empty
		{
			System.out.println("List not Empty.");
			System.exit(1);
		}
		
		afterTime	= System.currentTimeMillis();
		System.out.print("\"Shifting List Removing Nodes\"\n");
		printRemoveStats(adjShift, beforeTime, afterTime);	// once we have processed out list we can send it to the printStats method
		input.close();
		
	}// endFifthPassShift
	
	
	public static void SixthPassSkip(File file) throws FileNotFoundException
	{
		// The final list, and by far the coolest list to work with is the skip list, this will make a list
		// where it can easily refer to specific nodes based off of a random (coin toss). 
		// when the coin toss is true it will add a whole new level to the list and add the new node
		// to that level. Once the coin toss finally becomes false it will break out and stop adding 
		// any more levels to the list
		
		Scanner input = new Scanner(file);
		SkipListClass skipList	= new SkipListClass();
		double beforeTime	= System.currentTimeMillis();
		
		while (input.hasNext())											 // Once we have the word we can send it to the add method to process the word to see if it needs
			skipList.insert(cutPunctuation(input.next().toLowerCase())); // to add it to the list or if it's already in the list just increment the count of the word being studied

		double afterTime	= System.currentTimeMillis();
		System.out.print("\"Skip List Creation\"\n");
		printStats(skipList, beforeTime, afterTime);	// once we have processed out list we can send it to the printStats method
		
		skipList.resetStats();
		input.close();
		input = new Scanner(file);

		beforeTime = System.currentTimeMillis();

		while (input.hasNext())											 // Once we have the word we can send it to the remove method to process the word 
			skipList.remove(cutPunctuation(input.next().toLowerCase())); // to either decrement the count or remove it from the list
	
		if (!skipList.isEmpty())	// checks to see if the list is empty
		{
			System.out.println("List not Empty.");
			System.exit(1);
		}
		
		afterTime	= System.currentTimeMillis();
		System.out.print("\"Skip List Removing Nodes\"\n");
		printRemoveStats(skipList, beforeTime, afterTime);	// once we have processed out list we can send it to the printStats method
		input.close();
		
	}// end SixthPasSkip 
	
	public static <T> void printStats(DefaultList<T> list, double before, double after)
	{
		// the PrintStats method will display all of the required stuff needed to know about the
		// the file, the following is a list of what we need to print out.
		//	1. Total Words
		//	2. Distinct Words
		//	3. Comparisons
		//	4. Reference Changes
		//	5. Time Elapsed
		System.out.println (" -Total Number of Words:   "   + list.TotalWordCount()							 + " words\n"+ 
	   			   			" -Total of Distinct Words: "   + list.DistinctWordCount()						 + " words\n"+ 
	   			   			" -Total Comparisons Made:  "   + list.compareNum()								 + " Comparisons\n"+
	   			   			" -Total Reference Changes: "	+ list.refNum()									 + " changes\n"+ 
	   			   			" -Total Time Elapsed:      "   + String.format("%.3f", (after - before) / 1000) + " seconds\n" );
	}
	
	public static <T> void printRemoveStats(DefaultList<T> list , double before, double after)
	{
		System.out.print(" -Total Comparisons Made:  "	+ list.compareNum()	 							 + " Comparisons\n"+
						 " -Total Reference Changes: "	+ list.refNum()									 + " changes\n"+
		   				 " -Total Time Elapsed:      "	+ String.format("%.3f", (after - before) / 1000) + " seconds\n" );
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

}// end class