/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esspi.hcbptool.task;

import java.io.IOException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

/**
 *
 * @author presmelito
 */
public abstract class DataSourceConfigureTask extends Task{

    @Override
    protected void executeCommand(String[] command) {
        ProcessBuilder builder = new ProcessBuilder();
        builder.command(command);
        builder.redirectErrorStream(true);
        doBeforeRun();
        String message = StringUtils.EMPTY;
        try {
            Process process = builder.start();
            try (java.util.Scanner scanner = new java.util.Scanner(process.getInputStream())) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    LoggerFactory.getLogger(Task.class).info(line);
                    doDuringRun(line);
                    message += line;
                }
            }
            int result = process.waitFor();
            if (StringUtils.isNotEmpty(message)) {
                doOnSuccess(message);
            }
        } catch (IOException | InterruptedException ex) {
            doOnError(ex.getMessage());
            LoggerFactory.getLogger(Task.class).error(ex.getMessage());
        }
    }
    
}
