package functional;

import com.pinger.automation.core.factories.TestDataDtoFactory;
import com.pinger.automation.core.helpers.executable.PingerExecutableHelper;
import com.pinger.automation.core.model.entites.dto.EndpointDto;
import com.pinger.automation.core.model.entites.dto.TestDataDto;
import com.pinger.automation.core.model.entites.dto.config.ConfigDto;
import com.pinger.automation.core.model.enums.Endpoint;
import io.qameta.allure.Description;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class TC015_VerifyElevenEndpointsProcessingTest extends BasePingTests {
    private TestDataDto testData;

    @BeforeClass
    public void setupTestConfig() {
        ConfigDto configDto = new ConfigDto();

        List<EndpointDto> list = Arrays.stream(Endpoint.values())
                .map(EndpointDto::new)
                .toList();
        if (list.size() > 11) {
            list.subList(0, 11);
        }

        configDto.setMaxPings(3).setMinSuccessfulPings(2).setEndpoints(list);
        testData = TestDataDtoFactory.createTestDataDto(this.getClass(), configDto);
    }

    @Test()
    @Description("Execution of config file that has more than 10 endpoints.")
    public void test() {
        PingerExecutableHelper.getPingerClient(testData).processValidScenario();
        cleanUpGeneratedFiles(testData);
    }
}