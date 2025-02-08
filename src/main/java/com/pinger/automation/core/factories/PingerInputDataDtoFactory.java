package com.pinger.automation.core.factories;

import com.pinger.automation.core.model.entites.dto.EndpointDto;
import com.pinger.automation.core.model.entites.dto.input.InputDataDto;
import com.pinger.automation.core.model.enums.Endpoints;

import java.util.Collections;

public class PingerInputDataDtoFactory {
    public InputDataDto getPingDataDto() {
        return getPingDataDto(Endpoints.CLOUDFLARE, 1, 1);
    }

    public InputDataDto getPingDataDto(Endpoints endpoint) {
        return getPingDataDto(endpoint, 1, 1);
    }

    public InputDataDto getPingDataDto(Endpoints endpoint, int minSuccess, int maxPings) {
//        EndpointDto endpointDto = new EndpointDto(endpoint.getAddress(), endpoint.getDescription(), endpoint.isIgnore());
//        InputDataDto config = new InputDataDto();
//
//        config.setMinSuccessfulPings(minSuccess);
//        config.setMaxPings(maxPings);
//        config.setEndpoints(Collections.singletonList(endpointDto));
        return getPingDataDto(endpoint, minSuccess, maxPings, endpoint.isIgnore());
    }

    public InputDataDto getPingDataDto(Endpoints endpoint, int minSuccess, int maxPings, boolean ignore) {
        EndpointDto endpointDto = new EndpointDto(endpoint.getAddress(), endpoint.getDescription(), ignore);
        InputDataDto config = new InputDataDto();

        config.setMinSuccessfulPings(minSuccess);
        config.setMaxPings(maxPings);
        config.setEndpoints(Collections.singletonList(endpointDto));
        return config;
    }
}
