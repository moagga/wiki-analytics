package com.magg.wiki.xml;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

public class WikiPageConsumerTest {

	@Test
	public void testConsume() throws Exception{
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("wikiPage.xml");
		Map<String, List<String>> links = WikiPageConsumer.consume(in);
		Assert.assertEquals(1, links.size());
	}
	
}
