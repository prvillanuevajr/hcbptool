/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esspi.hcbptool.ui;

import com.esspi.hcbptool.MainFrame;
import com.esspi.hcbptool.config.ToolConfig;
import com.esspi.hcbptool.constants.Constants;
import com.esspi.hcbptool.task.TaskNotifier;
import com.esspi.hcbptool.transfer.ToRepositoryTransfer;
import com.esspi.hcbptool.transfer.ToWorkspaceTransfer;
import com.esspi.hcbptool.transfer.Transfer;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author presmelito
 */
public class TransferPanelController {

    public static final ToolConfig config = ToolConfig.getInstance();
    public List<String> selectedFolders = config.getSelectedFolders();
    public Map<String, JCheckBox> foldersCBMap = new HashMap<>();
    private static TransferPanelController instance;
    
    public static TransferPanelController getInstance(){
        if (Objects.nonNull(instance)) {
            return instance;
        }
        return instance = new TransferPanelController();
    }

    public TransferPanelController() {
        addComponentListener();
        addComponentIcons();
    }
    
    

    public void initTransferPanel() {
        String repoPath = Objects.isNull(config.getRepoPath()) ? "" : config.getRepoPath().toString();
        String wsPath = Objects.isNull(config.getWorkspacePath()) ? "" : config.getWorkspacePath().toString();
        MainFrame.getInstance().getRepoPathTf().setText(Constants.REPOPATH_INITIAL_TEXT + repoPath);
        MainFrame.getInstance().getWorkspacePathTf().setText(Constants.WORKSPACEPATH_INITIAL_TEXT + wsPath);
        System.err.println("");
        for (String folder : config.getFolders()) {
            JCheckBox cb = new JCheckBox();
            cb.setName(folder);
            cb.setVisible(Boolean.TRUE);
            cb.setText(folder);
            foldersCBMap.put(folder, cb);
            MainFrame.getInstance().getFoldersPanel().add(cb);
            cb.setSelected(selectedFolders.contains(folder));
            cb.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    selectedFolders.add(folder);
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    selectedFolders.remove(folder);
                }
            });
        }
    }

    public void toRepoBtnActionPerformed(java.awt.event.ActionEvent evt) {
        if (!transferPathsAreValid()) {
            return;
        }
        if (JOptionPane.showConfirmDialog(MainFrame.getInstance().getTranPanel(), "Are you sure?", "To Repository", JOptionPane.YES_NO_OPTION, 1) == JOptionPane.YES_OPTION) {
            resetFoldersCheckBoxes();
            JDialog dialog = new JOptionPane("Transferring...", JOptionPane.INFORMATION_MESSAGE, JOptionPane.NO_OPTION, null, new Object[]{}).createDialog("To Repository...");
            dialog.setLocationRelativeTo(MainFrame.getInstance());
            Transfer repoTransfer = new ToRepositoryTransfer();
            repoTransfer.setBeforeRun((folder) -> () -> foldersCBMap.get(folder).setForeground(Color.LIGHT_GRAY));
            repoTransfer.setDuringRun((folder) -> (message) -> foldersCBMap.get(folder).setText(message));
            repoTransfer.setOnSuccess((folder) -> (message) -> {
                foldersCBMap.get(folder).setForeground(new Color(24, 170, 55));
                foldersCBMap.get(folder).setText(folder + " " + message);
            });
            new TaskNotifier().setFutures(repoTransfer.transfer()).setDoAfter(() -> {
                JOptionPane.showMessageDialog(MainFrame.getInstance(), "Done Transferring");
                dialog.dispose();
            }).listen();
            dialog.setVisible(Boolean.TRUE);
        }
    }

    public void toWorkspaceBtnActionPerformed(java.awt.event.ActionEvent evt) {
        if (!transferPathsAreValid()) {
            return;
        }
        if (JOptionPane.showConfirmDialog(MainFrame.getInstance().getTranPanel(), "Are you sure?", "To Workspace", JOptionPane.YES_NO_OPTION, 1) == JOptionPane.YES_OPTION) {
            resetFoldersCheckBoxes();
            JDialog dialog = new JOptionPane("Transferring...", JOptionPane.INFORMATION_MESSAGE, JOptionPane.NO_OPTION, null, new Object[]{}).createDialog("Transferring...");
            dialog.setLocationRelativeTo(MainFrame.getInstance());
            Transfer toWorkspace = new ToWorkspaceTransfer();
            toWorkspace.setBeforeRun((folder) -> () -> foldersCBMap.get(folder).setForeground(Color.LIGHT_GRAY));
            toWorkspace.setDuringRun((folder) -> (message) -> foldersCBMap.get(folder).setText(message));
            toWorkspace.setOnSuccess((folder) -> (message) -> {
                foldersCBMap.get(folder).setForeground(new Color(24, 170, 55));
                foldersCBMap.get(folder).setText(folder + " " + message);
            });
            new TaskNotifier().setFutures(toWorkspace.transfer()).setDoAfter(() -> {
                dialog.dispose();
                JOptionPane.showMessageDialog(MainFrame.getInstance(), "Done Transferring");
            }).listen();
            dialog.setVisible(Boolean.TRUE);
        }

    }

    public boolean transferPathsAreValid() {
        Path repoPath = ToolConfig.getInstance().getRepoPath();
        Path workspacePath = ToolConfig.getInstance().getWorkspacePath();
        boolean isInvalid = Objects.isNull(repoPath) || Objects.isNull(workspacePath);
        if (isInvalid) {
            JOptionPane.showMessageDialog(MainFrame.getInstance(), "Set Repository and Workspace folders correctly.");
        }
        return !isInvalid;
    }

    public void resetFoldersCheckBoxes() {
        foldersCBMap.entrySet().forEach((e) -> {
            e.getValue().setText(e.getKey());
            e.getValue().setForeground(Color.BLACK);
        });
    }

    private void addComponentListener() {
        MainFrame.getInstance().getToRepoBtn().addActionListener(e -> toRepoBtnActionPerformed(e));
        MainFrame.getInstance().getToWorkspaceBtn().addActionListener(e -> toWorkspaceBtnActionPerformed(e));
        MainFrame.getInstance().getTranSelectAllBtn().addActionListener(e -> foldersCBMap.entrySet().forEach(cb -> cb.getValue().setSelected(Boolean.TRUE)));
        MainFrame.getInstance().getTranDeSelectAllBtn().addActionListener(e -> foldersCBMap.entrySet().forEach(cb -> cb.getValue().setSelected(Boolean.FALSE)));
    }

    private void addComponentIcons() {
        MainFrame.getInstance().getTranSelectAllBtn().setIcon(new FlatSVGIcon("com/esspi/hcbptool/svgs/square-check.svg"));
        MainFrame.getInstance().getTranDeSelectAllBtn().setIcon(new FlatSVGIcon("com/esspi/hcbptool/svgs/square.svg"));
    }
}
