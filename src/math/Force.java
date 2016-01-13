package math;

public class Force extends MyVector{
	private double magnitude;
	private double magnitudeSqrd;
	public String label;
	public double fx,fy;
	public Obj objectRef;
	private int type;
	/*
	 * 0 = Tension
	 * 1 = Push
	 * 2 = Friction
	 * 3 = Reaction
	 */
	
	public Force(double x,double y,double fx,double fy,String label,Obj objectRef){
		super(x,y);
		this.label = label;
		this.fx = fx;
		this.fy = fy;
		magnitudeSqrd = Math.pow(fx,2)+Math.pow(fy,2);
		magnitude = Math.sqrt(magnitudeSqrd);
		this.objectRef = objectRef;
		
	}	
}
