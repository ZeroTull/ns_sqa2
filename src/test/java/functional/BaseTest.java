package functional;

import core.entites.PingDataDto;
import core.factories.PingDataDtoFactory;
import core.utils.ConfigLoader;

public class BaseTest {
    protected static final String PINGER_EXECUTABLE = ConfigLoader.getString("pinger.executable");
    protected static final String WORKING_DIRECTORY = ConfigLoader.getString("pinger.workingDirectory");


    protected PingDataDto dto;
    protected PingDataDtoFactory pingDataDtoFactory = new PingDataDtoFactory();

}
