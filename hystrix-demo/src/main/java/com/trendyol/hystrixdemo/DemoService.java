package com.trendyol.hystrixdemo;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class DemoService {

    private final RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "demoFallback", commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="500")
    })
    public String getDemo() {
        URI uri = URI.create("http://localhost:8080/demo");
        return restTemplate.getForObject(uri, String.class);
    }

    public String demoFallback() {
        return "FALLBACK";
    }

    @HystrixCommand(fallbackMethod = "demoFallbackThrowException")
    public String getDemoException() {
        throw new RuntimeException("bla bla");
    }

    public String demoFallbackThrowException(Throwable throwable) {
        return "FALLBACK " + throwable.getMessage();
    }

    @HystrixCommand(fallbackMethod = "successFallback", commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="5000")
    })
    public String success() {
        URI uri = URI.create("http://localhost:8080/demo");
        return restTemplate.getForObject(uri, String.class);
    }

    public String successFallback(Throwable throwable) {
        return "FALLBACK " + throwable.getMessage();
    }

    @HystrixCommand(fallbackMethod = "getDemoIgnoreExceptionFallback", ignoreExceptions = NullPointerException.class)
    public String getDemoIgnoreException() {
        throw new NullPointerException("bla bla");
        //throw new IndexOutOfBoundsException("bla bla");
    }

    public String getDemoIgnoreExceptionFallback(Throwable throwable) {
        return "FALLBACK " + throwable.getMessage();
    }

    // 2 sn içerisinde en az 3 request gelirse
    @HystrixCommand(fallbackMethod = "demoFallback", commandProperties = {
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="4"),
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="50"),
            @HystrixProperty(name="metrics.rollingStats.timeInMilliseconds", value="10000"),
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="50")
    })
    public String getDemoWithThreshold() {
        URI uri = URI.create("http://localhost:8080/demo");
        return restTemplate.getForObject(uri, String.class);
    }
}
