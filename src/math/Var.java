package math;

public class Var implements java.io.Serializable {
	public String contents;
	public String name;
	public String label;
	public boolean given;

	/**
	 * Constructs a variable
	 * 
	 * @param n
	 *            The name of the variable.
	 * @param c
	 *            The numerical value of the variable. Set to "?" if variable
	 *            content is not yet known.
	 * @param label
	 *            Displayed name of variable;
	 */
	public Var(String n, String c, String label) {
		contents = c;
		name = n;
		this.label = label;
		given = false;
	}

	public double getVal() {
		if (contents.equals("?")) {
			return Double.MAX_VALUE;
		}
		return Double.parseDouble(contents);
	}

	public void setContents(String in, boolean given) {
		contents = new String(in);
		given = true;
	}

	public boolean isKnown() {
		return !contents.equals("?");
	}

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

	public Var copy() {
		Var out = new Var(new String(name), new String(contents), new String(label));
		out.given = given;
		return out;
	}

	public static Var[] initVars(Var[] vars, String type) {

		if (type.equals("circVars")) {
			vars = new Var[8];
			vars[0] = new Var("w", "?", "w");
			vars[1] = new Var("m", "?", "m");
			vars[2] = new Var("u", "?", "u");
			vars[3] = new Var("x", "?", "x");
			vars[4] = new Var("v", "?", "v");
			vars[5] = new Var("r", "?", "r");
			vars[6] = new Var("a", "?", "a");
			vars[7] = new Var("t", "?", "t");
		} else if (type.equals("projVars")) {
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
		} else if (type.equals("colVarA")) {
			vars = new Var[5];
			vars[0] = new Var("a", "A", "1");
			vars[1] = new Var("m1", "?", "M1");
			vars[2] = new Var("v1", "?", "V1");
			vars[3] = new Var("u1", "?", "U1");
			vars[4] = new Var("i1", "?", "i1");
		} else if (type.equals("colVarB")) {
			vars = new Var[5];
			vars[0] = new Var("b", "B", "2");
			vars[1] = new Var("m2", "?", "M2");
			vars[2] = new Var("v2", "?", "V2");
			vars[3] = new Var("u2", "?", "U2");
			vars[4] = new Var("i2", "?", "i2");
		}

		return vars;

	}
}
