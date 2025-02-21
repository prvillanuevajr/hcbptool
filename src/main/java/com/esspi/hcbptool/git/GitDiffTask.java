/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esspi.hcbptool.git;

import com.esspi.hcbptool.config.ToolConfig;
import com.esspi.hcbptool.task.Task;
import java.io.IOException;
import static com.esspi.hcbptool.git.GitConstants.*;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author presmelito
 */
public class GitDiffTask extends Task {

    private GitFile file;

    public void setFile(GitFile file) {
        this.file = file;
    }

    @Override
    public String call() throws Exception {
        ProcessBuilder builder = new ProcessBuilder().directory(ToolConfig.getInstance().getRepoPath().toFile().getAbsoluteFile());
        boolean cached = GitStatus.getStatusNeedsDiffCached().contains(file.getState());
        builder.command(GIT, CONFIG, DIFF_MNEMONIC_PREFIX_FALSE, CONFIG, CORE_QUOTE_PATH_FALSE, NO_OPTIONAL_LOCKS, DIFF, UNIFIED_3, WHITESPACE_IGNORE, NO_COLOR, DASH_ABIGUITY,this.file.getName());
        if (cached) {
            builder.command().add(9, CACHED);
        }
        System.out.println(StringUtils.join(builder.command()," "));
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
