package aggregator.unit;

import aggregator.ServiceConnectionFactory;
import aggregator.ServiceRequester;
import org.junit.jupiter.api.Test;
import util.exceptions.NullArgumentException;

import static aggregator.testhelper.NullArgumentExceptionDataAsserter.assertNullArgumentExceptionData;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class ServiceRequesterConstructorTest
{
    @Test
    void testConstructorFail()
    {
        NullArgumentException exception = assertThrows(
                NullArgumentException.class,
                ()-> new ServiceRequester(null),
                "Expected exception not thrown.");
        assertNullArgumentExceptionData(exception, new NullArgumentException(0, ServiceConnectionFactory.class));
    }

    @Test
    void testConstructorSuccess()
    {
        ServiceConnectionFactory dummyFactory = mock(ServiceConnectionFactory.class);
        assertDoesNotThrow(()-> new ServiceRequester(dummyFactory));
    }
}
