package math;

import java.awt.Point;
import java.awt.Polygon;


/**
 * Object Class
 * 
 */
public class Obj {
	float density;
	float e;
	float mass;
	float invMass;	
	float r;
	float theta;
	/**
	 * Center Of mass, Used as position of object in world space.
	 * The shape associated with the object has cOfM as its origin.
	 */
	
	public final int Polygon = 0;
	public final int PointMass = 1;
	public final int Polyline = 2;
	/**
	 *  0 = Polygon;
	 *  1 = PointMass;
	 *  2 = Polyline;
	 */
	int type = 0;
	
	MyPoint cOfM;
	Shape shape;
	
	private Polygon worldPoly;
	
	
	private Double[] ypoints;
	private Double[] xpoints;
	
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
		public int getType(){
			return type;
		}

		public Polygon createShape(float scale,int offx,int offy) {
			updateWorldSpace();
			int[] nxpoints = new int[shape.getNPoints()];
			int[] nypoints = new int[shape.getNPoints()];
			
			for(int i =0;i < shape.getNPoints();i++){
				nxpoints[i] = (int) (xpoints[i]/scale + offx);
				nypoints[i] = (int) (offy - ypoints[i]/scale);
			}
			worldPoly = new Polygon(nxpoints,nypoints,shape.getNPoints());
			return worldPoly;
		}

		private void updateWorldSpace() {
			if(type != Polygon)return;
			//TODO: Make this work
			
			Double[] nypoints = new Double[shape.getNPoints()];
			Double[] nxpoints = new Double[shape.getNPoints()];
			for(int i =0;i < shape.getNPoints();i++){
				nypoints[i] = shape.points[i].y+cOfM.y;
				nxpoints[i] = shape.points[i].x+cOfM.x;
			}
			this.ypoints = nypoints;
			this.xpoints = nxpoints;
		}
		
		/**
		 * https://en.wikipedia.org/wiki/Ray_casting
		 * Checks if the given point in world space is with an object's vertices
		 * @param point The point to test
		 * @return True is point is in polygon 
		 */
		public boolean PointInPolygon(Point point,int ox,int oy,float scale) {
			 if( Math.abs(point.x - cOfM.x) <= 2 && Math.abs(point.y - cOfM.y) <= 2 && type!=0){
				 return true;
			 }
			 
			 if(type == 0){
				  int i, j, nvert = xpoints.length;
				  boolean c = false;
				  for(i = 0, j = nvert - 1; i < nvert; j = i++) {
				    if( ( (worldPoly.ypoints[i] >= point.y ) != (worldPoly.ypoints[j] >= point.y) ) &&
				        (point.x <= (worldPoly.xpoints[j] - worldPoly.xpoints[i]) * (point.y -worldPoly. ypoints[i]) / (worldPoly.ypoints[j] - worldPoly.ypoints[i]) + worldPoly.xpoints[i])
				      )
				      c = !c;
				  }
				  return c;
			 }
			 else{
				 if(type == 1){
					 
					if(Math.abs(point.x-(cOfM.x/scale + ox))<1/scale  && Math.abs(point.y-(oy - cOfM.y/scale))<1/scale){
						System.out.println("Tested");
						return true;
					}
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
		public void rotate(Double x, boolean radians) {
			if(!radians)x = Math.toRadians(x);
			System.out.println("Attempted to rotate by " + x + " radians");
			shape.rotate(x);
			updateWorldSpace();
			
		}
		public int getWorldX(float scale,int offx) {
			return (int) (cOfM.x/scale + offx);
			
		}
		public int getWorldY(float scale,int offy) {
			return (int) (offy - cOfM.y/scale);
			
		}
	}
