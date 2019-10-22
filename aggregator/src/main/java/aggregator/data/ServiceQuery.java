package aggregator.data;

import util.exceptions.NullArgumentException;

import java.util.Objects;

public final class ServiceQuery
{
    public final Supplier supplier;
    public final float pickupLat, pickupLon, dropoffLat, dropoffLon;
    public final int capacity;

    public ServiceQuery(final Supplier supplier, final GeneralQuery generalQuery)
    {
        if (supplier == null) throw new NullArgumentException(0, Supplier.class);
        else if (generalQuery == null) throw new NullArgumentException(1, GeneralQuery.class);

        this.supplier = supplier;
        this.pickupLat = generalQuery.pickupLat;
        this.pickupLon = generalQuery.pickupLon;
        this.dropoffLat = generalQuery.dropoffLat;
        this.dropoffLon = generalQuery.dropoffLon;
        this.capacity = generalQuery.capacity;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) return false;
        else if (this == obj) return true;
        else if (obj instanceof ServiceQuery)
        {
            ServiceQuery query = (ServiceQuery) obj;
            return this.pickupLat == query.pickupLat && this.pickupLon == query.pickupLon &&
                    this.dropoffLat == query.dropoffLat && this.dropoffLon == query.dropoffLon &&
                    this.capacity == query.capacity && this.supplier.equals(query.supplier);
        }
        else return false;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(supplier, pickupLat, pickupLon, dropoffLat, dropoffLon, capacity);
    }
}
