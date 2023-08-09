/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esspi.hcbptool.task;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

/**
 *
 * @author presmelito.villanuev
 */
public abstract class Task implements Callable<String> {

    private Runnable beforeRun;
    private Consumer<String> duringRun;
    private Consumer<String> onSuccess;
    private Consumer<String> onError;

    public void setBeforeRun(Runnable beforeRun) {
        this.beforeRun = beforeRun;
    }

    public void setDuringRun(Consumer<String> duringRun) {
        this.duringRun = duringRun;
    }

    public void setOnSuccess(Consumer<String> onSuccess) {
        this.onSuccess = onSuccess;
    }

    public void setOnError(Consumer<String> onError) {
        this.onError = onError;
    }

    protected void doBeforeRun() {
        if (Objects.nonNull(this.beforeRun)) {
            this.beforeRun.run();
        }
    }

    protected void doDuringRun(String message) {
        if (Objects.nonNull(this.duringRun)) {
            this.duringRun.accept(message);
        }
    }

    protected void doOnSuccess(String message) {
        if (Objects.nonNull(this.onSuccess)) {
            this.onSuccess.accept(message);
        }
    }

    protected void doOnError(String message) {
        if (Objects.nonNull(this.onError)) {
            this.onError.accept(message);
        }
    }

    protected void executeCommand(String[] command) {
        ProcessBuilder builder = new ProcessBuilder();
        builder.command(command);
        doBeforeRun();
        try {
            Process process = builder.start();
            try (java.util.Scanner scanner = new java.util.Scanner(process.getInputStream())) {
                while (scanner.hasNextLine()) {
                    doDuringRun(scanner.nextLine());
                }
            }
            int result = process.waitFor();
            doOnSuccess(String.valueOf(result));
        } catch (IOException | InterruptedException ex) {
            doOnError(ex.getMessage());
        }
    }

    protected String[] getCommands() {
        return new String[]{};
    }

}
