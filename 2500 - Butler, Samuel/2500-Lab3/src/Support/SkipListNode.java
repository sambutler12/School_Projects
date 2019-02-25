package Support;

public class SkipListNode
{
	public String key;	// String that goes with the node
	public int value;		// the int value that goes with the node
	
	public SkipListNode up;	// up link
	public SkipListNode down;	// down link
	public SkipListNode right;	// right link
	public SkipListNode left;	// left link
	
	public static String NEG_INF = "negInf";	// Negative infinity value (left bound)
	public static String POS_INF = "posInf";	// Positive infinity value (right bound)
	
	public SkipListNode(String element, int num)
	{
		key = element;
		value  = num;
		up     = null;
		down   = null;
		left   = null;
		right  = null;
	}
	
	public String getInfo()
	{
		return key;	// will get the info of the Node
	}
	
	public void setInfo(String info)
	{
		this.key = info;	// will get the info of the Node
	}
	
	public int getCount()
	{
		return value;	// will get the count of the Node
	}
	
	public void setCount(int count)
	{
		this.value = count;	// will get the info of the Node
	}

}
