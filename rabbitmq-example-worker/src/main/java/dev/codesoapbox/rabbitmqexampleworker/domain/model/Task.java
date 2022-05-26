package dev.codesoapbox.rabbitmqexampleworker.domain.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Task {

    private String name = UUID.randomUUID().toString();
}