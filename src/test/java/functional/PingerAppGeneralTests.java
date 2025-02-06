package functional;

import core.utils.JsonUtils;
import functional.pingTestCases.BasePingTests;

public class PingerAppGeneralTests extends BasePingTests {
    protected static String RESULTS_FILE_PATH = "src/main/resources/GeneralTestResults.json";

    protected String  createTestConfigFile() {
        String fileName = this.getClass().getSimpleName() + "TestData.json";
        String filePath = "src/main/resources/" + fileName;

        testConfig = pingDataDtoFactory.createPingDataDto();
        JsonUtils.saveDtoToJsonFile(testConfig, filePath);

        return fileName;
    }
}

