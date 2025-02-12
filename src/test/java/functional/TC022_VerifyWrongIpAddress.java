package functional;

import com.pinger.automation.core.factories.TestDataDtoFactory;
import com.pinger.automation.core.helpers.executable.PingerExecutableHelper;
import com.pinger.automation.core.model.entites.dto.EndpointDto;
import com.pinger.automation.core.model.entites.dto.config.ConfigDto;
import com.pinger.automation.core.model.enums.Endpoint;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class TC022_VerifyWrongIpAddress extends BasePingTests {
    @BeforeClass
    public void beforeClass() {
        ConfigDto configDto = new ConfigDto();
        configDto.setMaxPings(3)
                .setMinSuccessfulPings(2)
                .setEndpoints(List.of(new EndpointDto(Endpoint.INVALID_IP)));
        testData = TestDataDtoFactory.createTestDataDto(this.getClass(), configDto);
    }

    @Test()
    @Description("Report JSON file matches expected schema.")
    public void test() {
        String execute = PingerExecutableHelper.getPingerClient(testData).execute();
        Assert.assertTrue(execute.contains("Failed to parse JSON: invalid IP address: 1.1.1"));
        cleanUpGeneratedFiles(testData);
    }
}
