/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esspi.hcbptool.task;

import com.esspi.hcbptool.constants.Constants;
import java.io.IOException;

/**
 *
 * @author presmelito.villanuev
 */
public class TransferTask extends Task {

    private String sourcePath;
    private String destPath;

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public String getDestPath() {
        return destPath;
    }

    public void setDestPath(String destPath) {
        this.destPath = destPath;
    }

    private String doCopy() {
        ProcessBuilder builder = new ProcessBuilder();
        builder.command(Constants.RBCP, sourcePath, destPath, Constants.RBCP_ALL_SUB, Constants.RBCP_ATTR, Constants.RBCP_MT, Constants.RBCP_PURGE);
        doBeforeRun();
        int result;
        try {
            Process process = builder.start();
            try (java.util.Scanner scanner = new java.util.Scanner(process.getInputStream())) {
                while (scanner.hasNextLine()) {
                    doDuringRun(scanner.nextLine());
                }
            }
            result = process.waitFor();
            doOnSuccess("Success with status: " + result);
        } catch (IOException | InterruptedException ex) {
            doOnError(ex.getMessage());
        }
        return "Done";
    }

    @Override
    public String call() throws Exception {
        return this.doCopy();
    }

}
