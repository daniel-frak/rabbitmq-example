package dev.codesoapbox.rabbitmqexampleworker.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    public static final String WORK_INBOUND_EXCHANGE = "work-inbound";
    public static final String WORK_INBOUND_WORKER_QUEUE = "work-inbound-worker-queue";
    public static final String WORK_OUTBOUND_EXCHANGE = "work-outbound";
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
    FanoutExchange workDiscardedExchange() {
        return new FanoutExchange(WORK_DISCARDED_EXCHANGE);
    }

    @Bean
    Queue workInboundWorkerQueue() {
        return QueueBuilder.nonDurable(WORK_INBOUND_WORKER_QUEUE)
                .ttl(10000)
                .deadLetterExchange(WORK_DISCARDED_EXCHANGE)
                .build();
    }

    @Bean
    Binding binding(Queue workInboundWorkerQueue, FanoutExchange workInboundExchange) {
        return BindingBuilder.bind(workInboundWorkerQueue)
                .to(workInboundExchange);
    }
}
