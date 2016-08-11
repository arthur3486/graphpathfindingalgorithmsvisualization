package com.arthurivanets.graphalgorithmsvisualization.controller;

import java.io.File;

import com.arthurivanets.graphalgorithmsvisualization.algorithms.Algorithm;
import com.arthurivanets.graphalgorithmsvisualization.graph.BaseGraph;
import com.arthurivanets.graphalgorithmsvisualization.graph.GraphType;
import com.arthurivanets.graphalgorithmsvisualization.graph.model.MovabilityModel;
import com.arthurivanets.graphalgorithmsvisualization.model.ControlPanelModel;
import com.arthurivanets.graphalgorithmsvisualization.util.AsyncTaskExecutor;
import com.arthurivanets.graphalgorithmsvisualization.view.ControlPanelView;
import com.arthurivanets.graphalgorithmsvisualization.view.ProgressDialog;

public class ControlPanelController implements ControlPanelView.Callback {
	
	
	private ControlPanelModel mModel;
	private ControlPanelView mView;
	
	private DisplayController mDisplayController;
	
	private ProgressDialog mProgressDialog;
	
	
	

	public ControlPanelController(ControlPanelModel model, ControlPanelView view,
								  DisplayController displayController) {
		mModel = model;
		mView = view;
		mView.setCallback(this);
		mDisplayController = displayController;
		configureDisplayController();
	}
	
	
	
	
	public void setSelectedAlgorithm(Algorithm algorithm) {
		mModel.setAlgorithm(algorithm);
	}
	
	
	
	
	public void setSelectedGraphType(GraphType graphType) {
		mModel.setGraphType(graphType);
	}
	
	
	
	
	public void setSelectedMovabilityModel(MovabilityModel movabilityModel) {
		mModel.setMovabilityModel(movabilityModel);
	}
	
	
	
	
	public void setSelectedNodeType(int nodeType) {
		mModel.setNodeType(nodeType);
	}
	
	
	
	
	public void setAssisatnceEnabled(boolean isEnabled) {
		mModel.setAssistanceEnabled(isEnabled);
	}
	
	
	
	
	public void updateView() {
		mView.setSelectedAlgorithm(mModel.getAlgorithm());
		mView.setSelectedGraphType(mModel.getGraphType());
		mView.setSelectedMovabilityModel(mModel.getMovabilityModel());
		mView.setSelectedNodeType(mModel.getNodeType());
		mView.setAssistanceEnabled(mModel.isAssistanceEnabled());
		mView.setAnimationEnabled(mModel.isAnimationEnabled());
		mView.setMovabilityModelPickerVisibility(mModel.isMovabilityPickerEnabled());
		mView.setNodeTypePickerVisibility(mModel.isNodeTypePickerEnabled());
		mView.setAssistanceToggleVisibility(mModel.isAssistanceToggleVisible());
		mView.setAnimationToggleVisibility(mModel.isAnimationToggleVisible());
		mView.setAnimationSpeedSliderVisibility(mModel.isAnimationEnabled());
		
		mDisplayController.updateView();
	}
	
	
	
	
	private void configureDisplayController() {
		mDisplayController.setGraph(mModel.createGraphForType(mModel.getGraphType()));
		mDisplayController.setAlgorithm(mModel.getAlgorithm());
		mDisplayController.setMovabilityModel(mModel.getMovabilityModel());
		mDisplayController.setNodeType(mModel.getNodeType());
		mDisplayController.setAnimationSpeedRate(mModel.getAnimationSpeedRate());
		mDisplayController.setAssistanceEnabled(mModel.isAssistanceEnabled());
		mDisplayController.setAnimationEnabled(mModel.isAnimationEnabled());
	}




	@Override
	public void onAlgorithmPicked(Algorithm algorithm) {
		if(mModel.getAlgorithm().equals(algorithm)) {
			//no changes here, returning.
			return;
		}
		
		mModel.setAlgorithm(algorithm);
		mDisplayController.setAlgorithm(algorithm);
		updateView();
	}




	@Override
	public void onGraphTypePicked(GraphType graphType) {
		if(mModel.getGraphType().equals(graphType)) {
			//no changes here, returning.
			return;
		}
		
		mModel.setGraphType(graphType);
		mDisplayController.setAnimationEnabled(mModel.isAnimationEnabled());
		createGraphAsync(mModel.getGraphType());
	}




	@Override
	public void onMovabilityModelPicked(MovabilityModel movabilityModel) {
		if(mModel.getMovabilityModel().equals(movabilityModel)) {
			//no changes here, returning.
			return;
		}
		
		mModel.setMovabilityModel(movabilityModel);
		mDisplayController.setMovabilityModel(movabilityModel);
		createGraphAsync(mModel.getGraphType());
	}




	@Override
	public void onNodeTypePicked(int nodeType) {
		mModel.setNodeType(nodeType);
		mDisplayController.setNodeType(nodeType);
		updateView();
	}




	@Override
	public void onAssistanceEnabled() {
		mModel.setAssistanceEnabled(true);
		mDisplayController.setAssistanceEnabled(true);
		updateView();
	}




	@Override
	public void onAssistanceDisabled() {
		mModel.setAssistanceEnabled(false);
		mDisplayController.setAssistanceEnabled(false);
		updateView();
	}
	
	
	
	
	@Override
	public void onAnimationEnabled() {
		mModel.setAnimationEnabled(true);
		mDisplayController.setAnimationEnabled(mModel.isAnimationEnabled());
		updateView();
	}




	@Override
	public void onAnimationDisabled() {
		mModel.setAnimationEnabled(false);
		mDisplayController.setAnimationEnabled(mModel.isAnimationEnabled());
		updateView();
	}




	@Override
	public void onAnimationSpeedRateChanged(float speedRate) {
		mModel.setAnimationSpeedRate(speedRate);
		mDisplayController.setAnimationSpeedRate(speedRate);
	}




	@Override
	public void onLoadGraph(File file) {
		loadGraphAsync(file);
	}
	
	
	
	
	@Override
	public void onSaveGraph(File file) {
		saveGraphAsync(file);
	}
	
	
	
	
	@Override
	public void onResetGraph() {
		createGraphAsync(mModel.getGraphType());
	}




	@Override
	public void onResetAlgorithmButtonClicked() {
		mDisplayController.resetAlgorithm();
	}




	@Override
	public void onRunAlgorithmButtonClicked() {
		mDisplayController.runAlgorithm();
	}
	
	
	
	
	@Override
	public void onQuit() {
		System.exit(0);
	}
	
	
	
	
	private void createGraphAsync(GraphType graphType) {
		new AsyncTaskExecutor<GraphType, BaseGraph>(graphType, new AsyncTaskExecutor.Listener<GraphType, BaseGraph>() {

			@Override
			public void onPreExecute() {
				mProgressDialog = ProgressDialog.init(mDisplayController.getParentFrame(), "Graph Generation", "Generating...");
				mProgressDialog.show();
			}

			@Override
			public BaseGraph onDoInBackground(GraphType graphType) {
				return mModel.createGraphForType(graphType);
			}

			@Override
			public void onPostExecute(BaseGraph result) {
				mProgressDialog.dismiss();
				mDisplayController.setGraph(result);
				updateView();
			}
			
		}).execute();
	}
	
	
	
	
	private void saveGraphAsync(File file) {
		new AsyncTaskExecutor<File, Void>(file, new AsyncTaskExecutor.Listener<File, Void>() {

			@Override
			public void onPreExecute() {
				mProgressDialog = ProgressDialog.init(mDisplayController.getParentFrame(), "Graph Saving", "Saving...");
				mProgressDialog.show();
			}

			@Override
			public Void onDoInBackground(File file) {
				mDisplayController.saveGraph(file);
				return null;
			}

			@Override
			public void onPostExecute(Void result) {
				mProgressDialog.dismiss();
			}
			
		}).execute();
	}
	
	
	
	
	private void loadGraphAsync(File file) {
		new AsyncTaskExecutor<File, BaseGraph>(file, new AsyncTaskExecutor.Listener<File, BaseGraph>() {

			@Override
			public void onPreExecute() {
				mProgressDialog = ProgressDialog.init(mDisplayController.getParentFrame(), "Graph Loading", "Loading...");
				mProgressDialog.show();
			}

			@Override
			public BaseGraph onDoInBackground(File file) {
				return mModel.loadGraph(file);
			}

			@Override
			public void onPostExecute(BaseGraph result) {
				mProgressDialog.dismiss();
				mDisplayController.setGraph(result);
				mDisplayController.setMovabilityModel(mModel.getMovabilityModel());
				mDisplayController.setAnimationEnabled(mModel.isAnimationEnabled());
				updateView();
			}
			
		}).execute();
	}
	
	
	

}
