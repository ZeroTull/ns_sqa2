package pingTestCases;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class NS02_GoogleDnsTest extends BasePingTests {

    @BeforeClass
    public void setupTestConfig() {
        testConfig = pingDataDtoDecorator.getPingDataDto("8.8.8.8", "Google DNS");
    }

    @Test
    public void testGooglePing() {
        runPingTest(testConfig);
    }
}