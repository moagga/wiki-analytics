package com.magg.wiki.analytics.service;

import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;


public class LinkServiceTest {

    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
    
    private Entity e1;
    private Entity e2;
    
    private LinkService service;
    
    @Before
    public void setUp() {
        helper.setUp();
        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        
        e1 = new Entity(KeyFactory.createKey("Link", "A"));
        e1.setProperty("links", new Text("[B]^,[C]"));
        ds.put(e1);

        e2 = new Entity(KeyFactory.createKey("Link", "B"));
        e2.setProperty("links", new Text("[C]^,[A]"));
        ds.put(e2);
        
        service = new LinkService();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }
    
    @Test
    public void testZeroResults(){
        Map<String, List<String>> results = service.fetch("C");
        Assert.assertTrue(results.get("in").isEmpty());
        Assert.assertTrue(results.get("out").isEmpty());
    }

    @Test
    public void testNonZeroResults(){
        Map<String, List<String>> results = service.fetch("B");
        Assert.assertEquals(1, results.get("in").size());
        Assert.assertEquals(1, results.get("out").size());
    }

}
