package aggregator.data.unit;

import aggregator.data.GeneralQuery;
import aggregator.data.ServiceQuery;
import aggregator.data.Supplier;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static aggregator.data.GeneralQuery.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ServiceQueryHashCodeAndEqualsTest
{
    private static Supplier supplier1, supplier2;
    private static float lat1, lon1, lat2, lon2;
    private static int capacity1, capacity2;
    private static ServiceQuery serviceQueryUnderTest;

    @BeforeAll
    static void setupServiceQueryValues()
    {
        supplier1 = Supplier.DAVE;
        supplier2 = Supplier.ERIC;
        lat1 = MIN_LATITUDE;
        lon1 = MIN_LONGITUDE;
        lat2 = MAX_LATITUDE;
        lon2 = MAX_LONGITUDE;
        capacity1 = MIN_CAPACITY;
        capacity2 = MIN_CAPACITY + 1;
        serviceQueryUnderTest = new ServiceQuery(supplier1, new GeneralQuery(lat1, lon1, lat1, lon1, capacity1));
    }

    private static Stream<Arguments> provideServiceQueryValues()
    {
        return Stream.of(
                Arguments.of(supplier2, lat2, lon2, lat2, lon2, capacity2, false),
                Arguments.of(supplier1, lat2, lon2, lat2, lon2, capacity2, false),
                Arguments.of(supplier1, lat1, lon2, lat2, lon2, capacity2, false),
                Arguments.of(supplier1, lat1, lon1, lat2, lon2, capacity2, false),
                Arguments.of(supplier1, lat1, lon1, lat1, lon2, capacity2, false),
                Arguments.of(supplier1, lat1, lon1, lat1, lon1, capacity2, false),
                Arguments.of(supplier1, lat1, lon1, lat1, lon1, capacity1, true)
        );
    }

    @ParameterizedTest
    @MethodSource("provideServiceQueryValues")
    void testEqualsNotNull(final Supplier supplier,
                           final float pickupLat,
                           final float pickupLon,
                           final float dropoffLat,
                           final float dropoffLon,
                           final int capacity,
                           final boolean expectedResponse)
    {
        ServiceQuery serviceQuery = new ServiceQuery(supplier, new GeneralQuery(
                pickupLat, pickupLon,
                dropoffLat, dropoffLon,
                capacity));

        assertEquals(expectedResponse, serviceQueryUnderTest.equals(serviceQuery));
    }

    @ParameterizedTest
    @MethodSource("provideServiceQueryValues")
    void testHashCode(final Supplier supplier,
                      final float pickupLat,
                      final float pickupLon,
                      final float dropoffLat,
                      final float dropoffLon,
                      final int capacity,
                      final boolean expectedResponse)
    {
        ServiceQuery serviceQuery = new ServiceQuery(supplier, new GeneralQuery(
                pickupLat,
                pickupLon,
                dropoffLat,
                dropoffLon,
                capacity));

        assertEquals(expectedResponse, serviceQueryUnderTest.hashCode() == serviceQuery.hashCode());
    }

    @Test
    void testEqualsNull()
    {
        assertNotEquals(null, serviceQueryUnderTest);
    }
}
