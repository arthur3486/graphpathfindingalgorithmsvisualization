package com.arthurivanets.graphalgorithmsvisualization.graph;

public enum GraphType {

	REGULAR("Arbitrary Graph"),
	VERTEX_GRID("Grid Graph");
	
	public final String name;
	
	
	GraphType(String name) {
		this.name = name;
	}
	
	
}
