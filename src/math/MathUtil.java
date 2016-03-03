package math;

import java.awt.Graphics2D;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

public class MathUtil {
	/**
	 * Checks if a string is numeric. Blank strings are not numeric.
	 * 
	 * @param str
	 *            The string to be tested.
	 * @return boolean true for numerical strings false if otherwire.
	 */
	public static boolean isNumeric(String str) {
		if (str.isEmpty()) {
			return false;
		}
		if (str.equals("-")) {
			return false;
		}
		DecimalFormatSymbols currentLocaleSymbols = DecimalFormatSymbols
				.getInstance();
		char localeMinusSign = currentLocaleSymbols.getMinusSign();

		if (!Character.isDigit(str.charAt(0))
				&& str.charAt(0) != localeMinusSign)
			return false;

		boolean isDecimalSeparatorFound = false;
		char localeDecimalSeparator = currentLocaleSymbols
				.getDecimalSeparator();

		for (char c : str.substring(1).toCharArray()) {
			if (!Character.isDigit(c)) {
				if (c == localeDecimalSeparator && !isDecimalSeparatorFound) {
					isDecimalSeparatorFound = true;
					continue;
				}
				return false;
			}
		}
		return true;
	}

	/**
	 * @param theta
	 *            Sector size in radians, theta > 6.28(2 pi) for full circle
	 *            ,theta < 0 returns null.
	 * @param r
	 *            Radius
	 * @param polygon
	 *            true if polygon,false if polyline
	 * @return <b>Shape</b> Polygonal representation of a circle sector.
	 */

	public static Shape genCirc(double theta, double r, boolean polygon) {
		ArrayList<MyPoint> list = new ArrayList<>();
		if (theta < 0)
			return null;
		double limit = 2 * Math.PI;

		list.add(new MyPoint(r, 0d));
		for (float i = 0; i < theta; i += 0.05) {
			if (i > limit)
				break;
			list.add(new MyPoint(r * Math.cos(i), r * Math.sin(i)));
		}
		list.add(new MyPoint(r * Math.cos(theta), r * Math.sin(theta)));

		if (theta < limit && polygon)
			list.add(new MyPoint(0, 0));
		System.out.println(list.size());
		return new Shape(list);
	}

	/**
	 * Test if point is on line segment within a certain degree of tolerance; .=
	 * 
	 * @param p
	 *            Point to test.
	 * @param p1
	 *            Start of line segment.
	 * @param p2
	 *            End of line segment.
	 * @param tolerance
	 *            The amount of tolerance.
	 * 
	 * @return Boolean true for point is on line, false if is not.
	 * 
	 * @see <a href=
	 *      "https://en.wikipedia.org/wiki/Distance_from_a_point_to_a_line">
	 *      https://en.wikipedia.org/wiki/Distance_from_a_point_to_a_line</a>
	 */
	public static boolean PointInLineSegment(MyPoint p, MyPoint p1, MyPoint p2,
			float tolerance) {
		if (tolerance == 0)
			tolerance = 0.01f;
		double a = Math.abs((p2.x - p1.x) * (p1.y - p.y) - (p1.x - p.x)
				* (p2.y - p1.y));
		double b = Math.sqrt(Math.pow(p2.x - p1.x, 2)
				+ Math.pow(p2.y - p1.y, 2));
		if (a / b < tolerance)
			return true;

		return false;
	}

	/**
	 * Evaluates an expression. Valid operators or +,-*,/,^. Functions as
	 * operands will be evaluated. Eg. 5 + 10 -> evaluate("5","+","10");
	 * 
	 * @param operandA
	 *            First operand
	 * @param operator
	 *            + - * / ^
	 * @param operandB
	 *            Second operand
	 * @return String representation of answer.
	 * @throws ArithmeticException
	 *             Dividing by zero
	 * @throws IllegalArgumentException
	 *             When missing operator
	 */
	public static String evaluate(String operandA, String operator,
			String operandB) throws ArithmeticException,
			IllegalArgumentException {

		/*
		 * Resolves functions first. Functions should have numeric parameters.
		 */

		// TODO: Code does not work.(array out of bounds) at string b +
		if (!isNumeric(operandA)) {
			String a = operandA.substring(0, operandA.indexOf("("));
			System.out.println(operandA);
			String b = operandA.substring(operandA.indexOf("(") + 1,
					operandA.length() - 1);
			operandA = solveFunc(a, b);
		}
		if (!isNumeric(operandB)) {
			String a = operandB.substring(0, operandB.indexOf("("));
			String b = operandB.substring(operandB.indexOf("(") + 1,
					operandB.length() - 1);
			operandB = solveFunc(a, b);
		}

		if (operator.equals("+")) {
			return ""
					+ (Double.parseDouble(operandA) + Double
							.parseDouble(operandB));
		}
		if (operator.equals("*")) {
			return ""
					+ (Double.parseDouble(operandA) * Double
							.parseDouble(operandB));
		}
		if (operator.equals("-")) {
			return ""
					+ (Double.parseDouble(operandA) - Double
							.parseDouble(operandB));
		}
		if (operator.equals("/")) {
			return ""
					+ (Double.parseDouble(operandA) / Double
							.parseDouble(operandB));
		}
		if (operator.equals("^")) {
			return ""
					+ (Math.pow(Double.parseDouble(operandA),
							Double.parseDouble(operandB)));
		}
		System.out.println(operator);
		throw new IllegalArgumentException("Missing operator");
	}

	/**
	 * Solves a function.
	 * 
	 * @param func
	 *            The function name. Available names: sin,cos,tan,abs
	 * @param x
	 *            The function's parameter.
	 * @return String representation of answer.
	 * @throws IllegalArgumentException
	 */
	private static String solveFunc(String func, String x)
			throws IllegalArgumentException {
		if (func.equals("sin")) {
			return "" + Math.sin(Double.parseDouble(x));
		}
		if (func.equals("cos")) {
			return "" + Math.cos(Double.parseDouble(x));
		}
		if (func.equals("tan")) {
			return "" + Math.tan(Double.parseDouble(x));
		}
		if (func.equals("abs")) {
			return "" + Math.abs(Double.parseDouble(x));
		}
		throw new IllegalArgumentException("Argument is not a valid function");
	}

	/**
	 * Draws an arrow.
	 * 
	 * @param g2d
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param size
	 */
	public static void drawArrow(Graphics2D g2d, int x1, int y1, int x2,
			int y2, int size) {
		size = size / 2;
		g2d.drawLine(x1, y1, x2, y2);
		double theta = 0;
		if (x2 == x1 && y2 == y1) {
			return;
		}
		if (x2 == x1) {
			if (y2 > y1) {
				theta = Math.PI * 1.5;
			} else if (y2 < y1) {
				theta = Math.PI * 0.5;
			}
		} else {
			if (y1 == y2) {
				if (x2 > x1) {
					theta = 0;
				} else if (x2 < x1) {
					theta = Math.PI;
				}
			} else {
				theta = Math.atan((double) (y1 - y2) / (double) (x2 - x1));

				if (y2 - y1 < 0 && x2 - x1 < 0) {
					theta = Math.PI + theta;
				} else if (x2 - x1 < 0) {
					theta = theta + Math.PI;
				}
			}
		}

		g2d.drawLine(x2, y2, (int) (x2 - size * Math.cos(Math.PI / 6 - theta)),
				(int) (y2 - size * Math.sin(Math.PI / 6 - theta)));
		g2d.drawLine(x2, y2,
				(int) (x2 - size * Math.cos(-Math.PI / 6 - theta)),
				(int) (y2 - size * Math.sin(-Math.PI / 6 - theta)));

	}

	public static String round(String contents) {
		if (contents.contains(".")) {
			if (contents.length() > contents.indexOf(".") + 4) {
				contents = contents.substring(0, contents.indexOf(".") + 4);
			}
		}

		return contents;
	}

	public static boolean isFunc(String in) {
		if (in.matches("(cos)|(sin)|(tan)|(abs)")) {
			return true;
		} else
			return false;
	}
}
