package aggregator.data;

public class LongitudeOutOfBoundsException extends IllegalArgumentException
{
    public LongitudeOutOfBoundsException(float lon)
    {
        super("Longitude " + lon + " is not within -180 degrees to 180 degrees.");
    }
}
