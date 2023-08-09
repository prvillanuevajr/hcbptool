package com.esspi.hcbptool;

import java.awt.AWTException;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import javax.swing.ImageIcon;


public class Hcbptool{
    public static void main(String[] args) throws AWTException {
        /* Create and display the form */
        MainFrame frame = new MainFrame();
        java.awt.EventQueue.invokeLater(() -> {
            frame.setVisible(true);
        });
    }
}