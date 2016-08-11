package com.arthurivanets.graphalgorithmsvisualization.graph.model;

import com.arthurivanets.graphalgorithmsvisualization.util.TextUtils;

public class MovabilityModelFactory {

	
	
	
	public static MovabilityModel getMovabilityModelForName(String name) {
		if(TextUtils.isEmpty(name)) {
			return null;
		}
		
		if(name.equals(MovabilityModel.FOUR_DIRECTIONS_CROSSWISE.name())) {
			return MovabilityModel.FOUR_DIRECTIONS_CROSSWISE;
		} else if(name.equals(MovabilityModel.ANY_DIRECTION.name())) {
			return MovabilityModel.ANY_DIRECTION;
		}
		
		return null;
	}
	
	
	

}
