package support;

public class linkedStack<T>
{
	protected LLNode<T> top; // A reference to the top of the stack
	
	
	public linkedStack()
	{	
		/*---------------------Constructor---------------------
		 * When an object is instantiated of class LinkedStack, 
		 * we can create a stack by setting the top = null
		 */
		top = null;
	}
	
	public void push(T element)
	{	// Places the element at the top of the stack
		LLNode<T> newNode = new LLNode<T>(element);
		newNode.setLink(top);	//This sets the newNodes link to the current top
		top = newNode;			//before we set top equal to the newNode
	}
	
	public boolean isEmpty()
	{
		return (top == null);	// will return true if the stack is empty
	}
	
	public T top() throws StackUnderFlowException
	{
		if(!isEmpty())	//will return the info of whatever is at the top of the stack
		{
			return top.getInfo();
		}
		else 			//will throw a StackUnderFlowException if the stack is empty when we try to top
			throw new StackUnderFlowException("Top attempted on empty stack.");	
	}

	public void pop() throws StackUnderFlowException
	{
		if(!isEmpty())	//this will pop the current link off the top of the stack by setting top to its link,
		{				//therefore loosing the reference to the old top and having a new top take place
			top = top.getLink();
		}
		else			//if the stack is empty we will not be able to pop is the stack is empty and throws StackUnderFlowException
			throw new StackUnderFlowException("Pop attempted on empty stack."); 
	}

	public boolean isFull()
	{
		// TODO Auto-generated method stub
		return false;
	}

}
