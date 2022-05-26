package dev.codesoapbox.rabbitmqexampleproducer.domain.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Task {

    public String name = UUID.randomUUID().toString();
}
