package com.magg.wiki.analytics.uploader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.tools.remoteapi.RemoteApiInstaller;
import com.google.appengine.tools.remoteapi.RemoteApiOptions;


public class Uploader {

	private static final String HOST = "wiki-analytics-01.appspot.com";
	private static final int PORT = 443;

//	private static final String HOST = "localhost";
//	private static final int PORT = 8181;
	
    public static void main(String[] args) throws Exception {
    	
    	String username = args[0];
        String password = args[1];
    	
    	Uploader up = new Uploader(username, password);
    	up.start();
    }
    
    private final RemoteApiOptions options;

    private Uploader(String username, String password) throws IOException {
        options = new RemoteApiOptions().server(HOST, PORT).credentials(username, password);
        RemoteApiInstaller installer = new RemoteApiInstaller();
        installer.install(options);
        try {
            options.reuseCredentials(username, installer.serializeCredentials());
        } finally {
            installer.uninstall();
        }
    }
    
    private void start() throws Exception{
        RemoteApiInstaller installer = new RemoteApiInstaller();
        installer.install(options);
        try {
            DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
            InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("data.txt");
            Properties props = new Properties();
            props.load(in);
            int batchNum = 1;
            int index = 1;
        	List<Entity> bulk = new ArrayList<>();
            for (Object key : props.keySet()) {
            	
                String link = (String) key;
                try {
                    Object value = props.get(key);
                    link = link.toLowerCase().replaceAll("_", " ");
                    Key k = KeyFactory.createKey("Link", link);
                    Entity entity = new Entity(k);
                    Text t = new Text(value.toString());
                    entity.setProperty("links", t);
                    
                	if (index < 11){
                		bulk.add(entity);
                		index++;
                	} else {
                		ds.put(bulk);
                		index = 1;
                		bulk.clear();
                		System.out.println(batchNum);
                		batchNum++;
                	}
                    
                } catch (Exception e) {
                    System.out.println("Exception occured while inserting link:" + link);
                }
            }
        } finally {
            installer.uninstall();
        }
    }
    
}
