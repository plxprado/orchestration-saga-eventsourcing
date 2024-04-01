package com.prado.tools.toolkitdev.eventsourcing.domain.vo.dynamic.objects;

import java.time.LocalDateTime;

public class EventContent extends EventFather {

    private String content;
    private String type;

    public EventContent(LocalDateTime eventDate, String content, String type) {
        super(eventDate);
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }
    public String getType() {
        return type;
    }

}
