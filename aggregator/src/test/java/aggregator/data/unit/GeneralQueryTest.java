package aggregator.data.unit;

import aggregator.data.GeneralQuery;
import aggregator.data.LatitudeOutOfBoundsException;
import aggregator.data.LongitudeOutOfBoundsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import util.exceptions.IntegerToLowException;

import java.util.stream.Stream;

import static aggregator.data.GeneralQuery.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GeneralQueryTest
{
    private static Stream<Arguments> provideGeneralQueryValues()
    {
        return Stream.of(
                Arguments.of(
                        MIN_LATITUDE-1f, MIN_LONGITUDE-1f,
                        MIN_LATITUDE-1f, MIN_LONGITUDE-1f,
                        MIN_CAPACITY-1,
                        new LatitudeOutOfBoundsException(MIN_LATITUDE-1f)
                ),
                Arguments.of(
                        MAX_LATITUDE+1f, MIN_LONGITUDE-1f,
                        MIN_LATITUDE-1f, MIN_LONGITUDE-1f,
                        MIN_CAPACITY-1,
                        new LatitudeOutOfBoundsException(MAX_LATITUDE+1f)
                ),
                Arguments.of(
                        MIN_LATITUDE, MIN_LONGITUDE-1f,
                        MIN_LATITUDE-1f, MIN_LONGITUDE-1f,
                        MIN_CAPACITY-1,
                        new LongitudeOutOfBoundsException(MIN_LONGITUDE-1f)
                ),
                Arguments.of(
                        MAX_LATITUDE, MAX_LONGITUDE+1f,
                        MIN_LATITUDE-1f, MIN_LONGITUDE-1f,
                        MIN_CAPACITY-1,
                        new LongitudeOutOfBoundsException(MAX_LONGITUDE+1f)
                ),
                Arguments.of(
                        MIN_LATITUDE, MIN_LONGITUDE,
                        MIN_LATITUDE-1f, MIN_LONGITUDE-1f,
                        MIN_CAPACITY-1,
                        new LatitudeOutOfBoundsException(MIN_LATITUDE-1f)
                ),
                Arguments.of(
                        MIN_LATITUDE, MAX_LONGITUDE,
                        MAX_LATITUDE+1f, MIN_LONGITUDE-1f,
                        MIN_CAPACITY-1,
                        new LatitudeOutOfBoundsException(MAX_LATITUDE+1f)
                ),
                Arguments.of(
                        MIN_LATITUDE, MIN_LONGITUDE,
                        MIN_LATITUDE, MIN_LONGITUDE-1f,
                        MIN_CAPACITY-1,
                        new LongitudeOutOfBoundsException(MIN_LONGITUDE-1f)
                ),
                Arguments.of(
                        MIN_LATITUDE, MIN_LONGITUDE,
                        MAX_LATITUDE, MAX_LONGITUDE+1f,
                        MIN_CAPACITY-1,
                        new LongitudeOutOfBoundsException(MAX_LONGITUDE+1f)
                ),
                Arguments.of(
                        MIN_LATITUDE, MIN_LONGITUDE,
                        MIN_LATITUDE, MIN_LONGITUDE,
                        MIN_CAPACITY-1,
                        new IntegerToLowException(MIN_CAPACITY-1, MIN_CAPACITY)
                )
        );
    }

    @ParameterizedTest
    @MethodSource("provideGeneralQueryValues")
    void testConstructorFail(final float pickupLat,
                             final float pickupLon,
                             final float dropoffLat,
                             final float dropoffLon,
                             final int capacity,
                             final RuntimeException expectedException)
    {
        assertThrows(
                expectedException.getClass(),
                ()-> new GeneralQuery(pickupLat, pickupLon, dropoffLat, dropoffLon, capacity),
                "Expected exception not thrown.");
    }

    @Test
    void testConstructorSuccess()
    {
        GeneralQuery query = new GeneralQuery(
                MAX_LATITUDE, MIN_LONGITUDE,
                MAX_LATITUDE-1f, MIN_LONGITUDE+1f,
                MIN_CAPACITY);
        assertEquals(MAX_LATITUDE, query.pickupLat);
        assertEquals(MIN_LONGITUDE, query.pickupLon);
        assertEquals(MAX_LATITUDE-1f, query.dropoffLat);
        assertEquals(MIN_LONGITUDE+1f, query.dropoffLon);
        assertEquals(MIN_CAPACITY, query.capacity);
    }
}
