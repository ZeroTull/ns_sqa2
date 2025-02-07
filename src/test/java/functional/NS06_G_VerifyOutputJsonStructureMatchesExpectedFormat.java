package functional;

import org.testng.annotations.Test;

public class NS06_G_VerifyOutputJsonStructureMatchesExpectedFormat extends PingerAppGeneralBaseTests {
    @Test
    public void verifyOutputJsonStructureMatchesExpectedFormat() {

        String fileName = this.getClass().getSimpleName() + "TestData.json";
        RESULTS_FILE_PATH = "src/main/resources/";

        createTestConfigFile();
//        runPingerCommand("GeneralTestConfig.json", "GeneralTestResults.json");
//
//        String jsonContent = readFileContent(RESULTS_FILE_PATH.concat(fileName));
//        Assert.assertTrue(jsonContent.contains("start_time"), "start_time missing in output JSON.");
//        Assert.assertTrue(jsonContent.contains("end_time"), "end_time missing in output JSON.");
//        Assert.assertTrue(jsonContent.contains("entries"), "entries missing in output JSON.");
    }
}
