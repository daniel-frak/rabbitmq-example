package dev.codesoapbox.rabbitmqexampleproducer.domain.services;

import dev.codesoapbox.rabbitmqexampleproducer.domain.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CertificationServiceTest {

    private CertificationService certificationService;

    @BeforeEach
    void setUp() {
        certificationService = new CertificationService();
    }

    @Test
    void shouldPerformTaskWithDelay() {
        Task result = certificationService.certify(new Task());

        assertTrue(result.getName().endsWith("-certified"));
    }
}