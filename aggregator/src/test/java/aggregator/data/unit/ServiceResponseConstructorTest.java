package aggregator.data.unit;

import aggregator.data.ServiceResponse;
import aggregator.data.Supplier;
import aggregator.data.SupplierOption;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import util.exceptions.NullArgumentException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static aggregator.data.GeneralQuery.MIN_CAPACITY;
import static aggregator.testhelper.NullArgumentExceptionDataAsserter.assertNullArgumentExceptionData;
import static org.junit.jupiter.api.Assertions.*;

class ServiceResponseConstructorTest
{
    private static Stream<Arguments> provideServiceResponseValues()
    {
        return Stream.of(
                Arguments.of(null, null, new NullArgumentException(0, Supplier.class)),
                Arguments.of(Supplier.DAVE, null, new NullArgumentException(1, List.class))
        );
    }

    @ParameterizedTest
    @MethodSource("provideServiceResponseValues")
    void testConstructorFail(final Supplier supplier,
                             final List<SupplierOption> supplierOptions,
                             final NullArgumentException expectedException)
    {
        NullArgumentException exception = assertThrows(
                expectedException.getClass(),
                ()-> new ServiceResponse(supplier, supplierOptions),
                "Expected exception not thrown.");
        assertNullArgumentExceptionData(exception, expectedException);
    }

    @Test
    void testConstructorSuccess()
    {
        List<SupplierOption> dummySupplierOptions = new ArrayList<>();
        ServiceResponse serviceResponse = new ServiceResponse(Supplier.DAVE, dummySupplierOptions);
        assertEquals(Supplier.DAVE, serviceResponse.supplier);
        List<SupplierOption> supplierOptions = serviceResponse.supplierOptions();
        assertEquals(dummySupplierOptions, supplierOptions);
        assertNotSame(supplierOptions, dummySupplierOptions);
        assertEquals(MIN_CAPACITY, serviceResponse.getCapacity());
    }
}
