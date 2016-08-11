package com.arthurivanets.graphalgorithmsvisualization.algorithms;

import java.util.ArrayList;
import java.util.HashSet;

import com.arthurivanets.graphalgorithmsvisualization.graph.BaseGraph;
import com.arthurivanets.graphalgorithmsvisualization.graph.model.Edge;
import com.arthurivanets.graphalgorithmsvisualization.graph.model.Vertex;
import com.arthurivanets.graphalgorithmsvisualization.listeners.ProgressListener;
import com.arthurivanets.graphalgorithmsvisualization.util.BinaryHeap;
import com.arthurivanets.graphalgorithmsvisualization.util.Drawable;

public abstract class BaseAlgorithm implements Drawable {
	
	
	protected Vertex mInitialNode;
	protected Vertex mDestinationNode;
	protected Vertex mCurrentNode;
	
	protected BaseGraph mGraph;
	
	protected HashSet<Vertex> mVisitedNodes;
	protected BinaryHeap<Vertex> mUnvisitedNodes;
	
	protected ArrayList<Edge> mComposedPath;
	
	protected ProgressListener mProgressListener;
	
	
	

	protected BaseAlgorithm(BaseGraph graph, Vertex initialNode, Vertex destinationNode) {
		mVisitedNodes = new HashSet<Vertex>();
		mUnvisitedNodes = new BinaryHeap<Vertex>();
		mComposedPath = new ArrayList<Edge>();
		reset(graph, initialNode, destinationNode);
	}
	
	
	
	
	/**
	 * 
	 * <br>
	 * 		Resets all the necessary data for proper execution of the the algorithm.
	 * 		(Allows a single instance of the given class to be reused multiple times)
	 * <br>
	 * 
	 * @param graph - the new Graph.
	 * @param initialNode - the new Initial Node(Vertex).
	 * @param destinationNode - the new Destination Node(Vertex).
	 * 
	 */
	public BaseAlgorithm reset(BaseGraph graph, Vertex initialNode, Vertex destinationNode) {
		mGraph = graph;
		mInitialNode = initialNode;
		mDestinationNode = destinationNode;
		mVisitedNodes.clear();
		mUnvisitedNodes.clear();
		mComposedPath.clear();
		prepareNodes();
		
		return this;
	}
	
	
	
	
	protected abstract void prepareNodes();
	
	
	
	
	public BaseAlgorithm setProgressListener(ProgressListener l) {
		mProgressListener = l;
		return this;
	}
	
	
	
	
	public abstract BaseAlgorithm findTheShortestPath();
	
	
	
	
	protected abstract BaseAlgorithm composePath();
	
	
	
	
	protected void dispatchNodeVisitedEvent(Vertex node) {
		if(mProgressListener != null) {
			mProgressListener.onNodeVisited(node);
		}
	}
	
	
	
	
	protected void dispatchNeighborVisitedEvent(Vertex neighbor) {
		if(mProgressListener != null) {
			mProgressListener.onNeighborVisited(neighbor);
		}
	}
	
	
	
	
	/**
	 * 
	 * <br>
	 * 		Changes the selection state of each individual Edge of the Path to true.
	 * 		All the "selected" edges will be highlighted with a specific color.
	 * <br>
	 * 
	 */
	public BaseAlgorithm selectTheShortestPath(boolean showWeight) {
		if(!isTheShortestPathAvailable()) {
			return this;
		}
		
		for(Edge edge : mComposedPath) {
			edge.setSelected(true);
			edge.setWeightVisible(showWeight);
		}
		
		return this;
	}
	
	
	
	
	/**
	 * 
	 * <br>
	 * 		Changes the selection state of each individual Edge of the Path to false.
	 * 		All the "unselected" edges will retain their native color.
	 * <br>
	 * 
	 */
	public BaseAlgorithm deselectTheShortestPath() {
		if(!isTheShortestPathAvailable()) {
			return this;
		}
		
		for(Edge edge : mComposedPath) {
			edge.setSelected(false);
		}
		
		return this;
	}
	
	
	
	
	/**
	 * 
	 * <br>
	 * 		Returns the cached version of the found shortest path, if there is any.
	 * <br>
	 * 
	 * @return a list of Edges which represent the Path(if the cached version of the path is available,
	 * 		   or an empty list(if there is no cached version of the path)
	 * 
	 */
	public ArrayList<Edge> getTheShortestPath() {
		return mComposedPath;
	}
	
	
	
	
	/**
	 * 
	 * <br>
	 * 		Determines whether there is a cached version of a previously found Path.
	 * <br>
	 * 
	 * @return <strong>true</strong> if the Path was previously found and is available in the cache, <strong>false</strong> otherwise. 
	 */
	public boolean isTheShortestPathAvailable() {
		return (mComposedPath != null && mComposedPath.size() > 0);
	}
	
	
	

}
