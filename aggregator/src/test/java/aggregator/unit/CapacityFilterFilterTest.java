package aggregator.unit;

import aggregator.CapacityFilter;
import aggregator.data.CarType;
import aggregator.data.ServiceOption;
import aggregator.data.Supplier;
import aggregator.data.UnfilteredServiceOption;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CapacityFilterFilterTest
{
    private static CapacityFilter filter;
    private static ServiceOption option1, option2;
    private static UnfilteredServiceOption unfilteredOption1, unfilteredOption2;

    @BeforeAll
    static void setupFilterTest()
    {
        filter = new CapacityFilter();
        int capacity = CarType.PEOPLE_CARRIER.capacity;
        option1 = new ServiceOption(CarType.PEOPLE_CARRIER, Supplier.DAVE, MIN_PRICE);
        unfilteredOption1 = new UnfilteredServiceOption(capacity, option1);
        option2 = new ServiceOption(CarType.EXECUTIVE, Supplier.DAVE, MIN_PRICE);
        unfilteredOption2 = new UnfilteredServiceOption(capacity, option2);
    }

    private static List<ServiceOption> createSingleOptionList(final boolean toBeFiltered)
    {
        List<ServiceOption> serviceOptions = new ArrayList<>();
        if (toBeFiltered) serviceOptions.add(option2);
        else serviceOptions.add(option1);
        return serviceOptions;
    }

    private static List<UnfilteredServiceOption> createSingleUnfilteredOptionList(final boolean toBeFiltered)
    {
        List<UnfilteredServiceOption> serviceOptions = new ArrayList<>();
        if (toBeFiltered) serviceOptions.add(unfilteredOption2);
        else serviceOptions.add(unfilteredOption1);
        return serviceOptions;
    }

    private static List<UnfilteredServiceOption> createMultiUnfilteredOptionList()
    {
        List<UnfilteredServiceOption> serviceOptions = new ArrayList<>();
        serviceOptions.add(unfilteredOption1);
        serviceOptions.add(unfilteredOption2);
        return serviceOptions;
    }

    private static Stream<Arguments> provideFilterTestData()
    {
        return Stream.of(
                Arguments.of(Collections.emptyList(), Collections.emptyList()),
                Arguments.of(createSingleUnfilteredOptionList(true), Collections.emptyList()),
                Arguments.of(createSingleUnfilteredOptionList(false), createSingleOptionList(false)),
                Arguments.of(createMultiUnfilteredOptionList(), createSingleOptionList(false))
        );
    }

    @ParameterizedTest
    @MethodSource("provideFilterTestData")
    void testFilterSuccess(final List<UnfilteredServiceOption> serviceOptions,
                           final List<ServiceOption> expectedResults)
    {
        List<ServiceOption> filteredServiceOptions = filter.filter(serviceOptions);
        assertTrue(expectedResults.containsAll(filteredServiceOptions),
                "Expected results list does not contain all filtered service options.");
        assertTrue(filteredServiceOptions.containsAll(expectedResults),
                "Filtered service options list does not contain all expected service options.");
    }

    @Test
    void testFilterFail()
    {
        NullArgumentException exception = assertThrows(
                NullArgumentException.class,
                ()-> filter.filter(null),
                "Expected exception was not thrown.");
        assertNullArgumentExceptionData(exception, new NullArgumentException(0, List.class));
    }
}
