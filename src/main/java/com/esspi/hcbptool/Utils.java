/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esspi.hcbptool;

import com.esspi.hcbptool.ui.EncryptionPanelController;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author presmelito
 */
public class Utils {

    public static void openFile(File file) {
        try {
            Desktop.getDesktop().open(file);
        } catch (IOException ex) {
            Logger.getLogger(EncryptionPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
