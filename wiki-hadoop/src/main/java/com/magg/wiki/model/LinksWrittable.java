package com.magg.wiki.model;

import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

public class LinksWrittable extends ArrayWritable {

	public LinksWrittable() {
		super(Text.class);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Writable[] values = get();
		if (values != null){
			for (Writable value : values) {
				sb.append(value.toString());
				sb.append(",");
			}
		}
		return sb.toString();
	}
	
}
