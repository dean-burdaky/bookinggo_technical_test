package application;

import aggregator.ServiceAggregator;
import aggregator.data.GeneralQuery;
import aggregator.data.ServiceOption;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static aggregator.data.GeneralQuery.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class AggregateController
{
    private static final String PICKUP_LAT = "pickup.lat";
    private static final String PICKUP_LON = "pickup.lon";
    private static final String DROPOFF_LAT = "dropoff.lat";
    private static final String DROPOFF_LON = "dropoff.lon";
    private static final String CAPACITY = "capacity";

    private static final String LAT_RANGE = String.format(
            "Inclusively between %.0f and %.0f.",
            MIN_LATITUDE, MAX_LATITUDE);
    private static final String LON_RANGE = String.format(
            "Inclusively between %.0f and %.0f.",
            MIN_LONGITUDE, MAX_LONGITUDE);
    private static final String CAPACITY_THRESHOLD = String.format("Greater than or equal to %d", MIN_CAPACITY);


    @Resource(name="serviceAggregator")
    private ServiceAggregator serviceAggregator;

    private static void validateParameters(final float pickupLat, final float pickupLon,
                                           final float dropoffLat, final float dropoffLon,
                                           final int capacity)
    {
        if (pickupLat < MIN_LATITUDE || pickupLat > MAX_LATITUDE)
            throw new BadParameterException(PICKUP_LAT, LAT_RANGE);
        else if (pickupLon < MIN_LONGITUDE || pickupLon > MAX_LONGITUDE)
            throw new BadParameterException(PICKUP_LON, LON_RANGE);
        else if (dropoffLat < MIN_LATITUDE || dropoffLat > MAX_LATITUDE)
            throw new BadParameterException(DROPOFF_LAT, LAT_RANGE);
        else if (dropoffLon < MIN_LONGITUDE || dropoffLon > MAX_LONGITUDE)
            throw new BadParameterException(DROPOFF_LON, LON_RANGE);
        else if (capacity < MIN_CAPACITY)
            throw new BadParameterException(CAPACITY, CAPACITY_THRESHOLD);
    }

    @ExceptionHandler(BadParameterException.class)
    public void handleBadParameterException(HttpServletResponse response) throws IOException
    {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }

    @RequestMapping(path = "/", method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<ServiceOption> aggregate(@RequestParam(PICKUP_LAT) final float pickupLat,
                                                       @RequestParam(PICKUP_LON) final float pickupLon,
                                                       @RequestParam(DROPOFF_LAT) final float dropoffLat,
                                                       @RequestParam(DROPOFF_LON) final float dropoffLon,
                                                       @RequestParam final int capacity)
    {
        validateParameters(pickupLat, pickupLon, dropoffLat, dropoffLon, capacity);

        GeneralQuery query = new GeneralQuery(
                pickupLat, pickupLon,
                dropoffLat, dropoffLon,
                capacity);
        return serviceAggregator.aggregate(query);
    }
}