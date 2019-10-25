package aggregator;

import aggregator.data.ServiceQuery;

import java.io.IOException;

public class ServiceConnectionFactory
{
    public ServiceConnection create(final ServiceQuery query) throws IOException
    {
        return new ServiceConnection(query);
    }
}
