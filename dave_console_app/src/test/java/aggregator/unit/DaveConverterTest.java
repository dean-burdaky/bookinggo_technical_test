package aggregator.unit;

import aggregator.DaveConverter;
import aggregator.data.GeneralQuery;
import aggregator.data.ServiceQuery;
import aggregator.data.Supplier;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.exceptions.NullArgumentException;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DaveConverterTest
{
    private static DaveConverter converter;

    @BeforeAll
    static void setupConverter()
    {
        converter = new DaveConverter();
    }

    @Test
    void testConvertFail()
    {
        NullArgumentException exception = assertThrows(
                NullArgumentException.class,
                ()-> converter.convert(null),
                "Expected exception not thrown.");
        assertEquals(0, exception.getArgumentPos());
        assertEquals(GeneralQuery.class, exception.getArgumentClass());
    }

    @Test
    void testConvertSuccess()
    {
        GeneralQuery generalQuery = new GeneralQuery(0, 0, 0, 0, 1);
        ServiceQuery serviceQuery = new ServiceQuery(Supplier.DAVE, generalQuery);
        Set<ServiceQuery> serviceQueries = converter.convert(generalQuery);
        assertEquals(1, serviceQueries.size());
        serviceQueries.contains(serviceQuery);
    }
}
