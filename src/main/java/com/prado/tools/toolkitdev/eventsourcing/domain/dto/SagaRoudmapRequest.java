package com.prado.tools.toolkitdev.eventsourcing.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.CommandBusinessContext;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.SagaRoudmap;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SagaRoudmapRequest {

    @JsonProperty("command_name")
    private String commmandName;

    public SagaRoudmap toVo() {
        return SagaRoudmap.builder()
                .commandBusinessContext(CommandBusinessContext.builder().name(this.commmandName).build())
                .creationDate(LocalDateTime.now())
                .build();
    }

}
