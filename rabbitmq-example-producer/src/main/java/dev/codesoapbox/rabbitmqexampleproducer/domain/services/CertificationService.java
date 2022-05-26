package dev.codesoapbox.rabbitmqexampleproducer.domain.services;

import dev.codesoapbox.rabbitmqexampleproducer.domain.model.Task;

public class CertificationService {

    public Task certify(Task task) {
        task.setName(task.getName() + "-certified");
        return task;
    }
}
