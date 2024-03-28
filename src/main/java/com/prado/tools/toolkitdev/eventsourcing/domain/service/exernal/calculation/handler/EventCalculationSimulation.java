package com.prado.tools.toolkitdev.eventsourcing.domain.service.exernal.calculation.handler;

import com.prado.tools.toolkitdev.eventsourcing.domain.service.exernal.calculation.EventCalculationDTO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class EventCalculationSimulation extends ApplicationEvent {

    private EventCalculationDTO eventCalculationDTO;
    public EventCalculationSimulation(EventCalculationDTO eventCalculationDTO) {
        super(eventCalculationDTO);
        this.eventCalculationDTO = eventCalculationDTO;
    }


}
