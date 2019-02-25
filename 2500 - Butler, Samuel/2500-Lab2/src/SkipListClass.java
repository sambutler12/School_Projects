import java.util.Random;
import SupportLL.SkipListNode;

public class SkipListClass extends DefaultList<String>
{
	//	Each link list element must have must have 4 links to it, an up, down, left and right:
	//
	//	Since each node has a total of 4 links on each            - Skip list have a header and trailer. Meaning a value at the beginning 
	//	data node it is acting like a doubly-linked list            of the list and a value at the end of the list. Those values act like   
	//	in all 4 directions. Therefore, we can go in any            bounds, where we know the data that we are putting into the skip list   
	//	direction we want from anywhere in the list. What           will never go over those bounds. In this case we use the symbols +oo    
	// 	a node would look like visually is shown below.             and -oo to represent positive and negative infinity respectively.    
	//                                                                 							  ________                                      
	//				 	   UP                                        					 		 | Data_3 |	                                    
	//				 	   ^						                null  		    null  	     |________|         null            null          
	//				 	   |						                 ^               ^              |  ^             ^               ^            
	//			 	   ____|____			                         |               |   		    |  |			 |               |            
	//			      |         |		                          ___|____  	  ___|____	      __V__|__	      ___|____        ___|____        
	//		LEFT <----|	  Data	|----> RIGHT                     |  -oo   |<-----| Data_4 |----->| Data_1 |----->| Data_2 |----->|  +oo   |       
	//			  	  |_________|			                     |________|----->|________|<-----|________|<-----|________|<-----|________|              																	     
	//				  	   |				                         |  			 |   		    |  ^			 |               |               
	//				   	   |                                         |			     |	  		    |  |			 |	             |	         
	//                 	   v                                         V               V            __V__|__   	     V               V                
	//		          	  DOWN 			                            null            null         | Data_5 |  	    null            null           
	//						 	                                                                 |________|   
	//
    //	Any of the links that are not being used on a data point(Node) will have its link set to null, as more words begin to be added to
	//	the list the list will grow in both height and length, since each node has an up and down link, it can keep going up as high as it 
	//	wants, the header and trailer nodes will always have there up link and down link be its self, meaning that -oo up link will refer 
	//	to -oo and the same goes for +oo.
	
	public SkipListNode head;
	public SkipListNode tail;
	
	public int entries;	// how many entries are in the skip list
	public int height;	// height of the skip list
	public Random r;	// generates a random value with a coin toss, determines the height of a new entry 
	public SkipListNode current;
	
	public SkipListClass()
	{
		super();
		SkipListNode neg, pos;

	     neg = new SkipListNode(SkipListNode.NEG_INF, 0);
	     pos = new SkipListNode(SkipListNode.POS_INF, 0);

	     head = neg;
	     tail = pos;

	     neg.right = pos;
	     pos.left = neg;

	     entries = 0;
	     height = 0;

	     r = new Random();	// creates the random number, for the coin toss
	}
	
	public void inc(SkipListNode element)
	{
		// this is what increments the count for a specific word in the list,
		// will only increment if the node is already in the list
		element.setCount(element.getCount() + 1);
	}
	
	public SkipListNode search(String k)
	{
		// this is how the skipLsit knows the current word being studied is in the list and also knows if
		// the word should come before or after the word it is comparing it to.
		SkipListNode p = head;
		while(true)
		{
			compare++;
			while(p.right.target != SkipListNode.POS_INF && p.right.target.compareTo(k) <= 0)
			{
				p = p.right;
				compare++;
				if(p.target == SkipListNode.POS_INF) compare--;
			}
			if(p.down == null) return p;
			p = p.down;
		}
	}
	
	public void insert(String k)
	{
		SkipListNode insertion, newNode;
		int i;
		
		insertion = search(k);
		if(k.equals("")) return;
		if(k.equals(insertion.target))
		{
			inc(insertion);
			compare++;
			return;
		}
		
		newNode = new SkipListNode(k,1);
		
		newNode.left		 = insertion;
		
		newNode.right		 = insertion.right;
		
		insertion.right.left = newNode;
		
		insertion.right	     = newNode;
		
		i = 0;
		refChange+=4;
		
		while(r.nextDouble() < 0.5)
		{
			refChange+=10;
			if( i >= height)
			{
				SkipListNode p1, p2;

			   height = height + 1;

		       p1 = new SkipListNode(SkipListNode.NEG_INF , 0);
		       p2 = new SkipListNode(SkipListNode.POS_INF , 0);
		   
			   p1.right = p2;
			   p1.down  = head;

			   p2.left = p1;
			   p2.down = tail;

			   head.up = p1;
			   tail.up = p2;

			   head = p1;
			   tail = p2;

			}
		
			while ( insertion.up == null)
				insertion = insertion.left;
			insertion = insertion.up;
			
			SkipListNode e = new SkipListNode(k , 0);
			
			e.left = insertion;
			e.right = insertion.right;
			e.down = newNode;
			
			insertion.right.left = e;
			insertion.right = e;
			newNode.up = e;
			newNode = e;
			
			i++;
		}
		
		entries++;
		
	}
	
	public String toString()
	{
		SkipListNode p = head;
		SkipListNode q;
		
		while(p.down != null)
			p = p.down;
		
		p = p.right;
		String list = "List: \n";
		while(p.target != SkipListNode.POS_INF )
		{
			q = p;
			if(q.getCount() >= 10)
				list = list + "Count: " + q.getCount()+ "   ";
			else 
				list = list + "Count: " + q.getCount()+"    ";
			do
			{
				list = list + q.getInfo() + "--";
				q = q.up;
			}while(q != null);
			
			list = list + "\n";
			p = p.right;
		}
		return list;
	}
	
	public int TotalWordCount()
	{
		//collects the total word count by going to each of the nodes and 
		// collecting the int value in the node and adding it to a running 
		// count until it reaches the end of the list.
		while(head.down != null)
			head = head.down;
		current = head.right;
		int total = 0;
		while (current.target != SkipListNode.POS_INF)
		{
			total   += current.getCount();
			current = current.right;
		}
		return total;
	}
	
	public int DistinctWordCount()
	{
		// This will just count the number of nodes in the list giving us
		// how many distinct words are in the list
		while(head.down != null)
			head = head.down;
		current = head.right;
		int distinctTotal = 0;
		while (current.target != SkipListNode.POS_INF)
		{
			distinctTotal++;
			current = current.right;
		}
		return distinctTotal;
	}
	
	
	
	
	
	
	
	
	
	
}                                                                                                                                                 
                                                                   
                                                                           