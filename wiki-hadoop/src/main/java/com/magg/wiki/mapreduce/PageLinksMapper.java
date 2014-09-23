package com.magg.wiki.mapreduce;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.magg.wiki.xml.WikiPageConsumer;

public class PageLinksMapper extends Mapper<LongWritable, Text, Text, Text>{

	protected void map(LongWritable key, Text value, Context context) throws IOException ,InterruptedException {
		InputStream in = new ByteArrayInputStream(value.toString().getBytes(StandardCharsets.UTF_8));
		try {
			Map<String, List<String>> links = WikiPageConsumer.consume(in);
			for (String link : links.keySet()) {
				List<String> childs = links.get(link);
				/*
				 * Creating inward (in) links.
				 * Current link will be inward link for child links.
				 */
				for (String child : childs) {
					context.write(new Text(child), new Text(link));
				}
				/*
				 * Creating forward(out) links.
				 * Child links which originate from current link.
				 * Add ^ to distinguish outward link from inward.
				 */
                for (String child : childs) {
                    context.write(new Text(link), new Text(child + "^"));
                }

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	};
	
}
