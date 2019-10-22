package aggregator.testhelper;

import util.exceptions.NullArgumentException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class NullArgumentExceptionDataAsserter
{
    public static void assertNullArgumentExceptionData(NullArgumentException exception,
                                                        NullArgumentException expected)
    {
        assertEquals(expected.getArgumentPos(), exception.getArgumentPos());
        assertEquals(expected.getArgumentClass(), exception.getArgumentClass());
    }
}
