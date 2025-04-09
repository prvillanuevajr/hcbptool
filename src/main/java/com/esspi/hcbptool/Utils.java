/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esspi.hcbptool;

import com.esspi.hcbptool.ui.EncryptionPanelController;
import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
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
public class Utils {

    public static void openFile(File file) {
        try {
            Desktop.getDesktop().open(file);
        } catch (IOException ex) {
            Logger.getLogger(EncryptionPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        public static void updateXMLValue(File xmlFile, String xPathExpression, String attributeName, String value) {
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
}
