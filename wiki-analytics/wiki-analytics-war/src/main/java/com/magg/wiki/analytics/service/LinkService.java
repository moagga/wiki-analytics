package com.magg.wiki.analytics.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Text;


public class LinkService {

    public Map<String, List<String>> fetch(String linkName){
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Key k = KeyFactory.createKey("Link", linkName);
        Query query = new Query(k);
        PreparedQuery pq = datastore.prepare(query);
        
        List<String> ins = new ArrayList<String>();
        List<String> out = new ArrayList<String>();
        Map<String, List<String>> results = new HashMap<String, List<String>>();
        results.put("in", ins);
        results.put("out", out);
        
        Entity e = null;
        for (Entity result : pq.asIterable()) {
            e = result;
            break;
        }
        if (e != null){
            Text t = (Text) e.getProperty("links");
            String[] parts = t.getValue().split(",");
            for (String part : parts) {
                if (part.endsWith("^")){
                    part = part.replaceAll("\\^", "");
                    out.add(part);
                } else {
                    ins.add(part);
                }
                
            }
        }
        return results;
    }
    
}
