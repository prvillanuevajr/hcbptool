/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esspi.hcbptool.config;

import com.esspi.hcbptool.constants.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
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

    private static final Path CONFIG_PATH = Paths.get("hcbptoolconfig.yaml");
    private static final Path CONFIG_PATH_JSON_FALLBACK = Paths.get("hcbptoolconfig.json");
    private static final YAMLFactory YAML_FACTORY = new YAMLFactory().enable(YAMLGenerator.Feature.MINIMIZE_QUOTES);
    private static ToolConfig instance;
    private String theme = FlatLightLaf.class.getName();
    private Path workspacePath;
    private Path repoPath;
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
        try {
            if (Files.exists(CONFIG_PATH)) {
                instance = new ObjectMapper(YAML_FACTORY).readValue(CONFIG_PATH.toFile(), ToolConfig.class);
            }else if(Files.exists(CONFIG_PATH_JSON_FALLBACK)){
                instance = new ObjectMapper().readValue(CONFIG_PATH_JSON_FALLBACK.toFile(), ToolConfig.class);
            }else{
                instance = new ToolConfig();
            }
        } catch (IOException ex) {
            Logger.getLogger(ToolConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveConfig() {
        try {
            new ObjectMapper(YAML_FACTORY).writeValue(CONFIG_PATH.toFile(), instance);
        } catch (IOException ex) {
            Logger.getLogger(ToolConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public void setThemeAndSave(String name) {
        this.setTheme(name);
        this.saveConfig();
    }

}
