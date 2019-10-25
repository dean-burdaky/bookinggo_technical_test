package aggregator.data;

import java.net.MalformedURLException;
import java.net.URL;

public enum Supplier
{
    DAVE("dave", createDefaultURL("dave"));

    public final URL url;
    public final String name;

    Supplier(final String name, final URL url)
    {
        this.name = name;
        this.url = url;
    }

    private static URL createDefaultURL(final String name)
    {
        try
        {
            return new URL("https://techtest.rideways.com/" + name + "/");
        }
        catch (MalformedURLException e)
        {
            return null;
        }
    }
}
