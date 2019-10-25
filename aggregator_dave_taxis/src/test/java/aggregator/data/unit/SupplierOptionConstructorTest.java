package aggregator.data.unit;

import aggregator.data.CarType;
import aggregator.data.SupplierOption;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import util.exceptions.IntegerToLowException;
import util.exceptions.NullArgumentException;

import java.util.stream.Stream;

import static aggregator.data.ServiceOption.MIN_PRICE;
import static aggregator.testhelper.NullArgumentExceptionDataAsserter.assertNullArgumentExceptionData;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SupplierOptionConstructorTest
{
    private static Stream<Arguments> provideSupplierOptionValues()
    {
        return Stream.of(
                Arguments.of(null, MIN_PRICE-1, new NullArgumentException(0, CarType.class)),
                Arguments.of(CarType.EXECUTIVE, MIN_PRICE-1, new IntegerToLowException(MIN_PRICE-1, MIN_PRICE))
        );
    }

    @ParameterizedTest
    @MethodSource("provideSupplierOptionValues")
    void testConstructorFail(final CarType carType,
                             final int price,
                             final RuntimeException expectedException)
    {
        RuntimeException exception = assertThrows(
                expectedException.getClass(),
                ()-> new SupplierOption(carType, price),
                "Expected exception not thrown.");
        if (exception instanceof NullArgumentException)
            assertNullArgumentExceptionData(
                    (NullArgumentException) exception,
                    (NullArgumentException) expectedException);
    }

    @Test
    void testConstructorSuccess()
    {
        SupplierOption option = new SupplierOption(CarType.EXECUTIVE, MIN_PRICE);
        assertEquals(CarType.EXECUTIVE, option.carType);
        assertEquals(MIN_PRICE, option.price);
    }
}
