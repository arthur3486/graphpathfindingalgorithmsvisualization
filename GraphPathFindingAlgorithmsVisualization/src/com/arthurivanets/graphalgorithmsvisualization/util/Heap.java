package com.arthurivanets.graphalgorithmsvisualization.util;

import java.util.ArrayList;

public interface Heap<T extends Comparable<T>> {
	
	/**
	 * <br>
	 * 		Creates a heap out of given List of elements.
	 * <br>
	 * 
	 * @param items to be used for the creation of the heap.
	 * 
	 */
	public void heapify(ArrayList<T> items);
	
	/**
	 * 
	 * <br>
	 * 		Used to add the given items to the current heap.(merge items)
	 * <br>
	 * 
	 * @param items to be merged.
	 * 
	 */
	public void merge(ArrayList<T> items);

	/**
	 * <br>
	 * 		Finds the maximum item of a max-heap(or a minimum item of a min-heap)
	 * <br>
	 * 
	 * @return the found maximum item(or a minimum item)
	 */
	public T peek();
	
	/**
	 * 
	 * <br>
	 * 		Returns the node of minimum value from a min-heap(or maximum value from a max-heap)
	 * 		after removing it from the heap.
	 * <br>
	 * 
	 * @return the node of minimum value or maximum value depending of the heap type.
	 * 
	 */
	public T pop();
	
	/**
	 * 
	 * <br>
	 * 		Adds a new item to the heap.
	 * <br>
	 * 
	 * @param item to be added to the heap.
	 * 
	 */
	public void push(T item);
	
	/**
	 * 
	 * <br>
	 * 		Indicates whether the heap is empty or not.
	 * <br>
	 * 
	 * @return true if the count of heap items is 0, false otherwise.
	 */
	public boolean isEmpty();
	
	/**
	 * 
	 * <br>
	 * 		Returns the actual item count of the heap.
	 * <br>
	 * 
	 * @return heap item count.
	 * 
	 */
	public int size();
	
	/**
	 * 
	 * <br>
	 * 		Removes all the items from the heap.
	 * <br>
	 * 
	 */
	public void clear();

}
