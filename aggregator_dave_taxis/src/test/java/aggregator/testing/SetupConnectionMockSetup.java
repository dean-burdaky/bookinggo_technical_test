package aggregator.testing;

import aggregator.ServiceConnection;
import aggregator.data.ServiceQuery;
import aggregator.data.Supplier;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class SetupConnectionMockSetup
{
    private static int determineSupplierCode(final int daveCode,
                                             final int ericCode,
                                             final Supplier supplier)
    {
        if (supplier == Supplier.DAVE) return daveCode;
        else return HttpURLConnection.HTTP_NOT_FOUND;
    }

    private static InputStream determineSupplierStream(final InputStream daveStream,
                                                       final InputStream ericStream,
                                                       final Supplier supplier)
    {
        if (supplier == Supplier.DAVE) return daveStream;
        else throw new NotTestSupplierException(supplier);
    }

    public static ServiceConnectionMockSetup setupConnectionMockSetup(final int daveCode,
                                                                      final InputStream daveStream,
                                                                      final int ericCode,
                                                                      final InputStream ericStream)
    {
        return (final ServiceConnection mockConnection, final ServiceQuery query)->
                setupMockBehaviour(daveCode, daveStream, ericCode, ericStream, mockConnection, query.supplier);
    }

    public static ServiceConnectionMockSetup setupConnectionMockSetupWithIOException(final int ericCode,
                                                                                     final InputStream ericStream)
    {
        return (final ServiceConnection mockConnection, final ServiceQuery query) ->
        {
            if (query.supplier == Supplier.DAVE)
            {
                try
                {
                    doThrow(IOException.class).when(mockConnection).getOutputStream();
                }
                catch (IOException exception)
                {
                    fail(exception);
                }
            }
            else
                setupMockBehaviour(0, null, ericCode, ericStream, mockConnection, query.supplier);
        };
    }

    private static void setupMockBehaviour(final int daveCode,
                                           final InputStream daveStream,
                                           final int ericCode,
                                           final InputStream ericStream,
                                           final ServiceConnection mockConnection,
                                           final Supplier supplier)
    {
        int supplierCode = determineSupplierCode(daveCode, ericCode, supplier);
        try
        {
            doReturn(mock(OutputStream.class)).when(mockConnection).getOutputStream();
            doReturn(supplierCode).when(mockConnection).getResponseCode();
        }
        catch (IOException exception)
        {
            fail(exception);
        }

        if (supplierCode == HttpURLConnection.HTTP_OK)
        {
            InputStream supplierStream = determineSupplierStream(daveStream, ericStream, supplier);
            try
            {
                doReturn(supplierStream).when(mockConnection).getInputStream();
            }
            catch (IOException exception)
            {
                fail(exception);
            }
        }
    }
}