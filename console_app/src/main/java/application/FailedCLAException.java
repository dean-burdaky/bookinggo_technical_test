package application;

public class FailedCLAException extends IllegalArgumentException
{
    public FailedCLAException()
    {
        super("Passed CommandLineArgument that failed to resolve the arg string array.");
    }
}
