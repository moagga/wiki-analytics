package com.magg.wiki.mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import com.magg.wiki.model.LinksWrittable;

public class WikiOutLinksDriver {
	
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		conf.set("xmlinput.start", "<page>");
		conf.set("xmlinput.end", "</page>");
		
		Job job = new Job(conf);
		job.setJarByClass(WikiOutLinksDriver.class);
		job.setJobName("Forward Links");
		
		job.setInputFormatClass(XmlInputFormat.class);
		job.setMapperClass(WikiPageOutLinksMapper.class);
		job.setReducerClass(WikiPageOutLinksReducer.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LinksWrittable.class);
		
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(ArrayWritable.class);

		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

}
