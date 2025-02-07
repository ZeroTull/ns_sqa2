package functional.pingTestCases;

import com.fasterxml.jackson.core.type.TypeReference;
import core.entites.PingDataDto;
import core.entites.PingResultsDto;
import core.entites.TestDataDto;
import core.utils.FileUtils;
import core.utils.JsonUtils;
import functional.BaseTest;
import lombok.extern.slf4j.Slf4j;
import model.CustomException;
import org.testng.Assert;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
public class BasePingTests extends BaseTest {
    protected static String resultsFilePath;
    private static String testDataFilePath;

    protected void executeTest(PingDataDto testData) {
        // Pass the file path to pinger instead of the inline JSON string
        //runPingerApplication(testDataFilePath); //todo - this should return some kinda dto with execution results to be used later during validation
        //todo move validation to a separate method + add negative scenario handling
        //validateResults();
    }


    public void runPingerApplication(TestDataDto dto) {
        TestDataDto testDataDto = runPingerApplication(dto.getTestDataPath(), dto.getResultDataPath());
        //move validation to a separate place
        validateResults(testDataDto);
    }

    private TestDataDto runPingerApplication(String testDataFilePath, String resultsFilePath) {
        try {
            // Ensure file exists before execution
            FileUtils.checkIfFileExists(testDataFilePath);
            log.info("Running Pinger: {} {} {}", PINGER_EXECUTABLE, testDataFilePath, resultsFilePath);

            //Tip: pass only filenames, not full paths
            ProcessBuilder processBuilder = new ProcessBuilder(PINGER_EXECUTABLE,
                    new File(testDataFilePath).getName(),
                    new File(resultsFilePath).getName());
            processBuilder.directory(new File(WORKING_DIRECTORY));
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                log.info("{}", line);
            }
            process.waitFor();

            return new TestDataDto(testDataFilePath, FileUtils.parseFileToObject(testDataFilePath, new TypeReference<PingDataDto>() {
            }), resultsFilePath, FileUtils.parseFileToObject(resultsFilePath, new TypeReference<PingResultsDto>() {
            }));

        } catch (Exception e) {
            log.error("Error executing pinger: {}", e.getMessage());
            throw new CustomException("Failed to execute test.", e);
        }
    }

    private void validateResults(TestDataDto testDataDto) {
        dto = testDataDto.getTestDataDto();
        PingResultsDto result = FileUtils.parseFileToObject(testDataDto.getResultDataPath(), new TypeReference<>() {
        });

        //todo - move to process method
        if (result.getEntries() == null || result.getEntries().isEmpty()) {
            Assert.fail("No entries found in result.json");
        }
        log.info("Result file: {}", JsonUtils.toJsonString(result));

        boolean testPassed = result.getEntries().stream()
                .anyMatch(entry -> entry.getEndpoint().getAddr().equals(dto.getEndpoints().getFirst().getAddress())
                        && entry.getSuccessfulPings() >= result.getMinSuccessfulPings());

        log.info("Test Result: {}", testPassed ? "PASS" : "FAIL");
        Assert.assertTrue(testPassed, "Expected at least " + result.getMinSuccessfulPings() +
                " successful pings for " + dto.getEndpoints().getFirst().getAddress());
        //todo move this to 'send' method after negative scenario validation
        cleanUpGeneratedFiles(testDataDto.getTestDataPath(), testDataDto.getResultDataPath());
    }

    protected String runPingerCommand(String... args) {
        StringBuilder output = new StringBuilder();
        try {
            ProcessBuilder processBuilder;
            if (args.length > 0) {
                String[] command = new String[args.length + 1];
                command[0] = PINGER_EXECUTABLE;
                System.arraycopy(args, 0, command, 1, args.length);
                processBuilder = new ProcessBuilder(command);
            } else {
                processBuilder = new ProcessBuilder(PINGER_EXECUTABLE);
            }

            processBuilder.directory(new File(WORKING_DIRECTORY));
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "";
        }
        return output.toString().trim();
    }

    public void cleanUpGeneratedFiles(String testDataFilePath, String resultsFilePath) {
        FileUtils.deleteFile(testDataFilePath);
        FileUtils.deleteFile(resultsFilePath);
    }
}
