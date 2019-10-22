package aggregator.data;

public class LatitudeOutOfBoundsException extends IllegalArgumentException
{
    public LatitudeOutOfBoundsException(float lat)
    {
        super("Latitude " + lat + " is not within -90 degrees to 90 degrees.");
    }
}
