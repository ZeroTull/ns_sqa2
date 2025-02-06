package functional.pingTestCases;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class NS01_CloudFlareTest extends BasePingTests {

    @BeforeClass
    public void setupTestConfig() {
        testConfig = pingDataDtoFactory.createPingDataDto("1.1.1.1", "Cloudflare DNS");
    }

    @Test
    public void testCloudflarePing() {
        runPingTest(testConfig);
    }
}