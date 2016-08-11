package com.arthurivanets.graphalgorithmsvisualization.graph.model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;

import com.arthurivanets.graphalgorithmsvisualization.core.Constants;
import com.arthurivanets.graphalgorithmsvisualization.util.CSVConvertable;
import com.arthurivanets.graphalgorithmsvisualization.util.Copyable;
import com.arthurivanets.graphalgorithmsvisualization.util.Drawable;
import com.arthurivanets.graphalgorithmsvisualization.util.MathUtils;
import com.arthurivanets.graphalgorithmsvisualization.util.Taggable;
import com.arthurivanets.graphalgorithmsvisualization.util.TextUtils;
import com.arthurivanets.graphalgorithmsvisualization.util.Unique;

public class Vertex implements Copyable<Vertex>, Drawable, Unique<String>,
		Taggable<Vertex>, Comparable<Vertex>, CSVConvertable<Vertex> {
	
	
	public static final Color DEFAULT_COLOR = Color.GREEN;
	public static final Color DEFAULT_SELECTED_COLOR = Color.RED;
	public static final Color DEFAULT_OBJECT_COLOR = Color.YELLOW;
	public static final Color DEFAULT_OBSTACLE_COLOR = Color.GRAY;
	public static final Color DEFAULT_PATH_COLOR = Color.WHITE;
	public static final Color DEFAULT_TEXT_COLOR = Color.WHITE;
	
	
	private int x;
	private int y;
	private int size;
	private int textSize;
	private int type;
	private int shapeType;
	
	private double gCost;
	private double hCost;
	
	private String mark;
	
	private Color color;
	private Color objectColor;
	private Color obstacleColor;
	private Color pathColor;
	private Color textColor;
	
	private MovabilityModel movabilityModel;
	
	private Vertex previousVertex;
	
	private Object tag;
	
	private boolean shouldBeFilled;
	private boolean isColorInheritable;
	
	
	
	
	public interface Type {
		
		public static final int PATH = 1;
		public static final int OBSTACLE = 2;
		public static final int OBJECT = 3;
		
	}
	
	
	
	
	public interface ShapeType {
		
		public static final int CIRLCE = 1;
		public static final int SQUARE = 2;
		
	}
	
	
	

	public Vertex() {
		this(0, 0);
	}
	
	
	
	
	public Vertex(int x, int y) {
		this.x = x;
		this.y = y;
		this.mark = "";
		this.type = Type.PATH;
		this.shapeType = ShapeType.CIRLCE;
		this.movabilityModel = Constants.DEFAULT_MOVABILITY_MODEL;
		this.color = DEFAULT_COLOR;
		this.objectColor = DEFAULT_OBJECT_COLOR;
		this.obstacleColor = DEFAULT_OBSTACLE_COLOR;
		this.pathColor = DEFAULT_PATH_COLOR;
		this.textColor = DEFAULT_TEXT_COLOR;
		this.shouldBeFilled = false;
		this.isColorInheritable = true;
	}
	
	
	
	
	public Vertex setX(int x) {
		this.x = x;
		return this;
	}
	
	
	
	
	public int getX() {
		return this.x;
	}
	
	
	
	
	public Vertex setY(int y) {
		this.y = y;
		return this;
	}
	
	
	
	
	public int getY() {
		return this.y;
	}
	
	
	
	
	public Vertex setSize(int size) {
		this.size = size;
		return this;
	}
	
	
	
	
	public int getSize() {
		return this.size;
	}
	
	
	
	
	public Vertex setTextSize(int textSize) {
		this.textSize = textSize;
		return this;
	}
	
	
	
	
	public int getTextSize() {
		return this.textSize;
	}
	
	
	
	
	public Vertex setType(int type) {
		this.type = type;
		return this;
	}
	
	
	
	
	public int getType() {
		return this.type;
	}
	
	
	
	
	public Vertex setMovabilityModel(MovabilityModel movabilityModel) {
		this.movabilityModel = movabilityModel;
		return this;
	}
	
	
	
	
	public MovabilityModel getMovabilityModel() {
		return this.movabilityModel;
	}
	
	
	
	
	/**
	 * 
	 * @param gCost - G cost(Tentative Distance) of the current node(Vertex).
	 * 
	 * @return
	 * 
	 */
	public Vertex setGCost(double gCost) {
		this.gCost = gCost;
		return this;
	}
	
	
	
	
	/**
	 * 
	 * @return G cost(Tentative Distance) of the current node(Vertex).
	 * 
	 */
	public double getGCost() {
		return this.gCost;
	}
	
	
	
	
	/**
	 * 
	 * @param hCost - H cost(Heuristic Distance) of the current node(Vertex).
	 * @return
	 * 
	 */
	public Vertex setHCost(double hCost) {
		this.hCost = hCost;
		return this;
	}
	
	
	
	
	/**
	 * 
	 * @return H cost(Heuristic Distance) of the current node(Vertex).
	 * 
	 */
	public double getHCost() {
		return this.hCost;
	}
	
	
	
	
	/**
	 * 
	 * <br>
	 * 		Returns the total distance of the current node(Vertex) which is
	 * 		calculated using the following formula: <strong>F cost = (G cost + H cost)</strong>
	 * <br>
	 * 
	 * @return the total distance((G cost + H cost) or in other words (Tentative Distance + Heuristic Distance)
	 * 		   of the current node(Vertex).
	 * 
	 */
	public double getFCost() {
		return ((this.gCost == Double.POSITIVE_INFINITY || this.hCost == Double.POSITIVE_INFINITY) ? Double.POSITIVE_INFINITY : (this.gCost + this.hCost));
	}
	
	
	
	
	public Vertex setShapeType(int shapeType) {
		this.shapeType = shapeType;
		return this;
	}
	
	
	
	
	public int getShapeType() {
		return this.shapeType;
	}
	
	
	
	
	public Vertex setMark(String mark) {
		this.mark = mark;
		return this;
	}
	
	
	
	
	public String getMark() {
		return this.mark;
	}
	
	
	
	
	public boolean hasMark() {
		return !TextUtils.isEmpty(this.mark);
	}
	
	
	
	
	public Vertex setColor(int color) {
		return setColor(new Color(color));
	}
	
	
	
	
	public Vertex setColor(Color color) {
		this.color = color;
		return this;
	}
	
	
	
	
	public Color getColor() {
		return this.color;
	}
	
	
	
	
	public Vertex setObjectColor(int color) {
		return setObjectColor(new Color(color));
	}
	
	
	
	
	public Vertex setObjectColor(Color color) {
		this.objectColor = color;
		return this;
	}
	
	
	
	
	public Color getObjectColor() {
		return this.objectColor;
	}
	
	
	
	
	public Vertex setObstacleColor(int color) {
		return setObstacleColor(new Color(color));
	}
	
	
	
	
	public Vertex setObstacleColor(Color color) {
		this.obstacleColor = color;
		return this;
	}
	
	
	
	
	public Color getObstacleColor() {
		return this.obstacleColor;
	}
	
	
	
	
	public Vertex setPathColor(int color) {
		return setPathColor(new Color(color));
	}
	
	
	
	
	public Vertex setPathColor(Color color) {
		this.pathColor = color;
		return this;
	}
	
	
	
	
	public Color getPathColor() {
		return this.pathColor;
	}
	
	
	
	
	public Color getCurrentTypeColor() {
		switch(this.type) {
		
		case Type.OBJECT:
			return this.objectColor;
			
		case Type.OBSTACLE:
			return this.obstacleColor;
			
		case Type.PATH:
			return this.pathColor;
			
		default:
			return this.color;
		
		}
	}
	
	
	
	
	public Vertex setTextColor(int color) {
		return setTextColor(new Color(color));
	}
	
	
	
	
	public Vertex setTextColor(Color color) {
		this.textColor = color;
		return this;
	}
	
	
	
	
	public Color getTextColor() {
		return this.textColor;
	}
	
	
	
	
	public Vertex setFill(boolean shouldBeFilled) {
		this.shouldBeFilled = shouldBeFilled;
		return this;
	}
	
	
	
	
	public boolean shouldBeFilled() {
		return this.shouldBeFilled;
	}
	
	
	
	
	public Vertex setColorInheritable(boolean isColorInheritable) {
		this.isColorInheritable = isColorInheritable;
		return this;
	}
	
	
	
	
	public boolean isColorInheritable() {
		return this.isColorInheritable;
	}
	
	
	
	
	public boolean isVisitable() {
		return (this.type != Type.OBSTACLE);
	}
	
	
	
	
	public Vertex setPreviousVertex(Vertex previousVertex) {
		this.previousVertex = previousVertex;
		return this;
	}
	
	
	
	
	public Vertex getPreviousVertex() {
		return this.previousVertex;
	}
	
	
	
	
	@Override
	public Vertex setTag(Object tag) {
		this.tag = tag;
		return this;
	}




	@Override
	public Object getTag() {
		return this.tag;
	}
	
	
	
	
	public boolean contains(Point point) {
		return contains(point.x, point.y);
	}
	
	
	
	
	public boolean contains(int x, int y) {
		if(shapeType == ShapeType.SQUARE) {
			return ((x >= (this.x - this.size)) && (x <= (this.x + this.size)) 
					&& (y >= (this.y - this.size)) && (y <= (this.y + this.size)));
		} else {
			return (MathUtils.distance(this.x, this.y, x, y) <= this.size * 2);
		}
	}
	
	
	
	
	public boolean equalsCoordinatewise(Vertex vertex) {
		return ((this.x == vertex.getX()) && (this.y == vertex.getY()));
	}
	
	
	
	
	public boolean equalsKeywise(Vertex vertex) {
		return getUniqueKey().equals(vertex.getUniqueKey());
	}
	
	
	
	
	/**
	 * 
	 * <br>
	 * 		Calculates the distance between the current Vertex and the given Vertex.
	 * <br>
	 * 
	 * @param vertex - the Vertex to calculate the distance to.
	 * 
	 * @return the calculated distance.
	 * 
	 */
	public double distance(Vertex vertex) {
		return MathUtils.distance(this.x, this.y, vertex.getX(), vertex.getY());
	}
	
	
	
	
	/**
	 * 
	 * <br>
	 * 		Calculates the heuristic distance between the current Vertex and the given Vertex.
	 * 		(Is used exclusively by the A* (A star) algorithm)
	 * <br>
	 * 
	 * @param vertex - the Vertex to calculate the heuristic distance to.
	 * 
	 * @return the calculated heuristic distance.
	 * 
	 */
	public double heuristicDistance(Vertex vertex) {
		if(this.movabilityModel.equals(MovabilityModel.FOUR_DIRECTIONS_CROSSWISE)) {
			return (this.movabilityModel.value * (Math.abs(this.x - vertex.getX()) + Math.abs(this.y - vertex.getY())));
		} else {
			return (this.movabilityModel.value * distance(vertex));
		}
	}
	
	
	
	
	@Override
	public String getUniqueKey() {
		return ("x:" + x + ";y:" + y);
	}




	@Override
	public void draw(Graphics2D g) {
		//drawing the actual vertex
		if(isColorInheritable()) {
			g.setColor(color);
		} else {
			g.setColor(getCurrentTypeColor());
		}
		
		if(shouldBeFilled) {
			if(shapeType == ShapeType.CIRLCE) {
				g.fillOval(
					(x - size),
					(y - size),
					(size * 2),
					(size * 2)
				);
			} else if(shapeType == ShapeType.SQUARE) {
				g.fillRect(
					(x - size),
					(y - size),
					(size * 2),
					(size * 2)
				);
			}
		} else {
			if(shapeType == ShapeType.CIRLCE) {
				g.drawOval(
					(x - size),
					(y - size),
					(size * 2),
					(size * 2)
				);
			} else if(shapeType == ShapeType.SQUARE) {
				g.drawRect(
					(x - size),
					(y - size),
					(size * 2),
					(size * 2)
				);
			}
		}
		
		//drawing the vertex's mark is necessary
		if(hasMark()) {
			g.setColor(textColor);
			g.setFont(new Font(Font.SERIF, Font.BOLD, textSize));
			g.drawString(mark, x, y);
		}
	}




	@Override
	public Vertex copy() {
		Vertex vertexCopy = new Vertex(this.x, this.y);
		vertexCopy.setSize(this.size);
		vertexCopy.setTextSize(this.textSize);
		vertexCopy.setMark(this.mark);
		vertexCopy.setColor(this.color.getRGB());
		vertexCopy.setObjectColor(this.objectColor.getRGB());
		vertexCopy.setObstacleColor(this.obstacleColor.getRGB());
		vertexCopy.setPathColor(this.pathColor.getRGB());
		vertexCopy.setTextColor(this.textColor.getRGB());
		vertexCopy.setType(this.type);
		vertexCopy.setShapeType(this.shapeType);
		vertexCopy.setMovabilityModel(this.movabilityModel);
		vertexCopy.setPreviousVertex(this.previousVertex);
		vertexCopy.setColorInheritable(this.isColorInheritable);
		vertexCopy.setFill(this.shouldBeFilled);
		vertexCopy.setTag(this.tag);
		
		return vertexCopy;
	}




	@Override
	public int compareTo(Vertex vertex) {
		if(getFCost() > vertex.getFCost()) {
			return 1;
		} else if(getFCost() < vertex.getFCost()) {
			return -1;
		}
		
		return 0;
	}
	
	
	
	
	@Override
	public Vertex fromCSV(String csvData) {
		String[] data = csvData.split(",");
		setX(Integer.parseInt(data[0]));
		setY(Integer.parseInt(data[1]));
		setSize(Integer.parseInt(data[2]));
		setTextSize(Integer.parseInt(data[3]));
		setType(Integer.parseInt(data[4]));
		setShapeType(Integer.parseInt(data[5]));
		setMark(data[6]);
		setMovabilityModel(MovabilityModelFactory.getMovabilityModelForName(data[7]));
		setColor(Integer.parseInt(data[8]));
		setObjectColor(Integer.parseInt(data[9]));
		setObstacleColor(Integer.parseInt(data[10]));
		setPathColor(Integer.parseInt(data[11]));
		setTextColor(Integer.parseInt(data[12]));
		setFill(Boolean.parseBoolean(data[13]));
		setColorInheritable(Boolean.parseBoolean(data[14]));
		
		return this;
	}




	@Override
	public String toCSV() {
		StringBuilder vertexCSVBuilder = new StringBuilder();
		vertexCSVBuilder.append(this.x).append(",");
		vertexCSVBuilder.append(this.y).append(",");
		vertexCSVBuilder.append(this.size).append(",");
		vertexCSVBuilder.append(this.textSize).append(",");
		vertexCSVBuilder.append(this.type).append(",");
		vertexCSVBuilder.append(this.shapeType).append(",");
		vertexCSVBuilder.append(this.mark).append(",");
		vertexCSVBuilder.append(this.movabilityModel.name()).append(",");
		vertexCSVBuilder.append(this.color.getRGB()).append(",");
		vertexCSVBuilder.append(this.objectColor.getRGB()).append(",");
		vertexCSVBuilder.append(this.obstacleColor.getRGB()).append(",");
		vertexCSVBuilder.append(this.pathColor.getRGB()).append(",");
		vertexCSVBuilder.append(this.textColor.getRGB()).append(",");
		vertexCSVBuilder.append(this.shouldBeFilled).append(",");
		vertexCSVBuilder.append(this.isColorInheritable);
		
		return vertexCSVBuilder.toString();
	}




	public static String getNameForVertexType(int vertexType) {
		switch(vertexType) {
		
		case Type.OBJECT:
			return "Object";
			
		case Type.OBSTACLE:
			return "Obstacle";
			
		case Type.PATH:
			return "Path";
		
		default:
			return "";
		
		}
	}
	
	
	
	
	public static int getVertexTypeForName(String name) {
		if(name.equals("Object")) {
			return Type.OBJECT;
		} else if(name.equals("Obstacle")) {
			return Type.OBSTACLE;
		} else {
			return Type.PATH;
		}
	}
	
	
	

}
