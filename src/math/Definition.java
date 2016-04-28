package math;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import mainGui.Frame;

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
		terms = method.split("((?<=[+*^/-])|(?=[+*^/-]))");

		/*
		 * Creates list of variables used in definition.
		 */
		List<Var> tempList = new ArrayList<Var>();
		tempList.add(new Var(name, "?", ""));
		for (int i = 0; i < terms.length; i += 2) {
			if (!MathUtil.isNumeric(terms[i])) {
				if (terms[i].contains("(")) {
					String contents = terms[i].substring(terms[i].indexOf("(") + 1, terms[i].length() - 1);
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

		for (int i = 0; i < terms.length; i++) {
			if (!terms[i].contains("(")) {
				for (Var v : vars) {
					if (terms[i].matches("[+-/*^]")) {
						continue;
					}
					if (v.name.equals(terms[i])) {
						if (v.isUnknown()) {
							return;
						}
						terms[i] = new String(v.contents);
					}
				}
			} else {
				for (Var v : vars) {
					if (v.name.equals(terms[i].substring(terms[i].indexOf("(") + 1, terms[i].length() - 1))) {
						if (v.isUnknown()) {
							return;
						}
						terms[i] = terms[i].substring(0, terms[i].indexOf("(") + 1) + new String(v.contents) + ")";
					}
				}
			}
		}
		if (terms.length > 1) {
			holder = MathUtil.evaluate(terms[0], terms[1], terms[2]);
			for (int i = 3; i < terms.length; i += 2) {
				holder = MathUtil.evaluate(holder, terms[i], terms[i + 1]);
			}
		} else {
			holder = terms[0];
		}
		if (vars[0].given && !MathUtil.isEqual(vars[0].contents, holder)) {
			JOptionPane.showMessageDialog(null, "The variables do not conform!");
		}
		vars[0].contents = new String(holder);
	}

	/**
	 * Create list of Definitions based on topic
	 * 
	 * @param topic
	 * @return A list of definitions
	 */
	public static Definition[] createDefs(int topic) {
		Definition[] defs = null;
		if (topic == Frame.PROJECTILES) {
			defs = new Definition[11];
			defs[0] = new Definition("d=u*cos(a)");
			defs[1] = new Definition("e=u*sin(a)");
			defs[2] = new Definition("d=b");
			defs[3] = new Definition("b=d");
			defs[4] = new Definition("x=b*t");
			defs[5] = new Definition("e=9.8*t+c");
			defs[6] = new Definition("y=c+e/2*t+h");
			defs[7] = new Definition("y=t^2*0.5*-9.8+(e*t)");
			defs[8] = new Definition("v=(b^2)+(c^2)^1/2");
			defs[9] = new Definition("u=(d^2)+(e^2)^1/2");
			defs[10] = new Definition("v=(u^2)+0.5*a*(y-h)^1/2");
		} else if (topic == Frame.CIRCLES) {
			defs = new Definition[10];
			defs[0] = new Definition("v=r*w");
			defs[1] = new Definition("w=v/r");
			defs[2] = new Definition("f=m*a");
			defs[3] = new Definition("a=v^2/r");
			defs[4] = new Definition("a=w^2*r");
			defs[5] = new Definition("x=t*w+u");
			defs[6] = new Definition("t=x-u/w");
			defs[7] = new Definition("r=v/w");
			defs[8] = new Definition("r=v^2/a");
			defs[9] = new Definition("m=f/a");
		}
		return defs;
	}
}
