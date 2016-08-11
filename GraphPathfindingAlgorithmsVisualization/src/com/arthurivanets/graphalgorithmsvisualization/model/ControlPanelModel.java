package com.arthurivanets.graphalgorithmsvisualization.model;

import java.io.File;

import com.arthurivanets.graphalgorithmsvisualization.algorithms.Algorithm;
import com.arthurivanets.graphalgorithmsvisualization.graph.BaseGraph;
import com.arthurivanets.graphalgorithmsvisualization.graph.GraphFactory;
import com.arthurivanets.graphalgorithmsvisualization.graph.GraphLoader;
import com.arthurivanets.graphalgorithmsvisualization.graph.GraphType;
import com.arthurivanets.graphalgorithmsvisualization.graph.model.MovabilityModel;
import com.arthurivanets.graphalgorithmsvisualization.graph.model.Vertex;

public class ControlPanelModel {
	
	
	private Algorithm mAlgorithm;
	private GraphType mGraphType;
	private MovabilityModel mMovabilityModel;
	
	private BaseGraph mCurrentGraph;
	
	private int mNodeType;
	
	private float mAnimationSpeedRate;
	
	private boolean mIsAssistanceEnabled;
	private boolean mIsAnimationEnabled;
	
	
	

	public ControlPanelModel() {
		this(
			Algorithm.DIJKSTRA,
			GraphType.REGULAR,
			MovabilityModel.ANY_DIRECTION,
			Vertex.Type.PATH,
			true
		);
	}
	
	
	
	
	public ControlPanelModel(Algorithm algorithm, GraphType graphType,
							 MovabilityModel movabilityModel, int nodeType,
							 boolean isAssistanceEnabled) {
		mAlgorithm = algorithm;
		mGraphType = graphType;
		mMovabilityModel = movabilityModel;
		mNodeType = nodeType;
		mAnimationSpeedRate = 1f;
		mIsAssistanceEnabled = isAssistanceEnabled;
	}
	
	
	
	
	public ControlPanelModel setAlgorithm(Algorithm algorithm) {
		mAlgorithm = algorithm;
		return this;
	}
	
	
	
	
	public Algorithm getAlgorithm() {
		return mAlgorithm;
	}
	
	
	
	
	public ControlPanelModel setGraphType(GraphType graphType) {
		mGraphType = graphType;
		return this;
	}
	
	
	
	
	public GraphType getGraphType() {
		return mGraphType;
	}
	
	
	
	
	public ControlPanelModel setMovabilityModel(MovabilityModel movabilityModel) {
		mMovabilityModel = movabilityModel;
		return this;
	}
	
	
	
	
	public MovabilityModel getMovabilityModel() {
		return mMovabilityModel;
	}
	
	
	
	
	public ControlPanelModel setNodeType(int nodeType) {
		mNodeType = nodeType;
		return this;
	}
	
	
	
	
	public int getNodeType() {
		return mNodeType;
	}
	
	
	
	
	public ControlPanelModel setAnimationSpeedRate(float speedRate) {
		mAnimationSpeedRate = speedRate;
		return this;
	}
	
	
	
	
	public float getAnimationSpeedRate() {
		return mAnimationSpeedRate;
	}
	
	
	
	
	public BaseGraph createGraphForType(GraphType graphType) {
		mCurrentGraph = GraphFactory.getGraph(graphType, mMovabilityModel);
		return mCurrentGraph;
	}
	
	
	
	
	public BaseGraph loadGraph(File file) {
		BaseGraph graph = GraphLoader.loadGraph(file);
		
		if(graph == null) {
			return mCurrentGraph;
		}
		
		setGraphType(graph.getGraphType());
		setMovabilityModel(graph.getMovabilityModel());
		
		return graph;
	}
	
	
	
	
	public ControlPanelModel setAssistanceEnabled(boolean isAssistanceEnabled) {
		mIsAssistanceEnabled = isAssistanceEnabled;
		return this;
	}
	
	
	
	
	public boolean isAssistanceEnabled() {
		return mIsAssistanceEnabled;
	}
	
	
	
	
	public ControlPanelModel setAnimationEnabled(boolean isAnimationEnabled) {
		mIsAnimationEnabled = isAnimationEnabled;
		return this;
	}
	
	
	
	
	public boolean isAnimationEnabled() {
		return mIsAnimationEnabled;
	}
	
	
	
	
	public boolean isMovabilityPickerEnabled() {
		return mGraphType.equals(GraphType.VERTEX_GRID);
	}
	
	
	
	
	public boolean isNodeTypePickerEnabled() {
		return mGraphType.equals(GraphType.VERTEX_GRID);
	}
	
	
	
	
	public boolean isAssistanceToggleVisible() {
		return mGraphType.equals(GraphType.REGULAR);
	}
	
	
	
	
	public boolean isAnimationToggleVisible() {
		return true;
	}
	
	
	

}
