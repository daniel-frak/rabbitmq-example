package dev.codesoapbox.rabbitmqexampleworker.infrastructure;

import dev.codesoapbox.rabbitmqexampleworker.config.RabbitMQConfiguration;
import dev.codesoapbox.rabbitmqexampleworker.domain.model.Task;
import dev.codesoapbox.rabbitmqexampleworker.domain.services.WorkerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkInboundListenerTest {

    @InjectMocks
    private WorkInboundListener workInboundListener;

    @Mock
    private WorkerService workerService;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Test
    void onMessageShouldProcessTaskAndSendToOutboundExchange() {
        Task task = new Task();
        Task modifiedTask = new Task();

        when(workerService.performTask(task))
                .thenReturn(modifiedTask);

        workInboundListener.onMessage(task, null);

        verify(rabbitTemplate).convertAndSend(eq(RabbitMQConfiguration.WORK_OUTBOUND_EXCHANGE), eq(""),
                same(modifiedTask));
    }
}