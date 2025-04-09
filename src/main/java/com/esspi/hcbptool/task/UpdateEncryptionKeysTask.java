/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esspi.hcbptool.task;

import com.esspi.hcbptool.Utils;
import com.esspi.hcbptool.config.EncryptionKey;
import com.esspi.hcbptool.config.ToolConfig;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.LoggerFactory;

/**
 *
 * @author presmelito.villanuev
 */
@Data
@AllArgsConstructor
public class UpdateEncryptionKeysTask extends Task {

    EncryptionKey key;

    @Override
    public String call() throws Exception {
        try {
            Path merchantKeyXML = ToolConfig.getInstance().getWorkspacePath().resolve("WC/xml/config/merchantKey.xml");
            Path wcServerXML = ToolConfig.getInstance().getWorkspacePath().resolve("WC/xml/config/wc-server.xml");

            if (merchantKeyXML.toFile().exists() && wcServerXML.toFile().exists()) {
                Utils.updateXMLValue(wcServerXML.toFile(), "config/InstanceProperties/Instance", "SessionKey", key.getSessionKey());
                Utils.updateXMLValue(merchantKeyXML.toFile(), "keys/key", "value", key.getMerchantKey());
            } else {
                throw new FileNotFoundException();
            }
            this.doOnSuccess("Encryption keys updated!");
        } catch (Exception e) {
            LoggerFactory.getLogger(UpdateEncryptionKeysTask.class).error("Error updating keys!", e);
            this.doOnError("Error updating keys!");
        }
        
        return "Done";
    }
}
