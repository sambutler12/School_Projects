import Support.LLNode;

public class HashList extends HeavyHandedList
{
	// inherits from heavy handed list since a hash table is self-adjusting and will move the node to
	// the front of the list each time that word occurs. The way hash tables work is the words are split up into
	// Certain locations in the array based on the manipulation of the key and how you are identifying the location
	// by. In our case we are hashing in 3 different types of ways. The first way is adding up the total ASCII value 
	// in the word, the second way is by only looking at the first letter of the word and hashing to that ASCII value, 
	// then the final way is by changing the hash to an 8-bit value instead of java's default 32-bit value.
	//
	//	How hashing table looks:		
	//				 _____		_____      _____
	//			0	|_____|--->|_____|--->|_____|                             * How it works is it is an array of linked list. In our case it is an array of   
	//			1	|_____|                                                     self-adjusting linked list in any of our 3 hashing functions, it will find the 
	//			2	|_____|                                                     hash value and hash it to that specific location in the table. So say that the 
	//			3	|_____|		_____      _____      _____                     total value of a words ASCII characters is 250, that means it will hash that   
	//          4   |_____|--->|_____|--->|_____|--->|_____|                    word to location 6 on the array, and then go onto the next word and do the same
	//			5	|_____|                                                     thing. If a word happens to have the same hash location (which it will in some 
	//			6	|_____|                                                     cases) then that is where linked list come into play, and all that will happen 
	//			7	|_____|		_____                                           is it will link it to the word that is already hashed at the location.         
	//			8	|_____|--->|_____|
	//			9	|_____|		_____
	//			10	|_____|--->|_____|
	//			11	|_____|     _____      _____      _____      _____
	//			12	|_____|--->|_____|--->|_____|--->|_____|--->|_____|
	//			13	|_____|     _____      _____
	//			14	|_____|--->|_____|--->|_____|
	//			15	|_____|     _____
	//			16	|_____|--->|_____|

	public HashList()
	{
		super(); // inherits all the variables from its parent class
	}
	
	public void insert(String element)
	{
		if(element.equals("")) return;
		
		 //loc = hash1(element);	// First hash is where it will sum up all the ASCII values of the word and add them
		 //loc = hash2(element);	// takes only the first ASCII value
		   loc = hash3(element);	// Code given by DR.Thomas, that produces an 8-bit hash instead of 32-bit
		 
		 list = table[loc];
		 add(element);
	}
	
	public int hash1(String element)
	{
		int hashLoc = 0;
		
		for(int i = 0; i < element.length(); i++)
			hashLoc += element.charAt(i);	// adds the chars ASCII value to a running sum
		return hashLoc % 256;	// mods 256 to chose what location it will go into
	}
	
	public int hash2(String element)
	{
		return element.charAt(0) % 256;	// small class that returns the first ASCII value mod 256
	}
	
	public int hash3(String element)
	{
		int hash = 0;
		for(int i = 0; i < element.length(); i++)
			hash = ((hash * 31) + element.charAt(i)) % 256;	// Allows for a bit hash
		return hash;	// returns an int value of the hash location
	}
	
	public int TotalWordCount()
	{
		// Total word count, by going to all of the distinct word nodes and adding up their count
		int total = 0;
		for(int i = 0; i < 256; i++)
		{
			location  = table[i];	// moves it to the next cell in the table

			while (location != null)
			{
				total   += location.getCount();
				location = location.getLink();	// moves to the next node in the list
			}
		}
		return total;
	}
	
	public int DistinctWordCount()
	{
		// Counts the number of distinct words in the list by adding up all of the nodes in the list
		int distinctTotal = 0;
		for(int i = 0; i < 256; i++)
		{
			location = table[i];	// moves it to the next cell in the table

			while (location != null)
			{
				distinctTotal++;
				location = location.getLink();	// moves to the next node in the list
			}

		}
		return distinctTotal;
	}
	
	public String toString()
	{
		LLNode currNode = table[0];	// starts at the first cell in the table
		String listString  = "List:\n";
		for(int i = 0; i < 256; i++)
		{
			while (currNode != null)
			{
				listString = listString + " "+ currNode.getInfo() + " " + "\n";
				currNode   = currNode.getLink();	// moves to the next node in the linked list
			}
			if(i < 255) currNode = table[i+1];	// moves to the next cell in the table but makes sure that its not at the last cell
		}
	
		return listString;
	}
	
}
