package functional.pingTestCases;

import com.pinger.automation.core.model.entites.dto.TestDataDto;
import com.pinger.automation.tests.BaseTest;
import com.pinger.automation.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BasePingTests extends BaseTest {

    //Todo - update this to somehow execute after each test case using @AfterClass as for now negatives leave files as they're failing before method execution.
    // Need to handle negative scenarios and it'll work, but AfterClass is preferable
    public void cleanUpGeneratedFiles(TestDataDto testData) {
        FileUtils.deleteFile(testData.getInputData().getPath());
        FileUtils.deleteFile(testData.getOutputData().getPath());
    }
}
