package aggregator.unit;

import aggregator.NoFilter;
import aggregator.data.CarType;
import aggregator.data.ServiceOption;
import aggregator.data.Supplier;
import aggregator.data.UnfilteredServiceOption;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.exceptions.NullArgumentException;

import java.util.ArrayList;
import java.util.List;

import static aggregator.data.GeneralQuery.MIN_CAPACITY;
import static aggregator.data.ServiceOption.MIN_PRICE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NoFilterTest
{
    private static NoFilter filter;

    @BeforeAll
    static void setupConverter()
    {
        filter = new NoFilter();
    }

    @Test
    void testConvertFail()
    {
        NullArgumentException exception = assertThrows(
                NullArgumentException.class,
                ()-> filter.filter(null),
                "Expected exception not thrown.");
        assertEquals(0, exception.getArgumentPos());
        assertEquals(List.class, exception.getArgumentClass());
    }

    @Test
    void testConvertSuccess()
    {
        ServiceOption option = new ServiceOption(CarType.STANDARD, Supplier.DAVE, MIN_PRICE);
        List<UnfilteredServiceOption> unfilteredSO = new ArrayList<>();
        unfilteredSO.add(new UnfilteredServiceOption(MIN_CAPACITY, option));
        List<ServiceOption> serviceOptions = filter.filter(unfilteredSO);
        assertEquals(1, serviceOptions.size());
        serviceOptions.contains(option);
    }
}
