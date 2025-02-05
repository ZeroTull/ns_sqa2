package pingTestCases;

import core.decorators.PingDataDtoDecorator;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class NS01_CloudFlareTest extends BasePingTests {

    @BeforeClass
    public void setupTestConfig() {
        testConfig = pingDataDtoDecorator.getPingDataDto("1.1.1.1", "Cloudflare DNS");
    }

    @Test
    public void testCloudflarePing() {
        runPingTest(testConfig);
    }
}