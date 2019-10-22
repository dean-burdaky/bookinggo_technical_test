package aggregator;

import aggregator.data.ServiceOption;
import aggregator.data.UnfilteredServiceOption;
import util.exceptions.NullArgumentException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CapacityFilter
{
    private static Predicate<UnfilteredServiceOption> isCapacityGreaterThanOrEqualToThreshold()
    {
        return option -> option.serviceOption.carType.capacity >= option.capacity;
    }

    public List<ServiceOption> filter(final List<UnfilteredServiceOption> unfilteredServiceOptions)
    {
        if (unfilteredServiceOptions == null) throw new NullArgumentException(0, List.class);

        List<UnfilteredServiceOption> tempServiceOption = unfilteredServiceOptions
                .stream()
                .filter(isCapacityGreaterThanOrEqualToThreshold())
                .collect(Collectors.toList());
        List<ServiceOption> serviceOptions = new ArrayList<>();
        for (UnfilteredServiceOption option : tempServiceOption) serviceOptions.add(option.serviceOption);
        return serviceOptions;
    }
}
