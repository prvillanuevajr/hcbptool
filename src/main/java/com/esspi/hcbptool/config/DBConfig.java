/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esspi.hcbptool.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.MessageFormat;
import oracle.jdbc.driver.OracleDriver;

/**
 *
 * @author presmelito.villanuev
 */
public class DBConfig {

    private String name;
    private String dbName;
    private String adminId;
    private String adminPassword;
    private String userId;
    private String userPassword;
    private String port;
    private String host;

    public DBConfig(String name, String dbName, String adminId, String adminPassword, String userId, String userPassword, String port, String host) {
        this.name = name;
        this.dbName = dbName;
        this.adminId = adminId;
        this.adminPassword = adminPassword;
        this.userId = userId;
        this.userPassword = userPassword;
        this.port = port;
        this.host = host;
    }

    public DBConfig() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

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
