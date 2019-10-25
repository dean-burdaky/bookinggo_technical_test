package aggregator.unit;

import aggregator.QueryConverter;
import aggregator.data.GeneralQuery;
import aggregator.data.ServiceQuery;
import aggregator.data.Supplier;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.exceptions.NullArgumentException;

import java.util.HashSet;
import java.util.Set;

import static aggregator.testhelper.NullArgumentExceptionDataAsserter.assertNullArgumentExceptionData;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QueryConverterTest
{
    private static QueryConverter converter;

    @BeforeAll
    static void setupQueryConverter()
    {
        converter = new QueryConverter();
    }

    private static Set<ServiceQuery> createExpectedServiceQueries(final GeneralQuery generalQuery)
    {
        Set<ServiceQuery> serviceQueries = new HashSet<>();
        serviceQueries.add(new ServiceQuery(Supplier.DAVE, generalQuery));
        return serviceQueries;
    }

    @Test
    void testConvertFail()
    {
        NullArgumentException exception = assertThrows(
                NullArgumentException.class,
                ()-> converter.convert(null),
                "Expected exception was not thrown!");
        assertNullArgumentExceptionData(exception, new NullArgumentException(0, GeneralQuery.class));
    }

    @Test
    void testConvertSuccess()
    {
        GeneralQuery generalQuery = new GeneralQuery(5f, 0f, 1f, 25f);
        Set<ServiceQuery> expectedQueries = createExpectedServiceQueries(generalQuery);

        Set<ServiceQuery> serviceQueries = converter.convert(generalQuery);
        assertEquals(expectedQueries, serviceQueries, "Returned result did not contain expected serviceQueries");
    }
}
