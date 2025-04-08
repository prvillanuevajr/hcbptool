/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esspi.hcbptool.ui;

import com.esspi.hcbptool.MainFrame;
import com.esspi.hcbptool.Utils;
import com.esspi.hcbptool.config.EncryptionKey;
import com.esspi.hcbptool.config.ToolConfig;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author presmelito
 */
public class EncryptionPanelController {

    private static EncryptionPanelController instance;

    public static EncryptionPanelController getInstance() {
        return Objects.nonNull(instance) ? instance : new EncryptionPanelController();
    }

    public void init() {
        addComponentListeners();
        addButtonIcons();
        refreshTable();
    }

    public void addEncryption() {
        String name = MainFrame.getInstance().getEncryptiontabNameField().getText().trim();
        String merchantKey = MainFrame.getInstance().getEncryptiontabMerchantKeyField().getText().trim();
        String sessionKey = MainFrame.getInstance().getEncryptiontabSessionkeyField().getText().trim();
        if (StringUtils.isAnyEmpty(name,merchantKey,sessionKey)) {
            JOptionPane.showMessageDialog(MainFrame.getInstance(), "All fields are required!");
            return;
        }
        EncryptionKey key = new EncryptionKey(name, merchantKey, sessionKey);
        ToolConfig.getInstance().getEncriptionKeys().add(key);
        ToolConfig.getInstance().saveConfig();
        MainFrame.getInstance().getEncryptiontabNameField().setText(StringUtils.EMPTY);
        MainFrame.getInstance().getEncryptiontabMerchantKeyField().setText(StringUtils.EMPTY);
        MainFrame.getInstance().getEncryptiontabSessionkeyField().setText(StringUtils.EMPTY);
        refreshTable();
    }

    private void addComponentListeners() {
        MainFrame.getInstance().getEpcAddButton().addActionListener(e -> addEncryption());
        MainFrame.getInstance().getEpcRemoveButton().addActionListener(e -> removeEncryption());
        MainFrame.getInstance().getEpcSetButton().addActionListener(e -> setEncryption());
        MainFrame.getInstance().getEpcViewMerchantKeyButton().addActionListener(e -> Utils.openFile(ToolConfig.getInstance().getWorkspacePath().resolve("WC/xml/config/merchantKey.xml").toFile()));
        MainFrame.getInstance().getEpcViewWCServerFileButton().addActionListener(e -> Utils.openFile(ToolConfig.getInstance().getWorkspacePath().resolve("WC/xml/config/wc-server.xml").toFile()));
    }

    private void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) MainFrame.getInstance().getEpcTable().getModel();
        ((DefaultTableModel) model).setRowCount(0);
        for (int i = 0; i < ToolConfig.getInstance().getEncriptionKeys().size(); i++) {
            model.addRow(new String[]{
                ToolConfig.getInstance().getEncriptionKeys().get(i).getName(),
                ToolConfig.getInstance().getEncriptionKeys().get(i).getMerchantKey(),
                ToolConfig.getInstance().getEncriptionKeys().get(i).getSessionKey()});
        }
    }

    private void removeEncryption() {
        if (MainFrame.getInstance().getEpcTable().getSelectedRow() != -1) {
            int result = JOptionPane.showConfirmDialog(MainFrame.getInstance().getSetDbPanel(),
                    "Remove this Config?", "Remove Encryption Config", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                ToolConfig.getInstance().getEncriptionKeys().remove(MainFrame.getInstance().getEpcTable().getSelectedRow());
                DefaultTableModel model = (DefaultTableModel) MainFrame.getInstance().getEpcTable().getModel();
                model.removeRow(MainFrame.getInstance().getEpcTable().getSelectedRow());
            }
        } else {
            JOptionPane.showMessageDialog(MainFrame.getInstance().getSetDbPanel(), "Please select encryption");
        }
        refreshTable();
    }

    private void setEncryption() {
        EncryptionKey key = ToolConfig.getInstance().getEncriptionKeys().get(MainFrame.getInstance().getEpcTable().getSelectedRow());

        Path merchantKeyXML = ToolConfig.getInstance().getWorkspacePath().resolve("WC/xml/config/merchantKey.xml");
        Path wcServerXML = ToolConfig.getInstance().getWorkspacePath().resolve("WC/xml/config/wc-server.xml");

        if (merchantKeyXML.toFile().exists() && wcServerXML.toFile().exists()) {
            updateXMLValue(wcServerXML.toFile(), "config/InstanceProperties/Instance", "SessionKey", key.getSessionKey());
            updateXMLValue(merchantKeyXML.toFile(), "keys/key", "value", key.getMerchantKey());
            JOptionPane.showMessageDialog(MainFrame.getInstance().getSetDbPanel(), "Keys Updated!");
        } else {
            JOptionPane.showMessageDialog(MainFrame.getInstance(), "Config XML files not found!");
        }

    }

    private void updateXMLValue(File xmlFile, String xPathExpression, String attributeName, String value) {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);
            XPath xpath = XPathFactory.newInstance().newXPath();
            NodeList nodes = (NodeList) xpath.evaluate(xPathExpression,
                    doc, XPathConstants.NODESET);
            Node node = nodes.item(0).getAttributes().getNamedItem(attributeName);
            node.setNodeValue(value);
            OutputFormat outputFormat = new OutputFormat(doc, "UTF-8", true);
            outputFormat.setIndenting(true);
            outputFormat.setIndent(2);
            outputFormat.setLineWidth(1000);
            try (FileWriter fileWriter = new FileWriter(xmlFile)) {
                XMLSerializer xmlSerializer = new XMLSerializer(fileWriter, outputFormat);
                xmlSerializer.serialize(doc);
            }
        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException ex) {
            Logger.getLogger(EncryptionPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addButtonIcons() {
        MainFrame.getInstance().getEpcViewMerchantKeyButton().setIcon(new FlatSVGIcon("com/esspi/hcbptool/svgs/file-code.svg"));
        MainFrame.getInstance().getEpcViewWCServerFileButton().setIcon(new FlatSVGIcon("com/esspi/hcbptool/svgs/file-code.svg"));
        MainFrame.getInstance().getEpcRemoveButton().setIcon(new FlatSVGIcon("com/esspi/hcbptool/svgs/trash-can.svg"));
        MainFrame.getInstance().getEpcAddButton().setIcon(new FlatSVGIcon("com/esspi/hcbptool/svgs/plus.svg"));
        MainFrame.getInstance().getEpcSetButton().setIcon(new FlatSVGIcon("com/esspi/hcbptool/svgs/user-secret.svg"));
        MainFrame.getInstance().getEpcSetButton().setIcon(new FlatSVGIcon("com/esspi/hcbptool/svgs/user-secret.svg"));
        MainFrame.getInstance().getJTabbedPane().setIconAt(3,new FlatSVGIcon("com/esspi/hcbptool/svgs/user-secret.svg"));
    }
}
