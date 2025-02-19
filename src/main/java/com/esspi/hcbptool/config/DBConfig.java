/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esspi.hcbptool.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.MessageFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import oracle.jdbc.driver.OracleDriver;

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

    public String validate() {
        String url = MessageFormat.format("jdbc:oracle:thin:@//{0}:{1}/{2}", host, port, dbName);
        DriverManager.setLoginTimeout(2);
        try {
            Class.forName(OracleDriver.class.getName());
            Connection conn = DriverManager.getConnection(url, userId, userPassword);
            String result;
            if (conn.isValid(2)) {
                result = "Success!";
            } else {
                result = "Invalid!";
            }
            conn.close();
            return result;
        } catch (ClassNotFoundException | SQLException ex) {
            return ex.getMessage();
        }
    }

    @Override
    public String toString() {
        return String.format("%-15s = %s:%s %s", name, host, port, dbName);
    }

}
