package dev.codesoapbox.rabbitmqexampleworker.infrastructure;

import dev.codesoapbox.rabbitmqexampleworker.config.RabbitMQConfiguration;
import dev.codesoapbox.rabbitmqexampleworker.domain.model.Task;
import dev.codesoapbox.rabbitmqexampleworker.domain.services.WorkerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
public class WorkInboundListener {

    private final WorkerService workerService;
    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = RabbitMQConfiguration.WORK_INBOUND_WORKER_QUEUE)
    public void onMessage(Task task, Message message) {
        Task performedTask = workerService.performTask(task);
        String expiration = calculateNewExpiration(message);
        if (expiration == null) {
            log.info("Discarding task: {}", performedTask);
            rabbitTemplate.convertAndSend(RabbitMQConfiguration.WORK_DISCARDED_EXCHANGE, "", performedTask);
            return;
        }
        log.info("Sending processed task: {}", performedTask);
        rabbitTemplate.convertAndSend(RabbitMQConfiguration.WORK_OUTBOUND_EXCHANGE, "", performedTask,
                newMessage -> {
                    newMessage.getMessageProperties().setTimestamp(message.getMessageProperties().getTimestamp());
                    newMessage.getMessageProperties().setExpiration(expiration);
                    return newMessage;
                });
        log.info("Processed task sent: {}", performedTask);
    }

    private String calculateNewExpiration(Message message) {
        long timestampMs = message.getMessageProperties().getTimestamp().toInstant().toEpochMilli();
        long elapsedTimeMs = Instant.now().toEpochMilli() - timestampMs;
        long newExpiration = Long.parseLong(message.getMessageProperties().getExpiration()) - elapsedTimeMs;
        return newExpiration > 0
                ? String.valueOf(newExpiration)
                : null;
    }
}
