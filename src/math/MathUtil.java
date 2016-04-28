package math;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.atan;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.tan;

import java.awt.Graphics2D;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

/**
 * The MathUtil class contatins math methods not included in the java.lang.Math
 * class;
 * 
 */
public class MathUtil {
	/**
	 * Checks if a string is numeric. Blank strings are not numeric.
	 * 
	 * @param str
	 *            The string to be tested.
	 * @return boolean true for numerical strings false if otherwire.
	 */
	public static boolean isNumeric(String str) {
		if (str == null) {
			return false;
		}
		if (str.isEmpty()) {
			return false;
		}
		if (str.equals("-")) {
			return false;
		}
		DecimalFormatSymbols currentLocaleSymbols = DecimalFormatSymbols.getInstance();
		char localeMinusSign = currentLocaleSymbols.getMinusSign();

		if (!Character.isDigit(str.charAt(0)) && str.charAt(0) != localeMinusSign)
			return false;

		boolean isDecimalSeparatorFound = false;
		char localeDecimalSeparator = currentLocaleSymbols.getDecimalSeparator();

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
		double limit = 2 * PI;

		list.add(new MyPoint(r, 0d));
		for (float i = 0; i < theta; i += 0.05) {
			if (i > limit) {
				break;
			}
			list.add(new MyPoint(r * cos(i), r * sin(i)));
		}
		list.add(new MyPoint(r * cos(theta), r * sin(theta)));

		if (theta < limit && polygon)
			list.add(new MyPoint(0, 0));

		if (polygon) {
			double y = (2 * r * sin(theta / 2) / (3 * theta / 2)) * sin(theta / 2);
			double x = (2 * r * sin(theta / 2) / (3 * theta / 2)) * cos(theta / 2);
			for (MyPoint p : list) {
				p.x = p.x - x;
				p.y = p.y - y;
			}
		} else {
			double y = (r * sin(theta / 2) / (theta / 2)) * sin(theta / 2);
			double x = (r * sin(theta / 2) / (theta / 2)) * cos(theta / 2);
			for (MyPoint p : list) {
				p.x = p.x - x;
				p.y = p.y - y;
			}
		}

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
	public static boolean PointInLineSegment(MyPoint p, MyPoint p1, MyPoint p2, double tolerance) {
		if (tolerance == 0)
			tolerance = 0.01f;
		double a = abs((p2.x - p1.x) * (p1.y - p.y) - (p1.x - p.x) * (p2.y - p1.y));
		double b = sqrt(pow(p2.x - p1.x, 2) + pow(p2.y - p1.y, 2));
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
	public static String evaluate(String operandA, String operator, String operandB)
			throws ArithmeticException, IllegalArgumentException {

		/*
		 * Resolves functions first. Functions should have numeric parameters.
		 */
		try {
			if (!isNumeric(operandA)) {
				String a = operandA.substring(0, operandA.indexOf("("));
				String b = operandA.substring(operandA.indexOf("(") + 1, operandA.length() - 1);
				operandA = solveFunc(a, b);
			}
			if (operandB.contains("(")) {
				String a = operandB.substring(0, operandB.indexOf("("));
				String b = operandB.substring(operandB.indexOf("(") + 1, operandB.length() - 1);
				operandB = solveFunc(a, b);
			}

			if (operator.equals("+")) {
				return "" + (Double.parseDouble(operandA) + Double.parseDouble(operandB));
			}
			if (operator.equals("*")) {
				return "" + (Double.parseDouble(operandA) * Double.parseDouble(operandB));
			}
			if (operator.equals("-")) {
				return "" + (Double.parseDouble(operandA) - Double.parseDouble(operandB));
			}
			if (operator.equals("/")) {
				return "" + (Double.parseDouble(operandA) / Double.parseDouble(operandB));
			}
			if (operator.equals("^")) {
				return "" + (pow(Double.parseDouble(operandA), Double.parseDouble(operandB)));
			}
			throw new IllegalArgumentException("Missing operator");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);// Stop for debug
		}
		return "";
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
	private static String solveFunc(String func, String x) throws IllegalArgumentException {
		if (func.equals("sin")) {
			return "" + sin(Double.parseDouble(x));
		}
		if (func.equals("cos")) {
			return "" + cos(Double.parseDouble(x));
		}
		if (func.equals("tan")) {
			return "" + tan(Double.parseDouble(x));
		}
		if (func.equals("abs")) {
			return "" + abs(Double.parseDouble(x));
		}
		throw new IllegalArgumentException("\"" + func + " \" is not a valid function.");
	}

	/**
	 * Draws an arrow.
	 * 
	 * @param g2d
	 *            The graphics object to draw the arrow to
	 * @param x1
	 *            The x coord of first point
	 * @param y1
	 *            The y coord of first point
	 * @param x2
	 *            The x coord of second point
	 * @param y2
	 *            The y coord of second point
	 * @param size
	 *            The size of the tip of the arrow
	 */
	public static void drawArrow(Graphics2D g2d, int x1, int y1, int x2, int y2, int size) {
		size = size / 2;
		g2d.drawLine(x1, y1, x2, y2);
		double theta = 0;
		if (x2 == x1 && y2 == y1) {
			return;
		}
		if (x2 == x1) {
			if (y2 > y1) {
				theta = PI * 1.5;
			} else if (y2 < y1) {
				theta = PI * 0.5;
			}
		} else {
			if (y1 == y2) {
				if (x2 > x1) {
					theta = 0;
				} else if (x2 < x1) {
					theta = PI;
				}
			} else {
				theta = atan((double) (y1 - y2) / (double) (x2 - x1));

				if (y2 - y1 < 0 && x2 - x1 < 0) {
					theta = PI + theta;
				} else if (x2 - x1 < 0) {
					theta = theta + PI;
				}
			}
		}

		g2d.drawLine(x2, y2, (int) (x2 - size * cos(PI / 6 - theta)), (int) (y2 - size * sin(PI / 6 - theta)));
		g2d.drawLine(x2, y2, (int) (x2 - size * cos(-PI / 6 - theta)), (int) (y2 - size * sin(-PI / 6 - theta)));

	}

	/**
	 * Round a numerical string to 3 decimal places
	 * 
	 * @param contents
	 *            The string to round
	 * @param precision
	 *            The decimal places to round to
	 * @return The rounded string
	 */
	public static String round(String contents, int precision) {
		if (contents.contains(".")) {
			if (contents.length() > contents.indexOf(".") + precision) {
				contents = contents.substring(0, contents.indexOf(".") + precision);
			}
		}

		return contents;
	}

	/**
	 * Checks if the parameters are approximately equal in numerical values;
	 * 
	 * @param a
	 *            The first string to compare
	 * @param b
	 *            The second string to compare
	 * @return true if parameters are apprixmately equal, false if otherwise
	 */
	public static boolean isEqual(String a, String b) {
		double num1 = Double.parseDouble(a);
		double num2 = Double.parseDouble(b);
		return abs(num1 - num2) < 0.001 ? true : false;
	}

	/**
	 * Rotate a point with the given parameters
	 * 
	 * @param point
	 *            The point to return;
	 * @param angle
	 *            The angle to rotate by;
	 */
	public static void rotate(MyPoint point, Double angle) {
		double tempx = point.x * Math.cos(angle) - point.y * Math.sin(angle);
		point.y = point.x * Math.sin(angle) + point.y * Math.cos(angle);
		point.x = tempx;
	}
}
