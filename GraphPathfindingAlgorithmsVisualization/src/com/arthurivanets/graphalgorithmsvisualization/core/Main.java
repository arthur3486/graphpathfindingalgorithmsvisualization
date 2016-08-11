package com.arthurivanets.graphalgorithmsvisualization.core;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.arthurivanets.graphalgorithmsvisualization.controller.ControlPanelController;
import com.arthurivanets.graphalgorithmsvisualization.controller.DisplayController;
import com.arthurivanets.graphalgorithmsvisualization.model.ControlPanelModel;
import com.arthurivanets.graphalgorithmsvisualization.model.DisplayModel;
import com.arthurivanets.graphalgorithmsvisualization.view.ControlPanelView;
import com.arthurivanets.graphalgorithmsvisualization.view.DisplayView;

public class Main {
	
	
	private DisplayController mDisplayController;
	private ControlPanelController mControlPanelController;
	
	
	
	
	public static void main(String[] args) {
		new Main();
	}
	
	
	

	public Main() {
		changeLookAndFeel();
		initUi();
	}
	
	
	
	
	private void changeLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	private void initUi() {
		DisplayView displayView = DisplayView.init(
			Constants.DISPLAY_TITLE,
			Constants.DISPLAY_SIZE,
			Constants.DISPLAY_FPS
		);
		
		mDisplayController = new DisplayController(
			new DisplayModel(),
			displayView
		);
		mDisplayController.updateView();
		
		mControlPanelController = new ControlPanelController(
			new ControlPanelModel(),
			ControlPanelView.init(Constants.CONTROL_PANEL_TITLE, Constants.CONTOL_PANEL_SIZE, displayView.getDisplay()),
			mDisplayController
		);
		mControlPanelController.updateView();
	}
	
	
	

}
