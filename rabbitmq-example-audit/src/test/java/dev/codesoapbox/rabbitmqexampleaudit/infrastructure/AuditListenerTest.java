package dev.codesoapbox.rabbitmqexampleaudit.infrastructure;

import dev.codesoapbox.rabbitmqexampleaudit.domain.EventCounterService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuditListenerTest {

    @InjectMocks
    private AuditListener auditListener;

    @Mock
    private EventCounterService eventCounterService;

    @Test
    void shouldHandleInboundMessage() {
        auditListener.onInboundMessage(new Object());
        verify(eventCounterService).incrementProducedTasks();
    }

    @Test
    void shouldHandleOutboundMessage() {
        auditListener.onOutboundMessage(new Object());
        verify(eventCounterService).incrementProcessedTasks();
    }

    @Test
    void shouldHandleCertifiedMessage() {
        auditListener.onCertifiedMessage(new Object());
        verify(eventCounterService).incrementCertifiedTasks();
    }

    @Test
    void shouldHandleDiscardedMessage() {
        auditListener.onDiscardedMessage(new Object());
        verify(eventCounterService).incrementDiscardedTasks();
    }
}