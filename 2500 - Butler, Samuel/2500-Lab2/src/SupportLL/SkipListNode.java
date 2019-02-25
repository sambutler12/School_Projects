package SupportLL;

public class SkipListNode
{
	public String target;	// String that goes with the node
	public int value;		// the int value that goes with the node
	
	public SkipListNode up;	// up link
	public SkipListNode down;	// down link
	public SkipListNode right;	// right link
	public SkipListNode left;	// left link
	
	public static String NEG_INF = "negInf";	// Negative infinity value (left bound)
	public static String POS_INF = "posInf";	// Positive infinity value (right bound)
	
	public SkipListNode(String element, int num)
	{
		target = element;
		value  = num;
		up     = null;
		down   = null;
		left   = null;
		right  = null;
	}
	
	public String getInfo()
	{
		return target;	// will get the info of the LLNode
	}
	
	public void setInfo(String info)
	{
		this.target = info;	// will get the info of the LLNode
	}
	
	public int getCount()
	{
		return value;	// will get the count of the LLNode
	}
	
	public void setCount(int count)
	{
		this.value = count;	// will get the info of the LLNode
	}

}
