package util;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URL;

public class LoadXML {
    public Document loadFromXML(InputStream is) throws ParserConfigurationException, SAXException, IOException {
        //得到DOM解析器的工厂实例
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        //从DOM工厂中获得DOM解析器
        DocumentBuilder db = dbf.newDocumentBuilder();

        Document doc = db.parse(is);

        return doc;
    }

    public Document loadFromXML(byte[] b) throws ParserConfigurationException, SAXException, IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(b);
        Document d = loadFromXML(bis);

        return d;
    }

    public Document loadFromXML(String file) throws ParserConfigurationException, SAXException, IOException {
        InputStream is = null;
        try{
            is = new FileInputStream(new File(file));
            Document doc = loadFromXML(is);
            return doc;
        }finally {
            try{
                if (is != null) is.close();
            }catch(IOException loaclIOException){
            }
        }
    }

    public Document loadFromXML(URL url) throws ParserConfigurationException, SAXException, IOException {
        InputStream is = null;
        try{
            is = url.openStream();
            Document doc = loadFromXML(is);
            return doc;
        }finally {
            try{
                if (is != null) is.close();
            }catch(IOException loaclIOException){
            }
        }
    }
}
