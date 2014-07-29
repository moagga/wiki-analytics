package com.magg.wiki.analytics.uploader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.tools.remoteapi.RemoteApiInstaller;
import com.google.appengine.tools.remoteapi.RemoteApiOptions;


public class Uploader {

    public static void main(String[] args) throws IOException {
        String username = "admin";
        String password = "";
        RemoteApiOptions options = new RemoteApiOptions().server("localhost", 8181).credentials(username, password);
        RemoteApiInstaller installer = new RemoteApiInstaller();
        installer.install(options);
        try {
            DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
            InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("sample.properties");
            Properties props = new Properties();
            props.load(in);
            for (Object key : props.keySet()) {
                Object value = props.get(key);
                Entity entity = new Entity("Link");
                entity.setProperty("name", key);
                entity.setProperty("links", value);
                ds.put(entity);
            }
            
        } finally {
            installer.uninstall();
        }
    }
    
}
