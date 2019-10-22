package aggregator.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import util.exceptions.IntegerToLowException;
import util.exceptions.NullArgumentException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static aggregator.data.GeneralQuery.MIN_CAPACITY;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class ServiceResponse
{
    public final Supplier supplier;
    private final List<SupplierOption> supplierOptions;

    private int capacity;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ServiceResponse(@JsonProperty("supplier_id") Supplier supplier,
                           @JsonProperty("options") List<SupplierOption> supplierOptions)
    {
        if (supplier == null) throw new NullArgumentException(0, Supplier.class);
        else if (supplierOptions == null) throw new NullArgumentException(1, List.class);

        this.supplier = supplier;
        this.supplierOptions = supplierOptions;
        this.capacity = MIN_CAPACITY;
    }

    public List<SupplierOption> supplierOptions()
    {
        return new ArrayList<>(supplierOptions);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) return false;
        else if (this == obj) return true;
        else if (obj instanceof ServiceResponse)
        {
            ServiceResponse response = (ServiceResponse) obj;
            return this.supplier == response.supplier && this.supplierOptions.equals(response.supplierOptions) &&
                    this.getCapacity() == response.getCapacity();
        }
        else return false;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(supplier, getCapacity(), supplierOptions);
    }

    public final int getCapacity()
    {
        return capacity;
    }

    public final void setCapacity(final int capacity)
    {
        if (capacity < MIN_CAPACITY) throw new IntegerToLowException(capacity, MIN_CAPACITY);

        this.capacity = capacity;
    }
}
