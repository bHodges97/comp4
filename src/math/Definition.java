package math;

/**
 * 
 */
public class Definition {
	// y=mx+C

	String name;
	String method;
	String[] terms;

	public Definition(String in) throws IllegalArgumentException{
		in = in.toLowerCase();
		if(!in.contains("=")) throw new IllegalArgumentException("Illegal Argument: Missing \"=\"");
		if(in.contains(" ")) throw new IllegalArgumentException("Illegal Argument: Contains spaces");
		//if(in.contains("(") || in.contains(")")) throw new IllegalArgumentException("Illegal Argument: Contains brackets");
		if(in.contains("%") || in.contains("|") || in.contains("⋅") || in.contains("×")||name.contains("±") || name.contains("∓")|| name.contains("÷")|| name.contains("√"))
			throw new IllegalArgumentException("Illegal Argument: Illegal operators");
		String[] parts =in.split("=",2);
		name = parts[0];
		if(name.contains("*")||name.contains("/")||name.contains("+")||name.contains("-") )
			throw new IllegalArgumentException("Illegal Argument: Variable contains operators");
		method  = parts[1];
		if(method.contains("=")) throw new IllegalArgumentException("Illegal Argument: Definition contains to many \"=\"");

		terms = method.split("((?<=[+-/*^])|(?=[+-/*^]))");

		for(String t:terms)	System.out.print(t+",");

	}
}
