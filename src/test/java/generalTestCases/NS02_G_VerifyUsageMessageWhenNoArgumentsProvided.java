package generalTestCases;

import org.testng.Assert;
import org.testng.annotations.Test;

public class NS02_G_VerifyUsageMessageWhenNoArgumentsProvided extends PingerAppGeneralTests{
    @Test
    public void verifyUsageMessageWhenNoArgumentsProvided() {
        String output = runPingerCommand();
        Assert.assertNotNull(output, "No output when no arguments provided.");
        Assert.assertTrue(output.contains("Usage:"), "Usage message not displayed when no arguments are provided.");
    }
}
