package aggregator.unit;

import aggregator.*;
import aggregator.data.*;
import aggregator.testhelper.ServiceAggregatorAggregateTestHelper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.util.List;
import java.util.Set;

import static aggregator.data.GeneralQuery.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ServiceAggregatorAggregateTest
{
    private static ServiceAggregatorAggregateTestHelper helper;

    @BeforeAll
    static void setupServiceAggregatorComponents()
    {
        helper = new ServiceAggregatorAggregateTestHelper();
        helper.setupServiceAggregatorComponents(
                mock(QueryConverter.class),
                mock(ResultsSplitter.class),
                mock(CapacityFilter.class),
                mock(SupplierResolver.class),
                mock(OptionsSorter.class));
    }

    @Test
    void testAggregateFail()
    {
        helper.testAggregateFail(new ServiceRequester(new ServiceConnectionFactory()));
    }

    private InOrder setupComponentBehaviour(ServiceRequester requester,
                                            GeneralQuery dummyGQ,
                                            Set<ServiceQuery> dummySQSet,
                                            Set<ServiceResponse> dummySRSet,
                                            List<UnfilteredServiceOption> dummySOList1,
                                            List<ServiceOption> dummySOList2,
                                            List<ServiceOption> dummySOList3,
                                            List<ServiceOption> dummySOList4)
    {
        doReturn(dummySQSet).when(helper.getConverter()).convert(dummyGQ);
        doReturn(dummySRSet).when(requester).request(dummySQSet);
        doReturn(dummySOList1).when(helper.getSplitter()).split(dummySRSet);
        doReturn(dummySOList2).when(helper.getFilter()).filter(dummySOList1);
        doReturn(dummySOList3).when(helper.getResolver()).resolve(dummySOList2);
        doReturn(dummySOList4).when(helper.getSorter()).sort(dummySOList3);
        return inOrder(
                helper.getConverter(),
                requester,
                helper.getSplitter(),
                helper.getFilter(),
                helper.getResolver(),
                helper.getSorter());
    }

    @Test
    void testAggregateSuccess()
    {
        GeneralQuery dummyGQ = new GeneralQuery(
                MIN_LATITUDE, MIN_LONGITUDE,
                MIN_LATITUDE, MIN_LONGITUDE,
                MIN_CAPACITY);
        Set<ServiceQuery> dummySQSet = mock(Set.class);
        Set<ServiceResponse> dummySRSet = mock(Set.class);
        List<UnfilteredServiceOption> dummySOList1 = mock(List.class);
        List<ServiceOption> dummySOList2 = mock(List.class);
        List<ServiceOption> dummySOList3 = mock(List.class);
        List<ServiceOption> dummySOList4 = mock(List.class);
        ServiceRequester requester = mock(ServiceRequester.class);
        InOrder inOrder = setupComponentBehaviour(
                requester, dummyGQ, dummySQSet, dummySRSet, dummySOList1, dummySOList2, dummySOList3, dummySOList4);
        ServiceAggregator serviceAggregator = helper.setupServiceAggregator(requester);

        List<ServiceOption> options = serviceAggregator.aggregate(dummyGQ);
        inOrder.verify(helper.getConverter()).convert(dummyGQ);
        inOrder.verify(requester).request(dummySQSet);
        inOrder.verify(helper.getSplitter()).split(dummySRSet);
        inOrder.verify(helper.getFilter()).filter(dummySOList1);
        inOrder.verify(helper.getResolver()).resolve(dummySOList2);
        inOrder.verify(helper.getSorter()).sort(dummySOList3);
        inOrder.verifyNoMoreInteractions();
        assertEquals(dummySOList4, options);
    }
}
