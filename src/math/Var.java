package math;

/**
 * Var is a class used to represent variables for used in calculations.
 * 
 */
public class Var implements java.io.Serializable {
	public String contents;
	public String name;
	public String label;
	public boolean given;
	public final static int CIRC_VARS = 0;
	public final static int COL_VAR_A = 1;
	public final static int COL_VAR_B = 2;
	public final static int PROJ_VARS = 3;

	/**
	 * Constructs a variable
	 * 
	 * @param n
	 *            The name of the variable.
	 * @param c
	 *            The numerical value of the variable. <br>
	 *            Set to "?" if variable content is unknown..
	 * @param label
	 *            Displayed name of variable;
	 */
	public Var(String n, String c, String label) {
		contents = c;
		name = n;
		this.label = label;
		given = false;
	}

	/**
	 * Gets the value of a variable. If variable is unkown returns
	 * <b>Double.MAX_VALUE</b>
	 * 
	 * @return <b>Double</b> representing variable of variable.
	 */
	public double getVal() {
		if (contents.equals("?")) {
			return Double.MAX_VALUE;
		}
		return Double.parseDouble(contents);
	}

	/**
	 * Sets the contents.
	 * 
	 * @param contents
	 *            The new contents of the variable.
	 * @param given
	 *            <true> if contents is inputed by user.
	 */
	public void setContents(String contents, boolean given) {
		this.contents = new String(contents);
		this.given = given;
	}

	/**
	 * Checks if contents is unkown.
	 * 
	 * @return <b>true</b> if contents is unkown
	 */
	public boolean isUnknown() {
		return contents.equals("?");
	}

	/**
	 * Checks if contents is approximately zero.
	 * 
	 * @return <b>true</b> if contents is approximately zero.
	 */
	public boolean isZero() {
		if (contents.equals("0")) {
			return true;
		}
		if (MathUtil.isNumeric(contents)) {
			if (Math.abs(Double.parseDouble(contents)) < 0.0001f) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Creates a list of variables.
	 * 
	 * @param type
	 *            an integer specifying the justification of the title -- one of
	 *            the following:<br>
	 *            •Var.CIRC_VARS <br>
	 *            •Var.COL_VAR_A<br>
	 *            •Var.COL_VAR_B <br>
	 *            •Var.PROJ_VARS <br>
	 * 
	 * @return the <b>Var[]</b> object
	 * 
	 */
	public static Var[] createVars(int type) {
		Var[] vars = null;
		if (type == CIRC_VARS) {
			vars = new Var[8];
			vars[0] = new Var("w", "?", "w");
			vars[1] = new Var("m", "?", "m");
			vars[2] = new Var("u", "?", "u");
			vars[3] = new Var("x", "?", "x");
			vars[4] = new Var("v", "?", "v");
			vars[5] = new Var("r", "?", "r");
			vars[6] = new Var("a", "?", "a");
			vars[7] = new Var("t", "?", "t");
		} else if (type == PROJ_VARS) {
			vars = new Var[13];
			vars[0] = new Var("a", "?", "θ");
			vars[1] = new Var("v", "?", "V");
			vars[2] = new Var("b", "?", "Vx");
			vars[3] = new Var("c", "?", "Vy");
			vars[4] = new Var("h", "?", "Height");
			vars[5] = new Var("z", "?", "UNUSED");
			vars[6] = new Var("t", "?", "t");
			vars[7] = new Var("u", "?", "U");
			vars[8] = new Var("d", "?", "Ux");
			vars[9] = new Var("e", "?", "Uy");
			vars[10] = new Var("x", "?", "x");
			vars[11] = new Var("", "A", "A");
			vars[12] = new Var("y", "?", "y");
		} else if (type == COL_VAR_A) {
			vars = new Var[5];
			vars[0] = new Var("a", "A", "1");
			vars[1] = new Var("m1", "?", "M1");
			vars[2] = new Var("v1", "?", "V1");
			vars[3] = new Var("u1", "?", "U1");
			vars[4] = new Var("i1", "?", "i1");
		} else if (type == COL_VAR_B) {
			vars = new Var[5];
			vars[0] = new Var("b", "B", "2");
			vars[1] = new Var("m2", "?", "M2");
			vars[2] = new Var("v2", "?", "V2");
			vars[3] = new Var("u2", "?", "U2");
			vars[4] = new Var("i2", "?", "i2");
		}

		return vars;
	}

	/**
	 * Returns a string representation of the object
	 * 
	 * @return a string representation of the object.
	 */
	public String toString() {
		return name + " " + contents + " " + label + " " + given;
	}
}
