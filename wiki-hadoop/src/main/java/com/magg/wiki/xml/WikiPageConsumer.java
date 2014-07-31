package com.magg.wiki.xml;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;



public class WikiPageConsumer {

    public static Map<String, List<String>> consume(InputStream in) throws Exception {
        
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        SAXParser saxParser = spf.newSAXParser();
        ContentHandlerImpl handler = new ContentHandlerImpl();
        
        XMLReader xmlReader = saxParser.getXMLReader();
        xmlReader.setContentHandler(handler);
        xmlReader.parse(new InputSource(in));
        return handler.getLinks();
    }
    
}
