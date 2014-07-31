package com.magg.wiki.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkExtractor {

	private final Pattern pattern;
	
	public LinkExtractor() {
		pattern = Pattern.compile("\\[\\[[\\w\\s\\|\\(\\)_,:]*\\]\\]");
	}
	
	public List<String> extractTokens(String input){
		List<String> tokens = new ArrayList<String>();
        Matcher m = pattern.matcher(input);
        while (m.find()){
            String match = m.group();
            match = applyWikiSyntaxRules(match);
            tokens.add(match);
        }
        return tokens;
	}
	
	public String applyWikiSyntaxRules(String input){
		String strippedSquaresVersion = input.replace("[[", "").replace("]]", "");
		String output = strippedSquaresVersion;
		//Case for pipe not present in end
		if (!strippedSquaresVersion.endsWith("|")){
			//If string contains pipe, extract first part
			if (strippedSquaresVersion.contains("|")){
				String linkName = strippedSquaresVersion.split("\\|")[0];
				output = linkName;
			}
			output = replaceSpaceWithUnderscore(output);
			output = camelCasing(output);
		} else {
			output = strippedSquaresVersion.substring(0, strippedSquaresVersion.lastIndexOf("|"));
			output = replaceSpaceWithUnderscore(output);
			//No camelcasing if ending with pipe, just capitalize first letter
			output = capitalize(output);
		}
		return "[[" + output + "]]";
	}
	
	private String replaceSpaceWithUnderscore(String input){
		return input.replaceAll("\\s", "_");
	}
	
	private String camelCasing(String input){
		String output = input;
		if (input.contains("_")){
			StringBuilder sb = new StringBuilder();
			String[] parts = input.split("_");
			for (String part : parts) {
				part = capitalize(part);
				sb.append(part);
				sb.append("_");
			}
			output = sb.toString();
			output = output.substring(0, output.lastIndexOf("_"));
		} else {
			output = capitalize(output);
		}
		return output;
	}
	
	private String capitalize(String input){
		char c = input.charAt(0);
		if (Character.isLetter(c)){
			String firstChar = input.substring(0, 1);
			input = input.replaceFirst(firstChar, firstChar.toUpperCase());
		}
		return input;
	}
}
