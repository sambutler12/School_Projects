

public class Precedence
{
	public static int Prec(String value)
	{	// The main function of this class is to let the postFixEvaluator know what 
		// operator comes first in certain operations. If the user enters a Q or C
		// that operator has the most precedence over any of the other operators, UNLESS
		// there are parenthesis's involved in the expression.
		
		if(value.equals("Q")) return 5;
		if(value.equals("C")) return 5;
		
		return 0;	// return a 0 meaning its either a +, -, *, /, %, <, or > operator
					// In which the precedence between those operators does not matter
	}// end Prec class
}
