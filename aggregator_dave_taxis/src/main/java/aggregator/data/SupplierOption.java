package aggregator.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import util.exceptions.IntegerToLowException;
import util.exceptions.NullArgumentException;

import java.util.Objects;

import static aggregator.data.ServiceOption.MIN_PRICE;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SupplierOption
{
    public final CarType carType;
    public final int price;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public SupplierOption(@JsonProperty("car_type") CarType carType,
                          @JsonProperty("price") int price)
    {
        if (carType == null) throw new NullArgumentException(0, CarType.class);
        else if (price < MIN_PRICE) throw new IntegerToLowException(price, MIN_PRICE);

        this.carType = carType;
        this.price = price;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) return false;
        else if (this == obj) return true;
        else if (obj instanceof SupplierOption)
        {
            SupplierOption option = (SupplierOption) obj;
            return this.carType == option.carType && this.price == option.price;
        }
        else return false;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(carType, price);
    }
}
