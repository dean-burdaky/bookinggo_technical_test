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
    private OptionsSorter sorter;

    public void setupServiceAggregatorComponents(final QueryConverter converter,
                                                 final ResultsSplitter splitter,
                                                 final OptionsSorter sorter)
    {
        this.converter = converter;
        this.splitter = splitter;
        this.sorter = sorter;
    }

    public ServiceAggregator setupServiceAggregator(final ServiceRequester requester)
    {
        return new ServiceAggregator(
                converter,
                requester,
                splitter,
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
    public OptionsSorter getSorter() { return sorter; }
}
