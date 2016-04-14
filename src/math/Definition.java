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

		if (in.contains("%") || in.contains("|") || in.contains("⋅") || in.contains("×")
				|| in.contains("±") || in.contains("∓") || in.contains("÷") || in.contains("√")) {
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
			throw new IllegalArgumentException(
					"Illegal Argument: Definition contains too many \"=\"");
		}

		terms = method.split("((?<=[+*^/-])|(?=[+*^/-]))");

		/*
		 * Creates list of variables used in definition.
		 */
		List<Var> tempList = new ArrayList<Var>();
		tempList.add(new Var(name, "?", ""));
		for (int i = 0; i < terms.length; i += 2) {
			if (!MathUtil.isNumeric(terms[i])) {
				if (terms[i].contains("(")) {
					String contents = terms[i].substring(terms[i].indexOf("(") + 1,
							terms[i].length() - 1);
					if (!MathUtil.isNumeric(contents)) {
						tempList.add(new Var(new String(contents), "?", ""));
					}

				} else {
					tempList.add(new Var(new String(terms[i]), "?", ""));
				}
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
				vars[counter] = new Var(new String(terms[i]), "?", "");
				counter++;
			}
		}
	}

	/**
	 * Finds the variable.
	 */
	public void resolve() {

		String holder = "0";
		if (!vars[0].isKnown()) {
			return;
		}
		for (int i = 0; i < terms.length; i++) {
			if (!terms[i].contains("(")) {
				for (Var v : vars) {
					if (v.name.equals(terms[i])) {
						if (v.isKnown()) {
							return;
						}
						terms[i] = new String(v.contents);
					}
					if (terms[i].matches("[+-/*^]")) {
						continue;
					}
				}
			} else {
				for (Var v : vars) {
					if (v.name.equals(terms[i].substring(terms[i].indexOf("(") + 1,
							terms[i].length() - 1))) {
						if (v.isKnown()) {
							return;
						}
						terms[i] = terms[i].substring(0, terms[i].indexOf("(") + 1)
								+ new String(v.contents) + ")";
					}
				}
			}
		}
		if (terms.length > 1) {
			holder = MathUtil.evaluate(terms[0], terms[1], terms[2]);
			for (int i = 3; i < terms.length; i += 2) {
				holder = MathUtil.evaluate(holder, terms[i], terms[i + 1]);
				System.out.println(holder);
			}
		} else {
			holder = terms[0];
		}
		vars[0].contents = new String(holder);
	}
}
