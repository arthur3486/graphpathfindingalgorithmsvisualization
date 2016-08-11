package com.arthurivanets.graphalgorithmsvisualization.core;

import java.awt.Color;
import java.awt.Dimension;

import com.arthurivanets.graphalgorithmsvisualization.graph.model.MovabilityModel;

public class Constants {

	
	public static final String DISPLAY_TITLE = "Graph Path Finding Algorithms Visualization";
	public static final String CONTROL_PANEL_TITLE = "Control Panel";
	
	public static final String GRAPH_FILE_EXTENSION = ".graph";
	
	public static final int DISPLAY_WIDTH = 900;
	public static final int DISPLAY_HEIGHT = 600;
	
	public static final int CONTROL_PANEL_WIDTH = 300;
	public static final int CONTROL_PANEL_HEIGHT = DISPLAY_HEIGHT;
	
	public static final Dimension DISPLAY_SIZE = new Dimension(DISPLAY_WIDTH, DISPLAY_HEIGHT);
	public static final Dimension CONTOL_PANEL_SIZE = new Dimension(CONTROL_PANEL_WIDTH, CONTROL_PANEL_HEIGHT);
	
	public static final int DISPLAY_FPS = 60;
	
	public static final int VERTEX_SIZE = 10;
	public static final int TEXT_SIZE = 14;
	
	public static final MovabilityModel DEFAULT_MOVABILITY_MODEL = MovabilityModel.ANY_DIRECTION;
	
	public static final int OBSTACLE_WEIGHT_COMPENSATION = 10;
	
	public static final int DEFAULT_CELL_SIZE = 18;
	
	public static final Color TEXT_COLOR = Color.WHITE;
	public static final Color EDGE_COLOR = Color.GREEN;
	public static final Color SELECTED_EDGE_COLOR = Color.RED;
	

}
