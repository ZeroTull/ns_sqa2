package com.pinger.automation.tests;

import com.pinger.automation.core.factories.PingerInputDataFactory;
import com.pinger.automation.core.model.entites.dto.OutputDataDto;
import com.pinger.automation.utils.ConfigLoader;

public class BaseTest {
    protected final String PINGER_EXECUTABLE = ConfigLoader.getString("pinger.executable");
    protected final String WORKING_DIRECTORY = ConfigLoader.getString("pinger.workingDirectory");

    //todo refactor remove
    protected OutputDataDto dto;
    protected PingerInputDataFactory pingerInputDataFactory = new PingerInputDataFactory();
}
