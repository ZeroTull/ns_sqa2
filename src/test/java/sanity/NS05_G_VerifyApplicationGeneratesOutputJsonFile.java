package sanity;

import functional.PingerAppGeneralBaseTests;
import org.testng.annotations.Test;

public class NS05_G_VerifyApplicationGeneratesOutputJsonFile extends PingerAppGeneralBaseTests {
    @Test
    public void verifyApplicationGeneratesOutputJsonFile() {
        RESULTS_FILE_PATH = "src/main/resources/";


//        createTestConfigFile();
//        runPingerCommand("GeneralTestConfig.json", "GeneralTestResults.json");
//
//        File resultsFile = new File(RESULTS_FILE_PATH);
//        Assert.assertTrue(resultsFile.exists(), "Output JSON file was not generated.");
    }
}
