package com.prado.tools.toolkitdev.eventsourcing.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ErrorMessage(@JsonProperty("message") String message) {
}
