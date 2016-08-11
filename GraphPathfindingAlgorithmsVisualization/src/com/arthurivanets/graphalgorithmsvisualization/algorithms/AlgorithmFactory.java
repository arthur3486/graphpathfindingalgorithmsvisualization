package com.arthurivanets.graphalgorithmsvisualization.algorithms;

import com.arthurivanets.graphalgorithmsvisualization.graph.BaseGraph;
import com.arthurivanets.graphalgorithmsvisualization.graph.model.Vertex;

public class AlgorithmFactory {

	
	
	
	public static BaseAlgorithm getAlgorithm(Algorithm algorithm, BaseGraph graph,
											 Vertex initialNode, Vertex destinationNode) {
		if(algorithm.equals(Algorithm.DIJKSTRA)) {
			return DijkstrasAlgorithm.fromGraph(graph, initialNode, destinationNode);
		} else if(algorithm.equals(Algorithm.A_STAR)) {
			return AStarAlgorithm.fromGraph(graph, initialNode, destinationNode);
		}
		
		return null;
	}
	
	

	
}
