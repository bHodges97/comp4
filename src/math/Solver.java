package math;

/**
 * Solver is a class the evaluates the Definitions class based on given
 * variable.
 * 
 * 
 */
public class Solver {

	Definition[] defs;
	Var[] vars;

	/**
	 * Attempts to solve a variable based on known variables.
	 * 
	 * @param defs
	 *            The definitions to be used.
	 * 
	 * @param vars
	 *            The variables to be used.
	 * 
	 */
	public void solve(Definition[] defs, Var[] vars) {

		// Links the variables Iterates through each def
		// Iterates through defs.
		for (int i = 0; i < defs.length; i++) {
			// iterate through each var
			for (int j = 0; j < defs[i].vars.length; j++) {
				for (int k = 0; k < vars.length; k++) {
					if (defs[i].vars[j].name.equals(vars[k].name)) {
						defs[i].vars[j] = vars[k];
					}
				}
			}
		}

		long t = System.currentTimeMillis();
		while (true) {
			// limit time spent on operation as the problem could be unsolvable.
			if (System.currentTimeMillis() - t > 500) {
				System.out.println("Maximum time reached");
				break;
			}
			// fills in vars
			for (int i = 0; i < defs.length; i++) {
				defs[i].resolve();
			}
		}
	}

	/**
	 * Special case solver for collisions as simultaneos equations are too
	 * complex for this class
	 * 
	 * @param a
	 *            circVarA
	 * @param b
	 *            circVarB
	 * @param e
	 *            circVarE
	 */
	public void solve(Var[] a, Var[] b, Var e) {
		// Special case as simultaneous equations are too complex to be
		// represented.
		while (true) {
			double m1 = a[1].getVal();
			double m2 = b[1].getVal();
			double v1 = a[2].getVal();
			double v2 = b[2].getVal();
			double u1 = a[3].getVal();
			double u2 = b[3].getVal();
			double c = e.getVal();// c since e is used.
			long startTime = System.currentTimeMillis();

			// mass 1
			if (!a[1].isUnknown() && (b[1].isUnknown() && b[2].isUnknown() && b[3].isUnknown() && a[3].isUnknown()
					&& a[2].isUnknown())) {
				a[1].setContents("" + (m2 * (v2 - u2) / (u1 - v1)), false);
			}
			// mass 2
			if (!b[1].isUnknown() && (a[1].isUnknown() && a[2].isUnknown() && a[3].isUnknown() && b[3].isUnknown()
					&& b[2].isUnknown())) {
				b[1].setContents("" + (m1 * (u1 - v1) / (v2 - u2)), false);
			}
			// e
			if (!e.isUnknown() && (a[2].isUnknown() && a[3].isUnknown() && b[2].isUnknown() && b[3].isUnknown())) {
				e.setContents("" + ((v2 - v1) / (u1 - u2)), false);
				break;// Exit loop as all var must be known by now.
			}
			// v1
			if (!a[2].isUnknown() && a[2].isUnknown() && b[3].isUnknown() && b[3].isUnknown()) {
				a[2].setContents("" + (u1 + (m2 * u2 - m2 * v2) / m1), false);
			}
			// v2
			if (!b[2].isUnknown() && a[2].isUnknown() && b[3].isUnknown() && a[3].isUnknown()) {
				b[2].setContents("" + (u2 - (m1 * u1 - m1 * v1) / m2), false);
			}
			// u1
			if (!a[3].isUnknown() && a[2].isUnknown() && b[2].isUnknown() && b[3].isUnknown()) {
				a[3].setContents("" + (v1 + (m2 * v2 - m2 * u2) / m1), false);
			}
			// u2
			if (!b[3].isUnknown() && a[2].isUnknown() && b[2].isUnknown() && a[3].isUnknown()) {
				b[3].setContents("" + v2 + (m1 * v1 - m1 * u1) / m2, false);
			}
			// v1 && v2
			if (!b[2].isUnknown() && !a[2].isUnknown() && b[3].isUnknown() && a[3].isUnknown()) {
				a[2].setContents("" + (u1 + (m2 * u2 - m2 * (c * (u1 - u2) + v1)) / m1), false);
				b[2].setContents("" + (u2 - (m1 * u1 - m1 * v1) / m2), false);
			}
			// u1 && u2
			if (!b[3].isUnknown() && !a[3].isUnknown() && b[2].isUnknown() && a[2].isUnknown()) {
				b[3].setContents("" + ((m1 * v1 / m2 - m1 / m2 * ((v2 - v1) / c) + u2 / m2 + v2) / (1 - 1 / m2)),
						false);
				a[3].setContents("" + (v1 + (m2 * v2 - m2 * u2) / m1), false);
			}

			// Escape if calc take too long
			// No recursion used as the solution may not exist causing overflow
			if (System.currentTimeMillis() - startTime > 500) {
				System.out.println("Maximum time reached");
				break;
			}
		}
	}
}
