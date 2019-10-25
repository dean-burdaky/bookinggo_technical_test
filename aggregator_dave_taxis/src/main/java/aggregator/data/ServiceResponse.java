package aggregator.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import util.exceptions.NullArgumentException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class ServiceResponse
{
    public final Supplier supplier;
    private final List<SupplierOption> supplierOptions;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ServiceResponse(@JsonProperty("supplier_id") Supplier supplier,
                           @JsonProperty("options") List<SupplierOption> supplierOptions)
    {
        if (supplier == null) throw new NullArgumentException(0, Supplier.class);
        else if (supplierOptions == null) throw new NullArgumentException(1, List.class);

        this.supplier = supplier;
        this.supplierOptions = supplierOptions;
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
            return this.supplier == response.supplier && this.supplierOptions.equals(response.supplierOptions);
        }
        else return false;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(supplier, supplierOptions);
    }
}
