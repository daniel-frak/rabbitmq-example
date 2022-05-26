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

import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class WorkInboundListener {

    private final WorkerService workerService;
    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = RabbitMQConfiguration.WORK_INBOUND_WORKER_QUEUE)
    public void onMessage(Task task, Message message) {
        Date messageTimestamp = message.getMessageProperties().getTimestamp();
        var messageTtl = message.getMessageProperties().getExpiration();
        log.info("Message timestamp: " + messageTimestamp + ", ttl: " + messageTtl);
        Task performedTask = workerService.performTask(task);
        log.info("Sending processed task: {}", performedTask);
        rabbitTemplate.convertAndSend(RabbitMQConfiguration.WORK_OUTBOUND_EXCHANGE, "", performedTask);
        log.info("Processed task sent: {}", performedTask);
    }
}
