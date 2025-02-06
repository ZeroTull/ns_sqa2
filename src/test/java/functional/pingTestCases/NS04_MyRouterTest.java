package functional.pingTestCases;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class NS04_MyRouterTest extends BasePingTests {

    @BeforeClass
    public void setupTestConfig() {
        testConfig = pingDataDtoFactory.createPingDataDto("192.168.0.1", "My Router");
    }

    @Test
    public void testRouterPing() {
        executeTest(testConfig);
    }
}