package math;

import java.util.ArrayList;

/*
 * 
 * 
 * 
 * 
 */

public class Plane {
	public ArrayList<Obj> objects = new ArrayList<Obj>();
	MyPoint cOfm = new MyPoint(0, 0);
	Float g = -9.8f;

	public Plane() {

	}

	public void interpret() {
		//TODO: interpret
	}

	public void findCofM() {
		Double x;
		Double y;
		Double sumMomx = 0d;
		Double sumMomy = 0d;
		Double sumMass = 0d;

		// Sum of force * dx / sum of force
		for (Obj obj : objects) {
			sumMomx += obj.cOfM.x * obj.mass * g;
			sumMomy += obj.cOfM.y * obj.mass * g;
			sumMass += obj.mass * g;
		}
		x = sumMomx / sumMass;
		y = sumMomy / sumMass;
		cOfm.x = x;
		cOfm.y = y;
		System.out.println("cOfm " + x + " " + y);
	}

	public void add(Obj obj) {
		objects.add(obj);
		if (obj.Name == null) {
			if (objects.indexOf(obj) + 'A' <= 'Z') {
				obj.Name = (char) (objects.indexOf(obj) + 'A') + "";
			} else {
				obj.Name = "Object " + objects.indexOf(obj);
			}
		}
		System.out.println("Object added(" + obj.Name + ")");
	}

	public void update() {
		//TODO: remove! this function  is completely useless/ and gives errors
		for (int i = 0; i < objects.size(); i++) {
			for (int u = i + 1; u < objects.size(); u++) {
				Obj A = objects.get(i);
				Obj B = objects.get(u);
				double[][] APoints = A.getWorldPoints();
				double[][] BPoints = B.getWorldPoints();
				for (int o = 0; o < APoints[0].length; o++) {
					float tolerance = 1f;
					for (int x = 0; x < BPoints[0].length - 1; x++) {
						MathUtil.PointInLineSegment(new MyPoint(APoints[0][o], APoints[1][o]),
								new MyPoint(BPoints[0][i], APoints[1][i]), new MyPoint(
										BPoints[0][i + 1], APoints[1][i + 1]), tolerance);
					}
				}
			}
		}

	}
}
