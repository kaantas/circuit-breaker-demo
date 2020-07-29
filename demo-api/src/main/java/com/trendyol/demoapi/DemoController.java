package com.trendyol.demoapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping
    public String getDemo() throws InterruptedException {
        long startTime = System.nanoTime();
        Thread.sleep(1000);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        return "Demo Duration: " + duration + " ms";
    }
}
