package aggregator.data.unit;

import aggregator.data.CarType;
import aggregator.data.ServiceOption;
import aggregator.data.Supplier;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static aggregator.data.ServiceOption.MIN_PRICE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ServiceOptionHashCodeAndEqualsTest
{
    private static CarType carType1, carType2;
    private static Supplier supplier1, supplier2;
    private static int price1, price2;
    private static ServiceOption serviceOptionUnderTest;

    @BeforeAll
    static void setupServiceQueryValues()
    {
        carType1 = CarType.EXECUTIVE;
        carType2 = CarType.LUXURY;
        supplier1 = Supplier.DAVE;
        supplier2 = Supplier.ERIC;
        price1 = MIN_PRICE;
        price2 = MIN_PRICE + 1;
        serviceOptionUnderTest = new ServiceOption(carType1, supplier1, price1);
    }

    private static Stream<Arguments> provideServiceOptionValues()
    {
        return Stream.of(
                Arguments.of(carType2, supplier2, price2, false),
                Arguments.of(carType1, supplier2, price2, false),
                Arguments.of(carType1, supplier1, price2, false),
                Arguments.of(carType1, supplier1, price1, true)
        );
    }

    @ParameterizedTest
    @MethodSource("provideServiceOptionValues")
    void testEqualsNotNull(final CarType carType,
                           final Supplier supplier,
                           final int price,
                           final boolean expectedResponse)
    {
        ServiceOption serviceOption = new ServiceOption(carType, supplier, price);

        assertEquals(expectedResponse, serviceOptionUnderTest.equals(serviceOption));
    }

    @ParameterizedTest
    @MethodSource("provideServiceOptionValues")
    void testHashCode(final CarType carType,
                      final Supplier supplier,
                      final int price,
                      final boolean expectedResponse)
    {
        ServiceOption serviceOption = new ServiceOption(carType, supplier, price);

        assertEquals(expectedResponse, serviceOptionUnderTest.hashCode() == serviceOption.hashCode());
    }

    @Test
    void testEqualsNull()
    {
        assertNotEquals(null, serviceOptionUnderTest);
    }
}
