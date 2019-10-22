package aggregator;

import aggregator.data.ServiceQuery;
import util.exceptions.NullArgumentException;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class ServiceConnection
{
    private final HttpsURLConnection connection;

    public ServiceConnection(ServiceQuery query) throws IOException
    {
        if (query == null) throw new NullArgumentException(0, ServiceQuery.class);
        URL url = new URL(query.supplier.url + "?pickup=" + query.pickupLat + "," + query.pickupLon
                + "&dropoff=" + query.dropoffLat + "," + query.dropoffLon);
        connection = (HttpsURLConnection) url.openConnection();
    }

    public String getResponseMessage() throws IOException
    {
        return connection.getResponseMessage();
    }

    public boolean getDoInput()
    {
        return connection.getDoInput();
    }

    public void setDoInput(boolean doInput)
    {
        connection.setDoInput(doInput);
    }

    public boolean getDoOutput()
    {
        return connection.getDoOutput();
    }

    public void setDoOutput(boolean doOutput)
    {
        connection.setDoOutput(doOutput);
    }

    public String getRequestMethod()
    {
        return connection.getRequestMethod();
    }

    public void setRequestMethod(String method) throws ProtocolException
    {
        connection.setRequestMethod(method);
    }

    public int getResponseCode() throws IOException
    {
        return connection.getResponseCode();
    }

    public void disconnect()
    {
        connection.disconnect();
    }

    public InputStream getErrorStream()
    {
        return connection.getErrorStream();
    }

    public void connect() throws IOException
    {
        connection.connect();
    }

    public int getConnectTimeout()
    {
        return connection.getConnectTimeout();
    }

    public void setConnectTimeout(int timeout)
    {
        connection.setConnectTimeout(timeout);
    }

    public int getReadTimeout()
    {
        return connection.getReadTimeout();
    }

    public void setReadTimeout(int timeout)
    {
        connection.setReadTimeout(timeout);
    }

    public void setRequestProperty(String key, String value) {
        connection.setRequestProperty(key, value);
    }

    public void addRequestProperty(String key, String value) {
        connection.addRequestProperty(key, value);
    }

    public String getRequestProperty(String key) {
        return connection.getRequestProperty(key);
    }

    public Map<String, List<String>> getRequestProperties() {
        return connection.getRequestProperties();
    }

    public InputStream getInputStream() throws IOException
    {
        return connection.getInputStream();
    }

    public OutputStream getOutputStream() throws IOException
    {
        return connection.getOutputStream();
    }
}
