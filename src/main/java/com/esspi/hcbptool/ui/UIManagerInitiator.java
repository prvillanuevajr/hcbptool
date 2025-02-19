/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esspi.hcbptool.ui;

import java.awt.Font;
import java.util.Objects;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

/**
 *
 * @author presmelito
 */
public class UIManagerInitiator {
    private static UIManagerInitiator instance;

    public static UIManagerInitiator getInstance() {
        if (Objects.nonNull(instance)) {
            return instance;
        }
        return instance = new UIManagerInitiator();
    }
    
    public void init(){
        UIManager.put("Button.font", new FontUIResource(new Font("SegoUi", Font.PLAIN, 14)));
        UIManager.put("Label.font", new FontUIResource(new Font("SegoUi", Font.PLAIN, 14)));
        UIManager.put("TextField.font", new FontUIResource(new Font("SegoUi", Font.PLAIN, 14)));
        UIManager.put("TextField.font", new FontUIResource(new Font("SegoUi", Font.PLAIN, 14)));
        UIManager.put("Table.font", new FontUIResource(new Font("SegoUi", Font.PLAIN, 14)));
        UIManager.put("CheckBox.font", new FontUIResource(new Font("SegoUi", Font.PLAIN, 14)));
        UIManager.put("TabbedPane.font", new FontUIResource(new Font("SegoUi", Font.PLAIN, 14)));
        UIManager.put("Menu.font", new FontUIResource(new Font("SegoUi", Font.PLAIN, 14)));
        UIManager.put("MenuItem.font", new FontUIResource(new Font("SegoUi", Font.PLAIN, 14)));
    }
}
