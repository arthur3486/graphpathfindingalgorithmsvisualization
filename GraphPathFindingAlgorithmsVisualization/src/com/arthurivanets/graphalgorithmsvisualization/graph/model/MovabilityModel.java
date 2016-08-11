package com.arthurivanets.graphalgorithmsvisualization.graph.model;

public enum MovabilityModel {
	
	/**
	 * <br> A crosswise movability model 
	 * <br>   |
	 * <br> --+--
	 * <br>   |
	 */
	FOUR_DIRECTIONS_CROSSWISE("Crosswise(+)", 4),
	
	/**
	 * <br> All-directions movability model
	 * <br>   \ | /
	 * <br>   --|--
	 * <br>   / | \
	 */
	ANY_DIRECTION("Any Direction", 1);
	
	
	public final String name;
	public final int value;
	
	
	MovabilityModel(String name, int value) {
		this.name = name;
		this.value = value;
	}
	
}
