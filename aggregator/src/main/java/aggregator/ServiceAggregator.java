package aggregator;

import aggregator.data.*;
import util.exceptions.NullArgumentException;

import java.util.List;
import java.util.Set;

public class ServiceAggregator
{
    private final QueryConverter converter;
    private final ServiceRequester requester;
    private final ResultsSplitter splitter;
    private final CapacityFilter filter;
    private final SupplierResolver resolver;
    private final OptionsSorter sorter;

    public ServiceAggregator(final QueryConverter converter,
                             final ServiceRequester requester,
                             final ResultsSplitter splitter,
                             final CapacityFilter filter,
                             final SupplierResolver resolver,
                             final OptionsSorter sorter)
    {
        if (converter == null) throw new NullArgumentException(0, QueryConverter.class);
        else if (requester == null) throw new NullArgumentException(1, ServiceRequester.class);
        else if (splitter == null) throw new NullArgumentException(2, ResultsSplitter.class);
        else if (filter == null) throw new NullArgumentException(3, CapacityFilter.class);
        else if (resolver == null) throw new NullArgumentException(4, SupplierResolver.class);
        else if (sorter == null) throw new NullArgumentException(5, OptionsSorter.class);

        this.converter = converter;
        this.requester = requester;
        this.splitter = splitter;
        this.filter = filter;
        this.resolver = resolver;
        this.sorter = sorter;
    }

    public List<ServiceOption> aggregate(final GeneralQuery generalQuery)
    {
        if (generalQuery == null) throw new NullArgumentException(0, GeneralQuery.class);

        Set<ServiceQuery> serviceQueries = converter.convert(generalQuery);
        Set<ServiceResponse> serviceResponses = requester.request(serviceQueries);
        List<UnfilteredServiceOption> unfilteredServiceOptions = splitter.split(serviceResponses);
        List<ServiceOption> serviceOptions = filter.filter(unfilteredServiceOptions);
        serviceOptions = resolver.resolve(serviceOptions);
        serviceOptions = sorter.sort(serviceOptions);
        return serviceOptions;
    }
}
