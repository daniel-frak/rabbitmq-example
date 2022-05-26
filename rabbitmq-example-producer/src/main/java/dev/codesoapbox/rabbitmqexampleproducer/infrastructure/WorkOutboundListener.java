package dev.codesoapbox.rabbitmqexampleproducer.infrastructure;

import dev.codesoapbox.rabbitmqexampleproducer.config.RabbitMQConfiguration;
import dev.codesoapbox.rabbitmqexampleproducer.domain.model.Task;
import dev.codesoapbox.rabbitmqexampleproducer.domain.services.CertificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WorkOutboundListener {

    private final CertificationService certificationService;
    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = RabbitMQConfiguration.WORK_OUTBOUND_WORKER_QUEUE)
    public void onMessage(Task task) {
        Task certifiedTask = certificationService.certify(task);
        log.info("Sending certified task: {}", certifiedTask);
        rabbitTemplate.convertAndSend(RabbitMQConfiguration.CERTIFIED_RESULT_EXCHANGE, "", certifiedTask);
        log.info("Certified task sent: {}", certifiedTask);
    }
}
