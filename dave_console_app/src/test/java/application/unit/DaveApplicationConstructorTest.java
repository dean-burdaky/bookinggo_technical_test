package application.unit;

import application.DaveApplication;
import application.DaveCommandLineArguments;
import application.FailedCLAException;
import org.junit.jupiter.api.Test;
import util.exceptions.NullArgumentException;

import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.Mockito.mock;

public class DaveApplicationConstructorTest
{
    static final String[] TEST_ARGS = {"25", "135.5", "-17.72", "-180.000"};

    @Test
    void testConstructorFailNullPtr()
    {
        assertThrows(
                NullPointerException.class,
                () -> new DaveApplication(null, null),
                "Expected exception not thrown.");
    }

    @Test
    void testConstructorFailNullArg()
    {
        DaveCommandLineArguments dcla = new DaveCommandLineArguments(System.err, TEST_ARGS);
        NullArgumentException exception = assertThrows(
                NullArgumentException.class,
                () -> new DaveApplication(dcla, null),
                "Expected exception not thrown.");
        assertEquals(1, exception.getArgumentPos());
        assertEquals(PrintStream.class, exception.getArgumentClass());
    }

    @Test
    void testConstructorFailFailedCLA()
    {
        String[] noArgs = {};
        DaveCommandLineArguments dcla = new DaveCommandLineArguments(System.err, noArgs);
        assumeTrue(dcla.getReadingArgsFailed());
        PrintStream out = mock(PrintStream.class);
        assertThrows(
                FailedCLAException.class,
                () -> new DaveApplication(dcla, out),
                "Expected exception not thrown.");
    }

    @Test
    void testConstructorSuccess()
    {
        DaveCommandLineArguments dcla = new DaveCommandLineArguments(System.err, TEST_ARGS);
        PrintStream out = mock(PrintStream.class);
        assertDoesNotThrow(()-> new DaveApplication(dcla, out));
    }
}
