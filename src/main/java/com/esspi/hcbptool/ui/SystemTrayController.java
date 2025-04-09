/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esspi.hcbptool.ui;

import com.esspi.hcbptool.Hcbptool;
import com.esspi.hcbptool.MainFrame;
import com.esspi.hcbptool.config.DBConfig;
import com.esspi.hcbptool.config.ToolConfig;
import java.awt.AWTException;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author presmelito
 */
public class SystemTrayController {


    private Menu setDbMenu = new Menu("Set DB");
    private static SystemTrayController instance;

    public static SystemTrayController getInstance() {
        if (Objects.nonNull(instance)) {
            return instance;
        }
        return  instance = new SystemTrayController();
    }

    public void initSystemTray() {
        try {
            SystemTray tray = SystemTray.getSystemTray();
            TrayIcon trayIcon = new TrayIcon(new ImageIcon(Hcbptool.class.getClassLoader().getResource("presico.png")).getImage());
            PopupMenu popupMenu = new PopupMenu();
            MenuItem exitTrayMenu = new MenuItem("Exit");
            MenuItem showTrayMenu = new MenuItem("Show");
            MenuItem toRepoTrayMenu = new MenuItem("To Repository");
            MenuItem toWorkspaceTrayMenu = new MenuItem("To Workspace");

            trayIcon.addActionListener((e) -> MainFrame.getInstance().setVisible(Boolean.TRUE));

            toRepoTrayMenu.addActionListener((e) -> TransferPanelController.getInstance().toRepoBtnActionPerformed(e));

            toWorkspaceTrayMenu.addActionListener((e) -> TransferPanelController.getInstance().toWorkspaceBtnActionPerformed(e));

            exitTrayMenu.addActionListener(e -> System.exit(0));

            showTrayMenu.addActionListener(e -> {
                MainFrame.getInstance().setVisible(Boolean.TRUE);
            });

            MenuItem menuItemTemp;
            for (DBConfig dbConfig : ToolConfig.getInstance().getDbConfigs()) {
                menuItemTemp = new MenuItem(dbConfig.getName());
                setDbMenu.add(menuItemTemp);
                menuItemTemp.addActionListener((e) -> SetDBPanelController.getInstance().setDb(dbConfig));
            }

            popupMenu.add(setDbMenu);
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

    public void addSetDbConfigToTrayMenu(DBConfig dBConfig) {
        MenuItem menuItemTemp = new MenuItem(dBConfig.getName());
        setDbMenu.add(menuItemTemp);
        menuItemTemp.addActionListener((e) -> SetDBPanelController.getInstance().setDb(dBConfig));
    }

    public void removeDBConfigFromSetDBTrayMenu(DBConfig dbconfig) {
        for (int i = 0; i < setDbMenu.getItemCount(); i++) {
            if (setDbMenu.getItem(i).getLabel().equals(dbconfig.getName())) {
                setDbMenu.remove(setDbMenu.getItem(i));
                break;
            }
        }
    }
}
