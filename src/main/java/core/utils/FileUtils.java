package core.utils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.*;

@Slf4j
public final class FileUtils {
    public static void checkIfFileExists(String path) {
        File file = new File(path);
        if (!file.exists()) {
            log.error("File {} does not exist!", path);
            System.exit(1);
        }
    }

    @SneakyThrows
    public static <T> T parseResultsFile(String resultsFilePath, TypeReference<T> typeReference) {
        try {
            FileUtils.checkIfFileExists(resultsFilePath);
            // Parse results.json into DTO
            InputStream is = new FileInputStream(resultsFilePath);
            String jsonTxt = IOUtils.toString(is, "UTF-8");
            System.out.println(jsonTxt);

            return JsonUtils.read(jsonTxt, typeReference);
        } catch (IOException e) {
            log.error("Error parsing results.json: {}", e.getMessage());
            throw new JsonGenerationException("Failed to parse results.json");
        }
    }

    private String readFileContent(String filePath) {
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
}
