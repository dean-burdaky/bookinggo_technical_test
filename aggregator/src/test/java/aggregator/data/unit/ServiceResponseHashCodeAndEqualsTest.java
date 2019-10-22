package aggregator.data.unit;

import aggregator.data.CarType;
import aggregator.data.ServiceResponse;
import aggregator.data.Supplier;
import aggregator.data.SupplierOption;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static aggregator.data.GeneralQuery.MIN_CAPACITY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ServiceResponseHashCodeAndEqualsTest
{
    private static Supplier supplier1, supplier2;
    private static int capacity1, capacity2;
    private static List<SupplierOption> supplierOptions1, supplierOptions2;
    private static ServiceResponse serviceResponseUnderTest;

    @BeforeAll
    static void setupServiceResponseValues()
    {
        supplier1 = Supplier.DAVE;
        supplier2 = Supplier.ERIC;
        capacity1 = MIN_CAPACITY + 1;
        capacity2 = MIN_CAPACITY + 2;
        supplierOptions1 = new ArrayList<>();
        supplierOptions1.add(new SupplierOption(CarType.EXECUTIVE, 1));
        supplierOptions2 = new ArrayList<>();
        supplierOptions2.add(new SupplierOption(CarType.EXECUTIVE, 2));
        serviceResponseUnderTest = new ServiceResponse(supplier1, supplierOptions1);
        serviceResponseUnderTest.setCapacity(capacity1);
    }

    private static Stream<Arguments> provideServiceResponseValues()
    {
        return Stream.of(
                Arguments.of(supplier2, capacity2, supplierOptions2, false),
                Arguments.of(supplier1, capacity2, supplierOptions2, false),
                Arguments.of(supplier1, capacity1, supplierOptions2, false),
                Arguments.of(supplier1, capacity1, supplierOptions1, true)
        );
    }

    @ParameterizedTest
    @MethodSource("provideServiceResponseValues")
    void testEqualsNotNull(final Supplier supplier,
                           final int capacity,
                           final List<SupplierOption> supplierOptions,
                           final boolean expectedResponse)
    {
        ServiceResponse serviceResponse = new ServiceResponse(supplier, supplierOptions);
        serviceResponse.setCapacity(capacity);

        assertEquals(expectedResponse, serviceResponseUnderTest.equals(serviceResponse));
    }

    @ParameterizedTest
    @MethodSource("provideServiceResponseValues")
    void testHashCode(final Supplier supplier,
                      final int capacity,
                      final List<SupplierOption> supplierOptions,
                      final boolean expectedResponse)
    {
        ServiceResponse serviceResponse = new ServiceResponse(supplier, supplierOptions);
        serviceResponse.setCapacity(capacity);

        assertEquals(expectedResponse, serviceResponseUnderTest.hashCode() == serviceResponse.hashCode());
    }

    @Test
    void testEqualsNull()
    {
        assertNotEquals(null, serviceResponseUnderTest);
    }
}
