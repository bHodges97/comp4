package math;

import java.util.ArrayList;

public class Plane implements java.io.Serializable {
	public ArrayList<Obj> objects = new ArrayList<Obj>();
	MyPoint COM = new MyPoint(0, 0);
	Float g = -9.8f;

	public Plane() {

	}

	public void findCOM() {
		Double x;
		Double y;
		Double sumMomx = 0d;
		Double sumMomy = 0d;
		Double sumMass = 0d;

		// Sum of force * dx / sum of force
		for (Obj obj : objects) {
			sumMomx += obj.COM.x * obj.mass * g;
			sumMomy += obj.COM.y * obj.mass * g;
			sumMass += obj.mass * g;
		}
		x = sumMomx / sumMass;
		y = sumMomy / sumMass;
		COM.x = x;
		COM.y = y;
		System.out.println("COM " + x + " " + y);
	}

	public void add(Obj obj) {
		objects.add(obj);
		if (obj.name == null) {
			if (objects.indexOf(obj) + 'A' <= 'Z') {
				obj.name = (char) (objects.indexOf(obj) + 'A') + "";
			} else {
				obj.name = "Object " + objects.indexOf(obj);
			}
		}
		System.out.println("Object added(" + obj.name + ")");
	}
}
