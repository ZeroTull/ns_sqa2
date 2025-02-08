package com.pinger.automation.core.model.clients;

import com.fasterxml.jackson.core.type.TypeReference;
import com.pinger.automation.core.model.entites.dto.EndpointDto;
import com.pinger.automation.core.model.entites.dto.TestDataDto;
import com.pinger.automation.core.model.entites.dto.input.InputDataDto;
import com.pinger.automation.core.model.entites.dto.output.EntryDto;
import com.pinger.automation.core.model.entites.dto.output.OutputDataDto;
import com.pinger.automation.utils.FileUtils;
import com.pinger.automation.utils.JsonUtils;
import com.pinger.automation.utils.SoftVerifier;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

import static com.pinger.automation.utils.PingerConfig.getPingerExecutable;
import static com.pinger.automation.utils.PingerConfig.getPingerWorkingDirectory;

@Slf4j
public class PingerClient extends ExecutableClient<OutputDataDto> {
    private static final String EXECUTABLE = getPingerExecutable();
    private static final String WORKING_DIRECTORY = getPingerWorkingDirectory();
    private final TestDataDto TEST_DATA_DTO;

    public PingerClient(TestDataDto dto) {
        super(EXECUTABLE, WORKING_DIRECTORY, dto.getInputDataFile().getName(), dto.getOutputDataFile().getName());
        TEST_DATA_DTO = dto;
    }

    @Override
    public OutputDataDto processValidScenario() {
        OffsetDateTime expectedStartDateTime = OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        execute();
        OffsetDateTime expectedEndDateTime = OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS);

        //Get an object with expected data.
        InputDataDto expected = TEST_DATA_DTO.getInputDataFile().getDto();

        //Get an object with actual data after execution
        OutputDataDto actual = FileUtils.parseFileToObject(WORKING_DIRECTORY + TEST_DATA_DTO.getOutputDataFile().getName(), new TypeReference<>() {
        });

        //Check if output file has data in it.
        if (actual.getEntries() == null || actual.getEntries().isEmpty()) {
            Assert.fail("No entries found in result.json");
        }
        log.info("Result file: {}", JsonUtils.toJsonString(actual));
        SoftVerifier verifier = new SoftVerifier();

        //Validate timestamps (doesn't work properly, fails from time to time due to one second edge)
        OffsetDateTime outputStartDateTime = OffsetDateTime.parse(actual.getStartTime()).truncatedTo(ChronoUnit.SECONDS);
        OffsetDateTime outputEndDateTime = OffsetDateTime.parse(actual.getEndTime()).truncatedTo(ChronoUnit.SECONDS);
        verifier.assertEquals(expectedStartDateTime, outputStartDateTime, "Verity start date time.");
        verifier.assertEquals(expectedEndDateTime, outputEndDateTime, "Verity end date time.");

        //Validate min/max pings
        verifier.assertEquals(actual.getMinSuccessfulPings(), expected.getMinSuccessfulPings(), "Verity min successful pings.");
        verifier.assertTrue(actual.getMaxPings() <= expected.getMaxPings(), "Verity max successful pings.");

        //Validate total count of entries
        verifier.assertEquals(expected.getEndpoints().size(), actual.getEntries().size(), "Verify total count of entries.");

        //Navigate through the list of endpoints to validate all of them.
        for (EntryDto actualEntry : actual.getEntries()) {
            EndpointDto expectedEndpoint = expected.getEndpoints().stream().filter(e -> e.getDescription().equals(actualEntry.getEndpoint().getDescription())).findFirst().orElse(null);

            if (expectedEndpoint == null) {
                log.warn("There is no {} endpoint in output file.", actualEntry.getEndpoint().getDescription());
                break;
            }
            log.warn("Verifying entry: {}.", actualEntry);

            verifier.assertTrue(actualEntry.getSuccessfulPings() >= expected.getMinSuccessfulPings(), "Assert actual vs minimum successful pings.");
            verifier.assertTrue(actualEntry.getTotalPings() <= expected.getMaxPings(), "Assert actual total vs maximum successful pings.");
            verifier.assertEquals(actualEntry.getEndpoint(), expectedEndpoint, "Assert endpoint data.");
        }
        verifier.verifyAll();
        return actual;
    }
}
