package aggregator.data;

import util.exceptions.IntegerToLowException;
import util.exceptions.NullArgumentException;

import java.util.Objects;

public final class ServiceOption
{
    public static final int MIN_PRICE = 0;

    public final CarType carType;
    public final Supplier supplier;
    public final int price;

    public ServiceOption(final CarType carType, final Supplier supplier, final int price)
    {
        if (carType == null) throw new NullArgumentException(0, CarType.class);
        else if (supplier == null) throw new NullArgumentException(1, Supplier.class);
        else if (price < MIN_PRICE) throw new IntegerToLowException(price, MIN_PRICE);

        this.carType = carType;
        this.supplier = supplier;
        this.price = price;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) return false;
        else if (this == obj) return true;
        else if (obj instanceof ServiceOption)
        {
            ServiceOption option = (ServiceOption)obj;
            return this.carType.equals(option.carType) && this.supplier.equals(option.supplier) &&
                    this.price == option.price;
        }
        else return false;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(carType, supplier, price);
    }
}
