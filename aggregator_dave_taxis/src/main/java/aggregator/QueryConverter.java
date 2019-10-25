package aggregator;

import aggregator.data.GeneralQuery;
import aggregator.data.ServiceQuery;
import aggregator.data.Supplier;
import util.exceptions.NullArgumentException;

import java.util.HashSet;
import java.util.Set;

public class QueryConverter
{
    public Set<ServiceQuery> convert(final GeneralQuery generalQuery)
    {
        if (generalQuery == null) throw new NullArgumentException(0, GeneralQuery.class);

        Set<ServiceQuery> serviceQueries = new HashSet<>();
        serviceQueries.add(new ServiceQuery(Supplier.DAVE, generalQuery));
        return serviceQueries;
    }
}
