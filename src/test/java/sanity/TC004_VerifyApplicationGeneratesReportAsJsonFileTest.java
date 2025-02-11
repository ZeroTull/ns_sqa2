package sanity;

import com.pinger.automation.core.factories.TestDataDtoFactory;
import com.pinger.automation.core.helpers.executable.PingerExecutableHelper;
import com.pinger.automation.core.model.enums.Endpoint;
import com.pinger.automation.utils.PingerFileUtils;
import functional.BasePingTests;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

public class TC004_VerifyApplicationGeneratesReportAsJsonFileTest extends BasePingTests {
    @BeforeClass
    public void beforeClass() {
        testData = TestDataDtoFactory.createTestDataDto(this.getClass(), Endpoint.GOOGLE_DNS);
    }

    @Test()
    @Description("Application successfully creates *.json file.")
    public void test() {
        PingerExecutableHelper.getPingerClient(testData).execute();
        File report = PingerFileUtils.getFile(testData.getReport().getPath());

        Assert.assertTrue(PingerFileUtils.hasJsonExtension(report));
        cleanUpGeneratedFiles(testData);
    }
}

