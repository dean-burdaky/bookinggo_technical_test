package aggregator.testing;

import aggregator.data.CarType;
import aggregator.data.ServiceOption;
import aggregator.data.Supplier;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum FixedSupplierData
{
    DAVE_200(
            "{" +
                    "\"supplier_id\": \"DAVE\"," +
                    "\"pickup\": \"51.470020,-0.454295\"," +
                    "\"dropoff\": \"51.0000, 1.000\"," +
                    "\"options\": [" +
                            "{" +
                                    "\"car_type\": \"STANDARD\"," +
                                    "\"price\": 671808" +
                            "}," +
                            "{" +
                                    "\"car_type\": \"EXECUTIVE\"," +
                                    "\"price\": 375545" +
                            "}" +
                    "]" +
            "}",
            new ServiceOption(CarType.STANDARD, Supplier.DAVE, 671808),
            new ServiceOption(CarType.EXECUTIVE, Supplier.DAVE, 375545)
    ),
    DAVE_400(
            "{" +
                    "\"timestamp\": \"2018-08-14T13:11:34.937+0000\"," +
                    "\"status\": 400," +
                    "\"error\": \"Bad Request\"," +
                    "\"message\": \"Required String parameter 'pickup' is not present\"," +
                    "\"path\": \"/dave\"" +
            "}"
    ),
    ERIC_200(
            "{" +
                    "\"supplier_id\": \"ERIC\"," +
                    "\"pickup\": \"51.470020,-0.454295\"," +
                    "\"dropoff\": \"51.0000, 1.000\"," +
                    "\"options\": [" +
                            "{" +
                                    "\"car_type\": \"MINIBUS\"," +
                                    "\"price\": \"37456\"" +
                            "}" +
                    "]" +
            "}",
            new ServiceOption(CarType.MINIBUS, Supplier.ERIC, 37456)
    ),
    ERIC_400(
            "{" +
                    "\"timestamp\": \"2018-08-14T13:11:34.937+0000\"," +
                    "\"status\": 400," +
                    "\"error\": \"Bad Request\"," +
                    "\"message\": \"Required String parameter 'pickup' is not present\"," +
                    "\"path\": \"/eric\"" +
                    "}"
    );

    private final String fixedJSON;
    private final List<ServiceOption> options;

    FixedSupplierData(final String fixedJSON, ServiceOption... options)
    {
        this.fixedJSON = fixedJSON;
        this.options = new ArrayList<>();
        this.options.addAll(Arrays.asList(options));
    }

    public final InputStream stream()
    {
        return new ByteArrayInputStream(fixedJSON.getBytes());
    }

    public final List<ServiceOption> getServiceOptions()
    {
        return new ArrayList<>(options);
    }
}
