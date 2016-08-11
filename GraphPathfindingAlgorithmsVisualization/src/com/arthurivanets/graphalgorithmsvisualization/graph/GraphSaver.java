package com.arthurivanets.graphalgorithmsvisualization.graph;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import com.arthurivanets.graphalgorithmsvisualization.graph.model.Edge;
import com.arthurivanets.graphalgorithmsvisualization.util.TextUtils;

public class GraphSaver {

	
	
	
	public static void saveGraph(File file, BaseGraph graph) {
		if(file == null || (file.exists() && (!file.isFile() || !file.canWrite()))) {
			return;
		}
		
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put(BaseGraph.Parameter.GRAPH_TYPE, graph.getGraphType().name());
		parameters.put(BaseGraph.Parameter.MOVABILITY_MODEL, graph.getMovabilityModel().name());
		parameters.put(BaseGraph.Parameter.DRAWING_MODE, graph.getDrawingMode().name());
		
		writeTheData(file, parameters, graph);
	}
	
	
	
	
	private static void writeTheData(File file, HashMap<String, String> parameters, BaseGraph graph) {
		BufferedWriter writer = null;
		
		try {
			writer = new BufferedWriter(new FileWriter(file));
			
			//writing the parameter header first
			writer.append(TextUtils.packParameters(parameters)).append("\n");
			
			//now writing the actual graph data
			for(Edge edge : graph.getEdges()) {
				writer.append(edge.toCSV()).append("\n");
			}
			
			writer.flush();
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			if(writer != null) {
				try {
					writer.close();
				} catch(IOException e) {
					// do nothing.
				}
			}
		}
	}
	
	
	

}
