/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esspi.hcbptool.concurrency;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author presmelito.villanuev
 */
public class TheExecutor {
    private ExecutorService executorService;
    private static TheExecutor instance;

    public static TheExecutor getInstance() {
        if (Objects.isNull(instance)) {
            instance = new TheExecutor();
        }
        return instance;
    }

    public ExecutorService getExecutorService() {
        if (Objects.isNull(this.executorService) || executorService.isShutdown()) {
            executorService = Executors.newFixedThreadPool(8);
        }
        return executorService;
    }
}
