package SupportLL;

public class LLNode<T>
{
	private T info;
	private int count;
	private LLNode<T> link;
	public SkipListNode up;		// up link
	public SkipListNode down;	// down link
	public SkipListNode right;	// right link
	public SkipListNode left;	// left link
	
	public static String NEG_INF = "negInf";	// Negative infinity value (left bound)
	public static String POS_INF = "posInf";	// Positive infinity value (right bound)
	
	public LLNode(T expressionInfo, int num)
	{
		/*---------------Constructor-----------
		 * Will set the info of the linked list and will set
		 * the link of the node to null in order to create the node
		 */
		info	= expressionInfo;
		count	= num;
		link	= null;
		up		= null;
		down	= null;
		left	= null;
		right	= null;
		
	}
	
	public T getInfo()
	{
		return info;	// will get the info of the LLNode
	}
	
	public void setInfo(T info)
	{
		this.info = info;	// will get the info of the LLNode
	}
	
	public int getCount()
	{
		return count;	// will get the count of the LLNode
	}
	
	public void setCount(int count)
	{
		this.count = count;	// will get the info of the LLNode
	}
	
	public void setLink(LLNode<T> link)
	{
		this.link = link;	// sets the link of the LLNode
	}
	
	public LLNode<T> getLink()
	{
		return link; // Returns the link of the LLNode
	}
}
