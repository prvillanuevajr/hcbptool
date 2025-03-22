package com.esspi.hcbptool;

import com.esspi.hcbptool.config.ToolConfig;
import java.awt.AWTException;
import java.util.Properties;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;

public class Hcbptool {

    public static void main(String[] args) throws AWTException {
        /* Create and display the form */
        configureLogger();
        setTheme();
        MainFrame frame = new MainFrame();
        frame.setLocationRelativeTo(null);
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

    private static void configureLogger() {
        Properties properties = System.getProperties();
        if (!System.getenv().containsKey("app.hcbptool.env") || System.getenv("app.hcbptool.env").equals("dev")) {
            properties.setProperty("org.slf4j.simpleLogger.logFile","System.out");
        }
    }
}
