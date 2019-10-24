package application;

import aggregator.ServiceAggregator;
import aggregator.data.CarType;
import aggregator.data.ServiceOption;
import aggregator.data.Supplier;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static aggregator.data.GeneralQuery.*;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.notNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = RESTApplication.class, webEnvironment=WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AggregateControllerTest
{
    public static final String PICKUP_LAT = "pickup.lat";
    public static final String PICKUP_LON = "pickup.lon";
    public static final String DROPOFF_LAT = "dropoff.lat";
    public static final String DROPOFF_LON = "dropoff.lon";
    public static final String CAPACITY = "capacity";
    public static final String VALID = "1.5";
    public static final String LOW_LAT = Float.toString(MIN_LATITUDE - 1);
    public static final String HIGH_LAT = Float.toString(MAX_LATITUDE + 1);
    public static final String LOW_LON = Float.toString(MIN_LONGITUDE - 1);
    public static final String HIGH_LON = Float.toString(MAX_LONGITUDE + 1);
    public static final String LOW_CAPACITY = Integer.toString(MIN_CAPACITY - 1);


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceAggregator aggregator;

    private static Stream<Arguments> provideGetParametersWithMissingParam()
    {
        return Stream.of(
                Arguments.of(null, null, null, null, null, PICKUP_LAT),
                Arguments.of(VALID, null, null, null, null, PICKUP_LON),
                Arguments.of(VALID, VALID, null, null, null, DROPOFF_LAT),
                Arguments.of(VALID, VALID, VALID, null, null, DROPOFF_LON),
                Arguments.of(VALID, VALID, VALID, VALID, null, CAPACITY)
        );
    }

    private static Stream<Arguments> provideGetParametersWithNoValue()
    {
        String empty = "";
        return Stream.of(
                Arguments.of(empty, empty, empty, empty, empty),
                Arguments.of(VALID, empty, empty, empty, empty),
                Arguments.of(VALID, VALID, empty, empty, empty),
                Arguments.of(VALID, VALID, VALID, empty, empty),
                Arguments.of(VALID, VALID, VALID, VALID, empty)
        );
    }

    private static Stream<Arguments> provideGetParametersWithInvalidTypeValue()
    {
        String invalid = "cheese";
        return Stream.of(
                Arguments.of(invalid, invalid, invalid, invalid, invalid),
                Arguments.of(VALID, invalid, invalid, invalid, invalid),
                Arguments.of(VALID, VALID, invalid, invalid, invalid),
                Arguments.of(VALID, VALID, VALID, invalid, invalid),
                Arguments.of(VALID, VALID, VALID, VALID, invalid)
        );
    }

    private static Stream<Arguments> provideGetParametersWithInvalidValue()
    {
        return Stream.of(
                Arguments.of(LOW_LAT, LOW_LON, LOW_LAT, LOW_LON, LOW_CAPACITY),
                Arguments.of(HIGH_LAT, LOW_LON, LOW_LAT, LOW_LON, LOW_CAPACITY),
                Arguments.of(VALID, LOW_LON, LOW_LAT, LOW_LON, LOW_CAPACITY),
                Arguments.of(VALID, HIGH_LON, LOW_LAT, LOW_LON, LOW_CAPACITY),
                Arguments.of(VALID, VALID, LOW_LAT, LOW_LON, LOW_CAPACITY),
                Arguments.of(VALID, VALID, HIGH_LAT, LOW_LON, LOW_CAPACITY),
                Arguments.of(VALID, VALID, VALID, LOW_LON, LOW_CAPACITY),
                Arguments.of(VALID, VALID, VALID, HIGH_LON, LOW_CAPACITY),
                Arguments.of(VALID, VALID, VALID, VALID, LOW_CAPACITY)
        );
    }

    private static List<ServiceOption> createDummyServiceOptions()
    {
        List<ServiceOption> serviceOptions = new ArrayList<>();
        serviceOptions.add(new ServiceOption(CarType.STANDARD, Supplier.DAVE, ServiceOption.MIN_PRICE));
        return serviceOptions;
    }

    private static String convertOptionsToJson(final List<ServiceOption> serviceOptions) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(serviceOptions);
    }

    private static MultiValueMap<String, String> createParamsMap(final String pickupLat, final String pickupLon,
                                                                 final String dropoffLat, final String dropoffLon,
                                                                 final String capacity)
    {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        if (pickupLat != null) params.add(PICKUP_LAT, pickupLat);
        if (pickupLon != null) params.add(PICKUP_LON, pickupLon);
        if (dropoffLat != null) params.add(DROPOFF_LAT, dropoffLat);
        if (dropoffLon != null) params.add(DROPOFF_LON, dropoffLon);
        if (capacity != null) params.add(CAPACITY, capacity);
        return params;
    }

    @ParameterizedTest
    @MethodSource("provideGetParametersWithMissingParam")
    void testAggregateWithMissingParam(final String pickupLat, final String pickupLon,
                                       final String dropoffLat, final String dropoffLon,
                                       final String capacity,
                                       final String expectedParam) throws Exception
    {
        MultiValueMap<String, String> params = createParamsMap(pickupLat, pickupLon, dropoffLat, dropoffLon, capacity);
        mockMvc.perform(get("/").params(params).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason(containsString(expectedParam)));
    }

    @ParameterizedTest
    @MethodSource("provideGetParametersWithNoValue")
    void testAggregateWithNoValue(final String pickupLat, final String pickupLon,
                                  final String dropoffLat, final String dropoffLon,
                                  final String capacity) throws Exception
    {
        MultiValueMap<String, String> params = createParamsMap(pickupLat, pickupLon, dropoffLat, dropoffLon, capacity);
        mockMvc.perform(get("/").params(params).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @MethodSource("provideGetParametersWithInvalidTypeValue")
    void testAggregateWithInvalidTypeValue(final String pickupLat, final String pickupLon,
                                           final String dropoffLat, final String dropoffLon,
                                           final String capacity) throws Exception
    {
        MultiValueMap<String, String> params = createParamsMap(pickupLat, pickupLon, dropoffLat, dropoffLon, capacity);
        mockMvc.perform(get("/").params(params).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @MethodSource("provideGetParametersWithInvalidValue")
    void testAggregateWithInvalidValue(final String pickupLat, final String pickupLon,
                                       final String dropoffLat, final String dropoffLon,
                                       final String capacity) throws Exception
    {
        MultiValueMap<String, String> params = createParamsMap(pickupLat, pickupLon, dropoffLat, dropoffLon, capacity);
        mockMvc.perform(get("/").params(params).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testAggregateSuccess() throws Exception
    {
        MultiValueMap<String, String> params = createParamsMap(VALID, VALID, VALID, VALID, "1");
        List<ServiceOption> serviceOptions = createDummyServiceOptions();
        String json = convertOptionsToJson(serviceOptions);
        doReturn(serviceOptions).when(aggregator).aggregate(notNull());
        mockMvc.perform(get("/").params(params).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }
}
