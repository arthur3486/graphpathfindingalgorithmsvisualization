package com.arthurivanets.graphalgorithmsvisualization.algorithms;

public enum Algorithm {

	DIJKSTRA("Dijkstra's Algorithm"),
	A_STAR("A* (A-Star) Algorithm");
	
	public final String name;
	
	
	Algorithm(String name) {
		this.name = name;
	}
	
	
}
