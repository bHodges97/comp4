package math;

public class Var {
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
	public Var(String n, String c, String label, boolean given) {
		contents = c;
		name = n;
		this.label = label;
	}

	public double getVal() {
		if (contents.equals("Unknown")) {
			return Double.MAX_VALUE;
		}
		return Double.parseDouble(contents);
	}

	public void setContents(String in, boolean given) {
		contents = new String(in);
		given = true;
	}
}
