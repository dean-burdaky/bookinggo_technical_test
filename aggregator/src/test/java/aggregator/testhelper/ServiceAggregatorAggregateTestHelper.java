package aggregator.testhelper;

import aggregator.*;
import aggregator.data.GeneralQuery;
import util.exceptions.NullArgumentException;

import static aggregator.testhelper.NullArgumentExceptionDataAsserter.assertNullArgumentExceptionData;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class ServiceAggregatorAggregateTestHelper
{
    private QueryConverter converter;
    private ResultsSplitter splitter;
    private CapacityFilter filter;
    private SupplierResolver resolver;
    private OptionsSorter sorter;

    public void setupServiceAggregatorComponents(final QueryConverter converter,
                                                 final ResultsSplitter splitter,
                                                 final CapacityFilter filter,
                                                 final SupplierResolver resolver,
                                                 final OptionsSorter sorter)
    {
        this.converter = converter;
        this.splitter = splitter;
        this.filter = filter;
        this.resolver = resolver;
        this.sorter = sorter;
    }

    public ServiceAggregator setupServiceAggregator(final ServiceRequester requester)
    {
        return new ServiceAggregator(
                converter,
                requester,
                splitter,
                filter,
                resolver,
                sorter);
    }

    public void testAggregateFail(final ServiceRequester requester)
    {
        ServiceAggregator serviceAggregator = setupServiceAggregator(requester);

        NullArgumentException exception = assertThrows(
                NullArgumentException.class,
                ()-> serviceAggregator.aggregate(null),
                "NullArgumentException was not thrown.");
        assertNullArgumentExceptionData(exception, new NullArgumentException(0, GeneralQuery.class));
    }

    public QueryConverter getConverter() { return converter; }
    public ResultsSplitter getSplitter() { return splitter; }
    public CapacityFilter getFilter() { return filter; }
    public SupplierResolver getResolver() { return resolver; }
    public OptionsSorter getSorter() { return sorter; }
}
