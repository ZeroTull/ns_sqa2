package core.entites;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class EndpointReport {
    private Endpoint endpoint;
    @JsonProperty("total_pings")
    private int totalPings;
    @JsonProperty("successful_pings")
    private int successfulPings;
}
