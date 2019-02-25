import Support.LLNode;

public class DefaultList
{
	// Default list is a general class that the other four list extend from since the all do
	// similar things  besides stuff like add or find, other than that they have the same
	// functions. This class consist of total word count method, distinct word count method,
	// to string method, compare count, reference count, and the find method. All the list 
	// will consist of having a current location, previous location, and list(refers to first node)
	
	protected static int refChange;
	protected long compare;
	protected boolean found;
	protected LLNode currPos;
	protected LLNode location;
	protected LLNode previous;
	protected LLNode list;
	protected int loc;
	protected LLNode[] table = new LLNode[256];
	
	public DefaultList()
	{
		// the constructor and sets all the values needed
		// for the four list to be created
		refChange	= 0;
		compare		= 0;
		loc = 0;
		list		= null;
		currPos		= null;
	}
	
	protected void find(String target)
	{
		// finds if the node is in the list or not, if it is in the list
		// it will return true, else that means its not in the list and
		// will return false
		location	= list;
		found		= false;
	
		while (location != null)
		{
			// each time it comes in here, it will increase comparison
			compare++;
			if (location.getInfo().equals(target))
			{
				found = true;
				return;
			}
			else
			{
				// moves the pointers to the next nodes to be studied in the list
				previous = location;
				location = location.getLink();
			}
		}
	}
	
	public void remove(String element)
	{
		find(element);	// finds the location of the element in the list
		if(found)
		{
			dec(element);	// decrements the count 
			if(location.getCount() == 0)	// if the count is zero, it removes the node
				if(list == location)	// if the word being removed is in the front of the list
					list = list.getLink();
				else
					previous.setLink(location.getLink());	// sets previous link to skip of the node being deleted 
			refChange++;
		}
	}
	
	public void inc(String element)
	{
		// this is what increments the count for a specific word in the list,
		// will only increment if the node is already in the list
		location.setCount(location.getCount() + 1);
	}
	
	public void dec(String element)
	{
		// this is what increments the count for a specific word in the list,
		// will only increment if the node is already in the list
		location.setCount(location.getCount() - 1);
	}
	
	public long compareNum()
	{
		// returns the total number of reference changers made by a list
		return compare;
	}
	
	public void resetStats()
	{
		compare		= 0;
		refChange 	= 0;
	}
	
	public int refNum()
	{
		// returns the total number of comparisons made by a list
		return refChange;
	}

	public String toString()
	{
		// returns the String representation of list, reads the list one
		// node at a time and as soon as the node == null then that means 
		// we reached the end of the list
		LLNode currNode = list;
		String listString  = "List:\n";
		while (currNode != null)
		{
			listString = listString + " "+ currNode.getInfo() + " " + currNode.getCount() + "\n";
			currNode   = currNode.getLink();
		}
	
		return listString;
	}
	
	public int TotalWordCount()
	{
		//collects the total word count by going to each of the nodes and 
		// collecting the int value in the node and adding it to a running 
		// count until it reaches the end of the list.
		location  = list;
		int total = 0;
		while (location != null)
		{
			total   += location.getCount();
			location = location.getLink();
		}
		return total;
	}
	
	public int DistinctWordCount()
	{
		//This will just count the number of nodes in the list giving us
		// how many distinct words are in the list
		location = list;
		int distinctTotal = 0;
		while (location != null)
		{
			distinctTotal++;
			location = location.getLink();
		}
		return distinctTotal;
	}
	
	public boolean isEmpty()
	{
		if(list == null) return true;
		else return false;
	}
	
}
