package core.entites;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class TestDataDto {
    private String testDataPath;
    private PingDataDto testDataDto;
    private String resultDataPath;
    private PingResultsDto resultsDataDto;
}
