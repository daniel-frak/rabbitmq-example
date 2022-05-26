package dev.codesoapbox.rabbitmqexampleproducer.config;

import dev.codesoapbox.rabbitmqexampleproducer.domain.services.CertificationService;
import dev.codesoapbox.rabbitmqexampleproducer.domain.services.ProducerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    ProducerService producerService() {
        return new ProducerService();
    }

    @Bean
    CertificationService certificationService() {
        return new CertificationService();
    }
}
