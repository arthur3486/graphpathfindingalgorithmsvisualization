package com.arthurivanets.graphalgorithmsvisualization.util;

import java.awt.Graphics2D;
import java.util.ArrayList;

import com.arthurivanets.graphalgorithmsvisualization.graph.model.Vertex;

public class VertexAnimator implements Drawable {
	
	
	private int mFrameCount;
	private int mItemCount;
	
	private long mTime;
	
	private float mSpeedRate;
	
	private ArrayList<Vertex> mDrawables;
	
	
	

	public VertexAnimator() {
		mFrameCount = 0;
		mSpeedRate = 1f;
		mDrawables = new ArrayList<Vertex>();
	}
	
	
	
	
	public void addVertex(Vertex vertex) {
		mDrawables.add(vertex);
	}
	
	
	
	
	public void removeVertex(Vertex vertex) {
		mDrawables.remove(vertex);
	}
	
	
	
	
	public void clear() {
		mDrawables.clear();
	}
	
	
	
	
	public void setSpeedRate(float speedRate) {
		mSpeedRate = speedRate;
	}
	
	
	
	
	public float getSpeedRate() {
		return mSpeedRate;
	}




	@Override
	public void draw(Graphics2D g) {
		if(mDrawables.size() == 0) {
			return;
		}
		
		mItemCount = (mFrameCount % mDrawables.size());
		
		for(int i = 0; i < mItemCount; i++) {
			mDrawables.get(i).draw(g);
		}
		
		if(mSpeedRate < 1f) {
			if((System.currentTimeMillis() - mTime) < ((1f - mSpeedRate) * 4 * 16)) {
				return;
			} else {
				mTime = System.currentTimeMillis();
			}
		}
		
		mFrameCount++;
	}
	
	
	

}
