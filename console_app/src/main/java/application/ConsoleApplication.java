package application;

import aggregator.*;
import aggregator.data.GeneralQuery;
import aggregator.data.ServiceOption;
import util.exceptions.NullArgumentException;

import java.io.PrintStream;
import java.util.List;
import java.util.logging.LogManager;

public class ConsoleApplication
{
    private final CommandLineArguments cla;
    private final PrintStream out;
    public ConsoleApplication(CommandLineArguments cla, PrintStream out)
    {
        if (cla == null) throw new NullArgumentException(0, CommandLineArguments.class);
        else if (out == null) throw new NullArgumentException(1, PrintStream.class);
        else if (cla.getReadingArgsFailed()) throw new FailedCLAException();

        this.cla = cla;
        this.out = out;
    }

    public static void main(final String[] args)
    {
        LogManager.getLogManager().reset();
        CommandLineArguments cla = new CommandLineArguments(System.err, args);
        if (!cla.getReadingArgsFailed())
        {
            ConsoleApplication app = new ConsoleApplication(cla, System.out);
            app.run();
        }

    }

    public void run()
    {
        ServiceAggregator aggregator = new ServiceAggregator(
                new QueryConverter(),
                new ServiceRequester(new ServiceConnectionFactory()),
                new ResultsSplitter(),
                new CapacityFilter(),
                new SupplierResolver(),
                new OptionsSorter());
        GeneralQuery query = new GeneralQuery(
                cla.pickupLat, cla.pickupLon,
                cla.dropoffLat, cla.dropoffLon,
                cla.capacity);
        printResults(aggregator.aggregate(query));
    }

    public void printResults(List<ServiceOption> serviceOptions)
    {
        if (serviceOptions == null) throw new NullArgumentException(0, List.class);

        for (ServiceOption option : serviceOptions)
            out.println(option.carType + " - " + option.supplier.name + " - " + option.price);
    }

    protected final CommandLineArguments getCLA()
    {
        return cla;
    }

    protected final PrintStream getOut()
    {
        return out;
    }
}
