/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.esspi.hcbptool;

import com.esspi.hcbptool.config.ToolConfig;
import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ItemEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author presmelito.villanuev
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        initSystemTray();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setTitle("Hc Bp Tool");
        setForeground(java.awt.Color.lightGray);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 459, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 325, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables


    private void initSystemTray() {
        try {
            SystemTray tray = SystemTray.getSystemTray();
            TrayIcon trayIcon = new TrayIcon(new ImageIcon(Hcbptool.class.getClassLoader().getResource("presico.png")).getImage());
            PopupMenu popupMenu = new PopupMenu();
            MenuItem exitTrayMenu = new MenuItem("Exit");
            MenuItem showTrayMenu = new MenuItem("Show");
            MenuItem toRepoTrayMenu = new MenuItem("To Repository");
            MenuItem toWorkspaceTrayMenu = new MenuItem("To Workspace");
            
            trayIcon.addActionListener((e) -> this.setVisible(Boolean.TRUE));

            exitTrayMenu.addActionListener(e -> System.exit(0));

            showTrayMenu.addActionListener(e -> this.setVisible(Boolean.TRUE));

            popupMenu.add(toWorkspaceTrayMenu);
            popupMenu.add(toRepoTrayMenu);
            popupMenu.addSeparator();
            popupMenu.add(showTrayMenu);
            popupMenu.add(exitTrayMenu);
            trayIcon.setPopupMenu(popupMenu);
            tray.add(trayIcon);
        } catch (AWTException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}