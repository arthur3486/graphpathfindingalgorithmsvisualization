package com.arthurivanets.graphalgorithmsvisualization.graph;

import com.arthurivanets.graphalgorithmsvisualization.core.Constants;
import com.arthurivanets.graphalgorithmsvisualization.graph.model.MovabilityModel;

public class GraphFactory {

	
	
	
	public static BaseGraph getGraph(GraphType graphType, MovabilityModel movabilityModel) {
		if(graphType.equals(GraphType.REGULAR)) {
			return new Graph();
		} else if(graphType.equals(GraphType.VERTEX_GRID)) {
			return new GridGraph(Constants.DEFAULT_CELL_SIZE, movabilityModel);
		}
		
		return null;
	}
	
	
	

}
