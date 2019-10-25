package aggregator;

import aggregator.data.ServiceOption;
import util.exceptions.NullArgumentException;

import java.util.ArrayList;
import java.util.List;

public class OptionsSorter
{
    private static final OptionPriceComparator comparator = new OptionPriceComparator();

    public List<ServiceOption> sort(final List<ServiceOption> serviceOptions)
    {
        if (serviceOptions == null) throw new NullArgumentException(0, List.class);

        List<ServiceOption> options = new ArrayList<>(serviceOptions);
        options.sort(comparator.reversed());
        return options;
    }
}
