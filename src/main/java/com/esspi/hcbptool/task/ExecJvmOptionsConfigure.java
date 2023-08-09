/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esspi.hcbptool.task;

import com.esspi.hcbptool.constants.Constants;
import com.esspi.hcbptool.constants.SetDbConstants;

/**
 *
 * @author presmelito.villanuev
 */
public class ExecJvmOptionsConfigure extends Task {

    @Override
    public String call() throws Exception {
        this.executeCommand(this.getCommands());
        return Constants.DONE_STATUS;
    }

    @Override
    protected String[] getCommands() {
        return new String[]{
            Constants.JAVA,
            SetDbConstants.PARAM_CLASSPATH,
            SetDbConstants.CLASS_PATH,
            SetDbConstants.CLASSNAME_JVMOPTIONSCONFIGURE,
            SetDbConstants.WC_TOOLKIT_JVM_OPTIONS,
            SetDbConstants.LIBERTY_SEARCH_JVM_OPTIONS
        };
    }

}
