package com.arthurivanets.graphalgorithmsvisualization.listeners;

import com.arthurivanets.graphalgorithmsvisualization.graph.model.Vertex;

public interface ProgressListener {
	
	public void onNodeVisited(Vertex node);
	
	public void onNeighborVisited(Vertex neighbor);
	
}
