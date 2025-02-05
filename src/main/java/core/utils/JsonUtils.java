package core.utils;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import core.entites.PingDataDto;
import lombok.extern.slf4j.Slf4j;
import model.CustomException;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Slf4j
public final class JsonUtils {
    public static String toJsonString(Object object) {
        try {
            Objects.requireNonNull(object);
            return getObjectWriter().writeValueAsString(object);
        } catch (Throwable t) {
            throw new CustomException("Cannot convert object to JSON string ", t);
        }
    }

    public static void saveDtoToJsonFile(PingDataDto dto, String filePath) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), dto);
            log.info("DTO saved to {}", filePath);
        } catch (IOException e) {
            log.error("Failed to save DTO: {}", e.getMessage());
        }
    }


    private static ObjectWriter getObjectWriter() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
        return mapper.writer();
    }

    private static synchronized ObjectMapper getObjectMapper(Module... modules) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.registerModules(modules);
        return mapper;
    }
}
