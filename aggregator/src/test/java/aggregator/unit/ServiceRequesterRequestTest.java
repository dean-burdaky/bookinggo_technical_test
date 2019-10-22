package aggregator.unit;

import aggregator.ServiceConnectionFactory;
import aggregator.ServiceRequester;
import aggregator.data.*;
import aggregator.testing.FixedSupplierData;
import aggregator.testing.ServiceConnectionMockFactory;
import aggregator.testing.ServiceConnectionMockSetup;
import aggregator.testing.SetupConnectionMockSetup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import util.exceptions.NullArgumentException;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Stream;

import static aggregator.testhelper.NullArgumentExceptionDataAsserter.assertNullArgumentExceptionData;
import static aggregator.testing.FixedSupplierData.DAVE_200;
import static aggregator.testing.FixedSupplierData.ERIC_200;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class ServiceRequesterRequestTest
{
    private static GeneralQuery testGQ;
    private static int capacity;

    @BeforeAll
    static void setupRequestTests()
    {
        capacity = GeneralQuery.MIN_CAPACITY + 1;
        testGQ = new GeneralQuery(0f, 0f, 0f, 0f, capacity);
    }
    // request() - Takes set of ServiceQueries. Expect it to create ServiceConnection for each ServiceQuery (with factory), setup request, connect, read response and parse it through ObjectMapper. Return set of ServiceResponses.
    // Easier to completely white box test this part (apart from the case of null, an exception should be thrown).

    private static Set<ServiceQuery> createSingleQuerySet(final Supplier supplier)
    {
        Set<ServiceQuery> serviceQueries = new HashSet<>();
        serviceQueries.add(new ServiceQuery(supplier, testGQ));
        return serviceQueries;
    }

    private static Set<ServiceQuery> createMultiQuerySet()
    {
        Set<ServiceQuery> serviceQueries = createSingleQuerySet(Supplier.DAVE);
        serviceQueries.add(new ServiceQuery(Supplier.ERIC, testGQ));
        return serviceQueries;
    }

    private static Set<ServiceResponse> createResponses(Supplier supplier, FixedSupplierData data)
    {
        List<SupplierOption> supplierOptions = new ArrayList<>();
        for (ServiceOption option : data.getServiceOptions())
            supplierOptions.add(new SupplierOption(option.carType, option.price));
        Set<ServiceResponse> responses = new HashSet<>();
        ServiceResponse serviceResponse = new ServiceResponse(supplier, supplierOptions);
        serviceResponse.setCapacity(capacity);
        responses.add(serviceResponse);
        return responses;
    }

    private static Stream<Arguments> provideRequestIOExceptionTestData()
    {
        return Stream.of(
                Arguments.of(createSingleQuerySet(Supplier.DAVE), null, Collections.emptySet()),
                Arguments.of(createMultiQuerySet(), ERIC_200.stream(), createResponses(Supplier.ERIC, ERIC_200))
        );
    }

    private static Stream<Arguments> provideRequestTestData()
    {
        Set<ServiceResponse> allResponses = new HashSet<>(createResponses(Supplier.DAVE, DAVE_200));
        allResponses.addAll(createResponses(Supplier.ERIC, ERIC_200));
        return Stream.of(
                Arguments.of(
                        Collections.emptySet(),
                        404, null, 404, null,
                        Collections.emptySet()
                ),
                Arguments.of(
                        createSingleQuerySet(Supplier.DAVE),
                        200, DAVE_200.stream(), 404, null,
                        createResponses(Supplier.DAVE, DAVE_200)
                ),
                Arguments.of(
                        createSingleQuerySet(Supplier.DAVE),
                        500, null, 404, null,
                        Collections.emptySet()
                ),
                Arguments.of(
                        createSingleQuerySet(Supplier.DAVE),
                        400, null, 404, null,
                        Collections.emptySet()
                ),
                Arguments.of(
                        createMultiQuerySet(),
                        500, null, 500, null,
                        Collections.emptySet()
                ),
                Arguments.of(
                        createMultiQuerySet(),
                        200, DAVE_200.stream(), 500, null,
                        createResponses(Supplier.DAVE, DAVE_200)
                ),
                Arguments.of(
                        createMultiQuerySet(),
                        200, DAVE_200.stream(), 200, ERIC_200.stream(),
                        allResponses
                ),
                Arguments.of(
                        createMultiQuerySet(),
                        400, null, 500, null,
                        Collections.emptySet()
                ),
                Arguments.of(
                        createMultiQuerySet(),
                        400, null, 200, ERIC_200.stream(),
                        createResponses(Supplier.ERIC, ERIC_200)
                ),
                Arguments.of(
                        createMultiQuerySet(),
                        400, null, 400, null,
                        Collections.emptySet()
                )
        );
    }

    private static void assertCapacity(final Set<ServiceResponse> serviceResponses, final int expectedCapacity)
    {
        for (ServiceResponse serviceResponse : serviceResponses)
            assertEquals(expectedCapacity, serviceResponse.getCapacity());
    }

    @ParameterizedTest
    @MethodSource("provideRequestIOExceptionTestData")
    void testRequestIOException(final Set<ServiceQuery> serviceQueries,
                                final InputStream ericStream,
                                final Set<ServiceResponse> expectedResponses)
    {
        ServiceConnectionMockSetup setup = SetupConnectionMockSetup
                .setupConnectionMockSetupWithIOException(200, ericStream);
        ServiceConnectionFactory factory = new ServiceConnectionMockFactory(setup);
        ServiceRequester requester = new ServiceRequester(factory);
        Set<ServiceResponse> serviceResponses =  requester.request(serviceQueries);
        assertEquals(
                expectedResponses,
                serviceResponses,
                "Returned service responses are not equal to expected responses.");
        assertCapacity(serviceResponses, capacity);
    }

    @ParameterizedTest
    @MethodSource("provideRequestTestData")
    void testRequestResponses(final Set<ServiceQuery> serviceQueries,
                              final int daveCode,
                              final InputStream daveStream,
                              final int ericCode,
                              final InputStream ericStream,
                              final Set<ServiceResponse> expectedResponses)
    {
        ServiceConnectionMockSetup setup = SetupConnectionMockSetup.setupConnectionMockSetup(
                daveCode,
                daveStream,
                ericCode,
                ericStream);
        ServiceConnectionFactory factory = new ServiceConnectionMockFactory(setup);
        ServiceRequester requester = new ServiceRequester(factory);
        Set<ServiceResponse> serviceResponses =  requester.request(serviceQueries);
        assertEquals(
                expectedResponses,
                serviceResponses,
                "Returned service responses are not equal to expected responses.");
        assertCapacity(serviceResponses, capacity);
    }

    @Test
    void testRequestFail()
    {
        ServiceRequester requester = new ServiceRequester(mock(ServiceConnectionFactory.class));
        NullArgumentException exception = assertThrows(
                NullArgumentException.class,
                ()-> requester.request(null),
                "Expected exception was not thrown.");
        assertNullArgumentExceptionData(exception, new NullArgumentException(0, Set.class));
    }
}
