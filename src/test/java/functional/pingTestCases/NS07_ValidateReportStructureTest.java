package functional.pingTestCases;

import org.testng.annotations.Test;

public class NS07_ValidateReportStructureTest extends BasePingTests{
    @Test
    public void validateReportFileStructureTest() {
        dto = pingDataDtoFactory.createPingDataDto("8.8.8.8", "Google DNS", false, 1, 1);
        executeTest(dto);
        //validateReportFileStructure();
    }
}
