package util.exceptions;

public class NullArgumentException extends IllegalArgumentException
{
    private final int argumentPos;
    private final Class<?> argumentClass;

    public NullArgumentException(final int argumentPos, final Class<?> argumentClass)
    {
        super(createMessage(argumentPos, argumentClass));
        this.argumentPos = argumentPos;
        this.argumentClass = argumentClass;
    }

    private static String createMessage(final int argumentPos, final Class<?> argumentClass)
    {
        return "Argument " + argumentPos + " (" + argumentClass.getSimpleName() + ") was null.";
    }

    public final int getArgumentPos() { return argumentPos; }

    public final Class<?> getArgumentClass()
    {
        return argumentClass;
    }
}
