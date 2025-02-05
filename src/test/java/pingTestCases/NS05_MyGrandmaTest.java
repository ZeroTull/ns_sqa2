package pingTestCases;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class NS05_MyGrandmaTest extends BasePingTests {

    @BeforeClass
    public void setupTestConfig() {
        testConfig = pingDataDtoDecorator.getPingDataDto("192.168.0.101", "My Grandma");
    }

    @Test
    public void testGrandmaPing() {
        runPingTest(testConfig);
    }
}