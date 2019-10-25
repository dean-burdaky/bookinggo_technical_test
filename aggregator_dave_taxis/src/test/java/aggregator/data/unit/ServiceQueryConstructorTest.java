package aggregator.data.unit;

import aggregator.data.GeneralQuery;
import aggregator.data.ServiceQuery;
import aggregator.data.Supplier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import util.exceptions.NullArgumentException;

import java.util.stream.Stream;

import static aggregator.testhelper.NullArgumentExceptionDataAsserter.assertNullArgumentExceptionData;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ServiceQueryConstructorTest
{
    private static Stream<Arguments> provideServiceQueryValues()
    {
        return Stream.of(
                Arguments.of(null, null, new NullArgumentException(0, Supplier.class)),
                Arguments.of(Supplier.DAVE, null, new NullArgumentException(1, GeneralQuery.class))
        );
    }

    @ParameterizedTest
    @MethodSource("provideServiceQueryValues")
    void testConstructorFail(final Supplier supplier,
                             final GeneralQuery generalQuery,
                             final NullArgumentException expectedException)
    {
        NullArgumentException exception = assertThrows(
                expectedException.getClass(),
                ()-> new ServiceQuery(supplier, generalQuery),
                "Expected exception not thrown.");
        assertNullArgumentExceptionData(exception, expectedException);
    }

    @Test
    void testConstructorSuccess()
    {
        GeneralQuery generalQuery = new GeneralQuery(75.5f, 101.001f, 0f, 23.23f);
        ServiceQuery serviceQuery = new ServiceQuery(Supplier.DAVE, generalQuery);
        assertEquals(Supplier.DAVE, serviceQuery.supplier);
        assertEquals(generalQuery.pickupLat, serviceQuery.pickupLat);
        assertEquals(generalQuery.pickupLon, serviceQuery.pickupLon);
        assertEquals(generalQuery.dropoffLat, serviceQuery.dropoffLat);
        assertEquals(generalQuery.dropoffLon, serviceQuery.dropoffLon);
    }
}
