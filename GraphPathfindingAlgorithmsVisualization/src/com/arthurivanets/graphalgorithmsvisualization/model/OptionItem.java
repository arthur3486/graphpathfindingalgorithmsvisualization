package com.arthurivanets.graphalgorithmsvisualization.model;

import com.arthurivanets.graphalgorithmsvisualization.util.Taggable;

public class OptionItem implements Taggable<OptionItem> {
	
	
	private String title;
	private Object tag;
	
	
	

	public OptionItem() {
		this("");
	}
	
	
	
	
	public OptionItem(String title) {
		this(title, null);
	}
	
	
	
	
	public OptionItem(String title, Object tag) {
		this.title = title;
		this.tag = tag;
	}
	
	
	
	
	public OptionItem setTitle(String title) {
		this.title = title;
		return this;
	}
	
	
	
	
	public String getTitle() {
		return this.title;
	}
	
	
	
	
	@Override
	public OptionItem setTag(Object tag) {
		this.tag = tag;
		return this;
	}
	
	
	
	
	@Override
	public Object getTag() {
		return this.tag;
	}
	
	
	
	
	@Override
	public String toString() {
		return this.title;
	}
	
	
	

}
