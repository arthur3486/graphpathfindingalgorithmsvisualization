package com.arthurivanets.graphalgorithmsvisualization.core;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Display extends JFrame {
	
	
	
	private long mUpdateInterval;
	
	private String mDisplayTitle;
	
	private Dimension mSize;
	
	private DisplayFrame mDisplayFrame;
	
	private UIThread mUIThread;
	
	private Callback mCallback;

	
	
	
	public Display() {
		this("");
	}
	
	
	
	
	public Display(String title) {
		super(title);
	}
	
	
	
	
	private void initFrame() {
		setTitle(mDisplayTitle);
		setSize(mSize);
		setMinimumSize(mSize);
		setPreferredSize(mSize);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setContentPane(createContentPane());
		setVisible(true);
	}
	
	
	
	
	private JPanel createContentPane() {
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new GridLayout(1, 1));
		
		mDisplayFrame = new DisplayFrame(mSize);
		
		contentPane.add(mDisplayFrame);
		
		return contentPane;
	}
	
	
	
	
	private void startUIThread() {
		stopUIThread();
		
		mUIThread = new UIThread();
		mUIThread.startThread();
	}
	
	
	
	
	private void stopUIThread() {
		if(mUIThread != null) {
			mUIThread.stopThread();
			mUIThread = null;
		}
	}
	
	
	
	
	private void update() {
		if(mDisplayFrame != null) {
			mDisplayFrame.repaint();
		}
	}
	
	
	
	
	public void start() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				initFrame();
				startUIThread();
			}
		});
	}
	
	
	
	
	@Override 
	public void dispose() {
		stopUIThread();
		mCallback = null;
		
		super.dispose();
	}
	
	
	
	
	public void setDisplayTitle(String displayTitle) {
		mDisplayTitle = displayTitle;
	}
	
	
	
	
	public void setSize(Dimension size) {
		mSize = size;
	}
	
	
	
	
	public void setSize(int width, int height) {
		mSize = new Dimension(width, height);
	}
	
	
	
	
	public void setFPS(int fps) {
		mUpdateInterval = (1000 / fps);
	}
	
	
	
	
	public void setCallback(Callback callback) {
		mCallback = callback;
	}
	
	
	
	
	public interface Callback {
		
		public void onDraw(Graphics2D g);
		
	}
	
	
	
	
	private class UIThread extends Thread {
		
		private volatile boolean isRunning;
		
		
		public UIThread() {
			isRunning = false;
		}
		
		
		
		@Override
		public void run() {
			while(isRunning) {
				update();
				
				try {
					Thread.sleep(mUpdateInterval);
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		public synchronized void startThread() {
			isRunning = true;
			super.start();
		}
		
		
		public synchronized void stopThread() {
			isRunning = false;
		}
		
		
		public boolean isRunning() {
			return this.isRunning;
		}
		
	}
	
	
	
	
	private class DisplayFrame extends Component {
		
		private BufferedImage bufferedImage;
		
		
		public DisplayFrame(Dimension size) {
			//using the so called double buffer here
			bufferedImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
		}

		
		@Override
		public void paint(Graphics g) {
			if(mCallback != null) {
				mCallback.onDraw((Graphics2D) bufferedImage.getGraphics()); 
			}
			
			g.drawImage(bufferedImage, 0, 0, null);
		}
		
		
	}
	
	
	

}
