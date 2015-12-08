package math;

import java.util.ArrayList;

public class Plane {
	public ArrayList<Obj> objects = new ArrayList<Obj>();
	String topic;
	MyPoint cOfm = new MyPoint(0,0);
	Float g = -9.8f;
	public Plane(String t){
		topic = t;
		
	}
	public void findCofM() {
		Double x;
		Double y;
		Double sumMomx = 0d;
		Double sumMomy = 0d;
		Double sumMass = 0d;
		
		//Sum of force * dx / sum of force
		for(Obj obj : objects){
			sumMomx += obj.cOfM.x * obj.mass*g;
			sumMomy += obj.cOfM.y * obj.mass*g;
			sumMass += obj.mass*g;
		}
		x = sumMomx/sumMass;
		y = sumMomy/sumMass;
		cOfm.x = x;
		cOfm.y = y;
		System.out.println("cOfm "+x+" "+y);
	}
	public void add(Obj obj) {
		objects.add(obj);
		System.out.println("Object added");
	}
}
