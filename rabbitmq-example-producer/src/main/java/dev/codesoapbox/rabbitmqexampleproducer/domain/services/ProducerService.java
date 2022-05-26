package dev.codesoapbox.rabbitmqexampleproducer.domain.services;

import dev.codesoapbox.rabbitmqexampleproducer.domain.model.Task;

public class ProducerService {

    public Task produceTask() {
        return new Task();
    }
}
