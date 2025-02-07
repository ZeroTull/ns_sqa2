package functional.pingTestCases;

import core.entites.TestDataDto;
import core.factories.TestDataFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class NS05_MyGrandmaTest extends BasePingTests {
    private TestDataDto testData;

    @BeforeClass
    public void setupTestConfig() {
        testData = TestDataFactory.createTestDataDto(this.getClass(), "192.168.0.101", "My Grandma");
    }

    @Test
    public void test() {
        runPingerApplication(testData);
    }
}