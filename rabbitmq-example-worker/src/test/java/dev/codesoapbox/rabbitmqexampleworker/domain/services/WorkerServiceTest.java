package dev.codesoapbox.rabbitmqexampleworker.domain.services;

import dev.codesoapbox.rabbitmqexampleworker.domain.model.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkerServiceTest {

    @Test
    void shouldPerformTaskWithDelay() {
        long processingTimeMillis = 500;
        var workerService = new WorkerService(processingTimeMillis, 0);
        long startTime = System.currentTimeMillis();

        Task result = workerService.performTask(new Task());

        long endTime = System.currentTimeMillis();

        var elapsedTime = endTime - startTime;

        assertTrue(elapsedTime >= processingTimeMillis);
        assertTrue(result.getName().endsWith("-processed"));
    }

    @Test
    void shouldFailEveryNthTask() {
        var workerService = new WorkerService(0, 3);

        Task task = new Task();
        assertDoesNotThrow(() -> workerService.performTask(task));
        assertDoesNotThrow(() -> workerService.performTask(task));
        assertThrows(RuntimeException.class, () -> workerService.performTask(task));
        assertDoesNotThrow(() -> workerService.performTask(task));
        assertDoesNotThrow(() -> workerService.performTask(task));
        assertThrows(RuntimeException.class, () -> workerService.performTask(task));
    }
}