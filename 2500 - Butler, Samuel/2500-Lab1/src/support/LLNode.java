package support;

public class LLNode<T>
{
	private T info;
	private LLNode<T> link;
	
	public LLNode(T expressionInfo)
	{
		/*---------------Constructor-----------
		 * Will set the info of the linked list and will set
		 * the link of the node to null in order to create the node
		 */
		info = expressionInfo;
		link = null;
	}
	
	public T getInfo()
	{
		return info;	// will get the info of the LLNode
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
