package functional.pingTestCases;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class NS03_MySoWanTest extends BasePingTests {

    @BeforeClass
    public void setupTestConfig() {
        testConfig = pingDataDtoFactory.createPingDataDto("123.123.123.123", "My S/O WAN");
    }

    @Test
    public void testSOWANPing() {
        executeTest(testConfig);
    }
}