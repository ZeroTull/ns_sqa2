package core.entites;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class PingResults {
    @JsonProperty("start_time")
    private String startTime;
    @JsonProperty("end_time")
    private String endTime;
    @JsonProperty("min_successful_pings")
    private int minSuccessfulPings;
    @JsonProperty("max_pings")
    private int maxPings;
    private List<EndpointReport> entries;
}
