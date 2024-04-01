package com.prado.tools.toolkitdev.eventsourcing.domain.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProcessCommandStatus {

    private Long id;
    private ProcessCommandStatusEnum status;
}
