package com.arthurivanets.graphalgorithmsvisualization.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

import com.arthurivanets.graphalgorithmsvisualization.algorithms.Algorithm;
import com.arthurivanets.graphalgorithmsvisualization.algorithms.AlgorithmFactory;
import com.arthurivanets.graphalgorithmsvisualization.algorithms.BaseAlgorithm;
import com.arthurivanets.graphalgorithmsvisualization.core.Constants;
import com.arthurivanets.graphalgorithmsvisualization.graph.BaseGraph;
import com.arthurivanets.graphalgorithmsvisualization.graph.Graph;
import com.arthurivanets.graphalgorithmsvisualization.graph.GridGraph;
import com.arthurivanets.graphalgorithmsvisualization.graph.model.Edge;
import com.arthurivanets.graphalgorithmsvisualization.graph.model.MovabilityModel;
import com.arthurivanets.graphalgorithmsvisualization.graph.model.Vertex;
import com.arthurivanets.graphalgorithmsvisualization.listeners.ProgressListener;
import com.arthurivanets.graphalgorithmsvisualization.util.Drawable;
import com.arthurivanets.graphalgorithmsvisualization.util.VertexAnimator;

public class DisplayModel {
	
	
	private BaseAlgorithm mAlgorithm;
	
	private BaseGraph mGraph;
	
	private Edge mNewEdge;
	private Vertex mVertex;
	
	private Algorithm mAlgorithmType;
	private MovabilityModel mMovabilityModel;
	private int mNodeType;
	
	private float mAnimationSpeedRate;
	
	private ArrayList<Vertex> mPickedVertices;
	private HashSet<Drawable> mDrawables;
	
	private boolean mIsShiftPressed;
	private boolean mIsAssistanceEnabled;
	private boolean mIsAnimationEnabled;
	
	private VertexAnimator mAnimator;
	
	
	

	public DisplayModel() {
		this(null, null, null, Vertex.Type.PATH, false);
	}
	
	
	
	
	public DisplayModel(BaseGraph graph, Algorithm algorithmType,
						MovabilityModel movabilityModel, int nodeType,
						boolean isAssistanceEnabled) {
		mGraph = graph;
		mAlgorithmType = algorithmType;
		mMovabilityModel = movabilityModel;
		mNodeType = nodeType;
		mIsAssistanceEnabled = isAssistanceEnabled;
		mDrawables = new HashSet<Drawable>();
		mPickedVertices = new ArrayList<Vertex>();
		mIsShiftPressed = false;
		mIsAnimationEnabled = false;
	}
	
	
	
	
	public DisplayModel addDrawable(Drawable drawable) {
		mDrawables.add(drawable);
		return this;
	}
	
	
	
	
	public Drawable removeDrawable(Drawable drawable) {
		return (mDrawables.remove(drawable) ? drawable : null);
	}
	
	
	
	
	public DisplayModel setGraph(BaseGraph graph) {
		resetAlgorithm();
		mGraph = graph;
		return this;
	}
	
	
	
	
	public BaseGraph getGraph() {
		return mGraph;
	}
	
	
	
	
	public DisplayModel setAlgorithmType(Algorithm algorithmType) {
		mAlgorithmType = algorithmType;
		resetAlgorithm();
		return this;
	}
	
	
	
	
	public Algorithm getAlgorithmType() {
		return mAlgorithmType;
	}
	
	
	
	
	public DisplayModel setMovabilityModel(MovabilityModel movabilityModel) {
		mMovabilityModel = movabilityModel;
		return this;
	}
	
	
	
	
	public MovabilityModel getMovabilityModel() {
		return mMovabilityModel;
	}
	
	
	
	
	public DisplayModel setNodeType(int nodeType) {
		mNodeType = nodeType;
		return this;
	}
	
	
	
	
	public int getNodeType() {
		return mNodeType;
	}
	
	
	
	
	public DisplayModel setAnimationSpeedRate(float speedRate) {
		mAnimationSpeedRate = speedRate;
		return this;
	}
	
	
	
	
	public float getAnimationSpeedRate() {
		return mAnimationSpeedRate;
	}
	
	
	
	
	public ArrayList<Vertex> getPickedVertices() {
		return mPickedVertices;
	}
	
	
	
	
	public boolean areTargetsSet() {
		return (mPickedVertices != null && mPickedVertices.size() >= 2);
	}
	
	
	
	
	public DisplayModel setAssistanceEnabled(boolean isEnabled) {
		mIsAssistanceEnabled = isEnabled;
		return this;
	}
	
	
	
	
	public boolean isAssistanceEnabled() {
		return mIsAssistanceEnabled;
	}
	
	
	
	
	public DisplayModel setAnimationEnabled(boolean isEnabled) {
		mIsAnimationEnabled = isEnabled;
		
		if(!isEnabled) {
			cancelAnimation();
		}
		
		return this;
	}
	
	
	
	
	public boolean isAnimationEnabled() {
		return mIsAnimationEnabled;
	}
	
	
	
	
	public boolean isGraphBuildingSupported() {
		return (mGraph instanceof Graph);
	}
	
	
	
	
	public boolean isNodeEditingSupported() {
		return (mGraph instanceof GridGraph);
	}
	
	
	
	
	public void handleMouseClick(int x, int y) {
		if(mGraph == null || (mVertex = mGraph.getExistingVertexForPoint(x, y)) == null) {
			return;
		}
		
		if(mNodeType == Vertex.Type.OBJECT || isGraphBuildingSupported()) {
			if(mVertex.getType() == Vertex.Type.OBJECT) {
				mVertex.setType(Vertex.Type.PATH);
				mPickedVertices.remove(mVertex);
				cancelAnimation();
				stopAlgorithm();
			} else if(mPickedVertices.size() < 2) {
				mVertex.setType(Vertex.Type.OBJECT);
				mPickedVertices.add(mVertex);
			}
		} else if(isNodeEditingSupported()) {
			mVertex.setType(mNodeType);
		}
	}
	
	
	
	
	public void handleMousePress(int x, int y) {
		if(isGraphBuildingSupported()) {
			mNewEdge = mGraph.createEdge(
				mGraph.getVertexForPoint(x, y),
				new Vertex(x, y).setSize(Constants.VERTEX_SIZE)
			);
			mNewEdge.setSelected(true);
		}
	}
	
	
	
	
	public void handleMouseRelease(int x, int y) {
		if(isGraphBuildingSupported()) {
			if(mNewEdge == null) {
				return;
			}
			
			adjustEdgeCoordinates(mNewEdge, x, y);
			
			mNewEdge.setEndVertex(mGraph.getVertexForPoint(
				mNewEdge.getEndVertex().getX(),
				mNewEdge.getEndVertex().getY()
			));
			mNewEdge.setSelected(false);
			
			if(mNewEdge.getLength() >= Constants.VERTEX_SIZE * 2) {
				mGraph.addEdge(mNewEdge);
			} else {
				mNewEdge = null;
			}
		}
	}
	
	
	
	
	public void handleMouseMove(int x, int y) {
		// do nothing.
	}
	
	
	
	
	public void handleMouseDrag(int x, int y) {
		if(isGraphBuildingSupported()) {
			adjustEdgeCoordinates(mNewEdge, x, y);
		} else if(isNodeEditingSupported()) {
			if((mNodeType == Vertex.Type.PATH || mNodeType == Vertex.Type.OBSTACLE) 
					&& (mVertex = mGraph.getExistingVertexForPoint(x, y)) != null) {
				mVertex.setType(mNodeType);
			}
		}
	}
	
	
	
	
	public void handleKeyPress(KeyEvent event) {
		mIsShiftPressed = event.isShiftDown();
	}
	
	
	
	
	public void handleKeyRelease(KeyEvent event) {
		mIsShiftPressed = event.isShiftDown();
	}
	
	
	
	
	public void runAlgorithm() {
		if(!areTargetsSet() || (mAlgorithm != null && mAlgorithm.isTheShortestPathAvailable())) {
			return;
		}
		
		if(mAlgorithm == null) {
			mAlgorithm = AlgorithmFactory.getAlgorithm(
				mAlgorithmType, 
				mGraph,
				mPickedVertices.get(0),
				mPickedVertices.get(1)
			);
		}
		
		mAlgorithm.setProgressListener(collectVisitedNodesIfNecessary())
				.findTheShortestPath()
				.selectTheShortestPath(isGraphBuildingSupported());
	}
	
	
	
	
	private ProgressListener collectVisitedNodesIfNecessary() {
		if(!mIsAnimationEnabled) {
			return null;
		}
		
		mAnimator = new VertexAnimator();
		
		ProgressListener progressListener = new ProgressListener() {
			
			@Override
			public void onNodeVisited(Vertex node) {
				if(node.equals(mPickedVertices.get(0)) || node.equals(mPickedVertices.get(1))) {
					return;
				}
				
				mAnimator.addVertex(node.copy().setFill(true).setColorInheritable(true).setColor(Color.BLUE));
			}
			
			@Override
			public void onNeighborVisited(Vertex neighbor) {
				if(neighbor.equals(mPickedVertices.get(0)) || neighbor.equals(mPickedVertices.get(1))) {
					return;
				}
				
				mAnimator.addVertex(neighbor.copy().setFill(true).setColorInheritable(true).setColor(Color.BLUE));
			}
			
		};
		
		return progressListener;
	}
	
	
	
	
	public void resetAlgorithm() {
		cancelAnimation();
		stopAlgorithm();
		clearTargets();
	}
	
	
	
	
	private void adjustEdgeCoordinates(Edge edge, int x, int y) {
		if(mGraph == null || mNewEdge == null) {
			return;
		}
		
		mNewEdge.getEndVertex().setX(x).setY(y);
		
		if(mIsAssistanceEnabled && mIsShiftPressed) {
			if(mNewEdge.isMostlyDiagonal()) {
				int deltaX = Math.abs(mNewEdge.getEndVertex().getX() - mNewEdge.getStartVertex().getX());
				int deltaY = Math.abs(mNewEdge.getEndVertex().getY() - mNewEdge.getStartVertex().getY());
				int xSign = (mNewEdge.getEndVertex().getX() < mNewEdge.getStartVertex().getX() ? -1 : 1);
				int ySign = (mNewEdge.getEndVertex().getY() < mNewEdge.getStartVertex().getY() ? -1 : 1);
				int sideSize = ((deltaX + deltaY) / 2);
				int hypotenuseSize = (int) Math.sqrt(sideSize * sideSize + sideSize * sideSize);
				
				mNewEdge.getEndVertex()
						.setX(mNewEdge.getStartVertex().getX() + xSign * hypotenuseSize)
						.setY(mNewEdge.getStartVertex().getY() + ySign * hypotenuseSize);
			} else if(mNewEdge.isMostlyHorizontal()) {
				mNewEdge.getEndVertex().setX(x).setY(mNewEdge.getStartVertex().getY());
			} else {
				mNewEdge.getEndVertex().setX(mNewEdge.getStartVertex().getX()).setY(y);
			}
		}
	}
	
	
	
	
	public void cancelEdgeCreation() {
		mNewEdge = null;
	}
	
	
	
	
	public void stopAlgorithm() {
		mAlgorithm = null;
	}
	
	
	
	
	public void cancelAnimation() {
		mAnimator = null;
	}
	
	
	
	
	public void saveGraph(File file) {
		if(file != null && mGraph != null) {
			mGraph.store(file);
		}
	}
	
	
	
	
	/**
	 * 
	 * <br>
	 * 		Removes all the Picked Vertices.
	 * <br>
	 * 
	 */
	public void clearTargets() {
		for(Vertex vertex : mPickedVertices) {
			vertex.setType(Vertex.Type.PATH);
		}
		
		mPickedVertices.clear();
	}
	
	
	
	
	public void draw(Graphics2D g) {
		//drawing the graph if necessary
		if(mGraph != null) {
			mGraph.draw(g);
		}
		
		//drawing the Edge to be Created(if necessary)
		if(mNewEdge != null) {
			mNewEdge.draw(g);
		}
		
		//drawing the animation of the Node visiting process
		if(mAnimator != null) {
			mAnimator.setSpeedRate(mAnimationSpeedRate);
			mAnimator.draw(g);
		}
		
		//Drawing the found shortest path, if there's any
		if(mAlgorithm != null && mAlgorithm.isTheShortestPathAvailable()) {
			mAlgorithm.draw(g);
		}
		
		//drawing the rest of the drawables, if there is any
		for(Drawable drawable : mDrawables) {
			drawable.draw(g);
		}
	}
	
	
	

}
