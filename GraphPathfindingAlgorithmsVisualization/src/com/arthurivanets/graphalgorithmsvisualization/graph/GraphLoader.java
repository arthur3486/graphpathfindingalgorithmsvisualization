package com.arthurivanets.graphalgorithmsvisualization.graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.arthurivanets.graphalgorithmsvisualization.core.Constants;
import com.arthurivanets.graphalgorithmsvisualization.graph.model.MovabilityModel;
import com.arthurivanets.graphalgorithmsvisualization.graph.model.MovabilityModelFactory;
import com.arthurivanets.graphalgorithmsvisualization.util.TextUtils;

public class GraphLoader {

	
	
	
	public static BaseGraph loadGraph(File file) {
		if(file == null || !file.exists() || !file.isFile() || !file.canRead()) {
			return null;
		}
		
		BaseGraph graph = null;
		
		HashMap<String, String> params = new HashMap<String, String>();
		ArrayList<String> data = new ArrayList<String>();
		
		readTheData(file, params, data);
		
		if(params.containsKey(BaseGraph.Parameter.GRAPH_TYPE) && params.containsKey(BaseGraph.Parameter.MOVABILITY_MODEL)) {
			GraphType graphType = GraphTypeFactory.getGraphTypeForName(params.get(BaseGraph.Parameter.GRAPH_TYPE));
			MovabilityModel movabilityModel = MovabilityModelFactory.getMovabilityModelForName(params.get(BaseGraph.Parameter.MOVABILITY_MODEL));
			
			if(graphType.equals(GraphType.REGULAR)) {
				graph = new Graph(data);
			} else if(graphType.equals(GraphType.VERTEX_GRID)) {
				graph = new GridGraph(Constants.DEFAULT_CELL_SIZE, movabilityModel, data);
			}
		}
		
		return graph;
	}
	
	
	
	
	private static void readTheData(File file, HashMap<String, String> outParams, ArrayList<String> outData) {
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader(file));
			
			String readLine;
			boolean isFirstLine = true;
			
			while((readLine = reader.readLine()) != null) {
				if(isFirstLine) {
					TextUtils.unpackParameters(outParams, readLine);
					isFirstLine = false;
				} else {
					outData.add(readLine);
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			if(reader != null) {
				try {
					reader.close();
				} catch(IOException e) {
					// do nothing.
				}
			}
		}
	}
	
	
	

}
