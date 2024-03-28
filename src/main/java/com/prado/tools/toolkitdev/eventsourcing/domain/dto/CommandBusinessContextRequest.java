package com.prado.tools.toolkitdev.eventsourcing.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.prado.tools.toolkitdev.eventsourcing.domain.vo.CommandBusinessContext;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommandBusinessContextRequest {

    private Long id;

    @JsonProperty("command_name")
    private String commandName;

    public CommandBusinessContext toVo() {
        return new CommandBusinessContext(this.id, this.commandName);
    }
}
