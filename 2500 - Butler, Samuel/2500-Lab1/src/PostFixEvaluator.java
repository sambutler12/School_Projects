import java.util.Scanner;

import support.*;
public class PostFixEvaluator
{
	public static long evaluate (String expression, Boolean close) throws PostFixException, StackUnderFlowException, MaxIntValueException, SquareOfNegativeException
	{
		/* The function of this class is to take a postfix expression and output the result based on 
		 * the given operations the user is allowed to input. We base our while loop off of having a next value,
		 * if there is not a next value then that means there is nothing else to evaluate and can break out of
		 * the loop. There are two things the class checks for, that it is either an operator or an operand if 
		 * it is not either of those then we know that there is an invalid symbol in the expression. The expression
		 * is evaluated left to right.
		 * 
		 * Example:
		 * postfix expression = 2 3 + 4 +			|	|	  | + |		|	|	  |	+ |		|	|
		 * 						^ ^ ^ ^ ^			| 3	|---->|	3 |---->|	|---->|	4 |---->|	|
		 * 	                	| | | | |			|_2_|	  |_2_|		|_5_|	  |_5_|		|_9_|
		 * operand -------------+ | | | |		
		 * operand ---------------+ | | |
		 * operator-----------------+ | |
		 * operand--------------------+ |
		 * operator---------------------+
		 * - Will Read expression left to right
		 * 
		 * How the PostFixEvaluator processes the expression to get a result
		 * 
		 */
		linkedStack<Long> stack = new linkedStack<Long>();	//creates a linked stack full of long numbers
		
		long value;	// value has to be a long to check if it is over the possible int value
		String operator;	// Stores the operator the user enters
		
		long operand1;
		long operand2;
		
		long result = 0;
		Scanner token = new Scanner(expression); // Stores
		
		while(token.hasNext()) 
		{
			if(token.hasNextLong())	// See's if the next is a number 
			{
				value = token.nextLong(); 
				stack.push(value); // Push's the operand onto the stack 
			}
			else
			{
				operator = token.next(); // Means that the next is an operator and puts that into a variable 
				if(stack.isEmpty())
					throw new PostFixException("Not enough operands - stack underflow");
				
				operand2 = stack.top(); // We call it the second operand since it's a stack and it's LIFO 
				stack.pop();			// Once we get the operand we can pop it
				
				if(operand2 > Integer.MAX_VALUE || operand2 < Integer.MIN_VALUE) // Makes sure the number isn't bigger than a long
					throw new MaxIntValueException("Operand out of range of an int.");
				
				if(operator.equals("Q"))	
				{	
					if(operand2 < 0)
						throw new SquareOfNegativeException("Attempted square root on negetive number.");
					
					result = (long) Math.sqrt(operand2);// takes the square root of the operand
					stack.push(result);					// push's that result back onto the stack
					continue;							// breaks out of the current loop 
				}
				
				if(operator.equals("C"))	
				{
					if(operand2 < 0)
						throw new SquareOfNegativeException("Attempted cube root on negetive number.");
					
					result = (long) Math.cbrt(operand2);// takes the cube root of the operand 
					stack.push(result);					//push's that result back onto the stack
					continue;							// breaks out of the current loop
				}
				
				operand1 = stack.top();
				stack.pop();
				
				if(operand1 > Integer.MAX_VALUE || operand1 < Integer.MIN_VALUE) // Makes sure the number isn't bigger than a long
					throw new MaxIntValueException("Operand out of range of an int.");
				
				// Where it calculates the expression based on the operator or operators that 
				// the user inputed into their expression
				if(operator.equals("/"))
				{
					if(operand2 == 0)
						throw new ArithmeticException("Error in expression: Division by 0.");
					else
						result = operand1 / operand2;
				}
				
				else if(operator.equals("*"))
					result = operand1 * operand2;
				
				else if(operator.equals("+"))
					result = operand1 + operand2;
				
				else if(operator.equals("-"))
					result = operand1 - operand2;
				
				else if(operator.equals(">"))	// Bitwise operators that shifts a binary values to the right (8 > 2) = 1000 > 2 = 10 = 2
					result = operand1 >> operand2;
				
				else if(operator.equals("<")) // Bitwise operators that shifts binary values to the left (8 < 2) = 1000 < 2 = 100000 = 32
					result = operand1 << operand2;
				
				else if(operator.equals("^"))
					result = (long) Math.pow(operand1, operand2);
				
				else if(operator.equals("%"))
					result = operand1 % operand2;
				else
					throw new PostFixException("Illegal symbol - "+ "\"" + operator + "\"");	// if it is not one of 
																								// the possible operations
				stack.push(result);
			}
			if(!close) token.close(); // closes the resource leak of the scanner
		}
		if(stack.isEmpty())
			throw new PostFixException("Not enough operands - stack underflow");	// if there are no operators on the stack
																					// that means there are not enough operands
		
		result = stack.top();	// if the stack is not empty that means the stack has the result
		stack.pop();			// then it will pop the stack and it should be empty
		
		if(!stack.isEmpty()) // if it is not empty that means there are to many operands 
			throw new PostFixException("Too many operands - operands left over");
		
		if(result > Integer.MAX_VALUE || result < Integer.MIN_VALUE)			// this checks to make sure that the result is 
			throw new MaxIntValueException("Result out of range of an int.");	// not over or below that max or min int 
		
		return result;
	}// end evaluate class
}// end PostFixEvaluator class
