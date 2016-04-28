package math;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The Shape class represents a 2d shape by using an array of vertices.
 * 
 */
public class Shape implements Serializable {
	private static final long serialVersionUID = 1L;

	private MyPoint[] points;
	private int nPoints;
	private Double area;
	private Double maxX, minX, maxY, minY;

	/**
	 * Creates a default shape.Used for debugging.
	 * 
	 */
	public Shape() {
		nPoints = 3;
		points = new MyPoint[] { new MyPoint(0, 0), new MyPoint(1, 0), new MyPoint(1, 1) };
	}

	/**
	 * Creates a new shape with its vertices set to parameter.
	 * 
	 * @param points
	 *            The vertices representing the shape
	 */
	public Shape(MyPoint[] points) {
		this.points = points;
		nPoints = points.length;
		findRange();
	}

	/**
	 * Creates a new shape with its vertices set to parameter.
	 * 
	 * @param vertices
	 *            The vertices representing the shape
	 */
	public Shape(ArrayList<MyPoint> vertices) {
		points = new MyPoint[vertices.size()];
		for (int i = 0; i < vertices.size(); i++) {
			points[i] = vertices.get(i).copy();
		}
		findRange();
		nPoints = points.length;
	}

	/**
	 * Finds the bounds of the current shape
	 */
	private void findRange() {
		maxX = Double.MIN_VALUE;
		minX = Double.MAX_VALUE;
		maxY = Double.MIN_VALUE;
		minY = Double.MAX_VALUE;

		for (MyPoint p : getPoints()) {
			minX = (p.x <= minX) ? p.x : minX;
			minY = (p.y <= minY) ? p.y : minY;
			maxX = (p.x >= maxX) ? p.x : maxX;
			maxY = (p.y >= maxY) ? p.y : maxY;
		}
	}

	/**
	 * Returns the point at index <b>i</b>
	 * 
	 * @param i
	 *            Index of the point to retreive
	 * @return point at index <b>i</b>
	 */
	public MyPoint getPoint(int i) {
		return points[i];
	}

	/**
	 * Gets an array of x coordinates.
	 * 
	 * @return an array of all x coordinates.
	 */
	public Double[] getXPoints() {
		Double[] XPoints = new Double[nPoints];
		for (int i = 0; i < nPoints; i++) {
			XPoints[i] = points[i].x;
		}
		return XPoints;
	}

	/**
	 * Gets an array of y coordinates.
	 * 
	 * @return an array of all y coordinates.
	 */
	public Double[] getYPoints() {
		Double[] YPoints = new Double[nPoints];
		for (int i = 0; i < nPoints; i++) {
			YPoints[i] = points[i].y;
		}
		return YPoints;
	}

	/**
	 * Finds area of current shape
	 */
	public void findArea() {
		Double sum = 0d;
		int i = 0;

		for (i = 0; i < nPoints - 1; i++) {
			sum += (points[i].x * points[i + 1].y - points[i + 1].x * points[i].y);
		}

		sum += (points[i].x * points[0].y - points[0].x * points[i].y);

		area = sum / 2;
	}

	/**
	 * Finds the geometric centre of the shape
	 * 
	 * @return point representing the centre of the shape.
	 */
	public MyPoint findCenter() {
		int i;
		Double sum = 0d;

		findArea();

		// find x co-ords of centre
		for (i = 0; i < nPoints - 1; i++) {// loops through each vertex
			sum += (points[i].x + points[i + 1].x) * (points[i].x * points[i + 1].y - points[i + 1].x * points[i].y);
		}
		sum += (points[i].x + points[0].x) * (points[i].x * points[0].y - points[0].x * points[i].y);
		Double centroidx = sum / (6 * area);

		// find y co-ords of centre
		i = 0;
		sum = 0d;

		for (i = 0; i < nPoints - 1; i++) {// loops through each vertex
			sum += (points[i].y + points[i + 1].y) * (points[i].x * points[i + 1].y - points[i + 1].x * points[i].y);
		}
		sum += (points[i].y + points[0].y) * (points[i].x * points[0].y - points[0].x * points[i].y);
		Double centroidy = sum / (6 * area);

		return new MyPoint(centroidx, centroidy);
	}

	/**
	 * Returns every vertex in this shape.
	 * 
	 * @return Every vertex in this shape.
	 */
	public MyPoint[] getPoints() {
		return points;
	}

	/**
	 * Returns the number of vertexs in this shape
	 * 
	 * @return the number of vertexs in this shape
	 */
	public int getNPoints() {
		return nPoints;
	}

	/**
	 * Returns vector representation of the range of the object
	 * 
	 * @return vector representation of the range of the object
	 */
	public MyVector getRange() {
		return new MyVector(maxX - minX, maxY - minY);
	}

	/**
	 * Gets the value of minX
	 * 
	 * @return the value of minX
	 */
	public Double getMinX() {
		return minX;
	}

	/**
	 * Gets the value of minY
	 * 
	 * @return the value of minY
	 */
	public Double getMinY() {
		return minY;
	}

	/**
	 * Checks if shape containts the point in parameters.
	 * 
	 * @param point
	 *            The point to text
	 * @return <b>true</b> if point is int shape,<b>false</b> if otherwise
	 * @deprecated
	 */
	public boolean contains(MyPoint point) {
		int i;
		int j;
		boolean result = false;
		for (i = 0, j = points.length - 1; i < points.length; j = i++) {
			if ((points[i].y > point.y) != (points[j].y > point.y)
					&& (point.x < (points[j].x - points[i].x) * (point.y - points[i].y) / (points[j].y - points[i].y)
							+ points[i].x)) {
				result = !result;
			}
		}
		return result;
	}

	/**
	 * Rotates the shape by theta degrees relative to origin
	 * 
	 * @param angle
	 *            The angle to rotate by
	 */
	public void rotate(Double angle) {
		// rotate each point
		for (MyPoint point : points) {
			MathUtil.rotate(point, angle);
		}
	}
}
