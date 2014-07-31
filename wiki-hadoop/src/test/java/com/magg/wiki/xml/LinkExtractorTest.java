package com.magg.wiki.xml;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class LinkExtractorTest {

	private LinkExtractor extractor;
	
	@Before
	public void setup(){
		extractor = new LinkExtractor();
	}
	
	@Test
	public void testBasicExtract(){
		String input = "[[Texas]]";
		List<String> tokens = extractor.extractTokens(input);
		Assert.assertEquals("[[Texas]]", tokens.get(0));
	}

	@Test
	public void testBasicExtract_AutoCapitalize(){
		String input = "[[texas]]";
		List<String> tokens = extractor.extractTokens(input);
		Assert.assertEquals("[[Texas]]", tokens.get(0));
		
	}

	@Test
	public void testRenamedLink(){
		String input = "[[Texas|Lone Star State]]";
		List<String> tokens = extractor.extractTokens(input);
		Assert.assertEquals("[[Texas]]", tokens.get(0));
		
		input = "[[public transport|public transportation]]";
		tokens = extractor.extractTokens(input);
		Assert.assertEquals("[[Public_Transport]]", tokens.get(0));
	}

	@Test
	public void testSpaceAndCamelCasing(){
		String input = "[[public transport]]";
		List<String> tokens = extractor.extractTokens(input);
		Assert.assertEquals("[[Public_Transport]]", tokens.get(0));

		input = "[[2001: a_Space_Odyssey_(film)]]";
		tokens = extractor.extractTokens(input);
		Assert.assertEquals("[[2001:_A_Space_Odyssey_(film)]]", tokens.get(0));
}

	@Test
	public void testPipeEndDisambiguation(){
		String input = "[[Pipe (computing)|]]";
		List<String> tokens = extractor.extractTokens(input);
		Assert.assertEquals("[[Pipe_(computing)]]", tokens.get(0));

		input = "[[Pipe, (computing)|]]";
		tokens = extractor.extractTokens(input);
		Assert.assertEquals("[[Pipe,_(computing)]]", tokens.get(0));
	}

	@Test
	public void testPipeEndDisambiguation_AutoCapitalize(){
		String input = "[[pipe (computing)|]]";
		List<String> tokens = extractor.extractTokens(input);
		Assert.assertEquals("[[Pipe_(computing)]]", tokens.get(0));
	}

	@Test
	public void testPipeEnd_no_camelcasing(){
		String input = "[[Il Buono, il Brutto, il Cattivo|]]";
		List<String> tokens = extractor.extractTokens(input);
		Assert.assertEquals("[[Il_Buono,_il_Brutto,_il_Cattivo]]", tokens.get(0));
	}

	@Test
	public void testPipeEnd_one_or_more_comma_and_final_bracket_just_remove_pipe(){
		String input = "[[Yours, Mine, and Ours (1968 film)|]]";
		List<String> tokens = extractor.extractTokens(input);
		Assert.assertEquals("[[Yours,_Mine,_and_Ours_(1968_film)]]", tokens.get(0));
	}

}
