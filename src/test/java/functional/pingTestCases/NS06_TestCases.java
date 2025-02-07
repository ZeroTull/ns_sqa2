package functional.pingTestCases;

import com.pinger.automation.core.model.entites.dto.EndpointDto;
import com.pinger.automation.core.model.entites.dto.OutputDataDto;
import com.pinger.automation.core.model.enums.Endpoints;
import org.testng.annotations.Test;

import java.util.List;

public class NS06_TestCases extends BasePingTests {
    @Test
    public void ignoredEndpointTest() {
        dto = pingerInputDataFactory.getPingDataDto(Endpoints.GOOGLE_DNS);
//        executeTest(dto);
    }

    @Test
    public void minSuccessfulPingsEdgeCaseTest() {
        dto = pingerInputDataFactory.getPingDataDto(Endpoints.GOOGLE_DNS_EDGE, 5, 3);
//        executeTest(dto);
    }

    @Test
    public void malformedConfigTest() {
        // This will intentionally create a malformed config by not setting required fields
        OutputDataDto config = new OutputDataDto();
//        executeTest(config);
    }

    @Test
    public void multipleConcurrentPingsTest() {
        EndpointDto endpoint1 = new EndpointDto(Endpoints.GOOGLE_DNS);
        EndpointDto endpoint2 = new EndpointDto(Endpoints.CLOUDFLARE);
        EndpointDto endpoint3 = new EndpointDto("123.123.123.123", "Unreachable", false);

        dto = new OutputDataDto();
        dto.setMinSuccessfulPings(1);
        dto.setMaxPings(2);
        dto.setEndpoints(List.of(endpoint1, endpoint2, endpoint3));

//        executeTest(dto);
    }
}
