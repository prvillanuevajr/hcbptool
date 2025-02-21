/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esspi.hcbptool.git;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author presmelito
 */
public enum GitStatus {
    ADDED_MODIFIED("AM"),
    DELETED_STAGED("D "),
    MODIFIED_MODIFIED("MM"),
    ADDED("A "),
    DELETED(" D"),
    MODIFIED("M "),
    MODIFIED_STAGED(" M"),
    RENAMED("R "),
    IGNORED("!!"),
    UNTRACKED("??");

    private final String state;

    private static Map<String, GitStatus> statusMap = new HashMap<>();

    static {
        for (GitStatus g : GitStatus.values()) {
            statusMap.put(g.getState(), g);
        }
    }

    private GitStatus(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public static List<GitStatus> getStagedStatus() {
        return Arrays.asList(ADDED_MODIFIED, ADDED, DELETED_STAGED, MODIFIED, MODIFIED_MODIFIED);
    }

    public static List<GitStatus> getUnstagedStatus() {
        return Arrays.asList(ADDED_MODIFIED, MODIFIED_MODIFIED, MODIFIED_STAGED, DELETED, RENAMED, UNTRACKED, IGNORED);
    }

    public static List<GitStatus> getStatusNeedsDiffCached() {
        return Arrays.asList(ADDED_MODIFIED, MODIFIED, MODIFIED_MODIFIED ,DELETED_STAGED);
    }

    public static List<GitStatus> getStatusNoDiff() {
        return Arrays.asList(ADDED_MODIFIED, MODIFIED, MODIFIED_MODIFIED, ADDED);
    }

    public static GitStatus getBystatus(String status) {
        return statusMap.get(status);
    }
}
