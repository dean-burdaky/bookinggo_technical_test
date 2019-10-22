package aggregator;

import aggregator.data.ServiceOption;
import aggregator.data.ServiceResponse;
import aggregator.data.SupplierOption;
import aggregator.data.UnfilteredServiceOption;
import util.exceptions.NullArgumentException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ResultsSplitter
{
    public List<UnfilteredServiceOption> split(final Set<ServiceResponse> serviceResponses)
    {
        if (serviceResponses == null) throw new NullArgumentException(0, Set.class);

        List<UnfilteredServiceOption> serviceOptions = new ArrayList<>();
        for (ServiceResponse serviceResponse : serviceResponses)
        {
            for (SupplierOption supplierOption : serviceResponse.supplierOptions())
            {
                serviceOptions.add(
                        new UnfilteredServiceOption(
                                serviceResponse.getCapacity(),
                                new ServiceOption(
                                        supplierOption.carType,
                                        serviceResponse.supplier,
                                        supplierOption.price
                                )
                        )
                );
            }
        }
        return serviceOptions;
    }
}
