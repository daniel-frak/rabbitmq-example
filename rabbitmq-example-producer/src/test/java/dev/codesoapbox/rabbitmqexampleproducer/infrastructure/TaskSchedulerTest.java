package dev.codesoapbox.rabbitmqexampleproducer.infrastructure;

import dev.codesoapbox.rabbitmqexampleproducer.config.RabbitMQConfiguration;
import dev.codesoapbox.rabbitmqexampleproducer.domain.model.Task;
import dev.codesoapbox.rabbitmqexampleproducer.domain.services.ProducerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskSchedulerTest {

    @InjectMocks
    private TaskScheduler taskScheduler;

    @Mock
    private ProducerService producerService;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Test
    void shouldNotScheduleNewTaskIfDisabled() {
        taskScheduler.setEnabled(false);

        taskScheduler.scheduleNewTask();

        verifyNoInteractions(producerService, rabbitTemplate);
    }

    @Test
    void shouldScheduleNewTaskIfEnabled() {
        taskScheduler.setEnabled(true);
        Task task = new Task();

        when(producerService.produceTask())
                .thenReturn(task);

        taskScheduler.scheduleNewTask();

        verify(rabbitTemplate).convertAndSend(RabbitMQConfiguration.WORK_INBOUND_EXCHANGE, "", task);
    }
}