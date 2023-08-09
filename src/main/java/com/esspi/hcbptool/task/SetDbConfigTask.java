/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esspi.hcbptool.task;

import com.esspi.hcbptool.concurrency.TheExecutor;
import com.esspi.hcbptool.config.DBConfig;
import com.esspi.hcbptool.constants.Constants;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 *
 * @author presmelito.villanuev
 */
public class SetDbConfigTask extends Task {

    private DBConfig dbConfig;

    public SetDbConfigTask(DBConfig dbConfig) {
        this.dbConfig = dbConfig;
    }

    @Override
    public String call() throws Exception {
        this.run();
        return Constants.DONE_STATUS;
    }

    public List<Future> run() {
        ExecutorService service = TheExecutor.getInstance().getExecutorService();
        List<Future> futures = new ArrayList<>();
        futures.add(service.submit(new ExecDataSourceConfigureTask(this.dbConfig)));
        futures.add(service.submit(new ExecSearchDatasourceConfigureTask(this.dbConfig)));
        futures.add(service.submit(new ExecJvmOptionsConfigure()));
        TheExecutor.getInstance().getExecutorService().shutdown();
        return futures;
    }

}
