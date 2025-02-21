/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esspi.hcbptool.git;

import com.esspi.hcbptool.config.ToolConfig;
import com.esspi.hcbptool.task.Task;
import java.io.IOException;
import static com.esspi.hcbptool.git.GitConstants.*;

/**
 *
 * @author presmelito
 */
public class GitStatusTask extends Task {

    public String call() throws Exception {
        ProcessBuilder builder = new ProcessBuilder().directory(ToolConfig.getInstance().getRepoPath().toFile().getAbsoluteFile());
        builder.command(GIT, CONFIG,DIFF_MNEMONIC_PREFIX_FALSE,CONFIG,CORE_QUOTE_PATH_FALSE,NO_OPTIONAL_LOCKS,STATUS,PORCELAIN,IGNORE_SUBMODULE_DIRTY,UNTRACKED_FILES_ALL,NO_AHEAD_BEHIND);
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

}
