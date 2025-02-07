package core.factories;

import core.entites.EndpointDto;
import core.entites.PingDataDto;

import java.util.Collections;

public class PingDataDtoFactory {
    public PingDataDto createPingDataDto() {
        return createPingDataDto("1.1.1.1", "Cloudflare DNS", false, 1, 1);
    }

    public PingDataDto createPingDataDto(String address, String description) {
        return createPingDataDto(address, description, false, 1, 1);
    }

    public PingDataDto createPingDataDto(String address, String description, boolean ignore, int minSuccess, int maxPings) {
        EndpointDto endpoint = new EndpointDto(address, description, ignore);
        PingDataDto config = new PingDataDto();
        config.setMinSuccessfulPings(minSuccess);
        config.setMaxPings(maxPings);
        config.setEndpoints(Collections.singletonList(endpoint));
        return config;
    }
}
