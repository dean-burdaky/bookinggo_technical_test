package aggregator.testing;

import aggregator.ServiceConnection;
import aggregator.ServiceConnectionFactory;
import aggregator.data.ServiceQuery;

import static org.mockito.Mockito.mock;

public class ServiceConnectionMockFactory extends ServiceConnectionFactory
{
    private final ServiceConnectionMockSetup setup;

    public ServiceConnectionMockFactory(final ServiceConnectionMockSetup setup)
    {
        this.setup = setup;
    }

    @Override
    public ServiceConnection create(final ServiceQuery query)
    {
        ServiceConnection mockConnection = mock(ServiceConnection.class);
        setup.run(mockConnection, query);
        return mockConnection;
    }
}
