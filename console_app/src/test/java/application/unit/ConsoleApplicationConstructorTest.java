package application.unit;

import application.CommandLineArguments;
import application.ConsoleApplication;
import application.FailedCLAException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import util.exceptions.NullArgumentException;

import java.io.PrintStream;
import java.util.stream.Stream;

import static application.integration.ConsoleApplicationRunTest.TEST_ARGS;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.Mockito.mock;

class ConsoleApplicationConstructorTest
{
    private static Stream<Arguments> provideConstructorNullArgTestData()
    {
        CommandLineArguments cla = new CommandLineArguments(System.err, TEST_ARGS);
        return Stream.of(
                Arguments.of(null, null, 0, CommandLineArguments.class),
                Arguments.of(cla, null, 1, PrintStream.class)
        );
    }

    @ParameterizedTest
    @MethodSource("provideConstructorNullArgTestData")
    void testConstructorFailNullArg(final CommandLineArguments cla,
                                    final PrintStream out,
                                    final int argPos,
                                    final Class<?> argClass)
    {
        NullArgumentException exception = assertThrows(
                NullArgumentException.class,
                () -> new ConsoleApplication(cla, out),
                "Expected exception not thrown.");
        assertEquals(argPos, exception.getArgumentPos());
        assertEquals(argClass, exception.getArgumentClass());
    }

    @Test
    void testConstructorFailFailedCLA()
    {
        String[] noArgs = {};
        CommandLineArguments cla = new CommandLineArguments(System.err, noArgs);
        assumeTrue(cla.getReadingArgsFailed());
        PrintStream out = mock(PrintStream.class);
        assertThrows(
                FailedCLAException.class,
                () -> new ConsoleApplication(cla, out),
                "Expected exception not thrown.");
    }

    @Test
    void testConstructorSuccess()
    {
        CommandLineArguments cla = new CommandLineArguments(System.err, TEST_ARGS);
        PrintStream out = mock(PrintStream.class);
        assertDoesNotThrow(()-> new ConsoleApplication(cla, out));
    }
}
