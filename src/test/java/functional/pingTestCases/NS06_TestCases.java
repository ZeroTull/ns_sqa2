package functional.pingTestCases;

import core.entites.EndpointDto;
import core.entites.PingDataDto;
import org.testng.annotations.Test;

import java.util.List;

public class NS06_TestCases extends BasePingTests {
    @Test
    public void ignoredEndpointTest() {
        dto = pingDataDtoFactory.createPingDataDto("8.8.8.8", "Google DNS Ignored", true, 1, 1);
        executeTest(dto);
    }

    @Test
    public void minSuccessfulPingsEdgeCaseTest() {
        dto = pingDataDtoFactory.createPingDataDto("8.8.8.8", "Google DNS Edge", false, 5, 3);
        executeTest(dto);
    }

    @Test
    public void malformedConfigTest() {
        // This will intentionally create a malformed config by not setting required fields
        PingDataDto config = new PingDataDto();
        executeTest(config);
    }

    @Test
    public void multipleConcurrentPingsTest() {
        EndpointDto endpoint1 = new EndpointDto("8.8.8.8", "Google DNS", false);
        EndpointDto endpoint2 = new EndpointDto("1.1.1.1", "Cloudflare DNS", false);
        EndpointDto endpoint3 = new EndpointDto("123.123.123.123", "Unreachable", false);

        dto = new PingDataDto();
        dto.setMinSuccessfulPings(1);
        dto.setMaxPings(2);
        dto.setEndpoints(List.of(endpoint1, endpoint2, endpoint3));

        executeTest(dto);
    }
}
