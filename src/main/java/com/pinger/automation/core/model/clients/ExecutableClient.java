package com.pinger.automation.core.model.clients;

import com.fasterxml.jackson.core.type.TypeReference;
import com.pinger.automation.core.model.entites.dto.PingOutputDto;
import com.pinger.automation.utils.AppRunner;
import com.pinger.automation.utils.FileUtils;

public abstract class ExecutableClient<O> {
    private final String EXECUTABLE;
    private final String WORKING_DIRECTORY;
    private final String DATA_FILE_NAME;
    private final String RESULTS_FILE_NAME;

    public ExecutableClient(String executable, String directoryPath, String dataFileName, String resultsFileName) {
        EXECUTABLE = executable;
        WORKING_DIRECTORY = directoryPath;
        DATA_FILE_NAME = dataFileName;
        RESULTS_FILE_NAME = resultsFileName;
    }

    public abstract O processValidScenario();

    public PingOutputDto execute() {
        AppRunner.runApplication(EXECUTABLE, DATA_FILE_NAME, RESULTS_FILE_NAME, WORKING_DIRECTORY);
        //probably need to return json in case there is no file generated -
        //todo - validate scenario if file is not generated.
        return FileUtils.parseFileToObject(WORKING_DIRECTORY + RESULTS_FILE_NAME, new TypeReference<>() {
        });
    }
}
