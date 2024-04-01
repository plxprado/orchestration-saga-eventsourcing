package com.prado.tools.toolkitdev.eventsourcing.domain.vo.dynamic.objects;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CalculationContent extends EventFather{

    private BigDecimal value;

    public CalculationContent(final LocalDateTime eventDate, final BigDecimal value) {
        super(eventDate);
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }
}
