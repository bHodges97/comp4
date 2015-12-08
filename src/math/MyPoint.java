package math;

public class MyPoint {
	public Double x,y;
	
	public MyPoint(Double x,Double y){
		this.x = x;
		this.y = y;
	}

	public MyPoint(int x, int y) {
		this.x = (double) x;
		this.y = (double) y;
	}

	public MyPoint copy() {
		return new MyPoint(x,y);
	}
}
