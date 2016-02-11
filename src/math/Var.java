package math;

public class Var {
	public String contents;
	public String name;
	public String label;

	/**
	 * Constructs a variable
	 * 
	 * @param n The name of the variable.
	 * @param c The numerical value of the variable. Set to "Unknown" if variable content is not yet known.
	 * @param label Displayed name of var;
	 */
	public Var(String n,String c,String label){
		contents = c;
		name = n;
		this.label = label;
	}

	public double getVal() {
		if(contents.equals("Unknown")){
			return Double.MAX_VALUE;
		}
		return Double.parseDouble(contents);
	}
}
