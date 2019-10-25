package aggregator;

import aggregator.data.GeneralQuery;
import aggregator.data.ServiceOption;
import aggregator.data.ServiceQuery;
import aggregator.data.ServiceResponse;
import util.exceptions.NullArgumentException;

import java.util.List;
import java.util.Set;

public class ServiceAggregator
{
    private final QueryConverter converter;
    private final ServiceRequester requester;
    private final ResultsSplitter splitter;
    private final OptionsSorter sorter;

    public ServiceAggregator(final QueryConverter converter,
                             final ServiceRequester requester,
                             final ResultsSplitter splitter,
                             final OptionsSorter sorter)
    {
        if (converter == null) throw new NullArgumentException(0, QueryConverter.class);
        else if (requester == null) throw new NullArgumentException(1, ServiceRequester.class);
        else if (splitter == null) throw new NullArgumentException(2, ResultsSplitter.class);
        else if (sorter == null) throw new NullArgumentException(5, OptionsSorter.class);

        this.converter = converter;
        this.requester = requester;
        this.splitter = splitter;
        this.sorter = sorter;
    }

    public List<ServiceOption> aggregate(final GeneralQuery generalQuery)
    {
        if (generalQuery == null) throw new NullArgumentException(0, GeneralQuery.class);

        Set<ServiceQuery> serviceQueries = converter.convert(generalQuery);
        Set<ServiceResponse> serviceResponses = requester.request(serviceQueries);
        List<ServiceOption> serviceOptions = splitter.split(serviceResponses);
        serviceOptions = sorter.sort(serviceOptions);
        return serviceOptions;
    }
}
