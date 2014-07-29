package com.magg.wiki.analytics.service;

import java.util.HashMap;
import java.util.Map;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;


public class LinkService {

    public Map<String, String> fetch(String linkName){
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Filter eq = new Query.FilterPredicate("name", FilterOperator.EQUAL, linkName);
        Query query = new Query("Link").setFilter(eq);
        PreparedQuery pq = datastore.prepare(query);
        
        Map<String, String> results = new HashMap<String, String>();
        for (Entity result : pq.asIterable()) {
            String name = (String) result.getProperty("name");
            String links = (String) result.getProperty("links");
            results.put(name, links);
        }
        return results;
    }
    
}
