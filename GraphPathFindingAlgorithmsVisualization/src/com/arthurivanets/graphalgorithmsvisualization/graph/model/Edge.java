package com.arthurivanets.graphalgorithmsvisualization.graph.model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;

import com.arthurivanets.graphalgorithmsvisualization.core.Constants;
import com.arthurivanets.graphalgorithmsvisualization.util.CSVConvertable;
import com.arthurivanets.graphalgorithmsvisualization.util.Drawable;
import com.arthurivanets.graphalgorithmsvisualization.util.MathUtils;
import com.arthurivanets.graphalgorithmsvisualization.util.Taggable;
import com.arthurivanets.graphalgorithmsvisualization.util.Unique;

public class Edge implements Drawable, Unique<String>, Taggable<Edge>, CSVConvertable<Edge> {
	
	
	public static final int FLAG_NONE = 0;
	public static final int FLAG_DIRECTION_IMPORTANT = 1;
	public static final int FLAG_DIRECTION_NOT_IMPORTANT = 2;
	
	public static final Color DEFAULT_COLOR = Color.GREEN;
	public static final Color DEFAULT_SELECTED_COLOR = Color.RED;
	public static final Color DEFAULT_TEXT_COLOR = Color.WHITE;
	
	public static final int DEFAULT_TEXT_SIZE = 14;
	
	
	private int textSize;
	private int weight;
	
	private Vertex startVertex;
	private Vertex endVertex;
	
	private Color color;
	private Color selectedColor;
	private Color textColor;
	
	private Object tag;
	
	private boolean isSelected;
	private boolean isWeightVisible;
	private boolean shouldBeFilled;
	
	
	
	
	public Edge() {
		this(null, null);
	}
	
	
	
	
	public Edge(Vertex startVertex, Vertex endVertex) {
		this.startVertex = startVertex;
		this.endVertex = endVertex;
		this.weight = -1;
		this.textSize = DEFAULT_TEXT_SIZE;
		this.color = DEFAULT_COLOR;
		this.selectedColor = DEFAULT_SELECTED_COLOR;
		this.textColor = DEFAULT_TEXT_COLOR;
		this.isSelected = false;
		this.isWeightVisible = true;
		this.shouldBeFilled = false;
	}
	
	
	
	
	public Edge setStartVertex(Vertex startVertex) {
		this.startVertex = startVertex;
		return this;
	}
	
	
	
	
	public Vertex getStartVertex() {
		return this.startVertex;
	}
	
	
	
	
	public Edge setEndVertex(Vertex endVertex) {
		this.endVertex = endVertex;
		return this;
	}
	
	
	
	
	public Vertex getEndVertex() {
		return this.endVertex;
	}
	
	
	
	
	public Edge setWeight(int weight) {
		this.weight = weight;
		return this;
	}
	
	
	
	
	public int getWeight() {
		return ((this.weight < 0) ? ((int) getLength()) : (this.weight + getWeightCompensation()));
	}
	
	
	
	
	public int getWeightCompensation() {
		if(startVertex.getType() == Vertex.Type.OBSTACLE && endVertex.getType() == Vertex.Type.OBSTACLE) {
			return Constants.OBSTACLE_WEIGHT_COMPENSATION;
		} else {
			return 0;
		}
	}
	
	
	
	
	public Edge setTextSize(int textSize) {
		this.textSize = textSize;
		return this;
	}
	
	
	
	
	public int getTextSize() {
		return this.textSize;
	}
	
	
	
	
	public Edge setColor(int color) {
		return setColor(new Color(color));
	}
	
	
	
	
	public Edge setColor(Color color) {
		this.color = color;
		return this;
	}
	
	
	
	
	public Color getColor() {
		return this.color;
	}
	
	
	
	
	public Edge setSelectedColor(int color) {
		return setSelectedColor(new Color(color));
	}
	
	
	
	
	public Edge setSelectedColor(Color color) {
		this.selectedColor = color;
		return this;
	}
	
	
	
	
	public Color getSelectedColor() {
		return this.selectedColor;
	}
	
	
	
	
	public Edge setTextColor(int color) {
		return setTextColor(new Color(color));
	}
	
	
	
	public Edge setTextColor(Color color) {
		this.textColor = color;
		return this;
	}
	
	
	
	
	public Color getTextColor() {
		return this.textColor;
	}
	
	
	
	
	public Edge setSelected(boolean isSelected) {
		this.isSelected = isSelected;
		return this;
	}
	
	
	
	
	public boolean isSelected() {
		return this.isSelected;
	}
	
	
	
	
	public Edge setWeightVisible(boolean isWeightVisible) {
		this.isWeightVisible = isWeightVisible;
		return this;
	}
	
	
	
	
	public boolean isWeightVisible() {
		return this.isWeightVisible;
	}
	
	
	
	
	public Edge setFill(boolean shouldBeFilled) {
		this.shouldBeFilled = shouldBeFilled;
		return this;
	}
	
	
	
	
	public boolean shouldBeFilled() {
		return this.shouldBeFilled;
	}
	
	
	
	
	/**
	 * 
	 * <br>
	 * 		Determines whether the current Edge has the given Vertices as a base,
	 * 		in either direction(doesn't consider a particular direction).
	 * <br>
	 * 
	 * @param vertex1
	 * @param vertex2
	 * 
	 * @return
	 * 
	 */
	public boolean equalsVertexwise(Vertex vertex1, Vertex vertex2) {
		return equalsVertexwise(vertex1, vertex2, FLAG_DIRECTION_NOT_IMPORTANT);
	}
	
	
	
	
	/**
	 * 
	 * <br>
	 * 		Determines whether the current Edge has the given Vertices as a base,
	 * 		in a direction(-s) specified via the flag.
	 * <br>
	 * 
	 * @param vertex1
	 * @param vertex2
	 * 
	 * @return
	 * 
	 */
	public boolean equalsVertexwise(Vertex vertex1, Vertex vertex2, int flag) {
		return equalsVertexwise(new Edge(vertex1, vertex2), flag); 
	}
	
	
	
	
	/**
	 * 
	 * <br>
	 * 		Determines whether the current Edge has the given Edge's Vertices as a base,
	 * 		in either direction(doesn't consider a particular direction).
	 * <br>
	 * 
	 * @param vertex1
	 * @param vertex2
	 * 
	 * @return
	 * 
	 */
	public boolean equalsVertexwise(Edge edge) {
		return equalsVertexwise(edge, FLAG_DIRECTION_NOT_IMPORTANT);
	}
	
	
	
	
	/**
	 * 
	 * <br>
	 * 		Determines whether the current Edge has the given Edge's Vertices as a base,
	 * 		in a direction(-s) specified via the flag.
	 * <br>
	 * 
	 * @param vertex1
	 * @param vertex2
	 * 
	 * @return
	 * 
	 */
	public boolean equalsVertexwise(Edge edge, int flag) {
		if(edge == null || edge.getStartVertex() == null || edge.getEndVertex() == null) {
			return false;
		}
		
		if(flag == FLAG_DIRECTION_IMPORTANT) {
			return (startVertex.equalsKeywise(edge.getStartVertex()) && endVertex.equalsKeywise(edge.getEndVertex()));
		} else {
			return ((startVertex.equalsKeywise(edge.getStartVertex()) && endVertex.equalsKeywise(edge.getEndVertex()))
					|| (startVertex.equalsKeywise(edge.getEndVertex()) && endVertex.equalsKeywise(edge.getStartVertex())));
		}
	}
	
	
	
	
	@Override
	public Edge setTag(Object tag) {
		this.tag = tag;
		return this;
	}




	@Override
	public Object getTag() {
		return this.tag;
	}
	
	
	
	
	@Override
	public String getUniqueKey() {
		return composeKey(createKeyPart(startVertex, endVertex), createKeyPart(endVertex, startVertex));
	}
	
	
	
	
	@Override
	public Edge fromCSV(String csvData) {
		String[] csvDataChunks = csvData.split(";");
		
		//edge part
		String[] dataChunk = csvDataChunks[0].split(",");
		setTextSize(Integer.parseInt(dataChunk[0]));
		setWeight(Integer.parseInt(dataChunk[1]));
		setColor(Integer.parseInt(dataChunk[2]));
		setSelectedColor(Integer.parseInt(dataChunk[3]));
		setTextColor(Integer.parseInt(dataChunk[4]));
		setSelected(Boolean.parseBoolean(dataChunk[5]));
		setWeightVisible(Boolean.parseBoolean(dataChunk[6]));
		setFill(Boolean.parseBoolean(dataChunk[7]));
		
		//vertices
		setStartVertex(new Vertex().fromCSV(csvDataChunks[1]));
		setEndVertex(new Vertex().fromCSV(csvDataChunks[2]));
		
		return this;
	}




	@Override
	public String toCSV() {
		StringBuilder edgeCSVBuilder = new StringBuilder();
		edgeCSVBuilder.append(this.textSize).append(",");
		edgeCSVBuilder.append(this.weight).append(",");
		edgeCSVBuilder.append(this.color.getRGB()).append(",");
		edgeCSVBuilder.append(this.selectedColor.getRGB()).append(",");
		edgeCSVBuilder.append(this.textColor.getRGB()).append(",");
		edgeCSVBuilder.append(this.isSelected).append(",");
		edgeCSVBuilder.append(this.isWeightVisible).append(",");
		edgeCSVBuilder.append(this.shouldBeFilled);
		
		return String.format("%s;%s;%s", edgeCSVBuilder.toString(), this.startVertex.toCSV(), this.endVertex.toCSV());
	}
	
	
	
	
	public static String composeKey(String firstPart, String secondPart) {
		return (firstPart + "|" + secondPart);
	}
	
	
	
	
	public static String createKeyPart(Vertex startVertex, Vertex endVertex) {
		return ("[{" + startVertex.getUniqueKey() + "},{" + endVertex.getUniqueKey() + "}]");
	}
	
	
	
	
	public Point getMidPoint() {
		Point point = new Point(
			((startVertex.getX() + endVertex.getX()) / 2),
			((startVertex.getY() + endVertex.getY()) / 2)
		);
		
		return point;
	}
	
	
	
	
	public double getLength() {
		return MathUtils.distance(startVertex.getX(), startVertex.getY(), endVertex.getX(), endVertex.getY());
	}
	
	
	
	
	/**
	 * 
	 * <br>
	 * 		Finds the intersection points for the given Vertex with the current Edge.
	 * 		(If they indeed intersect with each other)
	 * <br>
	 * 
	 * @param vertex
	 * 
	 * @return an intersection Point(if the objects intersect), null(if they don't intersect with each other)
	 * 
	 */
	public Point getIntersectionPoint(Vertex vertex) {
		float dX;
		float dY;
		float a; 
		float b;
		float c;
		float determinant;
		float t;
		
		Point firstIntersectionPoint;
		Point secondIntersectionPoint;

	    dX = (endVertex.getX() - startVertex.getX());
	    dY = (endVertex.getY() - startVertex.getY());

	    a = (dX * dX + dY * dY);
	    b = (2 * (dX * (startVertex.getX() - vertex.getX()) + dY * (startVertex.getY() - vertex.getY())));
	    c = ((startVertex.getX() - vertex.getX()) * (startVertex.getX() - vertex.getX()) + (startVertex.getY() - vertex.getY()) * (startVertex.getY() - vertex.getY()) - vertex.getSize() * vertex.getSize());

	    determinant = (b * b - 4 * a * c);
	    
	    if((a <= 0.0000001) || (determinant < 0)) {
	        // No real solutions.
	        return null;
	    } else if (determinant == 0) {
	        // One solution.
	        t = (-b / (2 * a));
	        
	        firstIntersectionPoint = new Point(
	        	(int) (startVertex.getX() + t * dX),
	        	(int) (startVertex.getY() + t * dY)
	        );
	        
	        return firstIntersectionPoint;
	    } else {
	        // Two solutions.
	        t = (float) ((-b + Math.sqrt(determinant)) / (2 * a));
	        
	        firstIntersectionPoint = new Point(
	        	(int) (startVertex.getX() + t * dX),
	        	(int) (startVertex.getY() + t * dY)
	        );
	        
	        t = (float) ((-b - Math.sqrt(determinant)) / (2 * a));
	        
	        secondIntersectionPoint = new Point(
	        	(int) (startVertex.getX() + t * dX),
	        	(int) (startVertex.getY() + t * dY)
	        );
	        
	        return MathUtils.midPoint(firstIntersectionPoint, secondIntersectionPoint);
	    }
	}
	
	
	
	
	/**
	 * 
	 * <br>
	 * 		Used to determine whether the current Edge can be considered as a "mostly" horizontal line.
	 * 		(The condition is satisfied if the angle created by the line(Edge) with the Ox axis
	 * 		 is contained within the following angle range 
	 * 		<br><strong>[-(PI / 4); +(PI / 4)] & [+(7 * PI / 4); +(9 * PI / 4)]</strong>)
	 * <br>
	 * 
	 * @return
	 * 
	 */
	public boolean isMostlyHorizontal() {
		int deltaX = Math.abs(endVertex.getX() - startVertex.getX());
		int deltaY = Math.abs(endVertex.getY() - startVertex.getY());
		
		return (deltaY < (deltaX / 2));
	}
	
	
	
	
	public boolean isHorizontal() {
		return (startVertex.getY() == endVertex.getY());
	}
	
	
	
	
	public boolean isMostlyVertical() {
		int deltaX = Math.abs(endVertex.getX() - startVertex.getX());
		int deltaY = Math.abs(endVertex.getY() - startVertex.getY());
		
		return (deltaX < (deltaY / 2));
	}
	
	
	
	
	public boolean isVertical() {
		return (startVertex.getX() == endVertex.getX());
	}
	
	
	
	
	public boolean isDiagonal() {
		return (Math.abs(endVertex.getX() - startVertex.getX()) == Math.abs(endVertex.getY() - startVertex.getY()));
	}
	
	
	
	
	public boolean isMostlyDiagonal() {
		int deltaX = Math.abs(endVertex.getX() - startVertex.getX());
		int deltaY = Math.abs(endVertex.getY() - startVertex.getY());
		
		return (((deltaY >= (deltaX / 2)) && (deltaY <= deltaX)) || ((deltaX >= (deltaY / 2)) && (deltaX <= deltaY)));
	}
	
	
	
	
	public boolean isContainedByTheCurrentCoordinateRange(Vertex vertex) {
		if(isMostlyHorizontal()) {
			return (vertex.getX() >= Math.min(startVertex.getX(), endVertex.getX()) 
					&& vertex.getX() <= Math.max(startVertex.getX(), endVertex.getX()));
		} else {
			return (vertex.getY() >= Math.min(startVertex.getY(), endVertex.getY()) 
					&& vertex.getY() <= Math.max(startVertex.getY(), endVertex.getY()));
		}
	}
	
	
	

	@Override
	public void draw(Graphics2D g) {
		//drawing the edge
		g.setColor(isSelected ? selectedColor : color);
		g.drawLine(
			startVertex.getX(),
			startVertex.getY(),
			endVertex.getX(),
			endVertex.getY()
		);
		
		//drawing the vertices
		startVertex.setColor(isSelected ? selectedColor : color);
		startVertex.setTextColor(textColor);
		startVertex.setTextSize(textSize);
		startVertex.setFill(shouldBeFilled);
		startVertex.draw(g);
		
		endVertex.setColor(isSelected ? selectedColor : color);
		endVertex.setTextColor(textColor);
		endVertex.setTextSize(textSize);
		endVertex.setFill(shouldBeFilled);
		endVertex.draw(g);
		
		//drawing the text which represents the approximate weight of the edge
		//(will be drawn if the visibility of the weight is set to true)
		if(!isWeightVisible) {
			return;
		}
		
		Point midPoint = getMidPoint();
		
		g.setColor(textColor);
		g.setFont(new Font(Font.SERIF, Font.BOLD, textSize));
		g.drawString(
			("~" + Math.round(getLength())),
			midPoint.x,
			midPoint.y
		);
	}
	
	
	

}
