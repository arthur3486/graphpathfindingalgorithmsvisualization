package com.arthurivanets.graphalgorithmsvisualization.util;

import java.awt.Point;

public class MathUtils {

	
	
	
	public static double distance(int x1, int y1, int x2, int y2) {
		return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
	}
	
	
	
	
	public static double distance(Point pointA, Point pointB) {
		return distance(pointA.x, pointA.y, pointB.x, pointB.y);
	}
	
	
	
	
	public static double distance(int x1, int y1, int z1, int x2, int y2, int z2) {
		return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2) + Math.pow((z2 - z1), 2));
	}
	
	
	
	
	public static Point midPoint(Point pointA, Point pointB) {
		return midPoint(pointA.x, pointA.y, pointB.x, pointB.y);
	}
	
	
	
	
	public static Point midPoint(int x1, int y1, int x2, int y2) {
		return new Point(((x1 + x2) / 2), ((y1 + y2) / 2));
	}
	
	
	

}
