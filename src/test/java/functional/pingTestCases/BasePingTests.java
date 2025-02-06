package functional.pingTestCases;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.entites.PingDataDto;
import core.entites.PingResults;
import core.factories.PingDataDtoFactory;
import core.utils.FileUtils;
import core.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;


@Slf4j
public class BasePingTests {
    protected PingDataDto testConfig;
    protected PingDataDtoFactory pingDataDtoFactory = new PingDataDtoFactory();
    protected static String resultsFilePath;
    private static String testDataFilePath;
    private static final String PINGER_EXECUTABLE = "./pinger";
    private static final String WORKING_DIRECTORY = "src/main/resources/";

    protected void executeTest(PingDataDto testData) {
        testDataFilePath = WORKING_DIRECTORY + super.getClass().getSimpleName() + "TestData.json";
        resultsFilePath = WORKING_DIRECTORY + super.getClass().getSimpleName() + "Results.json";


        // Save the DTO to JSON file
        JsonUtils.saveDtoToJsonFile(testData, testDataFilePath);

        // Pass the file path to pinger instead of the inline JSON string
        executePinger(testDataFilePath); //todo - this should return some kinda dto with execution results to be used later during validation
        //todo move validation to a separate method + add negative scenario handling
        validateResults();
    }

    private void executePinger(String testConfigFilePath) {
        try {
            // Ensure file exists before execution
            FileUtils.checkIfFileExists(testConfigFilePath);
            log.info("Running Pinger: {} {} {}", PINGER_EXECUTABLE, testConfigFilePath, resultsFilePath);

            //Tip: pass only filenames, not full paths
            ProcessBuilder processBuilder = new ProcessBuilder("./pinger",
                    new File(testConfigFilePath).getName(),
                    new File(resultsFilePath).getName());
            processBuilder.directory(new File("src/main/resources"));
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                log.info("{}", line);
            }
            process.waitFor();
        } catch (Exception e) {
            log.error("Error executing pinger: {}", e.getMessage());
        }
    }

    private void validateResults() {
        PingResults results = FileUtils.parseResultsFile(resultsFilePath, new TypeReference<>() {
        });


        //todo - move to process method
        if (results.getEntries() == null || results.getEntries().isEmpty()) {
            Assert.fail("No entries found in results.json");
        }

        boolean testPassed = results.getEntries().stream()
                .anyMatch(entry -> entry.getEndpoint().getAddr().equals(testConfig.getEndpoints().getFirst().getAddress())
                        && entry.getSuccessfulPings() >= results.getMinSuccessfulPings());

        log.info("Test Result: {}", testPassed ? "PASS" : "FAIL");
        Assert.assertTrue(testPassed, "Expected at least " + results.getMinSuccessfulPings() +
                " successful pings for " + testConfig.getEndpoints().getFirst().getAddress());
    }

    //todo update this to validate json structure against passed DTO (exp struct)
    public void validateReportFileStructure() {
        try {
            String resultsFile = "src/main/resources/" + super.getClass().getSimpleName() + "Results.json";

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(new File(resultsFile));

            if (!rootNode.has("start_time") || !rootNode.has("end_time")) {
                throw new AssertionError("Missing start_time or end_time in report");
            }

            if (!rootNode.has("min_successful_pings") || !rootNode.has("max_pings")) {
                throw new AssertionError("Missing min_successful_pings or max_pings in report");
            }

            JsonNode entries = rootNode.get("entries");
            if (entries == null || !entries.isArray() || entries.isEmpty()) {
                throw new AssertionError("No entries found in report");
            }

            for (JsonNode entry : entries) {
                if (!entry.has("endpoint") || !entry.has("total_pings") || !entry.has("successful_pings")) {
                    throw new AssertionError("Incomplete endpoint report data");
                }
            }

            System.out.println("Report file structure validated successfully.");
        } catch (IOException e) {
            throw new AssertionError("Failed to parse results.json for structure validation", e);
        }
        cleanUpGeneratedFiles();
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

    @AfterClass
    public void cleanUpGeneratedFiles() {
        deleteFile(testDataFilePath);
        deleteFile(resultsFilePath);
    }

    private void deleteFile(String filePath) {
        File file;
        try {
            file = new File(filePath);
            if (file.exists() && file.delete()) {
                log.info("Deleted file: {}", filePath);
            } else {
                log.warn("Failed to delete file or file does not exist: {}", filePath);
            }
        } catch (Exception e) {
            log.info("File does not exist.");
        }
    }
}
