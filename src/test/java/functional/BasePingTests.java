package functional;

import com.pinger.automation.core.model.entites.dto.TestDataDto;
import com.pinger.automation.utils.PingerFileUtils;
import org.testng.annotations.AfterClass;

public class BasePingTests {

    protected TestDataDto testData;

    public void cleanUpGeneratedFiles(TestDataDto testData) {
        cleanUpGeneratedFile(testData.getConfig().getPath());
        cleanUpGeneratedFile(testData.getReport().getPath());
    }

    public void cleanUpGeneratedFile(String path) {
        PingerFileUtils.deleteFile(path);
    }

    @AfterClass
    public void afterClass() {
        if (testData != null) {
            cleanUpGeneratedFiles(testData);
        }
    }
}
