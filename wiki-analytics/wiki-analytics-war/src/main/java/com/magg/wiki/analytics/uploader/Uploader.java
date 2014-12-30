package com.magg.wiki.analytics.uploader;

import java.io.IOException;
import java.io.InputStream;
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

    public static void main(String[] args) throws IOException {
        String username = "admin";
        String password = "";
        RemoteApiOptions options = new RemoteApiOptions().server("localhost", 8181).credentials(username, password);
        RemoteApiInstaller installer = new RemoteApiInstaller();
        installer.install(options);
        try {
            DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
            InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("inout.properties");
            Properties props = new Properties();
            props.load(in);
            for (Object key : props.keySet()) {
                String link = (String) key;
                try {
                    Object value = props.get(key);
                    Key k = KeyFactory.createKey("Link", link);
                    Entity entity = new Entity(k);
                    Text t = new Text(value.toString());
                    entity.setProperty("links", t);
                    ds.put(entity);
                } catch (Exception e) {
                    System.out.println("Exception occured while inserting link:" + link);
                }
            }
        } finally {
            installer.uninstall();
        }
    }
    
}
