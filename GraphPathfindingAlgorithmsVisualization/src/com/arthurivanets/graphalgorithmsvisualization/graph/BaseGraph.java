package com.arthurivanets.graphalgorithmsvisualization.graph;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import com.arthurivanets.graphalgorithmsvisualization.core.Constants;
import com.arthurivanets.graphalgorithmsvisualization.graph.model.Edge;
import com.arthurivanets.graphalgorithmsvisualization.graph.model.MovabilityModel;
import com.arthurivanets.graphalgorithmsvisualization.graph.model.Vertex;
import com.arthurivanets.graphalgorithmsvisualization.util.Drawable;
import com.arthurivanets.graphalgorithmsvisualization.util.Loadable;
import com.arthurivanets.graphalgorithmsvisualization.util.Storable;

public abstract class BaseGraph implements Drawable, Storable<File>, Loadable<ArrayList<String>> {
	
	
	protected DrawingMode mDrawingMode;
	protected GraphType mGraphType;
	protected MovabilityModel mMovabilityModel;
	
	protected HashMap<String, Edge> mEdges;
	protected HashMap<String, Vertex> mVertices;
	protected HashSet<Drawable> mDrawables;
	
	protected HashMap<String, HashMap<String, Vertex>> mVertexNeighborsMap;
	
	protected int mItemCount;
	
	private boolean mIsDirected;
	private boolean mIsEdgeSplittingEnabled;
	
	
	
	
	public enum DrawingMode {
		
		DRAW_EDGES_ONLY,
		DRAW_VERTICES_ONLY,
		DRAW_EVERYTHING;
		
	}
	
	
	
	
	public interface Parameter {
		
		public static final String DRAWING_MODE = "DRAWING_MODE";
		public static final String GRAPH_TYPE = "GRAPH_TYPE";
		public static final String MOVABILITY_MODEL = "MOVABILITY_MODEL";
		
	}
	
	
	
	
	public BaseGraph() {
		this(DrawingMode.DRAW_EDGES_ONLY, false);
	}
	
	
	
	
	public BaseGraph(boolean isDirected) {
		this(DrawingMode.DRAW_EDGES_ONLY, isDirected);
	}
	
	
	
	
	public BaseGraph(DrawingMode drawingMode) {
		this(drawingMode, false);
	}
	
	
	

	public BaseGraph(DrawingMode drawingMode, boolean isDirected) {
		mDrawingMode = drawingMode;
		mEdges = new HashMap<String, Edge>();
		mVertices = new HashMap<String, Vertex>();
		mDrawables = new HashSet<Drawable>();
		mVertexNeighborsMap = new HashMap<String, HashMap<String, Vertex>>();
		mIsDirected = isDirected;
		mIsEdgeSplittingEnabled = true;
	}
	
	
	
	
	public void setDrawingMode(DrawingMode drawingMode) {
		mDrawingMode = drawingMode;
	}
	
	
	
	
	public DrawingMode getDrawingMode() {
		return mDrawingMode;
	}
	
	
	
	
	public void setGraphType(GraphType graphType) {
		mGraphType = graphType;
	}
	
	
	
	
	public GraphType getGraphType() {
		return mGraphType;
	}
	
	
	
	
	public void setMovabilityModel(MovabilityModel movabilityModel) {
		mMovabilityModel = movabilityModel;
	}
	
	
	
	
	public MovabilityModel getMovabilityModel() {
		return mMovabilityModel;
	}
	
	
	
	
	public void setDirected(boolean isDirected) {
		mIsDirected = isDirected;
	}
	
	
	
	
	public boolean isDirected() {
		return mIsDirected;
	}
	
	
	
	
	public void setEdgeSplittingEnabled(boolean isEdgeSplittingEnabled) {
		mIsEdgeSplittingEnabled = isEdgeSplittingEnabled;
	}
	
	
	
	
	public boolean isEdgeSplittingEnabled() {
		return mIsEdgeSplittingEnabled;
	}
	
	
	
	
	public Edge createEdge(Vertex startVertex, Vertex endVertex) {
		Edge edge = new Edge(startVertex, endVertex);
		edge.setColor(Constants.EDGE_COLOR);
		
		return edge;
	}
	
	
	
	
	public void removeSelectionFromEdges() {
		for(Edge edge : getEdges()) {
			edge.setSelected(false);
		}
	}
	
	
	
	
	public void addEdge(Edge edge) {
		//preventing the creation of duplicates
		if(contains(edge)) {
			return;
		}
		
		//adding the edge
		mEdges.put(edge.getUniqueKey(), edge);
		addVertices(edge);
		mDrawables.add(edge);
		
		//keeping the track of the neighbors
		addNeighborsIfNecessary(edge);
	}
	
	
	
	
	private void addVertices(Edge edge) {
		if(edge == null) {
			return;
		}
		
		addVertex(edge.getStartVertex());
		addVertex(edge.getEndVertex());
	}
	
	
	
	
	private void addVertex(Vertex vertex) {
		mVertices.put(vertex.getUniqueKey(), vertex);
	}
	
	
	
	
	private void removeVertices(Edge edge) {
		if(edge == null) {
			return;
		}
		
		removeVertex(edge.getStartVertex());
		removeVertex(edge.getEndVertex());
	}
	
	
	
	
	private void removeVertex(Vertex vertex) {
		if(!hasReferences(vertex)) {
			mVertices.remove(vertex.getUniqueKey());
		}
	}
	
	
	
	
	private void addNeighborsIfNecessary(Edge edge) {
		//adding neighbors for the start Vertex
		HashMap<String, Vertex> neighbors = mVertexNeighborsMap.get(edge.getStartVertex().getUniqueKey());
		
		if(neighbors == null) {
			neighbors = new HashMap<String, Vertex>();
		}
		
		neighbors.put(edge.getEndVertex().getUniqueKey(), edge.getEndVertex());
		
		mVertexNeighborsMap.put(edge.getStartVertex().getUniqueKey(), neighbors);
		
		//if the Graph is directed, then there is no point in creating the bidirectional relation between the nodes(Vertices)
		if(isDirected()) {
			return;
		}
		
		//adding neighbors for the end Vertex
		neighbors = mVertexNeighborsMap.get(edge.getEndVertex().getUniqueKey());
		
		if(neighbors == null) {
			neighbors = new HashMap<String, Vertex>();
		}
		
		neighbors.put(edge.getStartVertex().getUniqueKey(), edge.getStartVertex());
		
		mVertexNeighborsMap.put(edge.getEndVertex().getUniqueKey(), neighbors);
	}
	
	
	
	
	private void removeNeighborsIfNecessary(Edge edge) {
		//updating/removing the neighbors of the Start Vertex of the Edge if necessary 
		HashMap<String, Vertex> neighbors = mVertexNeighborsMap.get(edge.getStartVertex().getUniqueKey());
		
		if(neighbors == null || neighbors.size() <= 1) {
			mVertexNeighborsMap.remove(edge.getStartVertex().getUniqueKey());
		} else if(neighbors != null && neighbors.size() > 1) {
			neighbors.remove(edge.getEndVertex().getUniqueKey());
			mVertexNeighborsMap.put(edge.getStartVertex().getUniqueKey(), neighbors);
		}
		
		//if the Graph is directed, then there is no point in checking and removing the bidirectional relation between the nodes(Vertices)
		if(isDirected()) {
			return;
		}
		
		//updating/removing the neighbors of the End Vertex of the Edge if necessary
		neighbors = mVertexNeighborsMap.get(edge.getEndVertex().getUniqueKey());
		
		if(neighbors == null || neighbors.size() <= 1) {
			mVertexNeighborsMap.remove(edge.getEndVertex().getUniqueKey());
		} else if(neighbors != null && neighbors.size() > 1) {
			neighbors.remove(edge.getStartVertex().getUniqueKey());
			mVertexNeighborsMap.put(edge.getEndVertex().getUniqueKey(), neighbors);
		}
	}
	
	
	
	
	public Edge getEdgeForVertices(Vertex vertex1, Vertex vertex2) {
		Edge edge = null;
		String key;
		
		key = Edge.composeKey(
			Edge.createKeyPart(vertex1, vertex2),
			Edge.createKeyPart(vertex2, vertex1)
		);
		
		if((edge = mEdges.get(key)) != null) {
			return edge;
		}
		
		key = Edge.composeKey(
			Edge.createKeyPart(vertex2, vertex1),
			Edge.createKeyPart(vertex1, vertex2)
		);
		
		if((edge = mEdges.get(key)) != null) {
			return edge;
		}
		
		return null;
	}
	
	
	
	
	public Edge removeEdge(Edge edge) {
		if((edge == null) || (mEdges.remove(edge.getUniqueKey()) == null)) {
			return null;
		}
		
		//removing the corresponding reference contained by the Drawables holder
		mDrawables.remove(edge);
		
		//removing the neighbors associated with the Edge, if necessary
		removeNeighborsIfNecessary(edge);
		
		//removing the vertices if necessary
		removeVertices(edge);
		
		return edge;
	}
	
	
	
	
	public Collection<Edge> getEdges() {
		return mEdges.values();
	}
	
	
	
	
	public Collection<Vertex> getVertices() {
		return mVertices.values();
	}
	
	
	
	
	public boolean contains(Edge edge) {
		return (getEdgeForVertices(edge.getStartVertex(), edge.getEndVertex()) != null);
	}
	
	
	
	
	/**
	 * <br>
	 * 		It's worth noting that this method doesn't create a new Vertex,
	 * 		 if it fails to find the existing one, like
	 * 		<strong>getVertexForPoint(int x, int y)</strong> does.
	 * <br>
	 * 
	 * <br>
	 * 		Tries to find the existing Vertex with the given coordinates.
	 * 		If it finds one, then returns the found vertex.
	 * 		If it doesm't, then returns null.
	 * <br>
	 * 
	 * @param x
	 * @param y
	 * 
	 * @return <strong>found Vertex</strong>, or <strong>null</strong>
	 * 
	 */
	public Vertex getExistingVertexForPoint(int x, int y) {
		for(Edge edge : getEdges()) {
			if(edge.getStartVertex().contains(x, y)) {
				return edge.getStartVertex();
			}
			
			if(edge.getEndVertex().contains(x, y)) {
				return edge.getEndVertex();
			}
		}
		
		return null;
	}
	
	
	
	
	/**
	 * 
	 * <br>
	 * 		Tries to find the Vertex for the given coordinates.
	 * 		If the Vertex is found, it gets returned.
	 * 		If no Vertex is found, it creates a new one. 
	 * <br>
	 * 
	 * @param x
	 * @param y
	 * 
	 * @return a found or created Vertex.
	 * 
	 */
	public Vertex getVertexForPoint(int x, int y) {
		Vertex newVertex = new Vertex(x, y).setSize(Constants.VERTEX_SIZE);
		Point intersectionPoint;
		
		for(Edge edge : getEdges()) {
			if(edge.getStartVertex().contains(x, y)) {
				return edge.getStartVertex();
			}
			
			if(edge.getEndVertex().contains(x, y)) {
				return edge.getEndVertex();
			}
			
			if(mIsEdgeSplittingEnabled && (intersectionPoint = edge.getIntersectionPoint(newVertex)) != null
					&& edge.isContainedByTheCurrentCoordinateRange(newVertex)) {
				newVertex.setX(intersectionPoint.x);
				newVertex.setY(intersectionPoint.y);
				
				//splitting the current Edge into two Edges and removing the it from the list.
				Edge currentEdge = removeEdge(edge);
				Edge firstEdge = createEdge(
					currentEdge.getStartVertex(),
					newVertex
				);
				Edge secondEdge = createEdge(
					newVertex, 
					currentEdge.getEndVertex()
				);
				
				//adding the newly created Edges.
				addEdge(firstEdge);
				addEdge(secondEdge);
				
				return newVertex;
			}
		}
		
		return newVertex;
	}
	
	
	
	
	/**
	 * 
	 * <br>
	 * 		Retrieves a Set of the (cached)neighbors for the given Vertex, if there is any.
	 * <br>
	 * 
	 * @param vertex - the Vertex to get the neighbors for.
	 * 
	 * @return a Set of the Vertices which are neighbors to the given Vertex,
	 * 		   or null if the given Vertex has no neighbors.
	 * 
	 */
	public ArrayList<Vertex> getNeighborsForVertex(Vertex vertex) {
		return (hasNeighbors(vertex) ? new ArrayList<Vertex>(mVertexNeighborsMap.get(vertex.getUniqueKey()).values()) : null);
	}
	
	
	
	
	/**
	 * 
	 * <br>
	 * 		Checks if the current Graph has the neighbors for the given node(Vertex).
	 * <br>
	 * 
	 * @param vertex - the node(Vertex) to check the neighbors existence for.
	 * @return <strong>true</strong> if the current Graph has neighbors for the given node(Vertex), <strong>false</strong> otherwise.
	 * 
	 */
	public boolean hasNeighbors(Vertex vertex) {
		return (mVertexNeighborsMap.get(vertex.getUniqueKey()) != null && mVertexNeighborsMap.get(vertex.getUniqueKey()).size() > 0);
	}
	
	
	
	
	/**
	 * 
	 * <br>
	 * 		Checks if the current Graph has any references to the given Vertex.
	 * <br>
	 * 
	 * @param vertex
	 * @return
	 * 
	 */
	public boolean hasReferences(Vertex vertex) {
		for(Edge edge : getEdges()) {
			if(edge.getStartVertex().equalsCoordinatewise(vertex) || edge.getEndVertex().equalsCoordinatewise(vertex)) {
				return true;
			}
		}
		
		return false;
	}
	
	
	
	
	/**
	 * 
	 * <br>
	 * 		Traverses the Edge list and tries to find all the neighbors for the given Vertex.
	 * <br>
	 * 
	 * @param vertex - the Vertex to get the neighbors for.
	 * 
	 * @return a Set of the Vertices which are neighbors to the given Vertex,
	 * 		   or an empty list if the given Vertex has no neighbors.
	 * 
	 */
	public ArrayList<Vertex> findNeighborsForVertex(Vertex vertex) {
		ArrayList<Vertex> neighbors = new ArrayList<Vertex>();
		
		for(Edge edge : getEdges()) {
			if(edge.getStartVertex().equalsCoordinatewise(vertex)) {
				neighbors.add(edge.getEndVertex());
			}
		}
		
		return neighbors;
	}
	
	
	
	
	public int getEdgeCount() {
		return ((mEdges != null) ? mEdges.size() : 0);
	}
	
	
	
	
	@Override
	public void draw(Graphics2D g) {
		//drawing the main background
		g.setColor(getBackgroundColor());
		g.fillRect(0, 0, Constants.DISPLAY_WIDTH, Constants.DISPLAY_HEIGHT);
		
		//drawing the actual graph
		if(mDrawingMode.equals(DrawingMode.DRAW_EDGES_ONLY)) {
			for(Edge edge : getEdges()) {
				edge.draw(g);
			}
		} else if(mDrawingMode.equals(DrawingMode.DRAW_VERTICES_ONLY)) {
			for(Vertex vertex : getVertices()) {
				vertex.draw(g);
			}
		} else {
			for(Drawable drawable : mDrawables) {
				drawable.draw(g);
			}
		}
		
		//allowing the extenders to draw the additional stuff
		onDraw(g);
	}
	
	
	
	
	public abstract void onDraw(Graphics2D g);
	
	
	
	
	public abstract Color getBackgroundColor();




	@Override
	public void store(File output) {
		GraphSaver.saveGraph(output, this);
	}




	@Override
	public void load(ArrayList<String> input) {
		Edge edge;
		Vertex startVertex;
		Vertex endVertex;
		
		int itemCount = input.size();
		
		for(int i = 0; i < itemCount; i++) {
			edge = new Edge().fromCSV(input.get(i));
			startVertex = getExistingVertexForPoint(edge.getStartVertex().getX(), edge.getStartVertex().getY());
			endVertex = getExistingVertexForPoint(edge.getEndVertex().getX(), edge.getEndVertex().getY());
			
			if(startVertex != null) {
				edge.setStartVertex(startVertex);
			}
			
			if(endVertex != null) {
				edge.setEndVertex(endVertex);
			}
			
			addEdge(edge);
		}
	}
	
	
	

}

