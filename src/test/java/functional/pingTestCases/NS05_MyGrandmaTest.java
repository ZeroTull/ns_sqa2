package functional.pingTestCases;

import com.pinger.automation.core.factories.TestDataFactory;
import com.pinger.automation.core.helpers.BSL;
import com.pinger.automation.core.model.entites.dto.TestDataDto;
import com.pinger.automation.core.model.enums.Endpoints;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class NS05_MyGrandmaTest extends BasePingTests {
    private TestDataDto testData;

    @BeforeClass
    public void setupTestConfig() {
        testData = TestDataFactory.createTestDataDto(this.getClass(), Endpoints.MY_GRANDMA);
    }

    @Test
    public void test() {
        BSL.pingerExecutableHelper.executePinger(testData).processValidScenario();
        cleanUpGeneratedFiles(testData);
    }
}