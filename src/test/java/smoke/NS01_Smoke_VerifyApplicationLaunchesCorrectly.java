package smoke;

import com.pinger.automation.core.helpers.BSL;
import com.pinger.automation.core.model.entites.dto.TestDataDto;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NS01_Smoke_VerifyApplicationLaunchesCorrectly {

    @Test
    public void verifyApplicationIsRunnable() {
        TestDataDto testDataDto = new TestDataDto();
        testDataDto.getInputDataFile().setName("");
        testDataDto.getOutputDataFile().setName("");
        String output = BSL.pingerExecutableHelper.executePinger(testDataDto).execute();

        Assert.assertNotNull(output, "Application provides output.");
        Assert.assertTrue(output.contains("Failed to read config file: open : no such file or directory"), "Application is executable.");
    }
}
