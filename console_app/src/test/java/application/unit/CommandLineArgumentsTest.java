package application.unit;

import application.CommandLineArguments;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InOrder;
import util.exceptions.NullArgumentException;

import java.io.PrintStream;
import java.util.stream.Stream;

import static aggregator.data.GeneralQuery.*;
import static application.CommandLineArguments.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommandLineArgumentsTest
{

    private static String testLat1, testLon1;
    private static String testLat2, testLon2;
    private static String testCapacity;
    private static String parseBreaker;

    @BeforeAll
    static void setupTestData()
    {

        testLat1 = String.format("%.02f", MIN_LATITUDE);
        testLon1 = String.format("%.0f", MIN_LONGITUDE);
        testLat2 = String.format("%.01ff", MAX_LATITUDE);
        testLon2 = Float.toString(MAX_LONGITUDE);
        testCapacity = Integer.toString(MIN_CAPACITY);
        parseBreaker = "apples";
    }

    private static String[] provideArgs(final String... args)
    {
        return args;
    }

    private static Stream<Arguments> provideHardFailTestData()
    {
        return Stream.of(
                Arguments.of(null, null, 0, PrintStream.class),
                Arguments.of(mock(PrintStream.class), null, 1, String[].class)
        );
    }

    private static String[] multiLine(final String... lines)
    {
        return lines;
    }

    private static Stream<Arguments> provideSoftFailOnParametersTestData()
    {
        return Stream.of(
                Arguments.of(
                        provideArgs(),
                        multiLine("Too few parameters. Need to be supplied with:", "\t{pickup latitude} "
                                + "{pickup longitude} {dropoff latitude} {dropoff longitude} {capacity}")
                ),
                Arguments.of(
                        provideArgs(testLat1, testLon1, testLat2, testLon2),
                        multiLine("Too few parameters. Need to be supplied with:", "\t{pickup latitude} "
                                + "{pickup longitude} {dropoff latitude} {dropoff longitude} {capacity}")
                ),
                Arguments.of(
                        provideArgs(testLat1, testLon1, testLat2, testLon2, testCapacity, testCapacity),
                        multiLine("Too many parameters. Need to be supplied with:", "\t{pickup latitude} "
                                + "{pickup longitude} {dropoff latitude} {dropoff longitude} {capacity}")
                ),
                Arguments.of(
                        provideArgs(testLat1 + parseBreaker, testLon1, testLat2, testLon2, testCapacity),
                        multiLine("Parameter 1 is not a floating point value (decimal value).",
                                "\tReceived: " + testLat1 + parseBreaker)
                ),
                Arguments.of(
                        provideArgs(testLat1, testLon1 + parseBreaker, testLat2, testLon2, testCapacity),
                        multiLine("Parameter 2 is not a floating point value (decimal value).",
                                "\tReceived: " + testLon1 + parseBreaker)
                ),
                Arguments.of(
                        provideArgs(testLat1, testLon1, testLat2 + parseBreaker, testLon2, testCapacity),
                        multiLine("Parameter 3 is not a floating point value (decimal value).",
                                "\tReceived: " + testLat2 + parseBreaker)
                ),
                Arguments.of(
                        provideArgs(testLat1, testLon1, testLat2, testLon2 + parseBreaker, testCapacity),
                        multiLine("Parameter 4 is not a floating point value (decimal value).",
                                "\tReceived: " + testLon2 + parseBreaker)
                ),
                Arguments.of(
                        provideArgs(testLat1, testLon1, testLat2, testLon2, testCapacity + parseBreaker),
                        multiLine("Parameter 5 is not an integer value.",
                                "\tReceived: " + testCapacity + parseBreaker)
                )
        );
    }

    private static Stream<Arguments> provideSoftFailOnRangesTestData()
    {
        return Stream.of(
                // First pair.
                Arguments.of(
                        provideArgs(Float.toString(MIN_LATITUDE - 1), testLon1, testLat2, testLon2, testCapacity),
                        multiLine("Parameter 1 is not inclusively between " + SHORT_MIN_LAT + " and " + SHORT_MAX_LAT
                                        + ".", "\tReceived: " + (MIN_LATITUDE - 1))
                ),
                Arguments.of(
                        provideArgs(Float.toString(MAX_LATITUDE + 1), testLon1, testLat2, testLon2, testCapacity),
                        multiLine("Parameter 1 is not inclusively between " + SHORT_MIN_LAT + " and " + SHORT_MAX_LAT
                                        + ".", "\tReceived: " + (MAX_LATITUDE + 1))
                ),
                // Second pair
                Arguments.of(
                        provideArgs(testLat1, Float.toString(MIN_LONGITUDE - 1), testLat2, testLon2, testCapacity),
                        multiLine("Parameter 2 is not inclusively between " + SHORT_MIN_LON + " and " + SHORT_MAX_LON
                                        + ".", "\tReceived: " + (MIN_LONGITUDE - 1))
                ),
                Arguments.of(
                        provideArgs(testLat1, Float.toString(MAX_LONGITUDE + 1), testLat2, testLon2, testCapacity),
                        multiLine("Parameter 2 is not inclusively between " + SHORT_MIN_LON + " and " + SHORT_MAX_LON
                                        + ".", "\tReceived: " + (MAX_LONGITUDE + 1))
                ),
                // Third pair
                Arguments.of(
                        provideArgs(testLat1, testLon1, Float.toString(MIN_LATITUDE - 1), testLon2, testCapacity),
                        multiLine("Parameter 3 is not inclusively between " + SHORT_MIN_LAT + " and " + SHORT_MAX_LAT
                                        + ".", "\tReceived: " + (MIN_LATITUDE - 1))
                ),
                Arguments.of(
                        provideArgs(testLat1, testLon1, Float.toString(MAX_LATITUDE + 1), testLon2, testCapacity),
                        multiLine("Parameter 3 is not inclusively between " + SHORT_MIN_LAT + " and " + SHORT_MAX_LAT
                                        + ".", "\tReceived: " + (MAX_LATITUDE + 1))
                ),
                // Fourth pair
                Arguments.of(
                        provideArgs(testLat1, testLon1, testLat2, Float.toString(MIN_LONGITUDE - 1), testCapacity),
                        multiLine("Parameter 4 is not inclusively between " + SHORT_MIN_LON + " and " + SHORT_MAX_LON
                                        + ".", "\tReceived: " + (MIN_LONGITUDE - 1))
                ),
                Arguments.of(
                        provideArgs(testLat1, testLon1, testLat2, Float.toString(MAX_LONGITUDE + 1), testCapacity),
                        multiLine("Parameter 4 is not inclusively between " + SHORT_MIN_LON + " and " + SHORT_MAX_LON
                                        + ".", "\tReceived: " + (MAX_LONGITUDE + 1))
                ),
                // Capacity
                Arguments.of(
                        provideArgs(testLat1, testLon1, testLat2, testLon2, Integer.toString(MIN_CAPACITY - 1)),
                        multiLine("Parameter 5 is less than " + MIN_CAPACITY + ".",
                                "\tReceived: " + (MIN_CAPACITY - 1))
                )
        );
    }

    @Test
    void testConstructorSuccess()
    {
        String[] args = provideArgs(testLat1, testLon1, testLat2, testLon2, testCapacity);
        PrintStream err = mock(PrintStream.class);
        CommandLineArguments cla = new CommandLineArguments(err, args);
        verifyNoInteractions(err);
        assertEquals(Float.parseFloat(testLat1), cla.pickupLat);
        assertEquals(Float.parseFloat(testLon1), cla.pickupLon);
        assertEquals(Float.parseFloat(testLat2), cla.dropoffLat);
        assertEquals(Float.parseFloat(testLon2), cla.dropoffLon);
        assertEquals(Integer.parseInt(testCapacity), cla.capacity);
    }

    @ParameterizedTest
    @MethodSource("provideHardFailTestData")
    void testConstructorHardFail(final PrintStream err, final String[] args, final int argPos, final Class<?> argClass)
    {
        NullArgumentException exception = assertThrows(
                NullArgumentException.class,
                ()-> new CommandLineArguments(err, args),
                "Expected exception not thrown.");
        assertEquals(argPos, exception.getArgumentPos());
        assertEquals(argClass, exception.getArgumentClass());
    }

    @ParameterizedTest
    @MethodSource("provideSoftFailOnParametersTestData")
    void testConstructorSoftFailOnParameters(final String[] args, String[] message)
    {
        PrintStream err = mock(PrintStream.class);
        InOrder inOrder = inOrder(err);
        CommandLineArguments cla = new CommandLineArguments(err, args);
        for (String line : message) inOrder.verify(err).println(line);
        inOrder.verifyNoMoreInteractions();
        assertTrue(cla.getReadingArgsFailed());
    }

    @ParameterizedTest
    @MethodSource("provideSoftFailOnRangesTestData")
    void testConstructorSoftFailOnRanges(final String[] args, String[] message)
    {
        PrintStream err = mock(PrintStream.class);
        InOrder inOrder = inOrder(err);
        CommandLineArguments cla = new CommandLineArguments(err, args);
        for (String line : message) inOrder.verify(err).println(line);
        inOrder.verifyNoMoreInteractions();
        assertTrue(cla.getReadingArgsFailed());
    }
}
