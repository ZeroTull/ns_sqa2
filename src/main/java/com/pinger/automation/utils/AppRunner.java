package com.pinger.automation.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
public class AppRunner {
    public static String runApplication(String executable, String workingDirectory, String... args) {
        log.info("Executing application: {} {} ", executable, args);
        StringBuilder output = new StringBuilder();
        try {
            ProcessBuilder processBuilder = getProcessBuilder(executable, args);
            processBuilder.directory(new File(workingDirectory));
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                log.info("{}", line);
                output.append(line).append("\n");
            }
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            log.error("Error executing application: {}", e.getMessage(), e);
            return "";
        }
        return output.toString().trim();
    }

    private static ProcessBuilder getProcessBuilder(String executable, String... args) {
        ProcessBuilder processBuilder;
        if (args.length > 0) {
            String[] command = new String[args.length + 1];
            command[0] = executable;
            System.arraycopy(args, 0, command, 1, args.length);
            processBuilder = new ProcessBuilder(command);
        } else {
            processBuilder = new ProcessBuilder(executable);
        }
        return processBuilder;
    }
}
