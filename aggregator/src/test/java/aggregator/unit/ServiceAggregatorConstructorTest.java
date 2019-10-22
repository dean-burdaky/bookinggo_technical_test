package aggregator.unit;

import aggregator.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import util.exceptions.NullArgumentException;

import java.util.stream.Stream;

import static aggregator.testhelper.NullArgumentExceptionDataAsserter.assertNullArgumentExceptionData;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class ServiceAggregatorConstructorTest
{
    private static QueryConverter converter;
    private static ServiceRequester requester;
    private static ResultsSplitter splitter;
    private static CapacityFilter filter;
    private static SupplierResolver resolver;
    private static OptionsSorter sorter;

    @BeforeAll
    static void setupAggregatorComponents()
    {
        converter = mock(QueryConverter.class);
        requester = mock(ServiceRequester.class);
        splitter = mock(ResultsSplitter.class);
        filter = mock(CapacityFilter.class);
        resolver = mock(SupplierResolver.class);
        sorter = mock(OptionsSorter.class);
    }

    private static Stream<Arguments> provideAggregatorComponentsFail()
    {
        return Stream.of(
                Arguments.of(null, null, null, null, null, null, 0, QueryConverter.class),
                Arguments.of(converter, null, null, null, null, null, 1, ServiceRequester.class),
                Arguments.of(converter, requester, null, null, null, null, 2, ResultsSplitter.class),
                Arguments.of(converter, requester, splitter, null, null, null, 3, CapacityFilter.class),
                Arguments.of(converter, requester, splitter, filter, null, null, 4, SupplierResolver.class),
                Arguments.of(converter, requester, splitter, filter, resolver, null, 5, OptionsSorter.class)
        );
    }

    @ParameterizedTest
    @MethodSource("provideAggregatorComponentsFail")
    void testConstructorFail(final QueryConverter converter,
                             final ServiceRequester requester,
                             final ResultsSplitter splitter,
                             final CapacityFilter filter,
                             final SupplierResolver resolver,
                             final OptionsSorter sorter,
                             final int expectedPos,
                             final Class<?> expectedClass)
    {
        NullArgumentException exception = assertThrows(
                NullArgumentException.class,
                () -> new ServiceAggregator(converter, requester, splitter, filter, resolver, sorter),
                "NullArgumentException was not thrown.");
        assertNullArgumentExceptionData(exception, new NullArgumentException(expectedPos, expectedClass));
    }

    @Test
    void testConstructorSuccess()
    {
        assertDoesNotThrow(
                () -> new ServiceAggregator(converter, requester, splitter, filter, resolver, sorter),
                "Unexpected exception was thrown.");
    }
}
