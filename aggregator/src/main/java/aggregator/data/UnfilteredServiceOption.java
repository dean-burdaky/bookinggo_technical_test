package aggregator.data;

import util.exceptions.IntegerToLowException;
import util.exceptions.NullArgumentException;

import java.util.Objects;

import static aggregator.data.GeneralQuery.MIN_CAPACITY;

public final class UnfilteredServiceOption
{
    public final int capacity;
    public final ServiceOption serviceOption;

    public UnfilteredServiceOption(final int capacity, final ServiceOption serviceOption)
    {
        if (capacity < MIN_CAPACITY) throw new IntegerToLowException(capacity, MIN_CAPACITY);
        else if (serviceOption == null) throw new NullArgumentException(1, ServiceOption.class);

        this.capacity = capacity;
        this.serviceOption = serviceOption;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) return false;
        else if (this == obj) return true;
        else if (obj instanceof UnfilteredServiceOption)
        {
            UnfilteredServiceOption option = (UnfilteredServiceOption) obj;
            return this.capacity == option.capacity && this.serviceOption.equals(option.serviceOption);
        }
        else return false;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(capacity, serviceOption);
    }
}
