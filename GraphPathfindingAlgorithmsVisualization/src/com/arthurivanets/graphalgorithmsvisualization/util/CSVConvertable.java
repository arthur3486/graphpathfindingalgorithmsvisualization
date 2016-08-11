package com.arthurivanets.graphalgorithmsvisualization.util;

public interface CSVConvertable<T> {
	
	public T fromCSV(String csvData);
	
	public String toCSV();

}
