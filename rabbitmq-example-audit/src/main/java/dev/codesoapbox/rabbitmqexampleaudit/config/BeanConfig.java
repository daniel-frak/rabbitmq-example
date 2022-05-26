package dev.codesoapbox.rabbitmqexampleaudit.config;

import dev.codesoapbox.rabbitmqexampleaudit.domain.EventCounterService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    EventCounterService eventCounterService() {
        return new EventCounterService();
    }
}
