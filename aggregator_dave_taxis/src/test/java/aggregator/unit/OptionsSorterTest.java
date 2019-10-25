package aggregator.unit;

import aggregator.OptionsSorter;
import aggregator.data.CarType;
import aggregator.data.ServiceOption;
import aggregator.data.Supplier;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import util.exceptions.NullArgumentException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static aggregator.data.ServiceOption.MIN_PRICE;
import static aggregator.testhelper.NullArgumentExceptionDataAsserter.assertNullArgumentExceptionData;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OptionsSorterTest
{
    private static OptionsSorter sorter;
    private static List<ServiceOption> orderedServiceOptions;

    @BeforeAll
    static void setupSorterTest()
    {
        sorter = new OptionsSorter();
        orderedServiceOptions = new ArrayList<>();
        orderedServiceOptions.add(new ServiceOption(CarType.EXECUTIVE, Supplier.DAVE, MIN_PRICE+2));
        orderedServiceOptions.add(new ServiceOption(CarType.EXECUTIVE, Supplier.DAVE, MIN_PRICE+1));
        orderedServiceOptions.add(new ServiceOption(CarType.EXECUTIVE, Supplier.DAVE, MIN_PRICE));
    }

    private static List<ServiceOption> createOptionsOfOrder(int... order)
    {
        List<ServiceOption> serviceOptions = new ArrayList<>();
        for (int index : order) serviceOptions.add(orderedServiceOptions.get(index));
        return serviceOptions;
    }

    private static Stream<Arguments> provideSorterTestData()
    {
        return Stream.of(
                Arguments.of(Collections.emptyList(), Collections.emptyList()),
                Arguments.of(createOptionsOfOrder(0), orderedServiceOptions.subList(0, 1)),
                Arguments.of(createOptionsOfOrder(0, 1), orderedServiceOptions.subList(0, 2)),
                Arguments.of(createOptionsOfOrder(1, 0), orderedServiceOptions.subList(0, 2)),
                Arguments.of(createOptionsOfOrder(0, 2, 1), orderedServiceOptions)
        );
    }

    @ParameterizedTest
    @MethodSource("provideSorterTestData")
    void testSortSuccess(final List<ServiceOption> serviceOptions, final List<ServiceOption> expectedOptions)
    {
        List<ServiceOption> sortedOptions = sorter.sort(serviceOptions);
        assertEquals(expectedOptions, sortedOptions, "List not sorted as expected.");
    }

    @Test
    void testSortFail()
    {
        NullArgumentException exception = assertThrows(
                NullArgumentException.class,
                ()-> sorter.sort(null),
                "Expected exception not thrown.");
        assertNullArgumentExceptionData(exception, new NullArgumentException(0, List.class));
    }
}
