import Support.LLNode;
public class HeavyHandedList extends DefaultList
{
	// Inherits all the methods from default class
	public HeavyHandedList()
	{
		// inherits all the variables from its parent class
		super();
	}
	
	public void add(String element)
	{
		// will add a word when we don't have it in the list already
		// first checks to see if the element is nothing, then it will
		// return
		if (element.equals("")) return;
		
		find(element);	// will see if the element is in the list by calling from its parent class
		
		if (found)	// if the word is found it will then be sent to the sendToFront method
			sendToFront(element);
		else
		{
			// adds a new word to the list
			LLNode newNode = new LLNode(element , 1);	// creates new node
			table[loc] = newNode;
			newNode.setLink(list);		// sets new word link to current front
			list = newNode;				// sets front of list to new word
			refChange += 2;
		}
	}
	
	public void sendToFront(String element)
	{
		// if it gets to this method, means the word is in the list and
		// increment the count for that word
		inc(element);
		
		// if the word is already in the front of the list then we don't
		// need to do anything and will just return
		if (element.equals(list.getInfo())) return;
		
		refChange+=3;
		currPos = previous.getLink();	// reference to the word we are studying
		
		previous.setLink(location.getLink());	// has previous links refer to the words link being studied
		currPos.setLink(list);		// set the word we want to move to the front of the list
		list = currPos;				// set the new front to the word that was moved
		table[loc] = list;

	}
	
	public void remove2(String element)
	{
		find(element);	// finds the location of the element
		
		if(found)
			dec(element);	// decrements the element 
		
		if(location == null) return; // if it has location referring to the end 
									 // of the list, then the list is empty
		if(location.getCount() == 0)
		{
			if(location == list) list = location.getLink();	// if the word is already in the front then we just change the reference of list
			else				 previous.setLink(location.getLink());	// otherwise it's somewhere else in the list and we need to remove it
			
			return;
		}
		
		if (element.equals(list.getInfo())) return; // if the word is already in the front of the list
		
		refChange+=3;
		currPos = previous.getLink();	// reference to the word we are studying
		
		previous.setLink(location.getLink());	// has previous links refer to the words link being studied
		currPos.setLink(list);		// set the word we want to move to the front of the list
		list = currPos;				// set the new front to the word that was moved
	}
	
}