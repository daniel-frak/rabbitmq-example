package dev.codesoapbox.rabbitmqexampleworker.domain.services;

import dev.codesoapbox.rabbitmqexampleworker.domain.model.Task;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class WorkerService {

    private final long processingTimeMs;

    @SneakyThrows
    public Task performTask(Task task) {
        log.info("Performing task: {}", task);
        Thread.sleep(processingTimeMs);
        task.setName(task.getName() + "-processed");
        log.info("Finished task: {}", task);
        return task;
    }
}
