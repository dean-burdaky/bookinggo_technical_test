package aggregator.integration;

import aggregator.*;
import aggregator.data.GeneralQuery;
import aggregator.data.ServiceOption;
import aggregator.testhelper.ServiceAggregatorAggregateTestHelper;
import aggregator.testing.ServiceConnectionMockFactory;
import aggregator.testing.ServiceConnectionMockSetup;
import aggregator.testing.SetupConnectionMockSetup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static aggregator.testing.FixedSupplierData.DAVE_200;
import static aggregator.testing.FixedSupplierData.ERIC_200;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class ServiceAggregatorAggregateTest
{
    private static ServiceAggregatorAggregateTestHelper helper;

    @BeforeAll
    static void setupServiceAggregatorComponents()
    {
        helper = new ServiceAggregatorAggregateTestHelper();
        helper.setupServiceAggregatorComponents(
                new QueryConverter(),
                new ResultsSplitter(),
                new CapacityFilter(),
                new SupplierResolver(),
                new OptionsSorter());
    }

    private static Stream<Arguments> provideConnectionResponses()
    {
        List<ServiceOption> allServiceOptions = new ArrayList<>(DAVE_200.getServiceOptions());
        allServiceOptions.addAll(ERIC_200.getServiceOptions());
        return Stream.of(
                Arguments.of(500, null,              500, null,              Collections.emptyList()),
                Arguments.of(200, DAVE_200.stream(), 500, null,              DAVE_200.getServiceOptions()),
                Arguments.of(200, DAVE_200.stream(), 200, ERIC_200.stream(), allServiceOptions),
                Arguments.of(400, null,              500, null,              Collections.emptyList()),
                Arguments.of(400, null,              200, ERIC_200.stream(), ERIC_200.getServiceOptions()),
                Arguments.of(400, null,              400, null,              Collections.emptyList())
        );
    }

    @Test
    void testAggregateFail()
    {
        // Mock used as aggregate call is expected to fail fast.
        ServiceRequester requester = mock(ServiceRequester.class);
        helper.testAggregateFail(requester);
    }

    @ParameterizedTest
    @MethodSource("provideConnectionResponses")
    void testAggregateSuccess(final int daveCode,
                              final InputStream daveStream,
                              final int ericCode,
                              final InputStream ericStream,
                              final List<ServiceOption> expectedOptions)
    {
        ServiceConnectionMockSetup setup = SetupConnectionMockSetup.setupConnectionMockSetup(
                daveCode,
                daveStream,
                ericCode,
                ericStream);
        ServiceConnectionMockFactory factory = new ServiceConnectionMockFactory(setup);
        ServiceRequester requester = new ServiceRequester(factory);
        ServiceAggregator serviceAggregator = helper.setupServiceAggregator(requester);
        GeneralQuery dummyQuery = new GeneralQuery(0f, 0f, 0f, 0f, 1);

        List<ServiceOption> options = serviceAggregator.aggregate(dummyQuery);
        assertTrue(expectedOptions.containsAll(options));
        assertTrue(options.containsAll(expectedOptions));
    }
}
