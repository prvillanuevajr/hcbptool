/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esspi.hcbptool.transfer;

import java.util.List;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 *
 * @author presmelito.villanuev
 */
public abstract class Transfer {
    protected Function<String, Runnable> beforeRun;
    protected Function<String, Consumer<String>> duringRun;
    protected Function<String, Consumer<String>> onSuccess;

    public void setBeforeRun(Function<String, Runnable> beforeRun) {
        this.beforeRun = beforeRun;
    }

    public void setDuringRun(Function<String, Consumer<String>> duringRun) {
        this.duringRun = duringRun;
    }

    public void setOnSuccess(Function<String, Consumer<String>> onSuccess) {
        this.onSuccess = onSuccess;
    }
    
    public abstract List<Future> transfer();
}
