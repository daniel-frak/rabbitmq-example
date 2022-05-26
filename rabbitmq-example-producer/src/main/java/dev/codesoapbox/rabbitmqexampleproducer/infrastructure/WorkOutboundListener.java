package dev.codesoapbox.rabbitmqexampleproducer.infrastructure;

import dev.codesoapbox.rabbitmqexampleproducer.config.RabbitMQConfiguration;
import dev.codesoapbox.rabbitmqexampleproducer.domain.model.Task;
import dev.codesoapbox.rabbitmqexampleproducer.domain.services.CertificationService;
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
public class WorkOutboundListener {

    private final CertificationService certificationService;
    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = RabbitMQConfiguration.WORK_OUTBOUND_WORKER_QUEUE)
    public void onMessage(Task task, Message message) {
        Task certifiedTask = certificationService.certify(task);
        String expiration = calculateNewExpiration(message);
        if(expiration == null) {
            log.info("Discarding task: {}", certifiedTask);
            rabbitTemplate.convertAndSend(RabbitMQConfiguration.WORK_DISCARDED_EXCHANGE, "", certifiedTask);
            return;
        }
        log.info("Sending certified task: {}", certifiedTask);
        rabbitTemplate.convertAndSend(RabbitMQConfiguration.CERTIFIED_RESULT_EXCHANGE, "", certifiedTask,
                newMessage -> {
                    newMessage.getMessageProperties().setTimestamp(message.getMessageProperties().getTimestamp());
                    newMessage.getMessageProperties().setExpiration(expiration);
                    return newMessage;
                });
        log.info("Certified task sent: {}", certifiedTask);
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
