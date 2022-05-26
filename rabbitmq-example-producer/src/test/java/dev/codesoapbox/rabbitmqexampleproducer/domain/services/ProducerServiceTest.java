package dev.codesoapbox.rabbitmqexampleproducer.domain.services;

import dev.codesoapbox.rabbitmqexampleproducer.domain.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProducerServiceTest {

    private ProducerService producerService;

    @BeforeEach
    void setUp() {
        producerService = new ProducerService();
    }

    @Test
    void ShouldProduceTask() {
        Task result = producerService.produceTask();

        assertNotNull(result);
    }
}