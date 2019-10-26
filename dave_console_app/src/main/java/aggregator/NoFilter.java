package aggregator;

import aggregator.data.ServiceOption;
import aggregator.data.UnfilteredServiceOption;
import util.exceptions.NullArgumentException;

import java.util.ArrayList;
import java.util.List;

public class NoFilter extends CapacityFilter
{
    @Override
    public List<ServiceOption> filter(List<UnfilteredServiceOption> unfilteredServiceOptions)
    {
        if (unfilteredServiceOptions == null) throw new NullArgumentException(0, List.class);

        List<ServiceOption> serviceOptions = new ArrayList<>();
        for (UnfilteredServiceOption uso : unfilteredServiceOptions) serviceOptions.add(uso.serviceOption);
        return serviceOptions;
    }
}
