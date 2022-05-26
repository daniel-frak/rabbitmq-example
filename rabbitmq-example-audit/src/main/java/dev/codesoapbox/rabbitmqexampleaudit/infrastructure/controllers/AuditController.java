package dev.codesoapbox.rabbitmqexampleaudit.infrastructure.controllers;

import dev.codesoapbox.rabbitmqexampleaudit.domain.EventCounterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuditController {

    private final EventCounterService eventCounterService;

    @GetMapping
    public AuditResponse getAuditInfo() {
        return new AuditResponse(
                eventCounterService.getProducedTasks(),
                eventCounterService.getProcessedTasks(),
                eventCounterService.getCertifiedTasks(),
                eventCounterService.getDiscardedTasks()
        );
    }
}
