package com.magg.wiki.model;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.Writable;

public class Link implements Writable{

	private String name;
	private List<Link> inwardLinks;
	private List<Link> outwardLinks;
	
	public Link() {
		this.inwardLinks = new ArrayList<Link>();
		this.outwardLinks = new ArrayList<Link>();
	}
	
	public Link(String name) {
		this();
		this.name = name;
	}

	public List<Link> getOutwardLinks() {
		return outwardLinks;
	}
	
	public List<Link> getInwardLinks() {
		return inwardLinks;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		
	}
	
	@Override
	public void readFields(DataInput in) throws IOException {
//		in.
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Link other = (Link) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
