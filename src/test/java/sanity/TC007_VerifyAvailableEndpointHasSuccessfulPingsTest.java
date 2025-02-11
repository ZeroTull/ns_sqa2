package sanity;

import com.pinger.automation.core.annotations.Defect;
import com.pinger.automation.core.factories.TestDataDtoFactory;
import com.pinger.automation.core.helpers.executable.PingerExecutableHelper;
import com.pinger.automation.core.model.entites.dto.report.EntryDto;
import com.pinger.automation.core.model.entites.dto.report.ReportDto;
import com.pinger.automation.core.model.enums.Endpoint;
import functional.BasePingTests;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class TC007_VerifyAvailableEndpointHasSuccessfulPingsTest extends BasePingTests {
    @BeforeClass
    public void beforeClass() {
        testData = TestDataDtoFactory.createTestDataDto(this.getClass(), Endpoint.GOOGLE_DNS);
        PingerExecutableHelper.getPingerClient(testData).execute();
    }

    @Test()
    @Defect(ids = {"DF_005"})
    @Description("Report JSON file matches expected schema.")
    public void test() {
        // Perform system-level ping check
        String address = testData.getConfig().getDto().getEndpoints().getFirst().getAddress();
        boolean systemPingSuccess = isSystemPingSuccessful(address);

        ReportDto report = PingerExecutableHelper.getPingerClient(testData).processValidScenario();
        EntryDto processedEndpoint = report.getEntries().stream().filter(x -> x.getEndpoint().getAddress().equals(address)).findFirst().orElseThrow();

        if (systemPingSuccess && processedEndpoint.getSuccessfulPings() == 0) {
            Assert.fail("Pinger incorrectly marked an available endpoint as unreachable!");
        }

        if (!systemPingSuccess && processedEndpoint.getSuccessfulPings() > 0) {
            Assert.fail("Pinger incorrectly marked an unreachable endpoint as available!");
        }
    }

    /**
     * Performs a system-level ping to verify endpoint availability.
     */
    private boolean isSystemPingSuccessful(String ipAddress) {
        try {
            ProcessBuilder processBuilder;
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                // Windows ping (4 packets)
                processBuilder = new ProcessBuilder("cmd.exe", "/c", "ping -n 4 " + ipAddress);
            } else {
                // Linux/Mac ping (4 packets)
                processBuilder = new ProcessBuilder("ping", "-c", "4", ipAddress);
            }

            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            return exitCode == 0;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }
}
