package application;

import util.exceptions.NullArgumentException;

import java.io.PrintStream;
import java.util.logging.Logger;

import static aggregator.data.GeneralQuery.*;

public final class CommandLineArguments
{
    public static final String SHORT_MIN_LAT = String.format("%.0f", MIN_LATITUDE);
    public static final String SHORT_MAX_LAT = String.format("%.0f", MAX_LATITUDE);
    public static final String SHORT_MIN_LON = String.format("%.0f", MIN_LONGITUDE);
    public static final String SHORT_MAX_LON = String.format("%.0f", MAX_LONGITUDE);
    private static final int EXPECTED_ARGUMENT_COUNT = 4;
    private static final Logger logger = Logger.getLogger(CommandLineArguments.class.getName());

    public final float pickupLat, pickupLon, dropoffLat, dropoffLon;

    private final PrintStream err;

    private boolean readingArgsFailed = false;

    public CommandLineArguments(PrintStream err, String[] args)
    {
        if (err == null) throw new NullArgumentException(0, PrintStream.class);
        else if (args == null) throw new NullArgumentException(1, String[].class);
        else
        {
            this.err = err;
            checkArgLength(args);
            if (!getReadingArgsFailed())
            {
                pickupLat = parseFloatArg(1, args[0]);
                pickupLon = parseFloatArg(2, args[1]);
                dropoffLat = parseFloatArg(3, args[2]);
                dropoffLon = parseFloatArg(4, args[3]);
                if (!getReadingArgsFailed())
                {
                    checkLatRange(1, pickupLat);
                    checkLonRange(2, pickupLon);
                    checkLatRange(3, dropoffLat);
                    checkLonRange(4, dropoffLon);
                }
            }
            else
            {
                pickupLat = pickupLon = dropoffLat = dropoffLon = 0f;
            }
        }
    }

    private void checkArgLength(String[] args)
    {
        if (args.length < EXPECTED_ARGUMENT_COUNT)
        {
            printError("Too few parameters. Need to be supplied with:",
                    "\t{pickup latitude} {pickup longitude} {dropoff latitude} {dropoff longitude}");
        }
        else if (args.length > EXPECTED_ARGUMENT_COUNT)
        {
            printError("Too many parameters. Need to be supplied with:",
                    "\t{pickup latitude} {pickup longitude} {dropoff latitude} {dropoff longitude}");
        }
        else return;
        setReadingArgsFailed(true);
    }

    private float parseFloatArg(final int position, final String arg)
    {
        try
        {
            return Float.parseFloat(arg);
        }
        catch (NullPointerException | NumberFormatException exception)
        {
            logger.severe(exception.toString());
            printError("Parameter " + position + " is not a floating point value (decimal value).",
                    "\tReceived: " + arg);
            setReadingArgsFailed(true);
            return 0f;
        }
    }

    private void checkLatRange(final int position, final float lat)
    {
        if (lat < MIN_LATITUDE || lat > MAX_LATITUDE)
        {
            printError("Parameter " + position + " is not inclusively between " + SHORT_MIN_LAT + " and "
                            + SHORT_MAX_LAT + ".", "\tReceived: " + lat);
            setReadingArgsFailed(true);
        }
    }

    private void checkLonRange(final int position, final float lon)
    {
        if (lon < MIN_LONGITUDE || lon > MAX_LONGITUDE)
        {
            printError("Parameter " + position + " is not inclusively between " + SHORT_MIN_LON + " and "
                    + SHORT_MAX_LON + ".", "\tReceived: " + lon);
            setReadingArgsFailed(true);
        }
    }

    private void printError(String... message)
    {
        for (String line : message) err.println(line);
    }

    public boolean getReadingArgsFailed()
    {
        return readingArgsFailed;
    }

    private void setReadingArgsFailed(final boolean readingArgsFailed)
    {
        this.readingArgsFailed = readingArgsFailed;
    }
}
