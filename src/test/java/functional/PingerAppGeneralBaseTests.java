package functional;

import core.utils.JsonUtils;
import functional.pingTestCases.BasePingTests;

public class PingerAppGeneralBaseTests extends BasePingTests {
    protected static String RESULTS_FILE_PATH = "src/main/resources/GeneralTestResults.json";

    protected String  createTestConfigFile() {
        String fileName = this.getClass().getSimpleName() + "TestData.json";
        String filePath = "src/main/resources/" + fileName;

        dto = pingDataDtoFactory.createPingDataDto();
        JsonUtils.createFileFromDto(dto, filePath);

        return fileName;
    }
}

