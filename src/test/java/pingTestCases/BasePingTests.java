package pingTestCases;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.decorators.PingDataDtoDecorator;
import core.entites.PingDataDto;
import core.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

import java.io.*;

@Slf4j
public class BasePingTests {
    protected PingDataDto testConfig;
    protected PingDataDtoDecorator pingDataDtoDecorator = new PingDataDtoDecorator();
    protected static String resultsFilePath;
    private static String testDataFilePath;
    private static final String PINGER_EXECUTABLE = "src/main/resources/pinger";


    protected void runPingTest(PingDataDto testData) {
        testDataFilePath = "src/main/resources/" + super.getClass().getSimpleName() + "TestData.json";
        resultsFilePath = "src/main/resources/" + super.getClass().getSimpleName() + "Results.json";

        // Save the DTO to JSON file
        JsonUtils.saveDtoToJsonFile(testData, testDataFilePath);

        // Pass the file path to pinger instead of the inline JSON string
        executePinger(testDataFilePath);
        validateResults();
    }

    private void executePinger(String testConfigFilePath) {
        try {
            // Ensure file exists before execution
            File configFile = new File(testConfigFilePath);
            if (!configFile.exists()) {
                log.error("Config file {} does not exist!", testConfigFilePath);
                Assert.fail("Config file not found.");
            }

            log.info("🚀 Running Pinger: {} {} {}", PINGER_EXECUTABLE, testConfigFilePath, resultsFilePath);

            // Pass only filenames, not full paths
            ProcessBuilder processBuilder = new ProcessBuilder("./pinger",
                    new File(testConfigFilePath).getName(),
                    new File(resultsFilePath).getName());
            processBuilder.directory(new File("src/main/resources"));
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                log.info("🔹 {}", line);
            }
            process.waitFor();
        } catch (Exception e) {
            log.error("Error executing pinger: {}", e.getMessage());
        }
    }

    private void validateResults() {
        try {
            // Check if results file exists
            File resultsFile = new File(resultsFilePath);
            if (!resultsFile.exists()) {
                log.error("Results file {} does not exist!", resultsFilePath);
                Assert.fail("Results file not found.");
            }

            // Log the contents of results.json
            BufferedReader reader = new BufferedReader(new FileReader(resultsFile));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            log.info("Results file content:\n{}", content);
            reader.close();

            // Parse results.json
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(resultsFile);
            JsonNode entries = rootNode.get("entries");

            if (entries == null || !entries.isArray() || entries.isEmpty()) {
                Assert.fail("No entries found in results.json");
            }

            boolean testPassed = false;
            for (JsonNode entry : entries) {
                String address = entry.get("endpoint").get("addr").asText();
                int successfulPings = entry.get("successful_pings").asInt();
                int minSuccessRequired = rootNode.get("min_successful_pings").asInt();

                if (address.equals(testConfig.getEndpoints().getFirst().getAddress()) && successfulPings >= minSuccessRequired) {
                    testPassed = true;
                    break;
                }
            }

            log.info("Test Result: {}", testPassed ? "PASS" : "FAIL");
            Assert.assertTrue(testPassed, "Expected at least " + rootNode.get("min_successful_pings").asInt() +
                    " successful pings for " + testConfig.getEndpoints().getFirst().getAddress());
        } catch (IOException e) {
            log.error("Error parsing results.json: {}", e.getMessage());
            Assert.fail("Failed to parse results.json");
        }

        cleanUpGeneratedFiles();
    }

    protected String runPingerCommand(String... args) {
        StringBuilder output = new StringBuilder();
        try {
            ProcessBuilder processBuilder;
            if (args.length > 0) {
                String[] command = new String[args.length + 1];
                command[0] = "./pinger";
                System.arraycopy(args, 0, command, 1, args.length);
                processBuilder = new ProcessBuilder(command);
            } else {
                processBuilder = new ProcessBuilder("./pinger");
            }

            processBuilder.directory(new File("src/main/resources"));
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

    protected String readFileContent(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString().trim();
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
