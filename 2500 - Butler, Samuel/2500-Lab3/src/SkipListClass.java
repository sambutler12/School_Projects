import java.util.Random;
import Support.SkipListNode;

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

	     neg = new SkipListNode(SkipListNode.NEG_INF, 0);	// negative infinity 
	     pos = new SkipListNode(SkipListNode.POS_INF, 0);	// positive infinity

	     head = neg;	// sets heads reference to neg infinity 
	     tail = pos;	// sets tails reference to pos infinity

	     // links head and tail together 
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
	
	public void dec(SkipListNode element)
	{
		// this is what decrements the count for a specific word in the list,
		// will only decrement if the node is already in the list
		element.setCount(element.getCount() - 1);
	}
	
	public SkipListNode search(String element)
	{
		// this is how the skipLsit knows the current word being studied is in the list and also knows if
		// the word should come before or after the word it is comparing it to.
		SkipListNode currHead = head;
		while(true)
		{
			compare++;
			while(currHead.right.key != SkipListNode.POS_INF && currHead.right.key.compareTo(element) <= 0)
			{
				currHead = currHead.right;	// moves to the next node
				compare++;
				if(currHead.key == SkipListNode.POS_INF) compare--;	// does this because once it goes back around it will increment the compare
																	// but if the currHead == POS_INF, it doesn't make a comparison so therefore 
			}														// it has to be decremented
			
			// moves the currHead to the bottom of the list, meaning the bottom lane (height = 0)
			if(currHead.down == null) return currHead;
			currHead = currHead.down;
			
		}
	}
	
	public void insert(String element)
	{
		// This method inserts the words into the list. The list consist of to sentinel values representing positive infinity
		// negative infinity. Each of the nodes are doubly linked together so they can go forward and backwards through the 
		// list when trying to find or add a value to the list. As well the list consist of lanes hat are built up and a node
		// goes to the upper lanes based off of a coin toss that tells it to keep going until the coin toss says otherwise
		
		SkipListNode insertion, newNode;
		int newLane;
		
		insertion = search(element);	// checks to see if the element is in the list already 
		if(element.equals("")) return;
		
		if(element.equals(insertion.key))	// if it is in the list then it will just increment the count 
		{									// else it will create a new node
			inc(insertion);
			compare++;
			return;
		}
		
		// sets all the newNodes links to its neighboring nodes
		newNode = new SkipListNode(element,1);
		
		newNode.left		 = insertion;
		
		newNode.right		 = insertion.right;
		
		insertion.right.left = newNode;
		
		insertion.right	     = newNode;
		
		newLane = 0;	// variable for sending the node to the next level
		refChange+=4;
		
		while(r.nextDouble() < 0.5)	// coin toss to see if the node will go up to the next level
		{
			refChange+=10;
			if(newLane >= height)	
			{
				SkipListNode newLaneHead, newTailHead;

			   height++;	// height is how many lanes the list has already

		       newLaneHead = new SkipListNode(SkipListNode.NEG_INF , 0);	// creates a new head 
		       newTailHead = new SkipListNode(SkipListNode.POS_INF , 0);	// creates a new tail
		   
			   newLaneHead.right = newTailHead;	// sets the new head to the new tail
			   newLaneHead.down  = head;		// sets the new head to the head below it 

			   newTailHead.left = newLaneHead;	// sets the new tail to the new head 
			   newTailHead.down = tail;			// sets the new tail to the tail below it 

			   head.up = newLaneHead;	// sets the old heads up reference to the new head 
			   tail.up = newTailHead;	// sets the old tails up reference to the new tail

			   head = newLaneHead;	// moves the reference of head to the new head 
			   tail = newTailHead;	// moves the reference of tail to the new tail

			}
		
			while (insertion.up == null)	// will search for a node until it is able to go up to the next lane to add the new node
				insertion = insertion.left;
			
			insertion = insertion.up;	// sets insertion to the nodes up reference 
			
			SkipListNode aboveNewNode = new SkipListNode(element , 0);	// creates a new node to be added to the lane above it 
			
			aboveNewNode.left  = insertion;			// sets it's left link to the insertion point 
			aboveNewNode.right = insertion.right;	// sets right link to the insertion point 
			aboveNewNode.down  = newNode;			// sets it's down link to the new node added to the list
			
			// sets the links for the insertion to the new node added to the lane
			insertion.right.left = aboveNewNode;	 
			insertion.right 	 = aboveNewNode;
			newNode.up 			 = aboveNewNode;
			newNode 			 = aboveNewNode;	// moves the reference of the new node to the node above it 
			
			newLane++;
		}
		entries++;
	}
	
	public String toString()
	{
		SkipListNode headLocation = head;
		SkipListNode currWord;
		
		while(headLocation.down != null)
			headLocation = headLocation.down;
		
		headLocation = headLocation.right;
		String list = "List: \n";
		while(headLocation.key != SkipListNode.POS_INF)
		{
			currWord = headLocation;
			if(currWord.getCount() >= 10)
				list = list + "Count: " + currWord.getCount() + "   ";
			else 
				list = list + "Count: " + currWord.getCount() + "    ";
			do
			{
				list = list + currWord.getInfo() + "--";
				currWord = currWord.up;
			}while(currWord != null);
			
			list = list + "null";
			list = list + "\n";
			headLocation = headLocation.right;
		}
		return list;
	}
	
	public int TotalWordCount()
	{
		//collects the total word count by going to each of the nodes and 
		// collecting the int value in the node and adding it to a running 
		// count until it reaches the end of the list.
		
		SkipListNode hold = head;
		while(hold.down != null)
			hold = hold.down;
		current = hold.right;
		int total = 0;
		while (current.key != SkipListNode.POS_INF)
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
		
		SkipListNode hold = head;
		while(hold.down != null)
			hold = hold.down;
		current  = hold.right;
		int distinctTotal = 0;
		while (current.key != SkipListNode.POS_INF)
		{
			distinctTotal++;
			current = current.right;
		}
		return distinctTotal;
	}
	
	public void remove(String element)
	{
		SkipListNode removal;
		
		removal = search(element);	// element gets sent to the search method, if the element is in
									// the list then removal = element. if it is not in the list then
									// it will return negInf
		if(removal.key.equals(element) && removal.getCount() != 1)
		{
			dec(removal);	// if the word is in the list then it decrements that words count
			return;
		}
		
		if(removal.key != SkipListNode.NEG_INF && removal.getCount() == 1)
		{
			while (removal != null)
			{
				refChange+=2;
				
				removal.left.right  = removal.right;	// sets the current nodes left link to the current nodes right link
				removal.right.left  = removal.left;		// sets the current nodes right link to the current nodes left link
				removal = removal.up;	// sets the reference to the nodes up link
				
				if(removal != null)
				{	// this means that the node has a reference to something above it and must remove it 
					// then it come back around again doing it until it's up reference is null
					refChange++;
					removal.down = null;
				}	
				
				if(head.right == tail && height > 1)
				{	// This removes a lane only if the lane is empty after a node was removed
				     head.right   = null;      tail.left    = null;
				     head         = head.down; tail         = tail.down;
				     head.up.down = null;      tail.up.down = null;
				     head.up      = null;      tail.up      = null;
				     height--;
				}
			}
		}
	}
	
}                                                                                                                                                 
                                                                   
                                                                           