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
 * @author presmelito
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EncryptionKey {
    private String name;
    private String merchantKey;
    private String sessionKey;
}
