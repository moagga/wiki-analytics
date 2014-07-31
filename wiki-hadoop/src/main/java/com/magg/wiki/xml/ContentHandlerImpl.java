package com.magg.wiki.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;


public class ContentHandlerImpl implements ContentHandler{

    public enum Scope{
        PAGE("page"), TITLE("title"), TEXT("text");
        
        private String tagName;
        
        private Scope(String tagName){
            this.tagName = tagName;
        }
        
        public static Scope findByTagName(String name){
            for(Scope value : values()){
                if (value.tagName.equals(name)){
                    return value;
                }
            }
            return null;
        }
        
        @Override
        public String toString() {
            return tagName;
        }
    }
    
    private boolean pageStart;
    private Map<String, List<String>> links;
    private Stack<Scope> stack;
    private String currentPageTitle;
    private List<String> currentLinks;
    private LinkExtractor extractor;
    
    public ContentHandlerImpl() {
        links = new HashMap<String, List<String>>();
        stack = new Stack<Scope>();
        currentLinks = new ArrayList<String>();
        extractor = new LinkExtractor();
    }
    
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (stack.isEmpty()){
            return;
        }
        switch (stack.peek()) {
            case TEXT:
                String s = new String(ch, start, length);
                currentLinks.addAll(extractor.extractTokens(s));
                break;
            case TITLE:
                currentPageTitle = new String(ch, start, length);
                currentPageTitle = extractor.applyWikiSyntaxRules(currentPageTitle);
                break;
        }
        if (pageStart){
        }
    }

    @Override
    public void endDocument() throws SAXException {
    }
    
    public Map<String, List<String>> getLinks() {
		return links;
	}

    @Override
    public void endElement(String uri, String localName, String name) throws SAXException {
        Scope s = Scope.findByTagName(name);
        if (s != null){
            stack.pop();
            switch (s) {
                case PAGE:
                    links.put(currentPageTitle, currentLinks);
                    currentLinks = new ArrayList<String>();
                    break;

                default:
                    break;
            }
        }
    }

    @Override
    public void endPrefixMapping(String prefix) throws SAXException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void processingInstruction(String target, String data) throws SAXException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setDocumentLocator(Locator locator) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void skippedEntity(String name) throws SAXException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void startDocument() throws SAXException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void startElement(String uri, String localName, String name, Attributes atts) throws SAXException {
        Scope s = Scope.findByTagName(name);
        if (s != null){
            stack.push(s);
        }
    }

    @Override
    public void startPrefixMapping(String prefix, String uri) throws SAXException {
        // TODO Auto-generated method stub
        
    }

}
