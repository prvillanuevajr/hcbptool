/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esspi.hcbptool.task;

import com.esspi.hcbptool.config.DBConfig;
import com.esspi.hcbptool.constants.Constants;
import com.esspi.hcbptool.constants.SetDbConstants;

/**
 *
 * @author presmelito.villanuev
 */
public class ExecSearchDatasourceConfigureTask extends Task {

    private DBConfig dbConfig;

    public ExecSearchDatasourceConfigureTask(DBConfig dbConfig) {
        this.dbConfig = dbConfig;
    }

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
            SetDbConstants.CLASSNAME_SEARCHDATASOURCECONFIGURE,
            SetDbConstants.PARAM_LIBERTYDIR,
            SetDbConstants.LIBERTY_HOME,
            SetDbConstants.PARAM_DB_TYPE,
            SetDbConstants.DB_TYPE,
            SetDbConstants.PARAM_DB_NAME,
            this.dbConfig.getDbName(),
            SetDbConstants.PARAM_DB_USER_ID,
            this.dbConfig.getUserId(),
            SetDbConstants.PARAM_DB_USER_PASSWORD,
            this.dbConfig.getUserPassword(),
            SetDbConstants.PARAM_DB_HOST,
            this.dbConfig.getHost(),
            SetDbConstants.PARAM_DB_SERVER_PORT,
            this.dbConfig.getPort()
        };
    }

}
