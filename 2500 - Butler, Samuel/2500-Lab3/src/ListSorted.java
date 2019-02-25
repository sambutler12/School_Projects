import Support.LLNode;

public class ListSorted<T extends Comparable<T>> extends DefaultList<T> 
{
	public ListSorted()
	{
		// inherits all the variables from its parent class
		super();
	}
	
	public void add(T element)
	{
		// will add a word when we don't have it in the list already
		// first checks to see if the element is nothing, then it will
		// return
		if(element.equals("")) return;
		
		find(element);	// will see if the element is in the list by calling from its parent class
		
		if(found)		// if the word is already in the list then it will increment that word
			inc(element);
		else
		{
			LLNode<T> prevLoc = null;
			LLNode<T> location;
			T listElement;
			
			location = list;
			while (location != null)
			{
				listElement = location.getInfo();		// sets listElement to the string we are studying
				if (listElement.compareTo(element) < 0)	// checks to see if the word should come before or after 
				{										// a word in the list. so "a" comes before "b" so a.compareTo(b) would be -1
					prevLoc		= location;				// moves to the next node
					location	= location.getLink();	// moves to the next node
				}
				else
					break;
			}
			LLNode<T> newNode = new LLNode<T>(element, 1);
			
			if(prevLoc == null)
			{
				// if the prevLoc is null then that means it will be inserted at
				// the front of the list because the list is empty or because it needs
				// to be inserted before the current first node
				newNode.setLink(list);
				list = newNode;
			}
			else
			{
				// otherwise if the list is not empty it will insert it between
				// two nodes in the list
				newNode.setLink(location);
				prevLoc.setLink(newNode);
			}
			refChange+=2;
		}
	}
	
	protected void find(T target)
	{
		location = list;
		found = false;
		
		// how it finds it is different the other list, because it is sorted that means we
		// can check to see if the target word comes before or after the word we are studying
		// therefore, once we hit a word that comes after the target that means we know its not
		// in the list and we need to add it.
		while (location != null && location.getInfo().compareTo(location.getInfo()) <= 0)
		{
			compare++;
			if (location.getInfo().equals(target))
			{
				found = true;
				return;
			}
			else
			{
				previous = location;			// moves previous to location
				location = location.getLink();	// location moves to next node
			}
		}
	}
	
}