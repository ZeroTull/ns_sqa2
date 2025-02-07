package com.pinger.automation.core.factories;

import com.pinger.automation.core.model.entites.dto.EndpointDto;
import com.pinger.automation.core.model.entites.dto.OutputDataDto;
import com.pinger.automation.core.model.enums.Endpoints;

import java.util.Collections;

public class PingerInputDataFactory {
    public OutputDataDto getPingDataDto() {
        return getPingDataDto(Endpoints.CLOUDFLARE, 1, 1);
    }

    public OutputDataDto getPingDataDto(Endpoints endpoint) {
        return getPingDataDto(endpoint, 1, 1);
    }

    public OutputDataDto getPingDataDto(Endpoints endpoint, int minSuccess, int maxPings) {
        EndpointDto endpointDto = new EndpointDto(endpoint.getAddress(), endpoint.getDescription(), endpoint.isIgnore());
        OutputDataDto config = new OutputDataDto();
        config.setMinSuccessfulPings(minSuccess);
        config.setMaxPings(maxPings);
        config.setEndpoints(Collections.singletonList(endpointDto));
        return config;
    }
}
