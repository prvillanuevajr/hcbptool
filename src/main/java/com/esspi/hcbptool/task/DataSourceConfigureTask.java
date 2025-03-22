/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esspi.hcbptool.task;

import java.io.IOException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author presmelito
 */
public abstract class DataSourceConfigureTask extends Task{

    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceConfigureTask.class);
    
    @Override
    protected void executeCommand(String[] command) {
        ProcessBuilder builder = new ProcessBuilder();
        builder.command(command);
        builder.redirectErrorStream(true);
        doBeforeRun();
        String message = StringUtils.EMPTY;
        try {
            LOGGER.info(StringUtils.join(command, StringUtils.SPACE));
            Process process = builder.start();
            try (java.util.Scanner scanner = new java.util.Scanner(process.getInputStream())) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    LOGGER.info(line);
                    doDuringRun(line);
                    message += line;
                }
            }
            int result = process.waitFor();
            doOnSuccess(message);
        } catch (IOException | InterruptedException ex) {
            doOnError(ex.getMessage());
            LOGGER.error(ex.getMessage());
        }
    }
    
}
