/* Balanced class that test if an infix expression is balanced by matching the open and close
 * symbols together using an ArrayStack. The 3 possible outcomes of an expression can either 
 * be a 0, 1, or 2. If the expression is balanced then it will return a 1, if the expression
 * is unbalanced the it will return a 1, and finally if the expression happen to come to a 
 * premature end then it will return a 2.
 * 
 * Symbol types are:
 * 						Parenthesis () 
 * 						Brackets    [] 
 *						Braces      {}
 */

import support.*;
public class Balanced
{
	private String openSet;
	private String closeSet;
	
	public Balanced(String openS, String closeS)
	{ /* ---------------------------------------Constructor--------------------------------------
	   * The size of the openSet characters should be equal to the size of the closeSet characters
	   * As well the openSet characters should match that of the closeSet characters by position.       	 
	   */
		openSet  = openS;
		closeSet = closeS;
	}
	
	public int test(String expression)
	{	/* This class test all the expressions to see if it is valid and that it is balanced
	 	 * Expression = ( [ { ( [ { } ] ) } ] )
	     * 
	     * 			    openSet:					   closeSet:
	     * 
	     *       			   					    }  ]  )  }  ]  )
	     *       							        ^  ^  ^  ^  ^  ^
	     *   Index:								    |  |  |  |  |  |	
	     *     0   +---->| { |----------------------+  |  |  |  |  |				
	     *     1   |     | [ |-------------------------+  |  |  |  |	
	     *     2   |     | ( |----------------------------+  |  |  |
	     *     3   |     | { |-------------------------------+  |  |
	     *     4   |     | [ |----------------------------------+  |
	     *     5   +---->|_(_|-------------------------------------+	
	     *           		
	     * We can see that the index of the open set of symbols should match the symbols of the closeSet
	     * symbols in order to be a balanced expression 
	     * 
		 * Returns: 0------> The expression is balanced
		 *			1------> The expression has unbalanced symbols
		 *			2------> The expression came to an end prematurely
	 	 */
		
		char    currChar;				//the current character in the expression
		
		int     currCharIndex;			//the index of the current character in the expression
		int     lastCharIndex;			//the index of the last character in the expression
		
		int     openIndex;				//index of the current character in the openSet
		int     closeIndex;				//index of the current character in the closeSet
		
		boolean stillBalanced = true;	//this will be true as long as the expression is balanced
		
		ArrayStack<Integer> stack = new ArrayStack<Integer>(expression.length()); //creates a stack to hold the openSet characters
		
		currCharIndex = 0;						//Process the characters 0
		lastCharIndex = expression.length()-1;	//through stack.length()-1
		
		while(stillBalanced && (currCharIndex <= lastCharIndex))	//This will only enter the loop if the current expression is still balanced
		{															//and that is not at the end of the expression
			
			currChar = expression.charAt(currCharIndex);	//looks at the next character
			openIndex = openSet.indexOf(currChar);			//makes sure that the current character is in the openSet
			
			if(openIndex != -1)				//Makes sure that that the current character is in the openSet
				stack.push(openIndex);		//This pushes the character's current index onto the stack
			else
			{
				closeIndex = closeSet.indexOf(currChar); //Is the character in the closeSet
				
				if(closeIndex != -1)	//If the character is in the closeSet
				{
					try					//This will try to pop the index off the stack
					{
						openIndex = (Integer)stack.top();	//this will retrieve the top of the stack
						stack.pop();						//and then remove it from the stack
						
						if(openIndex != closeIndex)		//if the popped opeIndex doesn't match the current closeIndex
						{
							stillBalanced = false;		//Then the expression is not balanced
						}
					}
					catch(StackUnderFlowException e)	//if the stack was empty
					{
						stillBalanced = false;			//then that means the expression is not balanced
					}
				} //end if
			} // end else
			currCharIndex++;
		} // end while loop
		
		if(!stillBalanced)   return 1;	//unbalanced symbols
		if(!stack.isEmpty()) return 2;	//has a premature end of expression
							 return 0;	//Means that the expression is balanced

	}
}
