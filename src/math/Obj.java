package math;

import java.awt.Point;
import java.awt.Polygon;
import java.io.Serializable;

/**
 * The Obj class represents shapes used in mechanics 2
 * 
 */
public class Obj implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private float density;
	private float mass;

	private int type = 0;
	static public final int POLYGON = 0;
	static public final int POINTMASS = 1;
	static public final int POLYLINE = 2;

	/**
	 * Center Of mass, Used as position of object in world space. The shape
	 * associated with the object has COM as its origin.
	 */
	private MyPoint COM;
	private Shape shape;

	/**
	 * For display
	 */
	private Polygon renderPoly;
	private double scale;
	private int offx, offy;

	/**
	 * For worldspace
	 */
	private double[] ypoints;
	private double[] xpoints;

	public Obj() {
		this.mass = 1;
		this.shape = new Shape();
		this.COM = shape.findCenter();
		this.type = POLYGON;
		updateWorldSpace();
	}

	/**
	 * Constructs a new Obj of type POLYGON based on parameters
	 * 
	 * @param centerOfMass
	 *            Point representing the center of mass of the object
	 * @param points
	 *            Points representing the vertices of the shape
	 * @param mass
	 *            The mass of the obj
	 */
	public Obj(Float mass, MyPoint centerOfMass, MyPoint[] points) {
		this.mass = mass;
		this.shape = new Shape(points);
		this.COM = centerOfMass;
		this.type = POLYGON;
		updateWorldSpace();
	}

	/**
	 * Constructs a new Obj based on parameters
	 * 
	 * @param type
	 *            Can be one of Obj.POLYGON, Obj.POINTMASS and Obj.POLYLINE
	 * @param centerOfMass
	 *            Point representing the center of mass of the object
	 * @param shape
	 *            The shape of the obj
	 * @param mass
	 *            The mass of the obj
	 */
	public Obj(int type, MyPoint centerOfMass, Shape shape, float mass) {
		this.mass = mass;
		this.type = type;
		this.shape = shape;
		this.COM = centerOfMass;
		updateWorldSpace();
	}

	/**
	 * Get object type, one of Obj.POLYGON, Obj.POINTMASS and Obj.POLYLINE
	 * 
	 * @return int type
	 */
	public int getType() {
		return type;
	}

	/**
	 * Passes canvas info to use for render.
	 * 
	 * @param ox
	 *            center of canvas (x axis)
	 * @param oy
	 *            center of canvas on y axis,
	 * @param scale
	 *            scale of the canvas
	 */
	public void prepareForPaint(int ox, int oy, double scale) {
		offx = ox;
		offy = oy;
		this.scale = scale;
		updateWorldSpace();
	}

	/**
	 * Update position in world space.
	 */
	private void updateWorldSpace() {
		if (type == POINTMASS) {
			return;
		}

		double[] nxpoints = new double[shape.getNPoints()];
		double[] nypoints = new double[shape.getNPoints()];

		for (int i = 0; i < shape.getNPoints(); i++) {
			nxpoints[i] = shape.getPoint(i).x + COM.x;
			nypoints[i] = shape.getPoint(i).y + COM.y;
		}
		this.ypoints = nypoints;
		this.xpoints = nxpoints;

		int[] wypoints = new int[shape.getNPoints()];
		int[] wxpoints = new int[shape.getNPoints()];

		for (int i = 0; i < shape.getNPoints(); i++) {
			wxpoints[i] = (int) (xpoints[i] / scale + offx);
			wypoints[i] = (int) (offy - ypoints[i] / scale);
		}
		renderPoly = new Polygon(wxpoints, wypoints, wxpoints.length);
	}

	/**
	 * Checks if the given point in world space is with an object's vertices.
	 * For lines and points test if distance from cursor is with the tolerance.
	 * Tolerance is 1/4 of the inverse of the display scale.
	 * 
	 * @see <a href="https://en.wikipedia.org/wiki/Ray_casting">
	 *      https://en.wikipedia.org/wiki/Ray_casting</a>
	 * @see <a href=
	 *      "http://mathworld.wolfram.com/Point-LineDistance2-Dimensional.html">
	 *      http://mathworld.wolfram.com/Point-LineDistance2-Dimensional.html

	 *      </a>
	 * @param point
	 *            The point to test
	 * @param ox
	 *            Offset x
	 * @param oy
	 *            Offset y
	 * @param scale
	 *            Scale of canvas
	 * @return True if point is in polygon
	 */
	public boolean PointInPolygon(Point point, int ox, int oy, double scale) {
		double tolerance = 0.25d / scale;
		// Polygon
		if (type == POLYGON) {
			int i, j;
			boolean test = false;
			for (i = 0, j = xpoints.length - 1; i < xpoints.length; j = i++) {
				int x1 = renderPoly.xpoints[i];
				int x2 = renderPoly.xpoints[j];

				int y1 = renderPoly.ypoints[i];
				int y2 = renderPoly.ypoints[j];

				if (((y1 >= point.y) != (y2 >= point.y))
						&& (point.x <= (x2 - x1) * (point.y - y1) / (y2 - y1) + x1)) {
					test = !test;
				}
			}
			return test;
		}
		// Point
		else if (type == POINTMASS) {
			if (Math.abs(point.x - (COM.x / scale + ox)) < tolerance
					&& Math.abs(point.y - (oy - COM.y / scale)) < tolerance) {
				return true;
			}
		}
		// PolyLine
		else if (type == POLYLINE) {
			for (int i = 0; i < renderPoly.npoints - 1; i++) {
				if (MathUtil.PointInLineSegment(new MyPoint(point.x, point.y), new MyPoint(
						renderPoly.xpoints[i], renderPoly.ypoints[i]), new MyPoint(
						renderPoly.xpoints[i + 1], renderPoly.ypoints[i + 1]), tolerance)) {
					return true;
				}
			}
			return false;
		}

		return false;
	}

	/**
	 * Set the object to a location. Not translation.
	 * 
	 * @param myPoint
	 *            The Point in world space to move it to.
	 * @param offx
	 *            Offset x
	 * @param offy
	 *            Offset y
	 * @param scale
	 *            Scale of canvas
	 */
	public void moveto(MyPoint myPoint, double scale, int offx, int offy) {

		this.COM.x = (myPoint.x - offx) * scale;
		this.COM.y = (offy - myPoint.y) * scale;
		updateWorldSpace();
	}

	/**
	 * Rotate object by angle theta about it's center of mass.
	 * 
	 * @param angle
	 *            The angle to rotate by.
	 * @param radians
	 *            True if theta is in radians.
	 * @param origin
	 *            The center to rotate the shape by.
	 */
	public void rotate(Double angle, boolean radians, MyPoint origin) {
		if (!radians) {
			angle = Math.toRadians(angle);
		}
		if (origin == null) {
			origin = new MyPoint(0, 0);
		}

		//offset by origin
		COM = new MyPoint(COM.x - origin.x, COM.y - origin.y);
		MathUtil.rotate(COM, angle);
		//undo offset
		COM = new MyPoint(COM.x + origin.x, COM.y + origin.y);

		shape.rotate(angle);

		updateWorldSpace();
	}

	public int getWorldX(double scale, int offx) {
		return (int) (COM.x / scale + offx);

	}

	public int getWorldY(double scale, int offy) {
		return (int) (offy - COM.y / scale);

	}

	/**
	 * @return the name of this obj
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the mass value
	 */
	public float getMass() {
		return mass;
	}

	/**
	 * 
	 * @return the point representing the COM
	 */
	public MyPoint getCOM() {
		return new MyPoint(COM.x, COM.y);
	}

	/**
	 * @return Polgygon used for display in gui
	 */
	public Polygon getRenderPoly() {
		return renderPoly;
	}

	/**
	 * @return The the points for worldspace
	 */
	public double[][] getWorldPoints() {
		return new double[][] { xpoints, ypoints };
	}

	/**
	 * Translate the obj by x units across and y units up.
	 * 
	 * @param x
	 *            The x amount to translate
	 * @param y
	 *            The y amount to translate
	 */
	public void translate(double x, double y) {
		COM.x += x;
		COM.y += y;
	}

	/**
	 * Translate the center of mass relative to the obj
	 * 
	 * @param x
	 *            The x amount to translate
	 * @param y
	 *            The y amount to translate
	 */
	public void shiftCOM(double x, double y) {
		for (MyPoint p : shape.getPoints()) {
			p.x -= x;
			p.y -= y;
		}
		translate(x, y);
	}

	/**
	 * 
	 * @param name
	 *            The new name of the obj
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @param mass
	 *            The new mass of the obj
	 * @throws NumberFormatException
	 */
	public void setMass(String mass) throws NumberFormatException {
		this.mass = Float.parseFloat(mass);
	}

}
