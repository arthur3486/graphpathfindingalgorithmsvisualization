package com.arthurivanets.graphalgorithmsvisualization.algorithms;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;

import com.arthurivanets.graphalgorithmsvisualization.graph.BaseGraph;
import com.arthurivanets.graphalgorithmsvisualization.graph.model.Vertex;

public class DijkstrasAlgorithm extends BaseAlgorithm {
	
	
	
	
	public static DijkstrasAlgorithm fromGraph(BaseGraph graph, Vertex initialNode, Vertex destinationNode) {
		return new DijkstrasAlgorithm(graph, initialNode, destinationNode);
	}
	
	
	
	
	
	private DijkstrasAlgorithm(BaseGraph graph, Vertex initialNode, Vertex destinationNode) {
		super(graph, initialNode, destinationNode);
	}
	
	
	
	
	@Override
	protected void prepareNodes() {
		//setting the tentative distances of all of the other nodes
		//to positive infinity and adding them to the set of the unvisited nodes
		//(and also resetting the H Cost in order to, when comparing the Vertices, not get the wrong results)
		for(Vertex vertex : mGraph.getVertices()) {
			vertex.setGCost(vertex.equals(mInitialNode) ? 0 : Double.POSITIVE_INFINITY);
			vertex.setHCost(0);
			vertex.setPreviousVertex(null);
			mUnvisitedNodes.push(vertex);
		}
	}
	
	
	
	
	@Override
	public BaseAlgorithm findTheShortestPath() {
		ArrayList<Vertex> neighbors;
		double calculatedDistance;
		
		//traversing all the unvisited nodes until the destination node is reached
		while(!mUnvisitedNodes.isEmpty()) {
			//consuming the node with the least tentative distance
			//(for the first cycle it's going to be our initial node)
			mCurrentNode = mUnvisitedNodes.pop();
			
			//dispatching the notification about the fact that the new node has been visited
			dispatchNodeVisitedEvent(mCurrentNode);
			
			//if our current node is equal to our destination node(the path was found)
			//then terminating the execution of the algorithm, we're done with path finding.
			if(mCurrentNode.equalsCoordinatewise(mDestinationNode)) {
				break;
			}
			
			//getting all the neighboring nodes for the current node
			neighbors = mGraph.getNeighborsForVertex(mCurrentNode);
			
			//if the node(Vertex) doesn't have any neighbors(which is only
			//possible if graph is directed), then move on to the next node(Vertex)
			if(neighbors == null) {
				continue;
			}
			
			//analyzing all the neighbors of the current node
			//and altering their tentative distances if necessary
			//(if the neighbor's tentative distance is greater that the calculated one)
			for(Vertex neighbor : neighbors) {
				if(!neighbor.isVisitable()) {
					continue;
				}
				
				calculatedDistance = (mCurrentNode.getGCost() + mGraph.getEdgeForVertices(mCurrentNode, neighbor).getWeight());
				
				if(calculatedDistance < neighbor.getGCost()) {
					neighbor.setGCost(calculatedDistance);
					neighbor.setPreviousVertex(mCurrentNode);
					
					//updating the priority of the updated(visited) neighbor
					//within the Vertex Binary Heap, so that all the vertices
					//that became out of order after the last item popping
					//will come back in place(the Heap Sorting will be performed)
					mUnvisitedNodes.updatePrioriry(neighbor);
					
					//dispatching the notification about the fact that the new neighbor has been visited
					dispatchNeighborVisitedEvent(neighbor);
				}
			}
		}
		
		//returning the composed path
		return composePath();
	}
	
	
	
	
	@Override
	protected BaseAlgorithm composePath() {
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		
		//fetching the path vertices
		Vertex vertex = mDestinationNode;
		
		while(vertex != null) {
			vertices.add(vertex);
			vertex = vertex.getPreviousVertex();
		}
		
		//Reversing the order of the Vertices
		Collections.reverse(vertices);
		
		//creating the edges which are going to
		//be the base for our path
		int itemCount = vertices.size();
		
		for(int i = 1; i < itemCount; i++) {
			mComposedPath.add(mGraph.createEdge(
				vertices.get(i - 1),
				vertices.get(i)
			));
		}
		
		return this;
	}




	@Override
	public void draw(Graphics2D g) {
		int itemCount = mComposedPath.size();
		
		if(itemCount == 0) {
			return;
		}
		
		//drawing the Path Edges
		for(int i = 0; i < itemCount; i++) {
			mComposedPath.get(i).draw(g);
		}
	}
	
	
	

}
