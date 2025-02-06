package core.entites;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Endpoint {
    private String addr;
    private String description;
    private boolean ignore;
}
