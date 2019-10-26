package aggregator;

import aggregator.data.ServiceOption;

import java.util.List;

public class NoResolver extends SupplierResolver
{
    @Override
    public List<ServiceOption> resolve(List<ServiceOption> serviceOptions)
    {
        return serviceOptions;
    }
}
