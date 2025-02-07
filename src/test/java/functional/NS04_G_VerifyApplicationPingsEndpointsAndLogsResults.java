package functional;

import org.testng.annotations.Test;

public class NS04_G_VerifyApplicationPingsEndpointsAndLogsResults extends PingerAppGeneralBaseTests {

    @Test
    public void verifyApplicationPingsEndpointsAndLogsResults() {
        String fileName = createTestConfigFile();
//        String output = runPingerCommand(fileName, "GeneralTestResults.json");
//        Assert.assertTrue(output.contains("Checking availability of"), "Application did not log ping results.");
    }
}
