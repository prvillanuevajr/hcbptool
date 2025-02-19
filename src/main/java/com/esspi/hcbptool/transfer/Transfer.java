/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esspi.hcbptool.transfer;

import java.util.List;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.Function;
import lombok.Data;

/**
 *
 * @author presmelito.villanuev
 */
@Data
public abstract class Transfer {
    protected Function<String, Runnable> beforeRun;
    protected Function<String, Consumer<String>> duringRun;
    protected Function<String, Consumer<String>> onSuccess;
    
    public abstract List<Future> transfer();
}
