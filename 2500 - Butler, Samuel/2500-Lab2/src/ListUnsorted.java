import SupportLL.*;

public class ListUnsorted<T> extends DefaultList<T> 
{
	public ListUnsorted()
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
		
		if(found)
			inc(element);	// if the word is already in the list then it will increment that word
		else
		{
			LLNode<T> newNode = new LLNode<T>(element , 1);	// creates new node
			newNode.setLink(list);	// sets new word link to current front
			list = newNode;			// sets front of list to new word
			refChange+=2;
		}
	}
	
}
