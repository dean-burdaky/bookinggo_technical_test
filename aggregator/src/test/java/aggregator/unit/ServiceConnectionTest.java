package aggregator.unit;

import aggregator.ServiceConnection;
import aggregator.data.GeneralQuery;
import aggregator.data.ServiceQuery;
import aggregator.data.Supplier;
import org.junit.jupiter.api.Test;
import util.exceptions.NullArgumentException;

import static aggregator.testhelper.NullArgumentExceptionDataAsserter.assertNullArgumentExceptionData;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ServiceConnectionTest
{

    @Test
    void testConstructorFail()
    {
        NullArgumentException exception = assertThrows(
                NullArgumentException.class,
                ()-> new ServiceConnection(null),
                "Expected exception was not thrown.");
        assertNullArgumentExceptionData(exception, new NullArgumentException(0, ServiceQuery.class));
    }

    @Test
    void testConstructorSuccess()
    {
        ServiceQuery serviceQuery = new ServiceQuery(Supplier.DAVE, new GeneralQuery(0,0,0,0,1));
        assertDoesNotThrow(()-> new ServiceConnection(serviceQuery), "Unexpected exception was thrown.");
    }
}
