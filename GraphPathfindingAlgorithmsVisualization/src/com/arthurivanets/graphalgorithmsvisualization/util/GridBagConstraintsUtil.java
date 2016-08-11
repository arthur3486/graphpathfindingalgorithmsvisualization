package com.arthurivanets.graphalgorithmsvisualization.util;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class GridBagConstraintsUtil {

	
	
	
	public static GridBagConstraints getConstraints(int gridX, int gridY,
			int weightX, int weightY, int fill) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = gridX;
		constraints.gridy = gridY;
		constraints.weightx = weightX;
		constraints.weighty = weightY;
		constraints.anchor = GridBagConstraints.NORTH;
		constraints.fill = fill;
		
		return constraints;
	}
	
	
	
	
	public static GridBagConstraints getConstraints(int gridX, int gridY,
			Insets paddings, double weightX, double weightY, int fill) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = gridX;
		constraints.gridy = gridY;
		constraints.insets = paddings;
		constraints.weightx = weightX;
		constraints.weighty = weightY;
		constraints.anchor = GridBagConstraints.NORTH;
		constraints.fill = fill;
		
		return constraints;
	}
	
	
	
	

}
