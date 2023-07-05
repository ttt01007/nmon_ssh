package util;


import base.BasicInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import service.ServerInfo;
import service.ServerTypeInfo;

import java.util.ArrayList;
import java.util.HashMap;

public class LoadConfig {
    Document doc;

    public LoadConfig(String configFilename) {
        this.doc = loadFromXML(configFilename);
    }

    public BasicInfo GetBasicInfo() {
        BasicInfo basicInfo = new BasicInfo(new HashMap<>());
        Element rootElement = this.doc.getDocumentElement();
        NodeList basicInfos = rootElement.getElementsByTagName("BasicInfo");
        if (basicInfos.getLength() > 0) {
            basicInfo = new BasicInfo(elementToMap((Element) basicInfos.item(0)));
        }
        return basicInfo;
    }

    public HashMap<String, ServerTypeInfo> GetServerTypeInfo() {
        HashMap map = new HashMap();
        Element rootElement = this.doc.getDocumentElement();
        NodeList serverTypeConf = rootElement.getElementsByTagName("ServerTypeConf");
        for (int i = 0; i < serverTypeConf.getLength(); i++) {
            ServerTypeInfo tmp = new ServerTypeInfo(elementToMap((Element) serverTypeConf.item(i)));
            map.put(tmp.GetConfToString("OSType"), tmp);
        }
        return map;
    }

    public ArrayList<ServerInfo> GetServerConf(HashMap<String, ServerTypeInfo> serverTypes) {
        ArrayList servers = new ArrayList();
        Element rootElement = this.doc.getDocumentElement();
        NodeList ServerConfs = rootElement.getElementsByTagName("ServerConf");
        for (int i = 0; i < ServerConfs.getLength(); i++) {
            ServerInfo tmp = new ServerInfo(elementToMap((Element) ServerConfs.item(i)));
            tmp.setServerTypeInfo((ServerTypeInfo) serverTypes.get(tmp.GetConfToString("OSTYPE")));
            servers.add(tmp);
        }
        return servers;
    }

    private HashMap<String, String> elementToMap(Element e) {
        HashMap map = new HashMap();

        NodeList nodeList = e.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node instanceof Element) {
                Element element = (Element) node;
                map.put(element.getNodeName().toUpperCase(), element.getTextContent());
            }
        }
        return map;
    }

    private Document loadFromXML(String xmlFileName) {
        try {
            LoadXML loadXML = new LoadXML();
            Document doc = loadXML.loadFromXML(xmlFileName);
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
