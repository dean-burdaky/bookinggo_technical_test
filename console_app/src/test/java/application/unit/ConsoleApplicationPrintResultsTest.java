package application.unit;

import aggregator.data.CarType;
import aggregator.data.ServiceOption;
import aggregator.data.Supplier;
import application.CommandLineArguments;
import application.ConsoleApplication;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import util.exceptions.NullArgumentException;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static application.integration.ConsoleApplicationRunTest.TEST_ARGS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ConsoleApplicationPrintResultsTest
{
    private static CommandLineArguments cla;

    @BeforeAll
    static void setupConsoleApp()
    {
        cla = new CommandLineArguments(System.err, TEST_ARGS);
    }

    private static Stream<Arguments> providePrintResultsTestData()
    {
        List<ServiceOption> singleSOList = new ArrayList<>(), multiSOList = new ArrayList<>();
        ServiceOption optionShared = new ServiceOption(CarType.MINIBUS, Supplier.DAVE, ServiceOption.MIN_PRICE);
        singleSOList.add(optionShared); multiSOList.add(optionShared);
        multiSOList.add(optionShared); // Allow duplicate for testing purposes.
        return Stream.of(
                Arguments.of(Collections.emptyList(), 0),
                Arguments.of(singleSOList, 1),
                Arguments.of(multiSOList, multiSOList.size())
        );
    }

    @Test
    void testPrintResultsFail()
    {
        PrintStream out = mock(PrintStream.class);
        ConsoleApplication consoleApplication = new ConsoleApplication(cla, out);

        NullArgumentException exception = assertThrows(
                NullArgumentException.class,
                ()-> consoleApplication.printResults(null),
                "Expected exception not thrown.");
        verifyNoInteractions(out);
        assertEquals(0, exception.getArgumentPos());
        assertEquals(List.class, exception.getArgumentClass());
    }

    @ParameterizedTest
    @MethodSource("providePrintResultsTestData")
    void testPrintResultsSuccess(final List<ServiceOption> serviceOptions, final int numberOfPrints)
    {
        PrintStream out = mock(PrintStream.class);
        ConsoleApplication consoleApplication = new ConsoleApplication(cla, out);

        consoleApplication.printResults(serviceOptions);
        verify(out, times(numberOfPrints)).println(anyString());
    }
}
