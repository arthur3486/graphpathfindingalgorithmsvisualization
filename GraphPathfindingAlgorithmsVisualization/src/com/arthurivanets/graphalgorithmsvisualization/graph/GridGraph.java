package com.arthurivanets.graphalgorithmsvisualization.graph;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import com.arthurivanets.graphalgorithmsvisualization.core.Constants;
import com.arthurivanets.graphalgorithmsvisualization.graph.model.Edge;
import com.arthurivanets.graphalgorithmsvisualization.graph.model.MovabilityModel;
import com.arthurivanets.graphalgorithmsvisualization.graph.model.Vertex;

public class GridGraph extends BaseGraph {
	
	
	private int mCellSize;
	
	
	

	public GridGraph(int cellSize, MovabilityModel movabilityModel) {
		super();
		setGraphType(GraphType.VERTEX_GRID);
		setMovabilityModel(movabilityModel);
		setCellSize(cellSize);
		setEdgeSplittingEnabled(false);
		createGrid();
	}
	
	
	
	
	public GridGraph(int cellSize, MovabilityModel movabilityModel, ArrayList<String> graphData) {
		super();
		setGraphType(GraphType.VERTEX_GRID);
		setMovabilityModel(movabilityModel);
		setCellSize(cellSize);
		setEdgeSplittingEnabled(false);
		load(graphData);
	}
	
	
	
	
	private void createGrid() {
		//precalculating stuff
		int halfSize = (mCellSize / 2);
		int itemPadding = 1;
		int frameWidth = Constants.DISPLAY_WIDTH;
		int frameHeight = Constants.DISPLAY_HEIGHT;
		int sizeWithPadding = (mCellSize + itemPadding * 2);
		int vertexHorizontalCount = (frameWidth / sizeWithPadding);
		int vertexVerticalCount = (frameHeight / sizeWithPadding);
		
		Vertex startVertex;
		Vertex endVertex;
		
		//creating the Edges
		for(int i = 1; i < vertexVerticalCount; i++) {
			for(int j = 1; j < vertexHorizontalCount; j++) {
				//horizontal edge
				startVertex = getVertexForPoint(
					(halfSize + itemPadding + (j - 1) * sizeWithPadding), 
					(halfSize + itemPadding + (i - 1) * sizeWithPadding)
				);
					
				endVertex = getVertexForPoint(
					(halfSize + itemPadding + j * sizeWithPadding), 
					(halfSize + itemPadding + (i - 1) * sizeWithPadding)
				);
					
				addEdge(createEdge(
					startVertex,
					endVertex
				));
				
				//the last horizontal edge
				if(i == (vertexVerticalCount - 1)) {
					startVertex = getVertexForPoint(
						(halfSize + itemPadding + (j - 1) * sizeWithPadding), 
						(halfSize + itemPadding + i * sizeWithPadding)
					);
							
					endVertex = getVertexForPoint(
						(halfSize + itemPadding + j * sizeWithPadding), 
						(halfSize + itemPadding + i * sizeWithPadding)
					);
							
					addEdge(createEdge(
						startVertex,
						endVertex
					));
				}
				
				//vertical edges
				startVertex = getVertexForPoint(
					(halfSize + itemPadding + (j - 1) * sizeWithPadding), 
					(halfSize + itemPadding + (i - 1) * sizeWithPadding)
				);
					
				endVertex = getVertexForPoint(
					(halfSize + itemPadding + (j - 1) * sizeWithPadding), 
					(halfSize + itemPadding + i * sizeWithPadding)
				);
							
				addEdge(createEdge(
					startVertex,
					endVertex
				));
				
				//the last one set of vertical edges
				if(j == (vertexHorizontalCount - 1)) {
					startVertex = getVertexForPoint(
						(halfSize + itemPadding + j * sizeWithPadding), 
						(halfSize + itemPadding + (i - 1) * sizeWithPadding)
					);
									
					endVertex = getVertexForPoint(
						(halfSize + itemPadding + j * sizeWithPadding), 
						(halfSize + itemPadding + i * sizeWithPadding)
					);
									
					addEdge(createEdge(
						startVertex,
						endVertex
					));
				}
				
				//handling the movability model
				if(!mMovabilityModel.equals(MovabilityModel.ANY_DIRECTION)) {
					continue;
				}
				
				//diagonal edge
				startVertex = getVertexForPoint(
					(halfSize + itemPadding + (j - 1) * sizeWithPadding), 
					(halfSize + itemPadding + (i - 1) * sizeWithPadding)
				);
					
				endVertex = getVertexForPoint(
					(halfSize + itemPadding + j * sizeWithPadding), 
					(halfSize + itemPadding + i * sizeWithPadding)
				);
					
				addEdge(createEdge(
					startVertex,
					endVertex
				));
					
				//anti-diagonal edge
				startVertex = getVertexForPoint(
					(halfSize + itemPadding + j * sizeWithPadding), 
					(halfSize + itemPadding + (i - 1) * sizeWithPadding)
				);
						
				endVertex = getVertexForPoint(
					(halfSize + itemPadding + (j - 1) * sizeWithPadding), 
					(halfSize + itemPadding + i * sizeWithPadding)
				);
						
				addEdge(createEdge(
					startVertex,
					endVertex
				));
			}
		}
	}
	
	
	

	@Override
	public void addEdge(Edge edge) {
		edge.setFill(true);
		edge.setWeightVisible(false);
		
		edge.getStartVertex()
				.setSize(mCellSize / 2)
				.setColorInheritable(false)
				.setShapeType(Vertex.ShapeType.SQUARE)
				.setMovabilityModel(mMovabilityModel);
		
		edge.getEndVertex()
				.setSize(mCellSize / 2)
				.setColorInheritable(false)
				.setShapeType(Vertex.ShapeType.SQUARE)
				.setMovabilityModel(mMovabilityModel);
		
		super.addEdge(edge);
	}

	


	@Override
	public Vertex getVertexForPoint(int x, int y) {
		return super.getVertexForPoint(x, y)
						.setSize(mCellSize / 2)
						.setColorInheritable(false)
						.setShapeType(Vertex.ShapeType.SQUARE)
						.setMovabilityModel(mMovabilityModel);
	}
	
	
	
	
	public GridGraph setCellSize(int cellSize) {
		mCellSize = cellSize;
		return this;
	}




	@Override
	public void onDraw(Graphics2D g) {
		//...
	}




	@Override
	public Color getBackgroundColor() {
		return Color.BLACK;
	}
	
	
	

}
