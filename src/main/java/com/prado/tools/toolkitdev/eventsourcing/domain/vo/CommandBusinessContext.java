package com.prado.tools.toolkitdev.eventsourcing.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommandBusinessContext {
    private Long id;
    private String name;
}
