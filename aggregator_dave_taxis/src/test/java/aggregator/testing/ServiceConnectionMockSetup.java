package aggregator.testing;

import aggregator.ServiceConnection;
import aggregator.data.ServiceQuery;

public interface ServiceConnectionMockSetup
{
    void run(ServiceConnection mockConnection, ServiceQuery query);
}
