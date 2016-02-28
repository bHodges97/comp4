package math;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class Definition {
	// y=m*x+C

	String name;
	String method;
	String[] terms;
	Var[] vars;
	String def;

	public Definition(String in) throws IllegalArgumentException {
		def = in;
		in = in.toLowerCase();
		in = in.replace(" ", "");
		if (!in.contains("=")) {
			throw new IllegalArgumentException("Illegal Argument: Missing \"=\"");
		}
		/*
		 * Removed to support math functions.
		 * 
		 * if(in.contains("(") || in.contains(")")) throw new
		 * IllegalArgumentException("Illegal Argument: Contains brackets");
		 */

		if (in.contains("%") || in.contains("|") || in.contains("⋅") || in.contains("×") || in.contains("±")
				|| in.contains("∓") || in.contains("÷") || in.contains("√")) {
			throw new IllegalArgumentException("Illegal Argument: Illegal operators");
		}
		String[] parts = in.split("=", 2);
		name = parts[0];
		if (name.contains("*") || name.contains("/") || name.contains("+") || name.contains("-")) {
			throw new IllegalArgumentException("Illegal Argument: Variable contains operators");
		}

		/*
		 * Splits up each term in defintion.
		 */
		method = parts[1];
		if (method.contains("=")) {
			throw new IllegalArgumentException("Illegal Argument: Definition contains too many \"=\"");
		}
		terms = method.split("((?<=[+-/*^])|(?=[+-/*^]))");

		/*
		 * Creates list of variables used in definition.
		 */
		int counter = 0;
		for (String s : terms) {
			if (!MathUtil.isNumeric(s)) {
				counter++;
			}
		}
		List<Var> tempList = new ArrayList<Var>();
		for (int i = 0; i < terms.length; i += 2) {
			if (!MathUtil.isNumeric(terms[i])) {
				tempList.add(new Var(new String(terms[i]), "?", "", false));
			}
		}
		vars = new Var[tempList.size()];
		for (int i = 0; i < tempList.size(); i++) {
			vars[i] = tempList.get(i);
		}
	}

	public void clearRef() {
		int counter = 0;
		for (int i = 0; i < terms.length; i += 2) {
			if (!MathUtil.isNumeric(terms[i])) {
				vars[counter] = new Var(new String(terms[i]), "?", "", false);
				counter++;
			}
		}
	}

	/**
	 * Finds the variable.
	 */
	public void resolve() {

		String holder = "0";

		for (String s : terms) {
			for (Var v : vars) {

				if (v.name.equals(s)) {
					if (v.contents.equals("?")) {
						return;
					}
					s = new String(v.contents);
				}
				if (!MathUtil.isNumeric(s) && !s.matches("[+-/*^]")) {
					return;
				}
			}

		}
		holder = MathUtil.evaluate(terms[0], terms[1], terms[2]);
		for (int i = 3; i < terms.length; i += 2) {
			holder = MathUtil.evaluate(holder, terms[i], terms[i + 1]);
			System.out.println(holder);
		}
		vars[0].contents = new String(holder);
	}
}
