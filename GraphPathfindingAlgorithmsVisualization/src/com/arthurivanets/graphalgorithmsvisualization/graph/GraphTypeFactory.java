package com.arthurivanets.graphalgorithmsvisualization.graph;

import com.arthurivanets.graphalgorithmsvisualization.util.TextUtils;

public class GraphTypeFactory {

	
	
	
	public static GraphType getGraphTypeForName(String name) {
		if(TextUtils.isEmpty(name)) {
			return null;
		}
		
		if(name.equals(GraphType.REGULAR.name())) {
			return GraphType.REGULAR;
		} else if(name.equals(GraphType.VERTEX_GRID.name())) {
			return GraphType.VERTEX_GRID;
		}
		
		return null;
	}
	
	
	

}
