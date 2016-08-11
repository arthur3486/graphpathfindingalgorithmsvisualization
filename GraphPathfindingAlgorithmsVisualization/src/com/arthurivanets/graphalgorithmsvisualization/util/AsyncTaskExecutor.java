package com.arthurivanets.graphalgorithmsvisualization.util;

public class AsyncTaskExecutor<E, R> {
	
	
	private static final Object MUTEX = new Object();
	
	
	private E mExtras; 
	
	private boolean mIsUsed;
	
	private Listener<E, R> mListener;
	
	
	

	public AsyncTaskExecutor(E extras, Listener<E, R> listener) {
		mExtras = extras;
		mListener = listener;
		mIsUsed = false;
	}
	
	
	
	
	public void execute() {
		if(mIsUsed) {
			return;
		}
		
		mListener.onPreExecute();
		new Thread(mRunnable).start();
		mIsUsed = true;
	}
	
	
	
	
	
	private Runnable mRunnable = new Runnable() {

		@Override
		public void run() {
			R result = mListener.onDoInBackground(mExtras);
			
			synchronized(MUTEX) {
				mListener.onPostExecute(result);
			}
		}
		
	};

	
	

	public interface Listener<E, R> {
		
		public void onPreExecute();
		
		public R onDoInBackground(E extras);
		
		public void onPostExecute(R result);
		
	}
	
	
	

}
