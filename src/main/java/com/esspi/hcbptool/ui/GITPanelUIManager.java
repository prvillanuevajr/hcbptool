/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esspi.hcbptool.ui;

import com.esspi.hcbptool.MainFrame;
import com.esspi.hcbptool.concurrency.TheExecutor;
import com.esspi.hcbptool.git.GitDiffTask;
import com.esspi.hcbptool.git.GitFile;
import com.esspi.hcbptool.git.GitFileListRenderer;
import com.esspi.hcbptool.git.GitStatus;
import com.esspi.hcbptool.git.GitStatusTask;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 *
 * @author presmelito
 */
public class GITPanelUIManager {

    private static GITPanelUIManager instance;

    public static GITPanelUIManager getInstance() {
        if (Objects.nonNull(instance)) {
            return instance;
        }
        return instance = new GITPanelUIManager();
    }

    public GITPanelUIManager() {
        addComponentListeners();
    }

    public void init() {

    }

    private void addComponentListeners() {
        MainFrame.getInstance().getGitPanel().addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                System.out.println(MainFrame.getInstance().getSize());
                doGitStatus();
            }
        });
        MainFrame.getInstance().getGitFileStagedList().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gitDiff(MainFrame.getInstance().getGitFileStagedList().getSelectedValue());
            }
        });
        MainFrame.getInstance().getGitFileUnstagedList().addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                gitDiff(MainFrame.getInstance().getGitFileUnstagedList().getSelectedValue());
            }
        });
    }

    public void doGitStatus() {
        DefaultListModel<GitFile> stagedFilesListModel = new DefaultListModel<>();
        DefaultListModel<GitFile> unStagedFilesListModel = new DefaultListModel<>();
        JList stagedList = MainFrame.getInstance().getGitFileStagedList();
        JList unstagedList = MainFrame.getInstance().getGitFileUnstagedList();
        stagedList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        unstagedList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        stagedList.setCellRenderer(new GitFileListRenderer());
        unstagedList.setCellRenderer(new GitFileListRenderer());
        
        GitStatusTask task = new GitStatusTask();
        task.setDuringRun(e -> {
            GitFile file = new GitFile(e);
            if (GitStatus.getStagedStatus().contains(file.getState())) {
                stagedFilesListModel.addElement(file);
            }if (GitStatus.getUnstagedStatus().contains(file.getState())){
                unStagedFilesListModel.addElement(file);
            }
        });
        task.setOnSuccess(e -> {
            stagedList.setModel(stagedFilesListModel);
            unstagedList.setModel(unStagedFilesListModel);
        });

        TheExecutor.getInstance().getExecutorService().submit(task);
    }

    public void gitDiff(GitFile file) {
        MainFrame.getInstance().getGitDiffTextPane().setText("");
        GitDiffTask task = new GitDiffTask();
        task.setFile(file);
        task.setDuringRun(line -> {
            gitDiffToGitextPane(line+"\n");
        });
        TheExecutor.getInstance().getExecutorService().submit(task);
    }

    private void gitDiffToGitextPane(String line) {
        Document document = MainFrame.getInstance().getGitDiffTextPane().getStyledDocument();
        SimpleAttributeSet attributeSet = new SimpleAttributeSet();
        StyleConstants.setForeground(attributeSet, Color.GRAY);
        if (line.startsWith("+")) {
            StyleConstants.setForeground(attributeSet, Color.GREEN.darker());
        }else if (line.startsWith("-")) {
            StyleConstants.setForeground(attributeSet, Color.RED.darker());
        }
        try {
                document.insertString(document.getLength(), line, attributeSet);
            } catch (BadLocationException ex) {
                Logger.getLogger(GITPanelUIManager.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

}
