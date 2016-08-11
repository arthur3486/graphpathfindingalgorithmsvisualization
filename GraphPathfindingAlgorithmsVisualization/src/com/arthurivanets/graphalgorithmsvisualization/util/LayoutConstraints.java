package com.arthurivanets.graphalgorithmsvisualization.util;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class LayoutConstraints {
	
	
	private GridBagConstraints mConstraints;
	
	
	
	
	public static LayoutConstraints init(int gridWidth, int gridHeight) {
		return new LayoutConstraints(gridWidth, gridHeight);
	}
	
	
	

	private LayoutConstraints(int gridWidth, int gridHeight) {
		mConstraints = new GridBagConstraints();
		mConstraints.gridwidth = gridWidth;
		mConstraints.gridheight = gridHeight;
	}
	
	
	
	
	public LayoutConstraints setGridX(int gridX) {
		mConstraints.gridx = gridX;
		return this;
	}
	
	
	
	
	public LayoutConstraints setGridY(int gridY) {
		mConstraints.gridy = gridY;
		return this;
	}
	
	
	
	
	public LayoutConstraints setInsets(Insets insets) {
		mConstraints.insets = insets;
		return this;
	}
	
	
	
	
	public LayoutConstraints setWeightX(double weightX) {
		mConstraints.weightx = weightX;
		return this;
	}
	
	
	
	
	public LayoutConstraints setWeightY(double weightY) {
		mConstraints.weighty = weightY;
		return this;
	}
	
	
	
	
	public LayoutConstraints setHorizontalPadding(int horizontalPadding) {
		mConstraints.ipadx = horizontalPadding;
		return this;
	}
	
	
	
	
	public LayoutConstraints setVerticalPadding(int verticalPadding) {
		mConstraints.ipady = verticalPadding;
		return this;
	}
	
	
	
	
	public LayoutConstraints setAnchor(int anchor) {
		mConstraints.anchor = anchor;
		return this;
	}
	
	
	
	
	public LayoutConstraints setFill(int fill) {
		mConstraints.fill = fill;
		return this;
	}
	
	
	
	
	
	public GridBagConstraints pack() {
		return mConstraints;
	}
	
	
	

}
