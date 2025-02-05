package core.decorators;

import core.entites.EndpointDto;
import core.entites.PingDataDto;

import java.util.Collections;

public class PingDataDtoDecorator {
    public PingDataDto getPingDataDto() {
        return getPingDataDto("1.1.1.1", "Cloudflare DNS", false, 1, 1);
    }

    public PingDataDto getPingDataDto(String address, String description) {
        return getPingDataDto(address, description, false, 1, 1);
    }

    public PingDataDto getPingDataDto(String address, String description, boolean ignore, int minSuccess, int maxPings) {
        EndpointDto endpoint = new EndpointDto(address, description, ignore);
        PingDataDto config = new PingDataDto();
        config.setMinSuccessfulPings(minSuccess);
        config.setMaxPings(maxPings);
        config.setEndpoints(Collections.singletonList(endpoint));
        return config;
    }

}
