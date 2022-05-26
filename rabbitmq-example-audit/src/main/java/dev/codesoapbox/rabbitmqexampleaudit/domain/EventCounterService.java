package dev.codesoapbox.rabbitmqexampleaudit.domain;

import java.util.concurrent.atomic.AtomicInteger;

public class EventCounterService {

    private final AtomicInteger producedTasks = new AtomicInteger();
    private final AtomicInteger processedTasks = new AtomicInteger();
    private final AtomicInteger certifiedTasks = new AtomicInteger();
    private final AtomicInteger discardedTasks = new AtomicInteger();

    public void incrementProducedTasks() {
        producedTasks.incrementAndGet();
    }

    public void incrementProcessedTasks() {
        processedTasks.incrementAndGet();
    }

    public void incrementCertifiedTasks() {
        certifiedTasks.incrementAndGet();
    }

    public void incrementDiscardedTasks() {
        discardedTasks.incrementAndGet();
    }

    public int getProducedTasks() {
        return producedTasks.get();
    }

    public int getProcessedTasks() {
        return processedTasks.get();
    }

    public int getCertifiedTasks() {
        return certifiedTasks.get();
    }

    public int getDiscardedTasks() {
        return discardedTasks.get();
    }
}
