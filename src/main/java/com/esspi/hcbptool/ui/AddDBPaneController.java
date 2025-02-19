/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esspi.hcbptool.ui;

import com.esspi.hcbptool.MainFrame;
import com.esspi.hcbptool.config.DBConfig;
import com.esspi.hcbptool.config.ToolConfig;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Objects;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author presmelito
 */
public class AddDBPaneController {

    private static AddDBPaneController instance;

    public static AddDBPaneController getInstance() {
        return Objects.nonNull(instance) ? instance : new AddDBPaneController();
    }

    public void init() {
        MainFrame.getInstance().getTestBtnADB().addActionListener(e -> testBtnADBActionPerformed(e));
        MainFrame.getInstance().getAddBtnADB().addActionListener(e -> addBtnADBActionPerformed(e));
    }

    public void testBtnADBActionPerformed(java.awt.event.ActionEvent evt) {
        DBConfig dBConfig = new DBConfig(MainFrame.getInstance().getNameFieldADB().getText(), MainFrame.getInstance().getDbNameFieldADB().getText(),
                MainFrame.getInstance().getAdminIdFieldADB().getText(), MainFrame.getInstance().getAdminPassFieldADB().getText(),
                MainFrame.getInstance().getUserIdFieldADB().getText(), MainFrame.getInstance().getUserPassFieldADB().getText(),
                MainFrame.getInstance().getPortFieldADB().getText(), MainFrame.getInstance().getHostFieldADB().getText());
        String message = dBConfig.validate();
        JOptionPane.showMessageDialog(MainFrame.getInstance(), message);
    }

    private void addBtnADBActionPerformed(java.awt.event.ActionEvent evt) {
        if (SetDBPanelController.getInstance().addDbHasMissingField()) {
            return;
        }
        if (addDbNameIsInValid(evt)) {
            return;
        }
        DBConfig dBConfig = new DBConfig(MainFrame.getInstance().getNameFieldADB().getText(), MainFrame.getInstance().getDbNameFieldADB().getText(),
                MainFrame.getInstance().getAdminIdFieldADB().getText(), MainFrame.getInstance().getAdminPassFieldADB().getText(),
                MainFrame.getInstance().getUserIdFieldADB().getText(), MainFrame.getInstance().getUserPassFieldADB().getText(),
                MainFrame.getInstance().getPortFieldADB().getText(), MainFrame.getInstance().getHostFieldADB().getText());
        ToolConfig.getInstance().getDbConfigs().add(dBConfig);
        Arrays.stream(MainFrame.getInstance().getAddDbPanel().getComponents()).forEach(component -> {
            if (component instanceof JTextField) {
                ((JTextField) component).setText("");
            }
        });
        SystemTrayController.getInstance().addSetDbConfigToTrayMenu(dBConfig);
        JOptionPane.showMessageDialog(MainFrame.getInstance().getRootPane(), "Config Added!");
    }

    private boolean addDbNameIsInValid(ActionEvent evt) throws HeadlessException {
        boolean isInvalid = false;
        if (ToolConfig.getInstance().getDbConfigs().stream().anyMatch(d -> d.getName().equalsIgnoreCase(MainFrame.getInstance().getNameFieldADB().getText()))) {
            JOptionPane.showMessageDialog(MainFrame.getInstance().getRootPane(), "Duplicate Name");
            isInvalid = true;
        }
        return isInvalid;
    }
}
