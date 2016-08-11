package com.arthurivanets.graphalgorithmsvisualization.util;

import java.util.ArrayList;

/**
 * 
 * <br>
 * 		Binary Min-Heap Implementation.
 * <br>
 * 
 * @author arthur
 *
 * @param <T>
 */
public class BinaryHeap<T extends Comparable<T>> implements Heap<T> {

	
	private ArrayList<T> mItems;
	
	
	
	
	public BinaryHeap() {
		this(new ArrayList<T>());
	}
	
	
	
	
	public BinaryHeap(ArrayList<T> items) {
		mItems = items;
		heapify(mItems);
	}

	
	
	
	@Override
	public void heapify(ArrayList<T> items) {
		for(T item : items) {
			push(item);
		}
	}

	
	
	
	@Override
	public void merge(ArrayList<T> items) {
		for(T item : items) {
			push(item);
		}
	}
	
	
	
	
	private void bubbleUp(int n) {
		if(n < 0 || n >= mItems.size()) {
			return;
		}
		
		int parentElementIndex = getParentElementIndex(n);
		
		while(n > 0 && mItems.get(parentElementIndex).compareTo(mItems.get(n)) > 0) {
			swapItems(parentElementIndex, n);
			n = parentElementIndex;
			parentElementIndex = getParentElementIndex(n);
		}
	}
	
	
	
	
	private void bubbleDown(int n) {
		if(n < 0 || n >= mItems.size()) {
			return;
		}
		
		int minElementIndex = getMinElementIndex(n);
		
		while(minElementIndex != -1 && mItems.get(minElementIndex).compareTo(mItems.get(n)) < 0) {
			swapItems(minElementIndex, n);
			n = minElementIndex;
			minElementIndex = getMinElementIndex(n);
		}
	}
	
	
	
	
	private void swapItems(int oldPosition, int newPosition) {
		if(oldPosition < 0 || oldPosition >= mItems.size() || newPosition < 0 || newPosition >= mItems.size()) {
			return;
		}
		
		T item = mItems.get(oldPosition);
		mItems.set(oldPosition, mItems.get(newPosition));
		mItems.set(newPosition, item);
	}

	
	
	
	@Override
	public T peek() {
		return (isEmpty() ? null : mItems.get(0));
	}

	
	
	
	@Override
	public T pop() {
		if(isEmpty()) {
			return null;
		}
		
		T item = mItems.get(0);
		mItems.set(0, mItems.get(mItems.size() - 1));
		mItems.remove(mItems.size() - 1);
		bubbleDown(0);
		
		return item;
	}

	
	
	
	@Override
	public void push(T item) {
		mItems.add(item);
		bubbleUp(mItems.size() - 1);
	}
	
	
	
	
	/**
	 * 
	 * <br>
	 * 		Rearranges the given item within the Heap(based on its priority), so that
	 *		the order of the items is kept in the right way.
	 * <br>
	 * 
	 * @param item
	 * 
	 */
	public void updatePrioriry(T item) {
		int itemPosition = mItems.indexOf(item);
		
		if(itemPosition == -1) {
			return;
		}
		
		bubbleUp(itemPosition);
	}
	
	
	

	@Override
	public boolean isEmpty() {
		return ((mItems == null) || (mItems.size() == 0));
	}
	
	
	

	@Override
	public int size() {
		return ((mItems != null) ? mItems.size() : 0);
	}




	@Override
	public void clear() {
		if(!isEmpty()) {
			mItems.clear();
		}
	}
	
	
	
	
	/**
	 * 
	 * <br>
	 * 		Calculates the index of the parent element of the given node.
	 * <br>
	 * 
	 * @param n - the node index.
	 * 
	 * @return the calculated index
	 * 
	 */
	private int getParentElementIndex(int n) {
		return ((n - 1) / 2);
	}
	
	
	
	
	/**
	 * 
	 * <br>
	 * 		Calculates the index of the left element of the given node.
	 * <br>
	 * 
	 * @param n - the node index.
	 * 
	 * @return the calculated index
	 * 
	 */
	private int getLeftElementIndex(int n) {
		return (2 * n + 1);
	}
	
	
	
	
	/**
	 * 
	 * <br>
	 * 		Calculates the index of the right element of the given node.
	 * <br>
	 * 
	 * @param n - the node index.
	 * 
	 * @return the calculated index
	 * 
	 */
	private int getRightElementIndex(int n) {
		return (2 * n + 2);
	}
	
	
	
	
	/**
	 * 
	 * <br>
	 * 		Retrieves the index of the minimum element of the given node.
	 * 		(Also, takes into consideration the existence of the elements)
	 * 		(Also, it's worth noting that it traverses the elements in the correct way of element traversal
	 * 		 for binary trees: from left to right) 
	 * <br>
	 * 
	 * @param n - node index
	 * @return -1 if the element was not found, or an actual index of the found element.
	 * 
	 */
	private int getMinElementIndex(int n) {
		int leftElementIndex = getLeftElementIndex(n);
		int rightElementIndex = getRightElementIndex(n);
		int lastElementIndex = (mItems.size() - 1);
		
		if(leftElementIndex < 0 || leftElementIndex > lastElementIndex) {
			return -1;
		}
		
		if(rightElementIndex < 0 || rightElementIndex > lastElementIndex) {
			return leftElementIndex;
		}
		
		return (mItems.get(leftElementIndex).compareTo(mItems.get(rightElementIndex)) <= 0 ? leftElementIndex : rightElementIndex);
	}
	
	
	

}
