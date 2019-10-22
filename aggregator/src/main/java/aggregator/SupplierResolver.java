package aggregator;

import aggregator.data.CarType;
import aggregator.data.ServiceOption;
import util.exceptions.NullArgumentException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupplierResolver
{
    public List<ServiceOption> resolve(final List<ServiceOption> serviceOptions)
    {
        if (serviceOptions == null) throw new NullArgumentException(0, List.class);

        Map<CarType, ServiceOption> resolvingMap = new HashMap<>();
        for (ServiceOption option : serviceOptions)
        {
            CarType carType = option.carType;
            boolean supplierMapped = resolvingMap.containsKey(carType);
            if (!supplierMapped || option.price < resolvingMap.get(carType).price)
                resolvingMap.put(carType, option);
        }
        return new ArrayList<>(resolvingMap.values());
    }
}
