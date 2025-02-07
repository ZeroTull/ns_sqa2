package functional.pingTestCases;

import core.entites.TestDataDto;
import core.factories.TestDataFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class NS03_MySoWanTest extends BasePingTests {

    private TestDataDto testData;

    @BeforeClass
    public void setupTestConfig() {
        testData = TestDataFactory.createTestDataDto(this.getClass(), "123.123.123.123", "My S/O WAN");
    }

    @Test
    public void test() {
        runPingerApplication(testData);
    }
}