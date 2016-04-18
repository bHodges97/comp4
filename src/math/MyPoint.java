package math;

import java.io.Serializable;

/**
 * The MyPoint class represents a point on a cartesian plane by using x and y
 * values for coordinates.
 * 
 */
public class MyPoint implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Double x, y;

	/**
	 * Construct a new point object
	 * 
	 * @param x
	 *            The x coordinate of the point
	 * @param y
	 *            The y coordinate of the point
	 */
	public MyPoint(Double x, Double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Construct a new point object
	 * 
	 * @param x
	 *            The x coordinate of the point
	 * @param y
	 *            The y coordinate of the point
	 */
	public MyPoint(int x, int y) {
		this.x = (double) x;
		this.y = (double) y;
	}

	/**
	 * Returns a hard copy of this object
	 * 
	 * @return a hard copy of this object
	 */
	public MyPoint copy() {
		return new MyPoint(x, y);
	}

	/**
	 * @return String representation of this point
	 */
	public String toString() {
		return x + " " + y;
	}
}
