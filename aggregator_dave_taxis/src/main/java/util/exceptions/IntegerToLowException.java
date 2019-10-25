package util.exceptions;

public class IntegerToLowException extends IllegalArgumentException
{
    public IntegerToLowException(final int number, final int lowerBound)
    {
        super("Number (" + number + ") was below the lower bound (" + lowerBound + ").");
    }
}
