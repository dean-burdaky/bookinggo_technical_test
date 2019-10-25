package aggregator.data.unit;

import aggregator.data.CarType;
import aggregator.data.ServiceOption;
import aggregator.data.Supplier;
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

class ServiceOptionConstructorTest
{

    private static Stream<Arguments> provideServiceOptionValues()
    {
        return Stream.of(
                Arguments.of(
                        null,
                        null,
                        MIN_PRICE-1,
                        new NullArgumentException(0, CarType.class)
                ),
                Arguments.of(
                        CarType.EXECUTIVE,
                        null,
                        MIN_PRICE-1,
                        new NullArgumentException(1, Supplier.class)
                ),
                Arguments.of(
                        CarType.EXECUTIVE,
                        Supplier.DAVE,
                        MIN_PRICE-1,
                        new IntegerToLowException(MIN_PRICE-1, MIN_PRICE)
                )
        );
    }

    @ParameterizedTest
    @MethodSource("provideServiceOptionValues")
    void testConstructorFail(final CarType carType,
                             final Supplier supplier,
                             final int price,
                             final RuntimeException expectedException)
    {
        RuntimeException exception = assertThrows(
                expectedException.getClass(),
                ()-> new ServiceOption(carType, supplier, price),
                "Expected exception not thrown.");
        if (expectedException instanceof NullArgumentException)
            assertNullArgumentExceptionData(
                    (NullArgumentException) exception,
                    (NullArgumentException) expectedException);
    }

    @Test
    void testConstructorSuccess()
    {
        ServiceOption option = new ServiceOption(CarType.EXECUTIVE, Supplier.DAVE, MIN_PRICE);
        assertEquals(CarType.EXECUTIVE, option.carType);
        assertEquals(Supplier.DAVE, option.supplier);
        assertEquals(MIN_PRICE, option.price);
    }
}
