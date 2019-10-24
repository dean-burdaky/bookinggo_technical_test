package application;

public class BadParameterException extends IllegalArgumentException
{

    public BadParameterException(final String name, final String condition)
    {
        super("Parameter '" + name + "' failed to meet condition: " + condition);
    }
}
