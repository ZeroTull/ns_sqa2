package generalTestCases;

import org.testng.Assert;
import org.testng.annotations.Test;

public class NS01_G_VerifyApplicationLaunchesCorrectly extends PingerAppGeneralTests {

    @Test
    public void verifyApplicationLaunchesCorrectly() {
        String output = runPingerCommand();
        Assert.assertNotNull(output, "Application did not launch correctly.");
        Assert.assertTrue(output.contains("Usage: [path_to_json_file] [path_to_result_file]"), "Unexpected application launch output.");
    }
}
