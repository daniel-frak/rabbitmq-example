package dev.codesoapbox.rabbitmqexampleaudit.infrastructure;

import dev.codesoapbox.rabbitmqexampleaudit.config.RabbitMQConfiguration;
import dev.codesoapbox.rabbitmqexampleaudit.domain.EventCounterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuditListener {

    private final EventCounterService eventCounterService;

    @RabbitListener(queues = RabbitMQConfiguration.WORK_INBOUND_AUDIT_QUEUE)
    public void onInboundMessage(Object payload) {
        log.info("Observing inbound work: {}", payload);
        eventCounterService.incrementProducedTasks();
    }

    @RabbitListener(queues = RabbitMQConfiguration.WORK_OUTBOUND_AUDIT_QUEUE)
    public void onOutboundMessage(Object payload) {
        log.info("Observing outbound work: {}", payload);
        eventCounterService.incrementProcessedTasks();
    }

    @RabbitListener(queues = RabbitMQConfiguration.CERTIFIED_RESULT_AUDIT_QUEUE)
    public void onCertifiedMessage(Object payload) {
        log.info("Observing certified work: {}", payload);
        eventCounterService.incrementCertifiedTasks();
    }

    @RabbitListener(queues = RabbitMQConfiguration.WORK_DISCARDED_QUEUE)
    public void onDiscardedMessage(Object payload) {
        log.info("Observing discarded work: {}", payload);
        eventCounterService.incrementDiscardedTasks();
    }
}
