package com.arthurivanets.graphalgorithmsvisualization.view;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.arthurivanets.graphalgorithmsvisualization.core.Display;

public class DisplayView implements Display.Callback {
	
	
	private String mTitle;
	
	private Dimension mSize;
	
	private int mFPS;
	
	private Display mDisplay;
	
	private Callback mCallback;
	
	
	
	
	public static DisplayView init(String title, Dimension size, int fps) {
		DisplayView displayView = new DisplayView(title, size, fps);
		displayView.init();
		
		return displayView;
	}
	
	
	

	public DisplayView(String title, Dimension size, int fps) {
		mTitle = title;
		mSize = size;
		mFPS = fps;
	}
	
	
	
	
	private void init() {
		mDisplay = new Display();
		mDisplay.setDisplayTitle(mTitle);
		mDisplay.setSize(mSize);
		mDisplay.setFPS(mFPS);
		mDisplay.setCallback(this);
		mDisplay.addMouseMotionListener(mMouseMotionListener);
		mDisplay.addMouseListener(mMouseListener);
		mDisplay.addKeyListener(mKeyListener);
		mDisplay.start();
	}
	
	
	
	
	public void setCallback(Callback callback) {
		mCallback = callback;
	}
	
	
	
	
	public Display getDisplay() {
		return mDisplay;
	}
	
	
	

	@Override
	public void onDraw(Graphics2D g) {
		if(mCallback != null) {
			mCallback.onDraw(g);
		}
	}
	
	
	
	
	private MouseListener mMouseListener = new MouseListener() {

		@Override
		public void mouseClicked(MouseEvent e) {
			if(mCallback != null) {
				mCallback.onMouseClicked(e);
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if(mCallback != null) {
				mCallback.onMousePressed(e);
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if(mCallback != null) {
				mCallback.onMouseReleased(e);
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			if(mCallback != null) {
				mCallback.onMouseOnscreen(e);
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if(mCallback != null) {
				mCallback.onMouseOffscreen(e);
			}
		}
		
	};
	
	
	
	
	private MouseMotionListener mMouseMotionListener = new MouseMotionListener() {
		
		@Override
		public void mouseMoved(MouseEvent e) {
			if(mCallback != null) {
				mCallback.onMouseMoved(e);
			}
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			if(mCallback != null) {
				mCallback.onMouseDragged(e);
			}
		}
		
	};
	
	
	
	
	private KeyListener mKeyListener = new KeyListener() {

		@Override
		public void keyTyped(KeyEvent e) {
			//do nothing.
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if(mCallback != null) {
				mCallback.onKeyPressed(e);
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if(mCallback != null) {
				mCallback.onKeyReleased(e);
			}
		}
		
	};
	
	
	
	
	public interface Callback {
		
		public void onDraw(Graphics2D graphics);
		
		public void onMousePressed(MouseEvent event);
		
		public void onMouseReleased(MouseEvent event);
		
		public void onMouseClicked(MouseEvent event);
		
		public void onMouseMoved(MouseEvent event);
		
		public void onMouseDragged(MouseEvent event);
		
		public void onMouseOnscreen(MouseEvent event);
		
		public void onMouseOffscreen(MouseEvent event);
		
		public void onKeyPressed(KeyEvent event);
		
		public void onKeyReleased(KeyEvent event);
		
	}
	
	
	

}
