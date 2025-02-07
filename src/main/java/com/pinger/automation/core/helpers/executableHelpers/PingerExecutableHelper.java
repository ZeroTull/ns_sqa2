package com.pinger.automation.core.helpers.executableHelpers;

import com.pinger.automation.core.model.clients.PingerClient;
import com.pinger.automation.core.model.entites.dto.TestDataDto;

public class PingerExecutableHelper {
    public PingerClient executePinger(String dataFileName, String resultsFileName) {
        return new PingerClient(dataFileName, resultsFileName);
    }

    public PingerClient executePinger(TestDataDto dto) {
        return new PingerClient(dto);
    }
}
