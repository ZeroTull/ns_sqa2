package core.entites;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class EndpointDto {
    @JsonProperty("addr")
    private String address;

    @JsonProperty("description")
    private String description;

    @JsonProperty("ignore")
    private boolean ignore;

}

