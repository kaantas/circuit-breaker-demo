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
        String result = "";
        for (int i = 0; i < 30; i++) {
            result = result + service.getDemo() + "\n";
        }
        return result;
    }

    @GetMapping("/custom")
    public String getCustomDemo() {
        return service.getDemoWithThreshold();
    }

    @GetMapping("/one")
    public String get(){
        return service.getDemo();
    }

    @GetMapping("/success")
    public String success(){
        return service.success();
    }

    @GetMapping("/exception")
    public String getException(){
        String result = "";
        try {
            result = service.getDemoException();
        } catch (RuntimeException e) {
            result = "NO FALLBACK " + e.getMessage();
        }
        return result;
    }

    @GetMapping("/ignore-exception")
    public String getIgnoreException(){
        String result = "";
        try {
            result = service.getDemoIgnoreException();
        } catch (NullPointerException e) {
            result = "NO FALLBACK " + e.getMessage();
        }
        return result;
    }
}
