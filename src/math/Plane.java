package math;

import java.util.ArrayList;

public class Plane implements java.io.Serializable {
	public ArrayList<Obj> objects = new ArrayList<Obj>();
	MyPoint COM = new MyPoint(0, 0);
	Float g = -9.8f;

	public Plane() {

	}

	/**
	 * Finds the COM of this plane.
	 */
	public void findCOM() {
		Double x;
		Double y;
		Double sumMomx = 0d;
		Double sumMomy = 0d;
		Double sumMass = 0d;

		// Sum of force * dx / sum of force
		for (Obj obj : objects) {
			sumMomx += obj.getCOM().x * obj.getMass() * g;
			sumMomy += obj.getCOM().y * obj.getMass() * g;
			sumMass += obj.getMass() * g;
		}
		x = sumMomx / sumMass;
		y = sumMomy / sumMass;
		COM.x = x;
		COM.y = y;
		System.out.println("COM " + x + " " + y);
	}

	/**
	 * Add an object to the current plane object.
	 * 
	 * @param obj
	 *            - the obj to add
	 * 
	 */
	public void add(Obj obj) {
		objects.add(obj);
		if (obj.getName() == null) {
			if (objects.indexOf(obj) + 'A' <= 'Z') {
				obj.setName((char) (objects.indexOf(obj) + 'A') + "");
			} else {
				obj.setName("Object " + objects.indexOf(obj));
			}
		}
		System.out.println("Object added(" + obj.getName() + ")");
	}
}
