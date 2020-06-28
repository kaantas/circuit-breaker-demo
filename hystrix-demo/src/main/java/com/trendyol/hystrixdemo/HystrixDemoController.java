package com.trendyol.hystrixdemo;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hystrix-demo")
@RequiredArgsConstructor
public class HystrixDemoController {
    private final DemoService service;

    @GetMapping
    public String getDemo() {
        return service.getDemo();
    }
}
