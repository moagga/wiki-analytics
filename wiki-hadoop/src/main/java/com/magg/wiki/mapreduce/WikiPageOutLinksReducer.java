package com.magg.wiki.mapreduce;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import com.magg.wiki.model.LinksWrittable;

public class WikiPageOutLinksReducer extends Reducer<Text, Text, Text, LinksWrittable>{

	protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException ,InterruptedException {
		LinksWrittable lw = new LinksWrittable();
		Set<Text> set = new HashSet<Text>();
		for (Text writable : values) {
			set.add(new Text(writable.toString()));
		}
		lw.set(set.toArray(new Text[]{}));
		context.write(key, lw);
	}
	
}
