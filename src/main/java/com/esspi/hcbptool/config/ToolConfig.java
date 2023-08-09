/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esspi.hcbptool.config;

import com.esspi.hcbptool.constants.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Arrays;

/**
 *
 * @author presmelito.villanuev
 */
public class ToolConfig {

    private static Path configPath = Paths.get("hcbptoolconfig.json");
    private Path workspacePath;
    private Path repoPath;
    private static ToolConfig instance;
    private List<String> folders = new ArrayList<>();
    private List<String> selectedFolders = new ArrayList<>();
    private List<DBConfig> dbConfigs = new ArrayList<>();

    public List<String> getFolders() {
        return folders;
    }

    public List<DBConfig> getDbConfigs() {
        return dbConfigs;
    }
    
    public List<String> getSelectedFolders() {
        return selectedFolders;
    }
    
    

    public static ToolConfig getInstance() {
        if (Objects.isNull(instance)) {
            initConfig();
        }
        return instance;
    }

    private ToolConfig() {
        this.folders = Arrays.asList(Constants.folders);
    }

    public Path getWorkspacePath() {
        return workspacePath;
    }

    public void setWorkspacePath(Path workspacePath) {
        this.workspacePath = workspacePath;
    }

    public Path getRepoPath() {
        return repoPath;
    }

    public void setRepoPath(Path repoPath) {
        this.repoPath = repoPath;
    }

    private static void initConfig() {
        File file = configPath.toFile();
        ObjectMapper mapper = new ObjectMapper();
        try {
            if (Files.exists(configPath)) {
                instance = mapper.readValue(file, ToolConfig.class);
                return;
            }
            instance = new ToolConfig();
        } catch (IOException ex) {
            Logger.getLogger(ToolConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveConfig() {
        try {
            File file = configPath.toFile();
            new ObjectMapper().writeValue(file, instance);
        } catch (IOException ex) {
            Logger.getLogger(ToolConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
