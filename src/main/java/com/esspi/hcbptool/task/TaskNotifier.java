/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esspi.hcbptool.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

/**
 *
 * @author presmelito.villanuev
 */
public class TaskNotifier {
    private List<Future> futures = new ArrayList<>();
    private Runnable doAfter;
    
    public TaskNotifier setFutures(List<Future> futures) {
        this.futures = futures;
        return this;
    }
    
    public TaskNotifier setFutures(Future ...futures) {
        this.futures.addAll(Arrays.asList(futures));
        return this;
    }

    public TaskNotifier setDoAfter(Runnable doAfter) {
        this.doAfter = doAfter;
        return this;
    }
    
    
    
    public void listen(){
        new Thread(() -> {
            while (true) {
                boolean done = futures.stream().allMatch(Future::isDone);
                if (done) {
                    doAfter.run();
                    break;
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    
                }
            }
        }).start();
    }
    
}
