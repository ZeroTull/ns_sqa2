package functional.pingTestCases;

import core.entites.TestDataDto;
import core.factories.TestDataFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class NS01_CloudFlareTest extends BasePingTests {
    private TestDataDto testData;

    @BeforeClass
    public void setupTestConfig() {
        testData = TestDataFactory.createTestDataDto(this.getClass(), "1.1.1.1", "Cloudflare DNS");
    }

    @Test
    public void test() {
        runPingerApplication(testData);
    }
}