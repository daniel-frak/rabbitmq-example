package dev.codesoapbox.rabbitmqexampleproducer.infrastructure.controllers;

import dev.codesoapbox.rabbitmqexampleproducer.infrastructure.TaskScheduler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ProducerController {

    private final TaskScheduler taskScheduler;

    @GetMapping("enable")
    public void enable() {
        taskScheduler.setEnabled(true);
    }

    @GetMapping("disable")
    public void disable() {
        taskScheduler.setEnabled(false);
    }

    @GetMapping("enable-timed")
    public void enable(@RequestParam(name = "time-ms") Long timeMs) throws InterruptedException {
        log.info("Enabling processing for " + timeMs);
        taskScheduler.setEnabled(true);
        Thread.sleep(timeMs);
        log.info("Disabling processing after " + timeMs);
        taskScheduler.setEnabled(false);
    }
}
