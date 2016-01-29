package math;

/**
 * 
 */
public class Definition {
	// y=mx+C

	String name;
	String method;
	String[] terms;
	Var[] vars;

	public Definition(String in) throws IllegalArgumentException{
		in = in.toLowerCase();
		if(!in.contains("=")){
			throw new IllegalArgumentException("Illegal Argument: Missing \"=\"");
		}
		if(in.contains(" ")){
			throw new IllegalArgumentException("Illegal Argument: Contains spaces");
		}
		/*
		 * Removed to support mathematic functions.
		 * 
		 * if(in.contains("(") || in.contains(")"))
		 *	throw new IllegalArgumentException("Illegal Argument: Contains brackets");
		 */

		if(in.contains("%") || in.contains("|") || in.contains("⋅") || in.contains("×")||name.contains("±")
				|| name.contains("∓")|| name.contains("÷")|| name.contains("√")){
			throw new IllegalArgumentException("Illegal Argument: Illegal operators");
		}
		String[] parts =in.split("=",2);
		name = parts[0];
		if(name.contains("*")||name.contains("/")||name.contains("+")||name.contains("-") ){
			throw new IllegalArgumentException("Illegal Argument: Variable contains operators");
		}

		/*
		 * Splits up each term in defintion.
		 */
		method  = parts[1];
		if(method.contains("=")){
			throw new IllegalArgumentException("Illegal Argument: Definition contains to many \"=\"");
		}
		terms = method.split("((?<=[+-/*^])|(?=[+-/*^]))");


		/*
		 * Creates list of variables used in definition.
		 */
		int counter = 0;
		for(String s : terms){
			if(!MathUtil.isNumeric(s)){
				counter++;
			}
		}
		vars = new Var[counter];
		counter = 0;
		for(int i = 0;i < terms.length;i++){
			if(!MathUtil.isNumeric(terms[i])){
				vars[counter] = new Var(terms[i],"Unknown");
				counter++;
			}
		}

		for(String t:terms)	System.out.print(t+",");

	}
}
