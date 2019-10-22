package aggregator.unit;

import aggregator.ResultsSplitter;
import aggregator.data.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import util.exceptions.NullArgumentException;

import java.util.*;
import java.util.stream.Stream;

import static aggregator.data.GeneralQuery.MIN_CAPACITY;
import static aggregator.testhelper.NullArgumentExceptionDataAsserter.assertNullArgumentExceptionData;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ResultsSplitterTest
{
    private static ResultsSplitter splitter;
    private static List<SupplierOption> supplierOptions;
    private static List<UnfilteredServiceOption> expectedServiceOptions;
    private static int capacity;

    private static UnfilteredServiceOption createUnfilteredSO(final CarType carType,
                                                              final Supplier supplier,
                                                              final int price)
    {
        return new UnfilteredServiceOption(capacity, new ServiceOption(carType, supplier, price));
    }

    @BeforeAll
    static void setupSplitTest()
    {
        splitter = new ResultsSplitter();
        capacity = MIN_CAPACITY + 1;
        int price1 = 10000, price2 = 15000, price3 = 12000;
        supplierOptions = new ArrayList<>();
        supplierOptions.add(new SupplierOption(CarType.EXECUTIVE, price1)); // DAVE
        supplierOptions.add(new SupplierOption(CarType.LUXURY, price2)); // DAVE
        supplierOptions.add(new SupplierOption(CarType.EXECUTIVE, price3)); // ERIC
        expectedServiceOptions = new ArrayList<>();
        expectedServiceOptions.add(createUnfilteredSO(CarType.EXECUTIVE, Supplier.DAVE, price1));
        expectedServiceOptions.add(createUnfilteredSO(CarType.LUXURY, Supplier.DAVE, price2));
        expectedServiceOptions.add(createUnfilteredSO(CarType.EXECUTIVE, Supplier.ERIC, price3));
    }

    private static Set<ServiceResponse> createSingleResponseSet(final int supplierOptionCount)
    {
        Set<ServiceResponse> responseSet = new HashSet<>();
        List<SupplierOption> responseSupplierOptions = supplierOptions.subList(0, supplierOptionCount);
        ServiceResponse serviceResponse = new ServiceResponse(Supplier.DAVE, responseSupplierOptions);
        serviceResponse.setCapacity(capacity);
        responseSet.add(serviceResponse);
        return responseSet;
    }

    private static Set<ServiceResponse> createMultiResponseSet()
    {
        Set<ServiceResponse> responseSet = new HashSet<>();
        List<SupplierOption> response1SupplierOptions = supplierOptions.subList(0, 2);
        ServiceResponse serviceResponse = new ServiceResponse(Supplier.DAVE, response1SupplierOptions);
        serviceResponse.setCapacity(capacity);
        responseSet.add(serviceResponse);
        List<SupplierOption> response2SupplierOptions = supplierOptions.subList(2, 3);
        serviceResponse = new ServiceResponse(Supplier.ERIC, response2SupplierOptions);
        serviceResponse.setCapacity(capacity);
        responseSet.add(serviceResponse);
        return responseSet;
    }

    private static Stream<Arguments> provideSplitterTestData()
    {
        return Stream.of(
                Arguments.of(Collections.emptySet(), Collections.emptyList()),
                Arguments.of(createSingleResponseSet(1), expectedServiceOptions.subList(0, 1)),
                Arguments.of(createSingleResponseSet(2), expectedServiceOptions.subList(0, 2)),
                Arguments.of(createMultiResponseSet(), expectedServiceOptions)
        );
    }

    @ParameterizedTest
    @MethodSource("provideSplitterTestData")
    void testSplitSuccess(final Set<ServiceResponse> serviceResponses,
                                final List<UnfilteredServiceOption> expectedServiceOptions)
    {
        List<UnfilteredServiceOption> serviceOptions = splitter.split(serviceResponses);
        assertTrue(expectedServiceOptions.containsAll(serviceOptions),
                "Expected service options list does not contain all service options returned.");
        assertTrue(serviceOptions.containsAll(expectedServiceOptions),
                "Returned service options list does not contain all expected service options.");
    }

    @Test
    void testSplitFail()
    {
        NullArgumentException exception = assertThrows(
                NullArgumentException.class,
                () -> splitter.split(null),
                "NullArgumentException was not thrown.");
        assertNullArgumentExceptionData(exception, new NullArgumentException(0, Set.class));
    }
}
