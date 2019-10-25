package aggregator.data;

public final class GeneralQuery
{
    public static final float MAX_LATITUDE = 90f, MIN_LATITUDE = -90f, MAX_LONGITUDE = 180f, MIN_LONGITUDE = -180f;
    public static final int MIN_CAPACITY = 1;

    public final float pickupLat, pickupLon, dropoffLat, dropoffLon;

    public GeneralQuery(final float pickupLat, final float pickupLon, float dropoffLat, float dropoffLon)
    {
        if (pickupLat < MIN_LATITUDE || pickupLat > MAX_LATITUDE)
            throw new LatitudeOutOfBoundsException(pickupLat);
        else if (pickupLon < MIN_LONGITUDE || pickupLon > MAX_LONGITUDE)
            throw new LongitudeOutOfBoundsException(pickupLon);
        else if (dropoffLat < MIN_LATITUDE || dropoffLat > MAX_LATITUDE)
            throw new LatitudeOutOfBoundsException(dropoffLat);
        else if (dropoffLon < MIN_LONGITUDE || dropoffLon > MAX_LONGITUDE)
            throw new LongitudeOutOfBoundsException(pickupLon);

        this.pickupLat = pickupLat;
        this.pickupLon = pickupLon;
        this.dropoffLat = dropoffLat;
        this.dropoffLon = dropoffLon;
    }
}
