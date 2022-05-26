package dev.codesoapbox.rabbitmqexampleaudit.infrastructure.controllers;

public record AuditResponse(
        int producedTasks,
        int processedTasks,
        int certifiedTasks,
        int discardedTasks
) {
}
