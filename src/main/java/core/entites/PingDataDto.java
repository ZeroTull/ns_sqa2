package core.entites;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class PingDataDto {
    @JsonProperty("min_successful_pings")
    private int minSuccessfulPings;

    @JsonProperty("max_pings")
    private int maxPings;

    @JsonProperty("endpoints")
    private List<EndpointDto> endpoints;

}