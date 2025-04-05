/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esspi.hcbptool.ui;

import com.esspi.hcbptool.Hcbptool;
import com.esspi.hcbptool.MainFrame;
import com.esspi.hcbptool.concurrency.TheExecutor;
import com.esspi.hcbptool.config.DBConfig;
import com.esspi.hcbptool.config.ToolConfig;
import com.esspi.hcbptool.task.Task;
import com.esspi.hcbptool.task.ValidateDBConfigTask;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
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
        JDialog dialogLoader = new JOptionPane("Validating...",JOptionPane.INFORMATION_MESSAGE,0, new ImageIcon(Hcbptool.class.getClassLoader().getResource("running.gif")), new Object[]{}).createDialog("Validating Database...");
        dialogLoader.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        JOptionPane jop = (JOptionPane) dialogLoader.getContentPane().getComponent(0);
        Task validateDBConfigTask = new ValidateDBConfigTask(dBConfig);
        Consumer<String> doAfter = message -> {
            jop.setMessage(message);
            jop.setOptions(new Object[]{"OK"});
            jop.setIcon(null);
            dialogLoader.pack();
            dialogLoader.setLocationRelativeTo(MainFrame.getInstance());
        };
        validateDBConfigTask.setOnError(doAfter);
        validateDBConfigTask.setOnSuccess(doAfter);
        TheExecutor.getInstance().getExecutorService().submit(validateDBConfigTask);
        dialogLoader.setVisible(true);
    }

    private void addBtnADBActionPerformed(java.awt.event.ActionEvent evt) {
        if (SetDBPanelController.getInstance().addDbHasMissingField() || addDbNameIsInValid(evt)) {
            return;
        }
       
        DBConfig dBConfig = new DBConfig(MainFrame.getInstance().getNameFieldADB().getText(), MainFrame.getInstance().getDbNameFieldADB().getText(),
                MainFrame.getInstance().getAdminIdFieldADB().getText(), MainFrame.getInstance().getAdminPassFieldADB().getText(),
                MainFrame.getInstance().getUserIdFieldADB().getText(), MainFrame.getInstance().getUserPassFieldADB().getText(),
                MainFrame.getInstance().getPortFieldADB().getText(), MainFrame.getInstance().getHostFieldADB().getText());
        Task validateTask = new ValidateDBConfigTask(dBConfig);
        JDialog dialogLoader = new JOptionPane("Validating...",JOptionPane.INFORMATION_MESSAGE,0, new ImageIcon(Hcbptool.class.getClassLoader().getResource("running.gif")), new Object[]{}).createDialog("Validating Database...");
        JOptionPane jOptionPane = (JOptionPane) dialogLoader.getContentPane().getComponent(0);
        dialogLoader.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        validateTask.setOnSuccess(message -> {
            dialogLoader.dispose();
            ToolConfig.getInstance().getDbConfigs().add(dBConfig);
            Arrays.stream(MainFrame.getInstance().getAddDbPanel().getComponents()).forEach(component -> {
                if (component instanceof JTextField) {
                    ((JTextField) component).setText("");
                }
            });
            SystemTrayController.getInstance().addSetDbConfigToTrayMenu(dBConfig);
            JOptionPane.showMessageDialog(MainFrame.getInstance().getRootPane(), "Config Added!");
        });
        validateTask.setOnError(message -> {
            jOptionPane.setMessage(message);
            jOptionPane.setOptions(new Object[]{"OK"});
            jOptionPane.setIcon(null);
            dialogLoader.pack();
            dialogLoader.setLocationRelativeTo(MainFrame.getInstance());
        });
        TheExecutor.getInstance().getExecutorService().submit(validateTask);
        dialogLoader.setVisible(true);
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
