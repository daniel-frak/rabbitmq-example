package dev.codesoapbox.rabbitmqexampleworker.domain.services;

import dev.codesoapbox.rabbitmqexampleworker.domain.model.Task;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class WorkerService {

    private final long processingTimeMs;
    private final int failEveryNthTask;

    private int tasksProcessed = 0;

    @SneakyThrows
    public synchronized Task performTask(Task task) {
        log.info("Performing task: {}", task);
        tasksProcessed++;
        if(failEveryNthTask > 0 && tasksProcessed % failEveryNthTask == 0) {
            throw new RuntimeException("Failed task: " + task);
        }
        Thread.sleep(processingTimeMs);
        task.setName(task.getName() + "-processed");
        log.info("Finished task: {}", task);
        return task;
    }
}
