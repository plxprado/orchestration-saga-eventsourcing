package com.prado.tools.toolkitdev.eventsourcing.domain.vo.dynamic.objects;

import lombok.ToString;

import java.time.LocalDateTime;

@ToString
public class EventFather {

    private LocalDateTime eventDate;

    public EventFather(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }
    public LocalDateTime getEventDate() {
        return eventDate;
    }
}
