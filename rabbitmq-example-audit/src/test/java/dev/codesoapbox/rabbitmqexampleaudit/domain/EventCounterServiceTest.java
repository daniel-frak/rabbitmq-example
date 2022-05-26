package dev.codesoapbox.rabbitmqexampleaudit.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventCounterServiceTest {

    private EventCounterService eventCounterService;

    @BeforeEach
    void setUp() {
        eventCounterService = new EventCounterService();
    }

    @Test
    void shouldIncrementProducedTasks() {
        eventCounterService.incrementProducedTasks();

        assertEquals(1, eventCounterService.getProducedTasks());
    }

    @Test
    void shouldIncrementProcessedTasks() {
        eventCounterService.incrementProcessedTasks();

        assertEquals(1, eventCounterService.getProcessedTasks());
    }

    @Test
    void shouldIncrementCertifiedTasks() {
        eventCounterService.incrementCertifiedTasks();

        assertEquals(1, eventCounterService.getCertifiedTasks());
    }

    @Test
    void shouldIncrementDiscardedTasks() {
        eventCounterService.incrementDiscardedTasks();

        assertEquals(1, eventCounterService.getDiscardedTasks());
    }
}