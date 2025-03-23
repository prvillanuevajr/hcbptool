/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esspi.hcbptool.ui;

import com.esspi.hcbptool.MainFrame;
import com.esspi.hcbptool.config.DBConfig;
import com.esspi.hcbptool.config.ToolConfig;
import com.esspi.hcbptool.task.SetDbConfigTask;
import com.esspi.hcbptool.task.TaskNotifier;
import java.util.Objects;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author presmelito
 */
public class SetDBPanelController {

    private static SetDBPanelController instance;

    public static SetDBPanelController getInstance() {
        if (Objects.nonNull(instance)) {
            return instance;
        }
        return  instance = new SetDBPanelController();
    }

    public SetDBPanelController() {
        MainFrame.getInstance().getDbConfigTable().setModel((DefaultTableModel) MainFrame.getInstance().getDbConfigTable().getModel());
        MainFrame.getInstance().getSetBtnSDB().addActionListener(e -> setBtnSDBActionPerformed(e));
        MainFrame.getInstance().getRemoveBtnSDB().addActionListener(e -> this.removeBtnSDBActionPerformed(e));
    }
    
    

    public void buildSetDbPanel() {
        String[] dbs = new String[ToolConfig.getInstance().getDbConfigs().size()];
        DefaultTableModel model = (DefaultTableModel) MainFrame.getInstance().getDbConfigTable().getModel();
        ((DefaultTableModel) model).setRowCount(0);
        for (int i = 0; i < ToolConfig.getInstance().getDbConfigs().size(); i++) {
            dbs[i] = ToolConfig.getInstance().getDbConfigs().get(i).toString();
            model.addRow(new String[]{
                ToolConfig.getInstance().getDbConfigs().get(i).getName(),
                ToolConfig.getInstance().getDbConfigs().get(i).getHost(),
                ToolConfig.getInstance().getDbConfigs().get(i).getPort(),
                ToolConfig.getInstance().getDbConfigs().get(i).getDbName(),});
        }
    }

    public void setDb(DBConfig dbConfig) {
        if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(MainFrame.getInstance().getSetDbPanel(), "Apply " + dbConfig.getName(), "Set DB Config", JOptionPane.YES_NO_OPTION)) {
            JDialog dialogLoader = new JOptionPane("Applying " + dbConfig.getName(), JOptionPane.INFORMATION_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[]{}).createDialog(MainFrame.getInstance().getSetDbPanel(), "Applying DB Config...");
            dialogLoader.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
            JOptionPane jOptionPane = (JOptionPane) dialogLoader.getContentPane().getComponent(0);
            SetDbConfigTask setDbConfigTask = new SetDbConfigTask(dbConfig);
            setDbConfigTask.setOnSuccess((message) -> {
                if (StringUtils.isNotEmpty(message)) {
                    jOptionPane.setMessage(jOptionPane.getMessage() + "\n" + message);
                    dialogLoader.repaint();
                    dialogLoader.pack();
                    dialogLoader.setLocationRelativeTo(MainFrame.getInstance());
                }
            });
            new TaskNotifier().setDoAfter(() -> {
                jOptionPane.setOptions(new Object[]{"OK"});
                dialogLoader.pack();
            }).setFutures(setDbConfigTask.run()).listen();
            dialogLoader.setVisible(Boolean.TRUE);
        }
    }

    public void setBtnSDBActionPerformed(java.awt.event.ActionEvent evt) {
        if (MainFrame.getInstance().getDbConfigTable().getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(MainFrame.getInstance().getSetDbPanel(), "Please select a config");
            return;
        }
        DBConfig dbConfig = ToolConfig.getInstance().getDbConfigs().get(MainFrame.getInstance().getDbConfigTable().getSelectedRow());
        SetDBPanelController.getInstance().setDb(dbConfig);
    }

    private void removeBtnSDBActionPerformed(java.awt.event.ActionEvent evt) {
        if (MainFrame.getInstance().getDbConfigTable().getSelectedRow() != -1) {
            int result = JOptionPane.showConfirmDialog(MainFrame.getInstance().getSetDbPanel(),
                    "Remove this Config?", "Remove Set DB Config", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                DBConfig dbconfig = ToolConfig.getInstance().getDbConfigs().remove(MainFrame.getInstance().getDbConfigTable().getSelectedRow());
                DefaultTableModel model = (DefaultTableModel) MainFrame.getInstance().getDbConfigTable().getModel();
                model.removeRow(MainFrame.getInstance().getDbConfigTable().getSelectedRow());
                SystemTrayController.getInstance().removeDBConfigFromSetDBTrayMenu(dbconfig);
            }
        } else {
            JOptionPane.showMessageDialog(MainFrame.getInstance().getSetDbPanel(), "Please select a config");
        }
    }

    public boolean addDbHasMissingField() {
        boolean hasMissingField = false;
        if (StringUtils.isEmpty(MainFrame.getInstance().getNameFieldADB().getText())
                || StringUtils.isEmpty(MainFrame.getInstance().getDbNameFieldADB().getText())
                || StringUtils.isEmpty(MainFrame.getInstance().getAdminIdFieldADB().getText())
                || StringUtils.isEmpty(MainFrame.getInstance().getAdminPassFieldADB().getText())
                || StringUtils.isEmpty(MainFrame.getInstance().getUserIdFieldADB().getText())
                || StringUtils.isEmpty(MainFrame.getInstance().getUserPassFieldADB().getText())
                || StringUtils.isEmpty(MainFrame.getInstance().getPortFieldADB().getText())
                || StringUtils.isEmpty(MainFrame.getInstance().getHostFieldADB().getText())) {
            JOptionPane.showMessageDialog(MainFrame.getInstance().getRootPane(), "All Fields Are Required");
            hasMissingField = true;
        }
        return hasMissingField;
    }
}
