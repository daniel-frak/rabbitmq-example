package dev.codesoapbox.rabbitmqexampleworker.config;

import dev.codesoapbox.rabbitmqexampleworker.domain.services.WorkerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    WorkerService workerService(@Value("${processing-time-ms}") Long processingTimeMs,
                                @Value("${fail-every-nth-task}") int failEveryNthTask) {
        return new WorkerService(processingTimeMs, failEveryNthTask);
    }
}
