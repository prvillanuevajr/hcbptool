/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esspi.hcbptool.config;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class DBConfig {

    private String name;
    private String dbName;
    @JsonAlias("dbaUser")
    private String adminId;
    @JsonAlias("dbaPassword")
    private String adminPassword;
    @JsonAlias("dbUser")
    private String userId;
    @JsonAlias("dbPassword")
    private String userPassword;
    @JsonAlias("dbPort")
    private String port;
    @JsonAlias("dbHost")
    private String host;
    
    @Override
    public String toString() {
        return String.format("%-15s = %s:%s %s", name, host, port, dbName);
    }

}
