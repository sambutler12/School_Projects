import SupportLL.LLNode;
public class ListAdjustableToFront<T extends Comparable<T>> extends DefaultList<T> 
{
	// Inherits all the methods from default class
	public ListAdjustableToFront()
	{
		// inherits all the variables from its parent class
		super();
	}
	
	public void add(T element)
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
			LLNode<T> newNode = new LLNode<T>(element , 1);	// creates new node
			newNode.setLink(list);		// sets new word link to current front
			list = newNode;				// sets front of list to new word
			refChange += 2;
		}
	}
	
	public void sendToFront(T element)
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

	}
	
}