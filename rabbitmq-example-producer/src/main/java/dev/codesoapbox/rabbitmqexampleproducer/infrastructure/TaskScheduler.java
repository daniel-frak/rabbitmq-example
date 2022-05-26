package dev.codesoapbox.rabbitmqexampleproducer.infrastructure;

import dev.codesoapbox.rabbitmqexampleproducer.config.RabbitMQConfiguration;
import dev.codesoapbox.rabbitmqexampleproducer.domain.model.Task;
import dev.codesoapbox.rabbitmqexampleproducer.domain.services.ProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@RequiredArgsConstructor
@Component("myTaskScheduler")
public class TaskScheduler {

    private final ProducerService producerService;
    private final RabbitTemplate rabbitTemplate;

    private final AtomicBoolean enabled = new AtomicBoolean(true);

    public void setEnabled(boolean isEnabled) {
        this.enabled.set(isEnabled);
    }

    @Scheduled(fixedRateString = "${task-pace-ms}")
    public void scheduleNewTask() {
        if(!enabled.get()) {
            return;
        }

        Task task = producerService.produceTask();
        log.info("Scheduling task: {}", task);
        rabbitTemplate.convertAndSend(RabbitMQConfiguration.WORK_INBOUND_EXCHANGE, "", task);
        log.info("Task scheduled: {}", task);
    }
}
