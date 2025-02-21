/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esspi.hcbptool.git;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author presmelito
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GitFile {
    private String name;
    private GitStatus state;

    public GitFile(String fileNameAndState) {
        this.state = GitStatus.getBystatus(fileNameAndState.substring(0,2));
        this.name = fileNameAndState.substring(3,fileNameAndState.length());
    }
}
