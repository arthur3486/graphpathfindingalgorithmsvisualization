package com.arthurivanets.graphalgorithmsvisualization.controller;

import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;

import com.arthurivanets.graphalgorithmsvisualization.algorithms.Algorithm;
import com.arthurivanets.graphalgorithmsvisualization.graph.BaseGraph;
import com.arthurivanets.graphalgorithmsvisualization.graph.model.MovabilityModel;
import com.arthurivanets.graphalgorithmsvisualization.model.DisplayModel;
import com.arthurivanets.graphalgorithmsvisualization.util.Drawable;
import com.arthurivanets.graphalgorithmsvisualization.view.DisplayView;

public class DisplayController implements DisplayView.Callback {
	
	
	private DisplayModel mModel;
	private DisplayView mView;
	
	
	

	public DisplayController(DisplayModel model, DisplayView view) {
		mModel = model;
		mView = view;
		mView.setCallback(this);
	}
	
	
	
	
	public void addDrawable(Drawable drawable) {
		mModel.addDrawable(drawable);
	}
	
	
	
	
	public void removeDrawable(Drawable drawable) {
		mModel.removeDrawable(drawable);
	}
	
	
	
	
	public void clearTargets() {
		mModel.clearTargets();
	}
	
	
	
	
	public void setGraph(BaseGraph graph) {
		mModel.setGraph(graph);
	}
	
	
	
	
	public void setAlgorithm(Algorithm algorithm) {
		mModel.setAlgorithmType(algorithm);
	}
	
	
	
	
	public void setMovabilityModel(MovabilityModel movabilityModel) {
		mModel.setMovabilityModel(movabilityModel);
	}
	
	
	
	
	public void setNodeType(int nodeType) {
		mModel.setNodeType(nodeType);
	}
	
	
	
	
	public void setAssistanceEnabled(boolean isEnabled) {
		mModel.setAssistanceEnabled(isEnabled);
	}
	
	
	
	
	public void setAnimationEnabled(boolean isEnabled) {
		mModel.setAnimationEnabled(isEnabled);
	}
	
	
	
	
	public void setAnimationSpeedRate(float speedRate) {
		mModel.setAnimationSpeedRate(speedRate);
	}
	
	
	
	
	public Frame getParentFrame() {
		return mView.getDisplay();
	}
	
	
	
	
	public void resetAlgorithm() {
		mModel.resetAlgorithm();
	}
	
	
	
	
	public void runAlgorithm() {
		mModel.runAlgorithm();
	}
	
	
	
	
	public void saveGraph(File file) {
		mModel.saveGraph(file);
	}
	
	
	
	
	public void updateView() {
		//...
	}




	@Override
	public void onDraw(Graphics2D graphics) {
		mModel.draw(graphics);
	}




	@Override
	public void onMousePressed(MouseEvent event) {
		mModel.handleMousePress(event.getX(), event.getY());
	}




	@Override
	public void onMouseReleased(MouseEvent event) {
		mModel.handleMouseRelease(event.getX(), event.getY());
	}




	@Override
	public void onMouseClicked(MouseEvent event) {
		mModel.handleMouseClick(event.getX(), event.getY());
	}




	@Override
	public void onMouseMoved(MouseEvent event) {
		mModel.handleMouseMove(event.getX(), event.getY());
	}




	@Override
	public void onMouseDragged(MouseEvent event) {
		mModel.handleMouseDrag(event.getX(), event.getY());
	}




	@Override
	public void onMouseOnscreen(MouseEvent event) {
		//do nothing.
	}




	@Override
	public void onMouseOffscreen(MouseEvent event) {
		mModel.cancelEdgeCreation();
	}




	@Override
	public void onKeyPressed(KeyEvent event) {
		mModel.handleKeyPress(event);
	}




	@Override
	public void onKeyReleased(KeyEvent event) {
		mModel.handleKeyRelease(event);
	}
	
	
	

}
