package com.pinger.automation.core.model.entites.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class EndpointReportDto {
    private EndpointDto endpoint;
    @JsonProperty("total_pings")
    private int totalPings;
    @JsonProperty("successful_pings")
    private int successfulPings;
}
