package functional.pingTestCases;

import core.entites.TestDataDto;
import core.factories.TestDataFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class NS02_GoogleDnsTest extends BasePingTests {

    private TestDataDto testData;

    @BeforeClass
    public void setupTestConfig() {
        testData = TestDataFactory.createTestDataDto(this.getClass(), "8.8.8.8", "Google DNS");
    }

    @Test
    public void test() {
        runPingerApplication(testData);
    }
}