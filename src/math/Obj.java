package math;

import java.awt.Point;
import java.awt.Polygon;

/**
 * Object Class
 * 
 */
public class Obj {

	boolean enabled = true;
	String name;
	float density;
	float mu = 0;
	float mass;
	float invMass;
	float r;
	float theta;

	/**
	 * 0 = Polygon; 1 = PointMass; 2 = Polyline;
	 */
	int type = 0;
	public final int Polygon = 0;
	public final int PointMass = 1;
	public final int Polyline = 2;

	/**
	 * Center Of mass, Used as position of object in world space. The shape
	 * associated with the object has COM as its origin.
	 */
	MyPoint COM;
	Shape shape;

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
		this.type = Polygon;
		updateWorldSpace();
	}

	public Obj(Float mass, MyPoint myPoint, MyPoint[] points) {
		this.mass = mass;
		this.shape = new Shape(points);
		this.COM = myPoint;
		this.type = Polygon;
		updateWorldSpace();
	}

	public Obj(int type, MyPoint centerOfMass, Shape PLACEHOLDER, double mass) {
		this.mass = (float) mass;
		this.type = type;
		this.shape = PLACEHOLDER;
		this.COM = centerOfMass;
		updateWorldSpace();
	}

	/**
	 * Get object Type 0 = Polygon 1 = Point 2 = PolyLine
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
		if (type == PointMass)
			return;

		double[] nxpoints = new double[shape.getNPoints()];
		double[] nypoints = new double[shape.getNPoints()];

		for (int i = 0; i < shape.getNPoints(); i++) {
			nxpoints[i] = shape.points[i].x + COM.x;
			nypoints[i] = shape.points[i].y + COM.y;
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
	 * @see <a href="https://en.wikipedia.org/wiki/Ray_casting">https://en.

	 *      wikipedia.org/wiki/Ray_casting</a>
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
		if (type == 0) {
			int i, j, nvert = xpoints.length;
			boolean c = false;
			for (i = 0, j = nvert - 1; i < nvert; j = i++) {
				if (((renderPoly.ypoints[i] >= point.y) != (renderPoly.ypoints[j] >= point.y))
						&& (point.x <= (renderPoly.xpoints[j] - renderPoly.xpoints[i])
								* (point.y - renderPoly.ypoints[i])
								/ (renderPoly.ypoints[j] - renderPoly.ypoints[i])
								+ renderPoly.xpoints[i]))
					c = !c;
			}
			return c;
		}
		// Point
		else if (type == 1) {
			if (Math.abs(point.x - (COM.x / scale + ox)) < tolerance
					&& Math.abs(point.y - (oy - COM.y / scale)) < tolerance) {
				return true;
			}
		}
		// PolyLine
		else if (type == 2) {
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
	 * @param theta
	 *            The angle to rotate by.
	 * @param radians
	 *            True if theta is in radians.
	 * @param origin
	 *            The center to rotate the shape by.
	 */
	public void rotate(Double theta, boolean radians, MyPoint origin) {
		if (!radians)
			theta = Math.toRadians(theta);
		if (origin != null) {
			COM = new MyPoint(COM.x - origin.x, COM.y - origin.y);

			double tempx = COM.x * Math.cos(theta) - COM.y * Math.sin(theta);
			COM.y = COM.x * Math.sin(theta) + COM.y * Math.cos(theta);
			COM.x = tempx;

		}
		COM = new MyPoint(COM.x + origin.x, COM.y + origin.y);

		shape.rotate(theta, origin, COM);
		// TODO: REALLY TEST THIS

		updateWorldSpace();
	}

	public int getWorldX(double scale, int offx) {
		return (int) (COM.x / scale + offx);

	}

	public int getWorldY(double scale, int offy) {
		return (int) (offy - COM.y / scale);

	}

	public String getName() {
		return name;
	}

	public double getMass() {
		return mass;
	}

	public MyPoint getCOM() {
		return new MyPoint(COM.x, COM.y);
	}

	public void disable() {
		enabled = true;
	}

	public void enable() {
		enabled = false;
	}

	public Polygon getRenderPoly() {
		return renderPoly;
	}

	public double[][] getWorldPoints() {
		return new double[][] { xpoints, ypoints };
	}

	public void translate(double x, double y) {
		COM.x += x;
		COM.y += y;

	}

	public void shiftCOM(double x, double y) {
		for (MyPoint p : shape.points) {
			p.x -= x;
			p.y -= y;
		}
		translate(x, y);
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMass(String mass) throws NumberFormatException {
		this.mass = Float.parseFloat(mass);
	}

}
