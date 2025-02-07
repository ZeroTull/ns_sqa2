package com.pinger.automation.core.model.clients;

import com.fasterxml.jackson.core.type.TypeReference;
import com.pinger.automation.core.model.entites.dto.PingOutputDto;
import com.pinger.automation.core.model.entites.dto.TestDataDto;
import com.pinger.automation.utils.ConfigLoader;
import com.pinger.automation.utils.FileUtils;
import com.pinger.automation.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;

@Slf4j
public class PingerClient extends ExecutableClient<PingOutputDto> {
    private static final String EXECUTABLE = ConfigLoader.getString("pinger.executable");
    private static final String WORKING_DIRECTORY = ConfigLoader.getString("pinger.workingDirectory");
    private final String RESULTS_FILE_NAME;

    public PingerClient(TestDataDto dto) {
        super(EXECUTABLE, WORKING_DIRECTORY, dto.getInputData().getName(), dto.getOutputData().getName());
        RESULTS_FILE_NAME = dto.getOutputData().getName();
    }

    public PingerClient(String dataFileName, String resultsFileName) {
        super(EXECUTABLE, WORKING_DIRECTORY, dataFileName, resultsFileName);
        RESULTS_FILE_NAME = resultsFileName;
    }

    @Override
    public PingOutputDto processValidScenario() {
        PingOutputDto actual = execute();

        if (actual.getEntries() == null || actual.getEntries().isEmpty()) {
            Assert.fail("No entries found in result.json");
        }
        log.info("Result file: {}", JsonUtils.toJsonString(actual));

        PingOutputDto expected = FileUtils.parseFileToObject(WORKING_DIRECTORY.concat(RESULTS_FILE_NAME), new TypeReference<>() {
        });

        boolean testPassed = actual.getEntries().stream()
                .anyMatch(entry -> entry.getEndpoint().getAddress().equals(expected.getEntries().getFirst().getEndpoint().getAddress())
                        && entry.getSuccessfulPings() >= actual.getMinSuccessfulPings());

        //Todo remove once assert logs are shown in console
        log.info("Test Result: {}", testPassed ? "PASS" : "FAIL");
        Assert.assertTrue(String.format("Expected at least %s successful pings for %s ", expected.getMinSuccessfulPings(),
                actual.getEntries().getFirst().getEndpoint().getDescription()), testPassed);

        return actual;
    }
}
