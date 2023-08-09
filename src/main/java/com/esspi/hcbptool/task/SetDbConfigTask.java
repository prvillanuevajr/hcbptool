/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esspi.hcbptool.task;

import com.esspi.hcbptool.Constants;
import com.esspi.hcbptool.config.DBConfig;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author presmelito.villanuev
 */
public class SetDbConfigTask extends Task{
    
    private DBConfig dbConfig;

    public SetDbConfigTask(DBConfig dbConfig) {
        this.dbConfig = dbConfig;
    }

    @Override
    public String call() throws Exception {
        String[] setDbTypeCommand = {
            Constants.setDbTypeBatchFile,
            Constants.dbType,
            Constants.oracleHome,
            this.dbConfig.getDbName(),
            this.dbConfig.getAdminId(),
            this.dbConfig.getAdminPassword(),
            this.dbConfig.getUserId(),
            this.dbConfig.getUserPassword(),
            this.dbConfig.getHost(),
            this.dbConfig.getPort()
        };
        ProcessBuilder builder = new ProcessBuilder();
        builder.directory(new File(Constants.binDirectory));
        builder.command(setDbTypeCommand);
        try {
            doBeforeRun();
            Process process = builder.start();
            try(Scanner scanner = new Scanner(process.getInputStream())){
                while (scanner.hasNextLine()) {
                    doDuringRun(scanner.nextLine());
                }
            }
            int result = process.waitFor();
            doOnSuccess(String.valueOf(result));
        } catch (InterruptedException | IOException ex) {
            doOnError(ex.getMessage());
        }
        return "done";
    }
     
}
