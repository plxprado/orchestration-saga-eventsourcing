package com.prado.tools.toolkitdev.eventsourcing.domain.vo.dynamic;

import com.prado.tools.toolkitdev.eventsourcing.domain.vo.dynamic.objects.EventContent;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.dynamic.objects.EventFather;

import java.time.LocalDateTime;

public class MainTest {
    public static void main(String[] args) {

        EventContent eventContent = new EventContent(LocalDateTime.now(), "{json}", "TARIFAR");
        System.out.println("DATA EVENTO:: " + eventContent.getEventDate());
        System.out.println("CONTENT:: " + eventContent.getContent());
        System.out.println("TYPE:: " + eventContent.getType());
        printEventFather(eventContent);

    }

    public static void printEventFather(EventFather eventFather){
        System.out.println("DATA EVENTO:: " + eventFather);
    }
}
