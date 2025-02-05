package generalTestCases;

import core.utils.JsonUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import pingTestCases.BasePingTests;

import java.io.*;

public class PingerAppGeneralTests extends BasePingTests {
    protected static String RESULTS_FILE_PATH = "src/main/resources/GeneralTestResults.json";

    protected String  createTestConfigFile() {
        String fileName = this.getClass().getSimpleName() + "TestData.json";
        String filePath = "src/main/resources/" + fileName;

        testConfig = pingDataDtoDecorator.getPingDataDto();
        JsonUtils.saveDtoToJsonFile(testConfig, filePath);

        return fileName;
    }
}

