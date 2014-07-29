package com.magg.wiki.analytics.service;

import java.util.Map;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
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
        
        e1 = new Entity("Link");
        e1.setProperty("name", "A");
        ds.put(e1);

        e2 = new Entity("Link");
        e2.setProperty("name", "B");
        ds.put(e2);
        
        service = new LinkService();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }
    
    @Test
    public void testZeroResults(){
        Map<String, String> results = service.fetch("C");
        Assert.assertTrue(results.isEmpty());
    }

    @Test
    public void testNonZeroResults(){
        Map<String, String> results = service.fetch("B");
        Assert.assertEquals(1, results.size());
    }

}
