package dev.codesoapbox.rabbitmqexampleaudit.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    public static final String WORK_INBOUND_EXCHANGE = "work-inbound";
    public static final String WORK_OUTBOUND_EXCHANGE = "work-outbound";
    public static final String CERTIFIED_RESULT_EXCHANGE = "certified-result";
    public static final String WORK_DISCARDED_EXCHANGE = "work-discarded";
    public static final String WORK_INBOUND_AUDIT_QUEUE = "work-inbound-audit-queue";
    public static final String WORK_OUTBOUND_AUDIT_QUEUE = "work-outbound-audit-queue";
    public static final String CERTIFIED_RESULT_AUDIT_QUEUE = "certified-result-audit-queue";
    public static final String WORK_DISCARDED_QUEUE = "work-discarded-queue";

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
    Queue workInboundAuditQueue() {
        return new Queue(WORK_INBOUND_AUDIT_QUEUE);
    }

    @Bean
    Queue workOutboundAuditQueue() {
        return new Queue(WORK_OUTBOUND_AUDIT_QUEUE);
    }

    @Bean
    Queue certifiedResultAuditQueue() {
        return new Queue(CERTIFIED_RESULT_AUDIT_QUEUE);
    }

    @Bean
    Queue workDiscardedAuditQueue() {
        return new Queue(WORK_DISCARDED_QUEUE);
    }

    @Bean
    Binding workInboundBinding(Queue workInboundAuditQueue, FanoutExchange workInboundExchange) {
        return BindingBuilder.bind(workInboundAuditQueue)
                .to(workInboundExchange);
    }

    @Bean
    Binding workOutboundBinding(Queue workOutboundAuditQueue, FanoutExchange workOutboundExchange) {
        return BindingBuilder.bind(workOutboundAuditQueue)
                .to(workOutboundExchange);
    }

    @Bean
    Binding certifiedResultBinding(Queue certifiedResultAuditQueue, FanoutExchange workOutboundExchange) {
        return BindingBuilder.bind(certifiedResultAuditQueue)
                .to(workOutboundExchange);
    }

    @Bean
    Binding workDiscardedBinding(Queue workDiscardedAuditQueue, FanoutExchange workDiscardedExchange) {
        return BindingBuilder.bind(workDiscardedAuditQueue)
                .to(workDiscardedExchange);
    }
}
