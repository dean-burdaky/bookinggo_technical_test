package aggregator.unit;

import aggregator.SupplierResolver;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static aggregator.data.ServiceOption.MIN_PRICE;
import static aggregator.testhelper.NullArgumentExceptionDataAsserter.assertNullArgumentExceptionData;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SupplierResolverTest
{
    private static SupplierResolver resolver;

    private static List<ServiceOption> createList(ServiceOption... options)
    {
        return new ArrayList<>(Arrays.asList(options));
    }

    @BeforeAll
    static void setupResolverTest()
    {
        resolver = new SupplierResolver();
    }

    private static Stream<Arguments> provideResolverTestData()
    {
        ServiceOption optionA1 = new ServiceOption(CarType.EXECUTIVE, Supplier.DAVE, MIN_PRICE+1);
        ServiceOption optionA2 = new ServiceOption(CarType.EXECUTIVE, Supplier.ERIC, MIN_PRICE);
        ServiceOption optionA3 = new ServiceOption(CarType.EXECUTIVE, Supplier.JEFF, MIN_PRICE);
        ServiceOption optionB = new ServiceOption(CarType.LUXURY, Supplier.DAVE, MIN_PRICE+1);

        List<ServiceOption> options1 = createList(optionA1);
        List<ServiceOption> options2 = createList(optionA1, optionB);
        List<ServiceOption> options3 = createList(optionA1, optionA2);
        List<ServiceOption> resolvedOptions3 = createList(optionA2);
        List<ServiceOption> options4 = createList(optionA1, optionA2, optionB);
        List<ServiceOption> resolvedOptions4 = createList(optionA2, optionB);
        List<ServiceOption> options5 = createList(optionA1, optionA2, optionA3);

        return Stream.of(
                Arguments.of(Collections.emptyList(), Collections.emptyList()),
                Arguments.of(options1, options1),
                Arguments.of(options2, options2),
                Arguments.of(options3, resolvedOptions3),
                Arguments.of(options4, resolvedOptions4),
                Arguments.of(options5, resolvedOptions3)
        );
    }

    @ParameterizedTest
    @MethodSource("provideResolverTestData")
    void testResolveSuccess(final List<ServiceOption> serviceOptions, final List<ServiceOption> expectedOptions)
    {
        List<ServiceOption> resolvedOptions = resolver.resolve(serviceOptions);
        assertTrue(expectedOptions.containsAll(resolvedOptions),
                "Expected options does not contain all of the resolved options.");
        assertTrue(resolvedOptions.containsAll(expectedOptions),
                "Resolved options does not contain all of the expected options.");
    }

    @Test
    void testResolveFail()
    {
        NullArgumentException exception = assertThrows(
                NullArgumentException.class,
                ()-> resolver.resolve(null),
                "Expected exception not thrown.");
        assertNullArgumentExceptionData(exception, new NullArgumentException(0, List.class));
    }
}
