/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esspi.hcbptool.ui;

import com.esspi.hcbptool.MainFrame;
import com.esspi.hcbptool.config.ToolConfig;
import com.esspi.hcbptool.constants.Constants;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author presmelito
 */
public class MainMenuBarController {

    private static MainMenuBarController instance;

    public static MainMenuBarController getInstance() {
        if (Objects.nonNull(instance)) {
            return instance;
        }
        return instance = new MainMenuBarController();
    }

    public MainMenuBarController() {
        addComponentListeners();
    }

    public void init() {
    }

    public void changeRepoPathListener() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int option = fileChooser.showDialog(MainFrame.getInstance(), "Select");
        if (JFileChooser.APPROVE_OPTION == option) {
            ToolConfig.getInstance().setRepoPath(fileChooser.getSelectedFile().toPath());
            MainFrame.getInstance().getRepoPathTf().setText(Constants.REPOPATH_INITIAL_TEXT + ToolConfig.getInstance().getRepoPath().toString());
        }
    }
    
    public void changeWorkspacePathListener(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int option = fileChooser.showDialog(MainFrame.getInstance(), "Select");
        if (JFileChooser.APPROVE_OPTION == option) {
            ToolConfig.getInstance().setWorkspacePath(fileChooser.getSelectedFile().toPath());
            MainFrame.getInstance().getWorkspacePathTf().setText(Constants.WORKSPACEPATH_INITIAL_TEXT + ToolConfig.getInstance().getWorkspacePath().toString());
        }
    }

    private void addComponentListeners() {
        MainFrame.getInstance().getExitMenuItemBtn().setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        MainFrame.getInstance().getSaveConfigMenuItem().setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        MainFrame.getInstance().getChangeWorkspacePathMenuItem().addActionListener(evt -> changeWorkspacePathListener());
        MainFrame.getInstance().getChangeRepoPathMenuItem().addActionListener(evt -> changeRepoPathListener());
        MainFrame.getInstance().getExitMenuItemBtn().addActionListener(evt -> System.exit(0));
        MainFrame.getInstance().getSaveConfigMenuItem().addActionListener(evt -> saveConfigListener());
        MainFrame.getInstance().getLightThemeMenuItem().addActionListener(e -> setTheme(new FlatLightLaf()));
        MainFrame.getInstance().getDarkThemeMenuItem().addActionListener(e -> setTheme(new FlatDarkLaf()));
    }
    
    public void saveConfigListener(){
        ToolConfig.getInstance().saveConfig();
        JOptionPane.showMessageDialog(MainFrame.getInstance(), "Saved!");
    }
    
    public void setTheme(LookAndFeel laf){
        try {
            UIManager.setLookAndFeel(laf);
            ToolConfig.getInstance().setTheme(laf.getClass().getName());
            SwingUtilities.updateComponentTreeUI(MainFrame.getInstance());
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(MainMenuBarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
