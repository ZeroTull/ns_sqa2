package core.factories;

import core.entites.EndpointDto;
import core.entites.PingDataDto;
import core.entites.TestDataDto;
import core.utils.FileUtils;

import java.io.File;
import java.util.Collections;

public class TestDataFactory {
    private static final PingDataDtoFactory pingDataDtoFactory = new PingDataDtoFactory();

    public PingDataDto createPingDataDto(String address, String description, boolean ignore, int minSuccess, int maxPings) {
        EndpointDto endpoint = new EndpointDto(address, description, ignore);
        PingDataDto config = new PingDataDto();
        config.setMinSuccessfulPings(minSuccess);
        config.setMaxPings(maxPings);
        config.setEndpoints(Collections.singletonList(endpoint));
        return config;
    }

    public static TestDataDto createTestDataDto(Class clazz, String address, String description) {
        TestDataDto dataFile = new TestDataDto();
        PingDataDto testDataDto = pingDataDtoFactory.createPingDataDto(address, description);
        File testData = FileUtils.createJsonFileFromDto(testDataDto, clazz);

        dataFile.setTestDataPath(testData.getPath());
        dataFile.setTestDataDto(testDataDto);
        dataFile.setResultDataPath(testData.getPath().replace("TestData", "Results"));
        return dataFile;

    }
}
