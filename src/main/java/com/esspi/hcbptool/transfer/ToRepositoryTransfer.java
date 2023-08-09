/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esspi.hcbptool.transfer;

import com.esspi.hcbptool.task.TransferTask;
import com.esspi.hcbptool.concurrency.TheExecutor;
import com.esspi.hcbptool.config.ToolConfig;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 *
 * @author presmelito.villanuev
 */
public class ToRepositoryTransfer extends Transfer{
    
    @Override
    public List<Future> transfer(){
        List<Future> futures = new ArrayList<>();
        for (String folder : ToolConfig.getInstance().getSelectedFolders()) {
            Path destDir = Paths.get(ToolConfig.getInstance().getRepoPath().toString(), folder);
            File sourceDir = new File(ToolConfig.getInstance().getWorkspacePath().toFile(), folder);
            TransferTask task = new TransferTask();
            task.setSourcePath(sourceDir.getPath());
            task.setDestPath(destDir.toString());
            task.setBeforeRun(beforeRun.apply(folder));
            task.setDuringRun(duringRun.apply(folder));
            task.setOnSuccess(onSuccess.apply(folder));
            Future future = TheExecutor.getInstance().getExecutorService().submit(task);
            futures.add(future);
        }
        TheExecutor.getInstance().getExecutorService().shutdown();
        return futures;
    }
    
    
}
