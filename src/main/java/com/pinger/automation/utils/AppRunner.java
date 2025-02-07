package com.pinger.automation.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

@Slf4j
public class AppRunner {
    public static void runApplication(String dataFileName, String resultFileName) {
        runApplication(ConfigLoader.getString("pinger.executable"), dataFileName, resultFileName, ConfigLoader.getString("pinger.workingDirectory"));
    }

    @SneakyThrows
    public static void runApplication(String executable, String dataFileName, String resultFileName, String workingDirectory) {
        log.info("Executing application: {} {} {}", executable, dataFileName, resultFileName);

        ProcessBuilder processBuilder = new ProcessBuilder(executable, dataFileName, resultFileName);
        processBuilder.directory(new File(workingDirectory));
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            log.info("{}", line);
        }
        process.waitFor();
    }
}
