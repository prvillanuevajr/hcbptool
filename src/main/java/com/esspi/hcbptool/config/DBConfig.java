/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esspi.hcbptool.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author presmelito.villanuev
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DBConfig {

    private String name;
    private String dbName;
    private String adminId;
    private String adminPassword;
    private String userId;
    private String userPassword;
    private String port;
    private String host;
    
    @Override
    public String toString() {
        return String.format("%-15s = %s:%s %s", name, host, port, dbName);
    }

}
