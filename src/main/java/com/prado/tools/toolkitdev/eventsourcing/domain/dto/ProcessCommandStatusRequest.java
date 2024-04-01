package com.prado.tools.toolkitdev.eventsourcing.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.ProcessCommandStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcessCommandStatusRequest {



    @JsonProperty("name")
    private String name;

    public ProcessCommandStatusEnum toVo() {
        return ProcessCommandStatusEnum.valueOf(name);

    }
}
