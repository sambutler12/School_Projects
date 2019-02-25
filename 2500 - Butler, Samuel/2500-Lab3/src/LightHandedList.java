import Support.LLNode;

public class LightHandedList<T extends Comparable<T>> extends DefaultList<T> 
{
	T holderS;
	int holderN;
	
	public LightHandedList()
	{
		// inherits all the variables from its parent class
		super();
		holderS	= null;
		holderN		= 0;
	}
	
	public void add(T element)
	{
		// will add a word when we don't have it in the list already
		// first checks to see if the element is nothing, then it will
		// return
		if(element.equals("")) return;
		
		find(element);	// will see if the element is in the list by calling from its parent class
		
		if(found)
			sendUpOne(element);
		else
		{
			LLNode<T> newNode = new LLNode<T>(element , 1);	// creates new node
			newNode.setLink(list);	// sets new word link to current front
			list = newNode;			// sets front of list to new word
			refChange+=2;          
		}
	}
	
	public void sendUpOne(T element)
	{
		// if it gets to this method, means the word is in the list and
		// increment the count for that word
		inc(element);
		
		// if the word is already in the front of the list then we don't
		// need to do anything and will just return
		if(element.equals(list.getInfo())) return;
		
		holderS = previous.getInfo();	// holds a reference to the string to previous
		holderN = previous.getCount();	// holds a reference to the count to previous
		
		previous.setInfo(location.getInfo());	// sets previous string to the node being studied
		previous.setCount(location.getCount());	// sets previous count to the word being studied
		location.setInfo(holderS);				// we then set the place where the word we moved to the string we saved
		location.setCount(holderN);				// we then set the place where the word we moved to the count we saved

	}
	
}