package functional.pingTestCases;

import com.pinger.automation.core.model.enums.Endpoints;
import org.testng.annotations.Test;

public class NS07_ValidateReportStructureTest extends BasePingTests{
    @Test
    public void validateReportFileStructureTest() {
        dto = pingerInputDataFactory.getPingDataDto(Endpoints.GOOGLE_DNS, 1, 1);
//        executeTest(dto);
        //validateReportFileStructure();
    }
}
