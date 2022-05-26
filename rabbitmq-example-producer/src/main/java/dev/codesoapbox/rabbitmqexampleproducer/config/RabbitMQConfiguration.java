package dev.codesoapbox.rabbitmqexampleproducer.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    public static final String WORK_INBOUND_EXCHANGE = "work-inbound";
    public static final String WORK_OUTBOUND_EXCHANGE = "work-outbound";
    public static final String CERTIFIED_RESULT_EXCHANGE = "certified-result";
    public static final String WORK_OUTBOUND_WORKER_QUEUE = "work-outbound-worker-queue";
    public static final String WORK_DISCARDED_EXCHANGE = "work-discarded";

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    FanoutExchange workInboundExchange() {
        return new FanoutExchange(WORK_INBOUND_EXCHANGE);
    }

    @Bean
    FanoutExchange workOutboundExchange() {
        return new FanoutExchange(WORK_OUTBOUND_EXCHANGE);
    }

    @Bean
    FanoutExchange certifiedResultExchange() {
        return new FanoutExchange(CERTIFIED_RESULT_EXCHANGE);
    }

    @Bean
    FanoutExchange workDiscardedExchange() {
        return new FanoutExchange(WORK_DISCARDED_EXCHANGE);
    }

    @Bean
    Queue workOutboundWorkerQueue() {
        return QueueBuilder.nonDurable(WORK_OUTBOUND_WORKER_QUEUE)
                .deadLetterExchange(WORK_DISCARDED_EXCHANGE)
                .build();
    }

    @Bean
    Binding binding(Queue workOutboundWorkerQueue, FanoutExchange workOutboundExchange) {
        return BindingBuilder.bind(workOutboundWorkerQueue)
                .to(workOutboundExchange);
    }
}
