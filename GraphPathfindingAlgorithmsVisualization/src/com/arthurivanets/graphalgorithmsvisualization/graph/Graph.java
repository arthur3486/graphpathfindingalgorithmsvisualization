package com.arthurivanets.graphalgorithmsvisualization.graph;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import com.arthurivanets.graphalgorithmsvisualization.graph.model.Edge;
import com.arthurivanets.graphalgorithmsvisualization.graph.model.MovabilityModel;
import com.arthurivanets.graphalgorithmsvisualization.graph.model.Vertex;

public class Graph extends BaseGraph {
	
	
	

	public Graph() {
		this(false);
	}
	
	
	
	
	public Graph(boolean isDirected) {
		super(isDirected);
		setGraphType(GraphType.REGULAR);
		setMovabilityModel(MovabilityModel.ANY_DIRECTION);
	}
	
	
	
	
	public Graph(ArrayList<String> data) {
		super(false);
		setGraphType(GraphType.REGULAR);
		setMovabilityModel(MovabilityModel.ANY_DIRECTION);
		load(data);
	}




	@Override
	public void onDraw(Graphics2D g) {
		//...
	}




	@Override
	public Color getBackgroundColor() {
		return Color.BLACK;
	}
	
	
	
	
	@Override
	public void addEdge(Edge edge) {
		edge.getStartVertex().setColorInheritable(false).setPathColor(Edge.DEFAULT_COLOR);
		edge.getEndVertex().setColorInheritable(false).setPathColor(Edge.DEFAULT_COLOR);
		edge.setWeightVisible(true);
		super.addEdge(edge);
	}

	


	@Override
	public Vertex getVertexForPoint(int x, int y) {
		return super.getVertexForPoint(x, y).setColorInheritable(false).setPathColor(Edge.DEFAULT_COLOR);
	}
	
	
	

}
