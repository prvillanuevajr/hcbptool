/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esspi.hcbptool.ui;

import com.esspi.hcbptool.Hcbptool;
import com.esspi.hcbptool.MainFrame;
import com.esspi.hcbptool.Utils;
import com.esspi.hcbptool.concurrency.TheExecutor;
import com.esspi.hcbptool.config.EncryptionKey;
import com.esspi.hcbptool.config.ToolConfig;
import com.esspi.hcbptool.task.Task;
import com.esspi.hcbptool.task.TaskNotifier;
import com.esspi.hcbptool.task.UpdateEncryptionKeysTask;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.util.Objects;
import java.util.function.Consumer;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author presmelito
 */
public class EncryptionPanelController {

    private static EncryptionPanelController instance;

    public static EncryptionPanelController getInstance() {
        return Objects.nonNull(instance) ? instance : new EncryptionPanelController();
    }

    public void init() {
        addComponentListeners();
        addButtonIcons();
        refreshTable();
    }

    public void addEncryption() {
        String name = MainFrame.getInstance().getEncryptiontabNameField().getText().trim();
        String merchantKey = MainFrame.getInstance().getEncryptiontabMerchantKeyField().getText().trim();
        String sessionKey = MainFrame.getInstance().getEncryptiontabSessionkeyField().getText().trim();
        if (StringUtils.isAnyEmpty(name,merchantKey,sessionKey)) {
            JOptionPane.showMessageDialog(MainFrame.getInstance(), "All fields are required!");
            return;
        }
        EncryptionKey key = new EncryptionKey(name, merchantKey, sessionKey);
        ToolConfig.getInstance().getEncriptionKeys().add(key);
        ToolConfig.getInstance().saveConfig();
        MainFrame.getInstance().getEncryptiontabNameField().setText(StringUtils.EMPTY);
        MainFrame.getInstance().getEncryptiontabMerchantKeyField().setText(StringUtils.EMPTY);
        MainFrame.getInstance().getEncryptiontabSessionkeyField().setText(StringUtils.EMPTY);
        refreshTable();
    }

    private void addComponentListeners() {
        MainFrame.getInstance().getEpcAddButton().addActionListener(e -> addEncryption());
        MainFrame.getInstance().getEpcRemoveButton().addActionListener(e -> removeEncryption());
        MainFrame.getInstance().getEpcSetButton().addActionListener(e -> setEncryption());
        MainFrame.getInstance().getEpcViewMerchantKeyButton().addActionListener(e -> Utils.openFile(ToolConfig.getInstance().getWorkspacePath().resolve("WC/xml/config/merchantKey.xml").toFile()));
        MainFrame.getInstance().getEpcViewWCServerFileButton().addActionListener(e -> Utils.openFile(ToolConfig.getInstance().getWorkspacePath().resolve("WC/xml/config/wc-server.xml").toFile()));
    }

    private void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) MainFrame.getInstance().getEpcTable().getModel();
        ((DefaultTableModel) model).setRowCount(0);
        for (int i = 0; i < ToolConfig.getInstance().getEncriptionKeys().size(); i++) {
            model.addRow(new String[]{
                ToolConfig.getInstance().getEncriptionKeys().get(i).getName(),
                ToolConfig.getInstance().getEncriptionKeys().get(i).getMerchantKey(),
                ToolConfig.getInstance().getEncriptionKeys().get(i).getSessionKey()});
        }
    }

    private void removeEncryption() {
        if (MainFrame.getInstance().getEpcTable().getSelectedRow() != -1) {
            int result = JOptionPane.showConfirmDialog(MainFrame.getInstance().getSetDbPanel(),
                    "Remove this Config?", "Remove Encryption Config", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                ToolConfig.getInstance().getEncriptionKeys().remove(MainFrame.getInstance().getEpcTable().getSelectedRow());
                DefaultTableModel model = (DefaultTableModel) MainFrame.getInstance().getEpcTable().getModel();
                model.removeRow(MainFrame.getInstance().getEpcTable().getSelectedRow());
            }
        } else {
            JOptionPane.showMessageDialog(MainFrame.getInstance().getSetDbPanel(), "Please select encryption");
        }
        refreshTable();
    }

    private void setEncryption() {
        
        EncryptionKey key = ToolConfig.getInstance().getEncriptionKeys().get(MainFrame.getInstance().getEpcTable().getSelectedRow());

        if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(MainFrame.getInstance(), "Apply " + key.getName(), "Set DB Config", JOptionPane.YES_NO_OPTION)) {
            JDialog dialogLoader = new JOptionPane("Applying " + key.getName(), JOptionPane.INFORMATION_MESSAGE, JOptionPane.PLAIN_MESSAGE, new ImageIcon(Hcbptool.class.getClassLoader().getResource("running.gif")), new Object[]{}).createDialog(MainFrame.getInstance().getSetDbPanel(), "Applying DB Config...");
            dialogLoader.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
            JOptionPane jOptionPane = (JOptionPane) dialogLoader.getContentPane().getComponent(0);
            Task task = new UpdateEncryptionKeysTask(key);
            Consumer<String> doAfter = (message) -> {
                if (StringUtils.isNotEmpty(message)) {
                    jOptionPane.setMessage(jOptionPane.getMessage() + "\n" + message);
                    dialogLoader.repaint();
                    dialogLoader.pack();
                    dialogLoader.setLocationRelativeTo(MainFrame.getInstance());
                }
            };
            task.setOnSuccess(doAfter);
            task.setOnError(doAfter);
            new TaskNotifier().setDoAfter(() -> {
                jOptionPane.setOptions(new Object[]{"OK"});
                jOptionPane.setIcon(null);
                dialogLoader.pack();
            }).setFutures(TheExecutor.getInstance().getExecutorService().submit(task)).listen();
            dialogLoader.setVisible(Boolean.TRUE);
        }

    }



    private void addButtonIcons() {
        MainFrame.getInstance().getEpcViewMerchantKeyButton().setIcon(new FlatSVGIcon("com/esspi/hcbptool/svgs/file-code.svg"));
        MainFrame.getInstance().getEpcViewWCServerFileButton().setIcon(new FlatSVGIcon("com/esspi/hcbptool/svgs/file-code.svg"));
        MainFrame.getInstance().getEpcRemoveButton().setIcon(new FlatSVGIcon("com/esspi/hcbptool/svgs/trash-can.svg"));
        MainFrame.getInstance().getEpcAddButton().setIcon(new FlatSVGIcon("com/esspi/hcbptool/svgs/plus.svg"));
        MainFrame.getInstance().getEpcSetButton().setIcon(new FlatSVGIcon("com/esspi/hcbptool/svgs/user-secret.svg"));
        MainFrame.getInstance().getEpcSetButton().setIcon(new FlatSVGIcon("com/esspi/hcbptool/svgs/user-secret.svg"));
        MainFrame.getInstance().getJTabbedPane().setIconAt(3,new FlatSVGIcon("com/esspi/hcbptool/svgs/user-secret.svg"));
    }
}
