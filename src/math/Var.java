package math;

public class Var {
	public String contents;
	public String name;

	/**
	 * Constructs a variable
	 * 
	 * @param n The name of the variable.
	 * @param c The numerical value of the variable. Set to "Unknown" if variable content is not yet known.
	 */
	public Var(String n,String c){
		contents = c;
		name = n;
	}
}
