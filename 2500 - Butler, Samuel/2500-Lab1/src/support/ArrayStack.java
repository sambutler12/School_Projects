package support;

public class ArrayStack<T>
{
	protected final int cap = 1000;
	protected T[] stack;
	protected int topIndex = -1; 
	
	@SuppressWarnings("unchecked")
	public ArrayStack()
	{
		/*----------------------Constructor-----------------
		 * Creates an array of objects of type T, with a specified size of 1000.
		 */
		stack = (T[]) new Object[cap]; //complier can't ensure that the array contains objects of class T
	}
	
	@SuppressWarnings("unchecked")
	public ArrayStack(int maxSize)
	{
		/*----------------------Constructor-----------------
		 * Creates an array of objects of type T, with an unknown size.
		 * The size and type of the object will be decided when it is implemented 
		 */
		stack = (T[]) new Object[maxSize]; //complier can't ensure that the array contains objects of class T
	}
	
	public boolean isEmpty()
	{	// returns true if the stack is empty
		// otherwise it will return false
		if(topIndex == -1)
			return true;
		else
			return false;
	}
	
	public boolean isFull()
	{	//returns true if the stack is full
		//otherwise it returns false
		if(topIndex == stack.length-1)
			return true;
		else 
			return false;
	}
	
	public T top() throws StackUnderFlowException
	{	// it will first check if the stack is empty, if it is empty then 
		// it will throw a StackUnderFlowException, otherwise it will
		// return the top of the stack
		
		T topOfStack = null;
		
		if(!isEmpty())	
			topOfStack = stack[topIndex];
		else	
			throw new StackUnderFlowException("Top attempted on empty stack.");	
		
		return topOfStack; //returns whatever is on the top of the stack
	}
	
	public void pop() throws StackUnderFlowException 
	{	// Will check to see if the stack is empty, if it is then it will
		// throw a StackUnderFlowException, otherwise it will pop whatever
		// is on the top of the stack
		
		if(!isEmpty())
		{
			stack[topIndex] = null;	
			topIndex--;
		}
		else	
			throw new StackUnderFlowException("Pop attempted on empty stack.");
	}
	
	public void push(T element)
	{	// This will place an element at the top of the stack
			topIndex++;
			stack[topIndex] = element;
	}
}
