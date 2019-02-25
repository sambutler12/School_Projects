package Support;

public class LLNode
{
	private String info;
	private int count;
	private LLNode link;
	public SkipListNode up;		// up link
	public SkipListNode down;	// down link
	public SkipListNode right;	// right link
	public SkipListNode left;	// left link
	
	public LLNode(String expressionInfo, int num)
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
	
	public String getInfo()
	{
		return info;	// will get the info of the LLNode
	}
	
	public void setInfo(String info)
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
	
	public void setLink(LLNode link)
	{
		this.link = link;	// sets the link of the LLNode
	}
	
	public LLNode getLink()
	{
		return link; // Returns the link of the LLNode
	}
}
