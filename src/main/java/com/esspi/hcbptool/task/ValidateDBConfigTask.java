/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esspi.hcbptool.task;

import com.esspi.hcbptool.config.DBConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.MessageFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import oracle.jdbc.driver.OracleDriver;
import org.slf4j.LoggerFactory;

/**
 *
 * @author presmelito
 */
@Data
@NoArgsConstructor
public class ValidateDBConfigTask extends Task {

    private DBConfig dbConfig;
    private boolean isValid = false;
    private String message = "";

    public ValidateDBConfigTask(DBConfig dbConfig) {
        this.dbConfig = dbConfig;
    }

    @Override
    public String call() throws Exception {
        this.validate();
        if (isValid()) {
            doOnSuccess(this.getMessage());
        } else {
            doOnError(this.getMessage());
        }
        return this.getMessage();
    }

    private void validate() {
        String url = MessageFormat.format("jdbc:oracle:thin:@//{0}:{1}/{2}", dbConfig.getHost(), dbConfig.getPort(), dbConfig.getDbName());
        DriverManager.setLoginTimeout(2);
        testConnection(url, dbConfig.getUserId(), dbConfig.getUserPassword());
        testConnection(url, dbConfig.getAdminId(), dbConfig.getAdminPassword());
    }

    private void testConnection(String url, String userName, String password) {
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            Class.forName(OracleDriver.class.getName());
            if (conn.isValid(2)) {
                this.message += MessageFormat.format("USER ID: {0} OK!\n", userName);
                setValid(Boolean.TRUE);
            } else {
                this.message += MessageFormat.format("USER ID: {0} Invalid!\n", userName);
                setValid(Boolean.FALSE);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            this.message += MessageFormat.format("USER ID: {0} {1}\n", userName, ex.getMessage());
            setValid(Boolean.FALSE);
        }
        LoggerFactory.getLogger(ValidateDBConfigTask.class).info(this.getMessage());
    }
}
