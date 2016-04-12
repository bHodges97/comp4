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
		return contents.equals("?");
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
}
