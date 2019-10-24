package application;

import aggregator.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

@SpringBootApplication
public class RESTApplication
{
    public static void main(final String[] args) {
        SpringApplication.run(RESTApplication.class, args);
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ServiceAggregator serviceAggregator(final QueryConverter converter,
                                               final ServiceRequester requester,
                                               final ResultsSplitter splitter,
                                               final CapacityFilter filter,
                                               final SupplierResolver resolver,
                                               final OptionsSorter sorter)
    {
        return new ServiceAggregator(converter, requester, splitter, filter, resolver, sorter);
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ServiceAggregator serviceAggregator(ServiceConnectionFactory factory)
    {
        return new ServiceAggregator(
                new QueryConverter(),
                new ServiceRequester(factory),
                new ResultsSplitter(),
                new CapacityFilter(),
                new SupplierResolver(),
                new OptionsSorter());
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ServiceAggregator serviceAggregator()
    {
        return new ServiceAggregator(
                new QueryConverter(),
                new ServiceRequester(new ServiceConnectionFactory()),
                new ResultsSplitter(),
                new CapacityFilter(),
                new SupplierResolver(),
                new OptionsSorter());
    }
}
