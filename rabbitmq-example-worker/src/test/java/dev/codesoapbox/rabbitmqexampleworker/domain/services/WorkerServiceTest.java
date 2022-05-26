package dev.codesoapbox.rabbitmqexampleworker.domain.services;

import dev.codesoapbox.rabbitmqexampleworker.domain.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkerServiceTest {

    long processingTimeMillis = 500;

    private WorkerService workerService;

    @BeforeEach
    void setUp() {
        workerService = new WorkerService(processingTimeMillis);
    }

    @Test
    void shouldPerformTaskWithDelay() {
        long startTime = System.currentTimeMillis();

        Task result = workerService.performTask(new Task());

        long endTime = System.currentTimeMillis();

        var elapsedTime = endTime - startTime;

        assertTrue(elapsedTime >= processingTimeMillis);
        assertTrue(result.getName().endsWith("-processed"));
    }
}