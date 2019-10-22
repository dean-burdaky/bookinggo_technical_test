package aggregator.data.unit;

import aggregator.data.CarType;
import aggregator.data.ServiceOption;
import aggregator.data.Supplier;
import aggregator.data.UnfilteredServiceOption;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import util.exceptions.IntegerToLowException;
import util.exceptions.NullArgumentException;

import java.util.stream.Stream;

import static aggregator.data.GeneralQuery.MIN_CAPACITY;
import static aggregator.data.ServiceOption.MIN_PRICE;
import static aggregator.testhelper.NullArgumentExceptionDataAsserter.assertNullArgumentExceptionData;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UnfilteredServiceOptionConstructorTest
{

    private static Stream<Arguments> provideUnfilteredServiceOptionValues()
    {
        return Stream.of(
                Arguments.of(MIN_CAPACITY-1, null, new IntegerToLowException(MIN_CAPACITY-1, MIN_CAPACITY)),
                Arguments.of(MIN_CAPACITY, null, new NullArgumentException(1, ServiceOption.class))
        );
    }

    @ParameterizedTest
    @MethodSource("provideUnfilteredServiceOptionValues")
    void testConstructorFail(final int capacity,
                             final ServiceOption option,
                             final RuntimeException expectedException)
    {
        RuntimeException exception = assertThrows(
                expectedException.getClass(),
                ()-> new UnfilteredServiceOption(capacity, option),
                "Expected exception not thrown.");
        if (exception instanceof NullArgumentException)
            assertNullArgumentExceptionData(
                    (NullArgumentException) exception,
                    (NullArgumentException) expectedException);
    }

    @Test
    void testConstructorSuccess()
    {
        ServiceOption option = new ServiceOption(CarType.EXECUTIVE, Supplier.DAVE, MIN_PRICE);
        UnfilteredServiceOption unfilteredSO = new UnfilteredServiceOption(MIN_CAPACITY, option);
        assertEquals(MIN_CAPACITY, unfilteredSO.capacity);
        assertEquals(option, unfilteredSO.serviceOption);
    }
}
