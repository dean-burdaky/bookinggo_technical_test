package aggregator.unit;

import aggregator.NoResolver;
import aggregator.data.CarType;
import aggregator.data.ServiceOption;
import aggregator.data.Supplier;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static aggregator.data.ServiceOption.MIN_PRICE;
import static org.junit.jupiter.api.Assertions.*;

public class NoResolverTest
{
    private static NoResolver resolver;

    @BeforeAll
    static void setupConverter()
    {
        resolver = new NoResolver();
    }

    @Test
    void testConvertFail()
    {
        List<ServiceOption> options = assertDoesNotThrow(()-> resolver.resolve(null));
        assertNull(options);
    }

    @Test
    void testConvertSuccess()
    {
        ServiceOption option = new ServiceOption(CarType.STANDARD, Supplier.DAVE, MIN_PRICE);
        List<ServiceOption> options = new ArrayList<>();
        options.add(option);
        List<ServiceOption> resultOptions = resolver.resolve(options);
        assertEquals(options, resultOptions);
    }
}
