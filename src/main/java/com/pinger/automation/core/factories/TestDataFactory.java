package com.pinger.automation.core.factories;

import com.pinger.automation.core.model.entites.PingerInputDataFile;
import com.pinger.automation.core.model.entites.PingerOutputDataFile;
import com.pinger.automation.core.model.entites.dto.OutputDataDto;
import com.pinger.automation.core.model.entites.dto.TestDataDto;
import com.pinger.automation.core.model.enums.Endpoints;
import com.pinger.automation.utils.ConfigLoader;
import com.pinger.automation.utils.FileUtils;

import java.io.File;

public class TestDataFactory {
    private static final PingerInputDataFactory pingerInputDataFactory = new PingerInputDataFactory();

    public static TestDataDto createTestDataDto(Class clazz, Endpoints endpoint) {
        OutputDataDto inputDto = pingerInputDataFactory.getPingDataDto(endpoint);
        String directory = ConfigLoader.getString("pinger.workingDirectory");
        String inputFileName = clazz.getSimpleName() + "InputData.json";
        String outputFileName = clazz.getSimpleName() + "OutputData.json";

        File testData = FileUtils.createJsonFileFromDto(inputDto, directory, inputFileName);


        PingerInputDataFile inputDataFile = new PingerInputDataFile();
        inputDataFile.setDto(inputDto);
        inputDataFile.setDirectory(directory);
        inputDataFile.setName(inputFileName);

        PingerOutputDataFile outputDataFile = new PingerOutputDataFile();
        outputDataFile.setDto(null);
        outputDataFile.setDirectory(directory);
        outputDataFile.setName(outputFileName);

        TestDataDto dataFile = new TestDataDto();
        dataFile.setInputData(inputDataFile);
        dataFile.setOutputData(outputDataFile);
        return dataFile;
    }
}
