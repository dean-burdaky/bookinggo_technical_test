package aggregator.data.unit;

import aggregator.data.CarType;
import aggregator.data.ServiceOption;
import aggregator.data.Supplier;
import aggregator.data.UnfilteredServiceOption;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static aggregator.data.GeneralQuery.MIN_CAPACITY;
import static aggregator.data.ServiceOption.MIN_PRICE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UnfilteredServiceOptionHashCodeAndEqualsTest
{
    private static int capacity1, capacity2;
    private static ServiceOption option1, option2;
    private static UnfilteredServiceOption unfilteredSOUnderTest;

    @BeforeAll
    static void setupServiceQueryValues()
    {
        capacity1 = MIN_CAPACITY;
        capacity2 = MIN_CAPACITY + 1;
        option1 = new ServiceOption(CarType.EXECUTIVE, Supplier.DAVE, MIN_PRICE);
        option2 = new ServiceOption(CarType.MINIBUS, Supplier.DAVE, MIN_PRICE);
        unfilteredSOUnderTest = new UnfilteredServiceOption(capacity1, option1);
    }

    private static Stream<Arguments> provideServiceOptionValues()
    {
        return Stream.of(
                Arguments.of(capacity2, option2, false),
                Arguments.of(capacity1, option2, false),
                Arguments.of(capacity1, option1, true)
        );
    }

    @ParameterizedTest
    @MethodSource("provideServiceOptionValues")
    void testEqualsNotNull(final int capacity,
                           final ServiceOption option,
                           final boolean expectedResponse)
    {
        UnfilteredServiceOption unfilteredSO = new UnfilteredServiceOption(capacity, option);

        assertEquals(expectedResponse, unfilteredSOUnderTest.equals(unfilteredSO));
    }

    @ParameterizedTest
    @MethodSource("provideServiceOptionValues")
    void testHashCode(final int capacity,
                      final ServiceOption option,
                      final boolean expectedResponse)
    {
        UnfilteredServiceOption unfilteredSO = new UnfilteredServiceOption(capacity, option);

        assertEquals(expectedResponse, unfilteredSOUnderTest.hashCode() == unfilteredSO.hashCode());
    }

    @Test
    void testEqualsNull()
    {
        assertNotEquals(null, unfilteredSOUnderTest);
    }
}
