package math;

import java.awt.Point;
import java.awt.Polygon;


/**
 * Object Class
 * 
 */
public class Obj {
	
	boolean enabled = true;
	String Name;
	float density;
	float e;
	float mass;
	float invMass;	
	float r;
	float theta;
	
	
	/**
	 *  0 = Polygon;
	 *  1 = PointMass;
	 *  2 = Polyline;
	 */
	int type = 0;
	public final int Polygon = 0;
	public final int PointMass = 1;
	public final int Polyline = 2;
	
	/**
	 * Center Of mass, Used as position of object in world space.
	 * The shape associated with the object has cOfM as its origin.
	 */	
	MyPoint cOfM;
	Shape shape;
	
	/**
	 * For display
	 */
	private Polygon renderPoly;
	private float scale;
	private int offx,offy;
	
	/**
	 * For worldspace
	 */
	private double[] ypoints;
	private double[] xpoints;
	
		public Obj(){
			this.e = 1;
			this.mass = 1;
			this.shape = new Shape();
			this.cOfM =  shape.findCenter();
			this.type = Polygon;
			updateWorldSpace();
		}		
		public Obj(Float mass, MyPoint myPoint, MyPoint[] points) {
			this.e = 1;
			this.mass = mass;
			this.shape = new Shape(points);
			this.cOfM =  myPoint;
			this.type = Polygon;
			updateWorldSpace();
		}
		public Obj(int type,MyPoint centerOfMass,Shape PLACEHOLDER,double mass,float e) {
			this.e = e;
			this.mass = (float) mass;
			this.type = type;
			this.shape = PLACEHOLDER;
			this.cOfM =  centerOfMass;
			updateWorldSpace();
		}
		/**
		 * Get object Type
		 * 0 = Polygon
		 * 1 = Point
		 * 2 = PolyLine
		 * @return int type
		 */
		public int getType(){
			return type;
		}		
		/**
		 * Passes canvas info to use for render.
		 */
		public void prepareForPaint(int ox,int oy, float scale){
			offx = ox;
			offy = oy;
			this.scale = scale;
			updateWorldSpace();
		}
		/**
		 * Update position in world space.
		 */
		private void updateWorldSpace() {
			if(type == PointMass)return;
			
			double[] nypoints = new double[shape.getNPoints()];
			double[] nxpoints = new double[shape.getNPoints()];
			for(int i =0;i < shape.getNPoints();i++){
				nypoints[i] = shape.points[i].y+cOfM.y;
				nxpoints[i] = shape.points[i].x+cOfM.x;
			}
			this.ypoints = nypoints;
			this.xpoints = nxpoints;
			
			int[] wypoints = new int[shape.getNPoints()];
			int[] wxpoints = new int[shape.getNPoints()];
			
			for(int i =0;i < shape.getNPoints();i++){
				wxpoints[i] = (int) (xpoints[i]/scale + offx);
				wypoints[i] = (int) (offy - ypoints[i]/scale);
			}
			renderPoly = new Polygon(wxpoints,wypoints,wxpoints.length);
		}
		
		/**Checks if the given point in world space is with an object's vertices. For lines and points test if distance from cursor is with the tolerance. Tolerance is 1/4 of the inverse of the display scale.
		 * 
		 * @see https://en.wikipedia.org/wiki/Ray_casting
		 * @see http://mathworld.wolfram.com/Point-LineDistance2-Dimensional.html
		 * @param point The point to test
		 * @param ox Offset x
		 * @param oy Offset y
		 * @paran scale Scale of canvas
		 * @return True if point is in polygon 
		 */
		public boolean PointInPolygon(Point point,int ox,int oy,float scale) {		
			float tolerance = 1/scale/4;
			 if(type == 0){
				  int i, j, nvert = xpoints.length;
				  boolean c = false;
				  for(i = 0, j = nvert - 1; i < nvert; j = i++) {
				    if( ( (renderPoly.ypoints[i] >= point.y ) != (renderPoly.ypoints[j] >= point.y) ) &&
				        (point.x <= (renderPoly.xpoints[j] - renderPoly.xpoints[i]) * (point.y -renderPoly. ypoints[i]) / (renderPoly.ypoints[j] - renderPoly.ypoints[i]) + renderPoly.xpoints[i])
				      )
				      c = !c;
				  }
				  return c;
			 }
			 else if(type == 1){					 
				if(Math.abs(point.x-(cOfM.x/scale + ox))<tolerance  && Math.abs(point.y-(oy - cOfM.y/scale))<tolerance){
					System.out.println("Tested");
					return true;
				}		 
			 }
			 else if(type == 2){

				 for(int i = 0; i < renderPoly.npoints-1;i++){
					float a = Math.abs((renderPoly.xpoints[i+1]-renderPoly.xpoints[i])*(renderPoly.ypoints[i]-point.y)-(renderPoly.xpoints[i]-point.x)*(renderPoly.ypoints[i+1]-renderPoly.ypoints[i]));
					double b = Math.sqrt(Math.pow(renderPoly.xpoints[i+1]-renderPoly.xpoints[i],2)+Math.pow(renderPoly.ypoints[i+1]-renderPoly.ypoints[i],2));
					if(a/b<tolerance)return true;
				 }
			 }
			 
			 return false;
		}
		/**
		 * Set the object to a location. Not translation.
		 * @param  myPoint The Point in world space to move it to. 
		 */
		public void moveto(MyPoint myPoint,float scale,int offx,int offy) {
			
			
			this.cOfM.x  = (myPoint.x-offx)*scale;
			this.cOfM.y  = (offy - myPoint.y)*scale;
			updateWorldSpace();
		}
		/**
		 * Rotate object by angle theta about it's center of mass.
		 * @param theta The angle to rotate by.
		 * @param radians True if theta is in radians.
		 * @param vertex The vertex to rotate around
		 * @param origin The center to rotate the shape by.
		 */
		public void rotate(Double theta, boolean radians,MyPoint origin) {
			if(!radians)theta = Math.toRadians(theta);
			System.out.println("Attempted to rotate by " + theta + " radians");
			if(origin != null){
				cOfM = new MyPoint(cOfM.x-origin.x,cOfM.y-origin.y);	
				
				double tempx = cOfM.x * Math.cos(theta) - cOfM.y * Math.sin(theta);
				cOfM.y = cOfM.x * Math.sin(theta) + cOfM.y * Math.cos(theta);
				cOfM.x = tempx;
				
			}	
			cOfM = new MyPoint(cOfM.x+origin.x,cOfM.y+origin.y);
			
			shape.rotate(theta,origin,cOfM);
			//TODO: REALLY TEST THIS
			
			updateWorldSpace();
		}
		public int getWorldX(float scale,int offx) {
			return (int) (cOfM.x/scale + offx);
			
		}
		public int getWorldY(float scale,int offy) {
			return (int) (offy - cOfM.y/scale);
			
		}
		public String getName(){
			return Name;
		}
		public double getMass() {
			return mass;
		}
		public MyPoint getCOM(){
			return new MyPoint(cOfM.x,cOfM.y);
		}
		public float getRest(){
			return e;
		}
		public void disable(){
			enabled = true;
		}
		public void enable(){
			enabled = false;
		}
		public Polygon getRenderPoly(){
			return renderPoly;
		}
		
	}
