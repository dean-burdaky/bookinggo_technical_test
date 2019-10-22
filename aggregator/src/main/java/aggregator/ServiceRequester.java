package aggregator;


import aggregator.data.ServiceQuery;
import aggregator.data.ServiceResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import util.exceptions.NullArgumentException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class ServiceRequester
{
    private static final Logger logger = Logger.getLogger(ServiceRequester.class.getName());
    private static final int timeout = 2000;

    private final ServiceConnectionFactory factory;

    public ServiceRequester(final ServiceConnectionFactory factory)
    {
        if (factory == null) throw new NullArgumentException(0, ServiceConnectionFactory.class);

        this.factory = factory;
    }

    public Set<ServiceResponse> request(Set<ServiceQuery> serviceQueries)
    {
        if (serviceQueries == null) throw new NullArgumentException(0, Set.class);

        Set<ServiceResponse> responses = new HashSet<>();
        for (ServiceQuery query : serviceQueries)
        {
            try
            {
                ServiceConnection connection = prepareConnection(query);
                connection.connect();
                handleResponse(responses, query, connection);
                connection.disconnect();
            }
            catch(IOException exception)
            {
                logger.info("ServiceRequester received IOException for Supplier " + query.supplier.name() +
                        ". Ignoring supplier.");
            }
        }
        return responses;
    }

    private void handleResponse(Set<ServiceResponse> responses,
                                ServiceQuery query,
                                ServiceConnection connection) throws IOException
    {
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK)
        {
            String responseBody = readResponseBody(connection);
            ObjectMapper objectMapper = new ObjectMapper();
            ServiceResponse serviceResponse = objectMapper.readValue(responseBody, ServiceResponse.class);
            serviceResponse.setCapacity(query.capacity);
            responses.add(serviceResponse);
        }
        else if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST
                || responseCode == HttpURLConnection.HTTP_INTERNAL_ERROR)
            logger.info("ServiceRequester received status code " + responseCode + " for Supplier " +
                    query.supplier.name() + ": \"" + connection.getResponseMessage() + "\" Ignoring supplier.");
    }

    private ServiceConnection prepareConnection(ServiceQuery query) throws IOException
    {
        ServiceConnection connection = factory.create(query);
        connection.setRequestMethod("GET");
        connection.setDoInput(true);
        connection.setRequestProperty("Content-type", "application/json");
        connection.setConnectTimeout(timeout);
        connection.setReadTimeout(timeout);
        return connection;
    }

    private String readResponseBody(ServiceConnection connection) throws IOException
    {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder content = new StringBuilder();
        int inCharInt;
        while ((inCharInt = in.read()) >= 0)
        {
            content.append((char) inCharInt);
        }
        return content.toString();
    }
}
