package math;

import java.util.ArrayList;

public class Shape {
	public MyPoint[] points;
	private int npoints;
	public Double area;
	private Double maxX,minX,maxY,minY;
	
	public Shape(){
		npoints = 4;
		points = new MyPoint[]{new MyPoint(0,0),new MyPoint(1,0),new MyPoint(1,1),new MyPoint(0,1),};
	}
	public Shape(MyPoint[] p){
		points = p;		
		npoints = points.length;
		findRange();
	}

	public Shape(ArrayList<MyPoint> vertices) {
		points = new MyPoint[vertices.size()];
		for(int i = 0; i < vertices.size();i++){
			points[i] = vertices.get(i).copy();
		}
		findRange();
		npoints = points.length;
	}
	
	public MyPoint getPoint(int i){
		return points[i];
	}
	private void findRange(){
		maxX = Double.MIN_VALUE;
		minX = Double.MAX_VALUE;
		maxY = Double.MIN_VALUE;
		minY = Double.MAX_VALUE;
		
		for(MyPoint p : getPoints()){
			minX = (p.x <= minX) ? p.x : minX ;
			minY = (p.y <= minY) ? p.y : minY ;
			maxX = (p.x >= maxX) ? p.x : maxX ;
			maxY = (p.y >= maxY) ? p.y : maxY ;
		}
	}
	public Double[] getXPoints(){
		Double[] XPoints = new Double[npoints];
		for(int i = 0; i < npoints ;i++){
			XPoints[i] = points[i].x;
		}
		return XPoints;
	}
	public Double[] getYPoints(){
		Double[] YPoints = new Double[npoints];
		for(int i = 0; i < npoints ;i++){
			YPoints[i] = points[i].y;
		}
		return YPoints;
	}

	public MyPoint findCenter() {
		int i;
		Double sum = 0d;
		
		for(i = 0; i < npoints-1;i++){
			sum += (points[i].x*points[i+1].y -
					points[i+1].x*points[i].y);
		}
		
		sum += (points[i].x*points[0].y -
				points[0].x*points[i].y);
		
		area = sum/2;
		
		//x
		i = 0;
		sum = 0d;
		
		for(i = 0; i < npoints-1;i++){
			sum += (points[i].x + points[i+1].x ) * 
					(points[i].x*points[i+1].y -
					points[i+1].x*points[i].y);
		}
		sum += (points[i].x + points[0].x ) * 
				(points[i].x*points[0].y -
				points[0].x*points[i].y);
		Double centroidx = sum/(6*area);	
		
		//y
		i = 0;
		sum = 0d;
		
		for(i = 0; i < npoints-1;i++){
			sum += (points[i].y + points[i+1].y ) * 
					(points[i].x*points[i+1].y -
					points[i+1].x*points[i].y);
		}
		sum += (points[i].y + points[0].y ) * 
				(points[i].x*points[0].y -
				points[0].x*points[i].y);
		Double centroidy = sum/(6*area);
		
		return new MyPoint(centroidx,centroidy);
	}

	public MyPoint[] getPoints() {
		return points;
	}
	public int getNPoints(){
		return npoints;
	}
	
	public MyVector getRange(){
		return new MyVector(maxX-minX,maxY-minY);
	}
	public Double getMinX(){
		return minX;
	}
	public Double getMinY(){
		return minY;
	}
    public boolean contains(MyPoint test) {
        int i;
        int j;
        boolean result = false;
        for (i = 0, j = points.length - 1; i < points.length; j = i++) {
          if ((points[i].y > test.y) != (points[j].y > test.y) &&
              (test.x < (points[j].x - points[i].x) * (test.y - points[i].y) / (points[j].y-points[i].y) + points[i].x)) {
            result = !result;
           }
        }
        return result;
      }
	public void rotate(Double x, MyPoint origin,MyPoint offset) {
		MyPoint[] offPoints = new MyPoint[points.length];
		if(origin == null){			
			for(int i = 0; i< points.length;i++){
				offPoints[i] = new MyPoint(points[i].x-origin.x,points[i].y-origin.y);
			}
		}
		else{
			offPoints = points;
		}
		for(MyPoint p : offPoints){
			double tempx = p.x * Math.cos(x) - p.y * Math.sin(x);
			p.y = p.x * Math.sin(x) + p.y * Math.cos(x);
			p.x = tempx;
		}
		if(origin != null){			
			for(int i = 0; i< points.length;i++){
				points[i] = new MyPoint(offPoints[i].x,offPoints[i].y);
			}
		}else{
			points = offPoints;
		}
		
	}
}
