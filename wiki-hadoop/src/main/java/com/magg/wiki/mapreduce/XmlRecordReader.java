package com.magg.wiki.mapreduce;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DataOutputBuffer;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class XmlRecordReader extends RecordReader<LongWritable, Text>{

	public static final String START_TAG_KEY = "xmlinput.start";
	public static final String END_TAG_KEY = "xmlinput.end";

	private byte[] startTag;
    private byte[] endTag;
    private long start;
    private long end;
    private FSDataInputStream fsin;
    private DataOutputBuffer buffer = new DataOutputBuffer();
    private LongWritable currentKey;
    private Text currentValue;

    @Override
    public void initialize(InputSplit in, TaskAttemptContext context) throws IOException, InterruptedException {
    	FileSplit split = (FileSplit) in;
    	Configuration conf = context.getConfiguration();
	    startTag = conf.get(START_TAG_KEY).getBytes(StandardCharsets.UTF_8);
	    endTag = conf.get(END_TAG_KEY).getBytes(StandardCharsets.UTF_8);
	
	    // open the file and seek to the start of the split
	    start = split.getStart();
	    end = start + split.getLength();
	    Path file = split.getPath();
	    FileSystem fs = file.getFileSystem(conf);
	    fsin = fs.open(split.getPath());
	    fsin.seek(start);
    }

	@Override
	public void close() throws IOException {
		if (fsin != null){
			fsin.close();
		}
	}

	@Override
	public LongWritable getCurrentKey() throws IOException, InterruptedException {
		return currentKey;
	}
	
	@Override
	public Text getCurrentValue() throws IOException, InterruptedException {
		return currentValue;
	}
	
	@Override
	public float getProgress() throws IOException {
		return (fsin.getPos() - start) / (float) (end - start);	}

	@Override
	public boolean nextKeyValue() throws IOException, InterruptedException {
		currentKey = new LongWritable();
		currentValue = new Text();
		return _next(currentKey, currentValue);
	}
	
	private boolean _next(LongWritable key, Text value) throws IOException {
	      if (fsin.getPos() < end && readUntilMatch(startTag, false)) {
	        try {
	          buffer.write(startTag);
	          if (readUntilMatch(endTag, true)) {
	            key.set(fsin.getPos());
	            value.set(buffer.getData(), 0, buffer.getLength());
	            return true;
	          }
	        } finally {
	          buffer.reset();
	        }
	      }
	      return false;
	}
	
	private boolean readUntilMatch(byte[] match, boolean withinBlock) throws IOException {
	      int i = 0;
	      while (true) {
	        int b = fsin.read();
	        // end of file:
	        if (b == -1) {
	          return false;
	        }
	        // save to buffer:
	        if (withinBlock) {
	          buffer.write(b);
	        }

	        // check if we're matching:
	        if (b == match[i]) {
	          i++;
	          if (i >= match.length) {
	            return true;
	          }
	        } else {
	          i = 0;
	        }
	        // see if we've passed the stop point:
	        if (!withinBlock && i == 0 && fsin.getPos() >= end) {
	          return false;
	        }
	      }
	}
	
}
