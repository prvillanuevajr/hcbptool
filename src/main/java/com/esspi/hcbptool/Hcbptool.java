package com.esspi.hcbptool;

import com.esspi.hcbptool.config.ToolConfig;
import java.awt.AWTException;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;

public class Hcbptool {

    public static void main(String[] args) throws AWTException {
        /* Create and display the form */
        setTheme();
        MainFrame frame = new MainFrame();

        java.awt.EventQueue.invokeLater(() -> {
            frame.setVisible(true);
        });
    }

    private static void setTheme() {
        String theme = ToolConfig.getInstance().getTheme();
        try {
            Class clazz = Class.forName(theme);
            UIManager.setLookAndFeel((LookAndFeel) clazz.newInstance());
        } catch (Exception ex) {
        }
        
    }
}
