package application;

import aggregator.*;
import aggregator.data.GeneralQuery;
import aggregator.data.ServiceOption;
import util.exceptions.NullArgumentException;

import java.io.PrintStream;
import java.util.List;
import java.util.logging.LogManager;

public class DaveApplication extends ConsoleApplication
{
    public DaveApplication(DaveCommandLineArguments dcla, PrintStream out)
    {
        super(dcla.getCLA(), out);
    }

    public static void main(final String[] args)
    {
        LogManager.getLogManager().reset();
        DaveCommandLineArguments dcla = new DaveCommandLineArguments(System.err, args);
        if (!dcla.getCLA().getReadingArgsFailed())
        {
            DaveApplication app = new DaveApplication(dcla, System.out);
            app.run();
        }

    }

    @Override
    public void run()
    {
        CommandLineArguments cla = getCLA();
        ServiceAggregator aggregator = new ServiceAggregator(
                new DaveConverter(),
                new ServiceRequester(new ServiceConnectionFactory()),
                new ResultsSplitter(),
                new NoFilter(),
                new NoResolver(),
                new OptionsSorter());
        GeneralQuery query = new GeneralQuery(
                cla.pickupLat, cla.pickupLon,
                cla.dropoffLat, cla.dropoffLon,
                cla.capacity);
        printResults(aggregator.aggregate(query));
    }

    @Override
    public void printResults(List<ServiceOption> serviceOptions)
    {
        if (serviceOptions == null) throw new NullArgumentException(0, List.class);

        for (ServiceOption option : serviceOptions)
            getOut().println(option.carType + " - " + option.price);
    }
}
