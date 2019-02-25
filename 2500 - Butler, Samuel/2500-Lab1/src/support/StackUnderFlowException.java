package support;

@SuppressWarnings("serial")
public class StackUnderFlowException extends Exception
{
	public StackUnderFlowException()
	{
		super();
	}
	
	public StackUnderFlowException(String message)
	{
		super(message);
	}
}
