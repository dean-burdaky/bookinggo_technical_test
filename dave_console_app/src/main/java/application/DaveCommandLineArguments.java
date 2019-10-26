package application;

import util.exceptions.NullArgumentException;

import java.io.PrintStream;

import static aggregator.data.GeneralQuery.MIN_CAPACITY;

public class DaveCommandLineArguments
{
    private static final String PARAMETERS =
            "\t{pickup latitude} {pickup longitude} {dropoff latitude} {dropoff longitude}";
    private final DelegateCLA delegateCLA;

    public DaveCommandLineArguments(PrintStream err, String[] args)
    {
        if (err == null) throw new NullArgumentException(0, PrintStream.class);
        else if (args == null) throw new NullArgumentException(1, String[].class);
        else
        {
            String[] newArgs = new String[args.length + 1];
            System.arraycopy(args, 0, newArgs, 0, args.length);
            newArgs[args.length] = Integer.toString(MIN_CAPACITY);
            delegateCLA = new DelegateCLA(err, newArgs);
        }
    }

    public CommandLineArguments getCLA()
    {
        return delegateCLA;
    }

    public boolean getReadingArgsFailed()
    {
        return getCLA().getReadingArgsFailed();
    }

    private static class DelegateCLA extends CommandLineArguments
    {

        public DelegateCLA(PrintStream err, String[] args)
        {
            super(err, args);
        }

        @Override
        protected void checkArgLength(String[] args)
        {
            if (args.length < EXPECTED_ARGUMENT_COUNT)
            {
                printError("Too few parameters. Need to be supplied with:", PARAMETERS);
            }
            else if (args.length > EXPECTED_ARGUMENT_COUNT)
            {
                printError("Too many parameters. Need to be supplied with:", PARAMETERS);
            }
            else return;
            setReadingArgsFailed(true);
        }
    }
}
