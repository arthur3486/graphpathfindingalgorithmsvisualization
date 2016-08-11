package com.arthurivanets.graphalgorithmsvisualization.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Frame;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class ProgressDialog {
	
	
	private String mTitle;
	private String mMessage;
	
	private Frame mParent;
	
	private JDialog mDialog;
	
	private JLabel mMessageLabel;
	
	private boolean mIsInitialized;
	private volatile boolean mIsShowing;
	
	
	
	
	public static ProgressDialog init(Frame parent, String title, String message) {
		return new ProgressDialog(parent, title, message);
	}
	
	
	

	private ProgressDialog(Frame parent, String title, String message) {
		mParent = parent;
		mTitle = title;
		mMessage = message;
		mIsInitialized = false;
		mIsShowing = false;
		initDialog();
	}
	
	
	
	
	private void initDialog() {
		mDialog = new JDialog(mParent, mTitle, true);
		mDialog.setSize(300, 100);
		mDialog.setResizable(false);
		mDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		mDialog.setLocationRelativeTo(mParent);
		mDialog.setLayout(new BorderLayout());
		
		mMessageLabel = new JLabel(mMessage, new ImageIcon(getClass().getResource("/loader.gif")), JLabel.CENTER);
		mMessageLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
		
		mDialog.add(mMessageLabel, BorderLayout.CENTER);
		
		mIsInitialized = true;
	}
	
	
	
	
	public void setMessage(String message) {
		mMessage = message;
		
		if(mIsInitialized) {
			mMessageLabel.setText(message);
		}
	}
	
	
	
	
	private Runnable mRunnable = new Runnable() {

		@Override
		public void run() {
			mDialog.setVisible(true);
		}
		
	};
	
	
	
	
	public void show() {
		if(mIsInitialized && !mIsShowing) {
			mIsShowing = true;
			new Thread(mRunnable).start();
		}
	}
	
	
	
	
	public void dismiss() {
		if(mIsInitialized) {
			mIsShowing = false;
			mDialog.setVisible(false);
			mDialog.dispose();
			mIsInitialized = false;
		}
	}
	
	
	

}
