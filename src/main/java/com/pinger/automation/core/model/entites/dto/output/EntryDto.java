package com.pinger.automation.core.model.entites.dto.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pinger.automation.core.model.entites.dto.EndpointDto;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors
public class EntryDto {
    private EndpointDto endpoint;
    @JsonProperty("total_pings")
    private int totalPings;
    @JsonProperty("successful_pings")
    private int successfulPings;
}
