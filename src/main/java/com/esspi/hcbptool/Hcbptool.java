package com.esspi.hcbptool;

import java.awt.AWTException;

public class Hcbptool{
    public static void main(String[] args) throws AWTException {
        /* Create and display the form */
        MainFrame frame = new MainFrame();
        java.awt.EventQueue.invokeLater(() -> {
            frame.setVisible(true);
        });
    }
}