package aggregator;


import aggregator.data.ServiceOption;

import java.util.Comparator;

class OptionPriceComparator implements Comparator<ServiceOption>
{
    @Override
    public int compare(final ServiceOption option1, final ServiceOption option2)
    {
        return option1.price - option2.price;
    }
}
