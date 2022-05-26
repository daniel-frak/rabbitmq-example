package dev.codesoapbox.rabbitmqexampleproducer.infrastructure;

import dev.codesoapbox.rabbitmqexampleproducer.config.RabbitMQConfiguration;
import dev.codesoapbox.rabbitmqexampleproducer.domain.model.Task;
import dev.codesoapbox.rabbitmqexampleproducer.domain.services.CertificationService;
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

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkOutboundListenerTest {

    @InjectMocks
    private WorkOutboundListener workOutboundListener;

    @Mock
    private CertificationService certificationService;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Test
    void onMessageShouldDiscardTaskIfStale() {
        Task task = new Task();
        Task modifiedTask = new Task();

        when(certificationService.certify(task))
                .thenReturn(modifiedTask);

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setTimestamp(new Date(0));
        messageProperties.setExpiration("10000");
        workOutboundListener.onMessage(task, new Message(new byte[]{}, messageProperties));

        verify(rabbitTemplate).convertAndSend(eq(RabbitMQConfiguration.WORK_DISCARDED_EXCHANGE), eq(""),
                same(modifiedTask));
        verify(rabbitTemplate, never()).convertAndSend(eq(RabbitMQConfiguration.CERTIFIED_RESULT_EXCHANGE), eq(""),
                same(modifiedTask), any(MessagePostProcessor.class));
    }

    @Test
    void onMessageShouldProcessTaskAndSendToOutboundExchange() {
        Task task = new Task();
        Task modifiedTask = new Task();

        when(certificationService.certify(task))
                .thenReturn(modifiedTask);

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setTimestamp(new Date());
        messageProperties.setExpiration("10000");
        workOutboundListener.onMessage(task, new Message(new byte[]{}, messageProperties));

        ArgumentCaptor<MessagePostProcessor> messagePostProcessorCaptor = ArgumentCaptor.forClass(
                MessagePostProcessor.class);
        verify(rabbitTemplate).convertAndSend(eq(RabbitMQConfiguration.CERTIFIED_RESULT_EXCHANGE), eq(""),
                same(modifiedTask), messagePostProcessorCaptor.capture());

        var processedMessage = messagePostProcessorCaptor.getValue().postProcessMessage(
                new Message(new byte[]{}, new MessageProperties()));
        assertEquals(messageProperties.getTimestamp(), processedMessage.getMessageProperties().getTimestamp());
        assertTrue(Integer.parseInt(processedMessage.getMessageProperties().getExpiration())
                <= Integer.parseInt(messageProperties.getExpiration()));
    }
}