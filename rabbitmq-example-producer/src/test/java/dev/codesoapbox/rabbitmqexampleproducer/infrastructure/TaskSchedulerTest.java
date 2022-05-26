package dev.codesoapbox.rabbitmqexampleproducer.infrastructure;

import dev.codesoapbox.rabbitmqexampleproducer.config.RabbitMQConfiguration;
import dev.codesoapbox.rabbitmqexampleproducer.domain.model.Task;
import dev.codesoapbox.rabbitmqexampleproducer.domain.services.ProducerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

        ArgumentCaptor<MessagePostProcessor> messagePostProcessorCaptor = ArgumentCaptor.forClass(
                MessagePostProcessor.class);
        verify(rabbitTemplate).convertAndSend(eq(RabbitMQConfiguration.WORK_INBOUND_EXCHANGE), eq(""), eq(task),
                messagePostProcessorCaptor.capture());

        var processedMessage = messagePostProcessorCaptor.getValue().postProcessMessage(
                new Message(new byte[]{}, new MessageProperties()));
        assertEquals("10000", processedMessage.getMessageProperties().getExpiration());
        assertNotNull(processedMessage.getMessageProperties().getTimestamp());
    }
}