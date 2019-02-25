/* The function of this console code is to change an infix expression into a postfix expression and then output the result. 
 * We will determine the postfix expression by sending it to the class infixToPostfix, the code ignores the spaces so the 
 * user can input spaces between there operands and operators and it will still work fine even if they did't put spaces. 
 * The result is evaluated by using the postFix expression instead of the infix. Once we have the infix expression and 
 * the result we can output it to the user and then if they want to input another expression they can, if they are done 
 * they just have to enter a blank line and the program will terminate.
 *  
 *  Author: Samuel Butler
 *  EECS 2500
 *  Date: 10/15/2018
 */
import java.util.Scanner;

import support.*;

public class ExpressionEvaluator
{
	public static void main(String[] args) throws PostFixException, StackUnderFlowException
	{ /* The function of main makes a series of instances of classes including the balance class, infixToPostfix, and
	   * and postfixEvaluator and as well as catching the exceptions that can occur throughout the program. Once we have
	   * the objects, we can go through a series of general steps on how to convert infix to postfix:
	   * 
	   * 1. Check to see if infix has balanced parenthesis's
	   * 	- Instantiate balanced class
	   * 	- Send user expression to balanced class
	   * 	- return result
	   * 		+ Possible returns are either 0 = balanced , 1 = unbalanced , 2 = premature end
	   * 
	   * 2. Convert infix to postfix expression
	   * 	- Instantiate infixToPostFix class
	   * 	- Send expression to infixToPostFix class
	   * 	- Create linked stack to hold operands and operators
	   * 	- return postfix expression
	   * 
	   * 3. Then calculate result based on postfix expression
	   * 	- Use postfix expression and send it to postfixEvaluator class
	   * 	- return result
	   * 		+ Return of type long 
	   * 
	   * 4. Catches the exceptions thrown by the classes
	   * 	-Possible exceptions
	   * 		+ StackUnderFlowExcetpion - When either you want to top or pop a stack
	   * 		+ PostFixException - When the user has to many operands or operators, or an illegal symbol
	   * 		+ MaxIntValueException - when the operand or result is greater than the max int value
	   */
		
		Balanced bal   = new Balanced("(",")");		// creates an instance from the balanced class
		Scanner scan   = new Scanner(System.in);	// creates the scanner to receive an input from the user
		Boolean answer = true; 
		String ans = "0";// the condition value of the loop
		System.out.print("Enter an expression to be evaluated: ");
		
		do
		{
			String expression;	// creates a string variable to hold the users expression
			int balResult;		// the condition for the loop, it will first do the operation and then test to see if condition is true
			int result = 0;
			String postFix = "";
			
			expression = scan.nextLine();	//gets the user's expression from the console
			
			if(expression.equals("")) System.exit(1);	// test to see if the user entered a blank line, meaning they are done
														// and the program will terminate
			
	
			balResult  = bal.test(expression);	// test to make sure that the expression that the user entered 
												// has balanced parenthesis's, if not it will tell the user
			if(balResult == 1)	// A 1 tells the user that there expression has unbalanced paren's
			{
				System.out.println("Unbalanced symbols, try again."+"\n");
				continue;
			}
			
			if(balResult == 2)	// A 2 tells the user that there expression came to a premature end
			{					// meaning they didn't close a paren they opened
			
				System.out.println("Has a premature end of expression, try again."+"\n");
				continue;
			}
			// otherwise it means that the expression is balanced and returned a 0
			
			try
			{
				InfixToPostfix exp = new InfixToPostfix(expression);	// Creates an instance of the infixToPostfix class
				postFix = exp.convert(ans);	// This will convert the infix expression to a postfix expression
				result = (int) PostFixEvaluator.evaluate(postFix, answer);
			}
			
			catch (PostFixException e)			// This will happen when there is an illegal symbol or not enough operands or operators
			{
				System.out.println("Error in expression: " + e.getMessage() + "\n");
				continue;
			}
			catch(MaxIntValueException e)		// This will happen when the max value an int can hold is exceeded
			{
				System.out.println("Error in expression: " + e.getMessage()+ "\n");
				continue;
			}
			catch(StackUnderFlowException e)	// This will happen when a pop or top is attempted on an empty stack
			{
				System.out.println("Error in expression: " + e.getMessage());
				continue;
			}
			catch(ArithmeticException e)
			{
				System.out.println(e.getMessage() + "\n"); // This will happen when the result or operand is greater than an int
				continue;
			}
			catch(SquareOfNegativeException e)
			{
				System.out.println("Error in expression: " +e.getMessage() + "\n");	// this will happen when the user try's to take the square root or cube
				continue;															// root of a negative number
			}
			catch(StringIndexOutOfBoundsException e )
			{
				System.out.println("Error in expression: Invalid epression" + "\n");
				continue;
			}
			
			System.out.println("Postfix Expression: "+ postFix + "");	// outputs postfix expression
			System.out.println("Result: "+ result +"\n");	// outputs result of postfix expression
			ans = Integer.toString(result);
			
		}while(answer);	// Will prompt the user to enter an expression as long as they don't enter a blank line
						// once they enter a blank line the program will end
		
		scan.close(); // closes the resource leak from the scanner
	} // end main
}// end ExpressionEvaluator class
