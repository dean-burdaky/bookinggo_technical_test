package aggregator.data.unit;

import aggregator.data.CarType;
import aggregator.data.SupplierOption;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static aggregator.data.ServiceOption.MIN_PRICE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class SupplierOptionHashCodeAndEqualsTest
{
    private static CarType carType1, carType2;
    private static int price1, price2;
    private static SupplierOption supplierOptionUnderTest;

    @BeforeAll
    static void setupSupplierOptionValues()
    {
        carType1 = CarType.EXECUTIVE;
        carType2 = CarType.LUXURY;
        price1 = MIN_PRICE;
        price2 = MIN_PRICE + 1;
        supplierOptionUnderTest = new SupplierOption(carType1, price1);
    }

    private static Stream<Arguments> provideSupplierOptionValues()
    {
        return Stream.of(
                Arguments.of(carType2, price2, false),
                Arguments.of(carType1, price2, false),
                Arguments.of(carType1, price1, true)
        );
    }

    @ParameterizedTest
    @MethodSource("provideSupplierOptionValues")
    void testEqualsNotNull(final CarType carType,
                           final int price,
                           final boolean expectedResponse)
    {
        SupplierOption option = new SupplierOption(carType, price);

        assertEquals(expectedResponse, supplierOptionUnderTest.equals(option));
    }

    @ParameterizedTest
    @MethodSource("provideSupplierOptionValues")
    void testHashCode(final CarType carType,
                      final int price,
                      final boolean expectedResponse)
    {
        SupplierOption option = new SupplierOption(carType, price);

        assertEquals(expectedResponse, supplierOptionUnderTest.hashCode() == option.hashCode());
    }

    @Test
    void testEqualsNull()
    {
        assertNotEquals(null, supplierOptionUnderTest);
    }
}
