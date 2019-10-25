package aggregator;

import aggregator.data.ServiceOption;
import aggregator.data.ServiceResponse;
import aggregator.data.SupplierOption;
import util.exceptions.NullArgumentException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ResultsSplitter
{
    public List<ServiceOption> split(final Set<ServiceResponse> serviceResponses)
    {
        if (serviceResponses == null) throw new NullArgumentException(0, Set.class);

        List<ServiceOption> serviceOptions = new ArrayList<>();
        for (ServiceResponse serviceResponse : serviceResponses)
        {
            for (SupplierOption supplierOption : serviceResponse.supplierOptions())
            {
                serviceOptions.add(
                        new ServiceOption(
                                supplierOption.carType,
                                serviceResponse.supplier,
                                supplierOption.price
                        )
                );
            }
        }
        return serviceOptions;
    }
}
