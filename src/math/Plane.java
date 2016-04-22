package math;

import java.awt.Color;
import java.util.ArrayList;

/**
 * The Plane class is used to hold a collection of the Obj class
 */
public class Plane implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	public ArrayList<Obj> objects;
	private final Float g = -9.8f;

	/**
	 * Create a new plane;
	 */
	public Plane() {
		objects = new ArrayList<Obj>();
	}

	/**
	 * Finds the COM of this plane.
	 * 
	 * @return The center of mass of the plane
	 */
	public MyPoint findCOM() {
		MyPoint COM = new MyPoint(0, 0);
		;
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
		return COM;
	}

	/**
	 * Add an object to the current plane object.
	 * 
	 * @param obj
	 *            - the obj to add
	 * 
	 */
	public void add(Obj obj) {
		while (isColorUsed(obj.getColor())) {
			obj.setColor(null);
		}
		if (obj.getName() == null || obj.getName().equals("")) {
			if (objects.indexOf(obj) + 'A' <= 'Z') {
				obj.setName("" + (char) (objects.size() + 'A'));
			} else {
				obj.setName("Object " + objects.size());
			}
		}
		objects.add(obj);
		System.out.println("Object added(" + obj.getName() + ")");
	}

	/**
	 * @param obj
	 *            The Obj to remove;
	 */
	public void remove(Obj obj) {
		int i = objects.indexOf(obj);
		objects.remove(i);
	}

	/**
	 * Check if there is an obj in this plane with specified color
	 * 
	 * @param color
	 *            The color to test
	 * @return True if color is in use, false if otherwise
	 */
	public boolean isColorUsed(Color color) {
		for (Obj obj : objects) {
			if (obj.getColor().equals(color)) {
				return true;
			}
		}
		return false;
	}
}
