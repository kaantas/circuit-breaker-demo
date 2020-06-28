package com.trendyol.hystrixdemo;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class DemoService {

    private final RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "demoFallback", commandKey = "demo")
    public String getDemo() {
        URI uri = URI.create("http://localhost:8080/demo");
        return restTemplate.getForObject(uri, String.class);
    }

    public String demoFallback() {
        return "FALLBACK";
    }
}
