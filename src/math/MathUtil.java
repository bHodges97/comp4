package math;

import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

public class MathUtil {
	/**
	 * Checks if a string is numeric. Blank strings are not numeric.
	 * 
	 * @param str The string to be tested.
	 * @return boolean true for numerical strings false if otherwire.
	 */
	public static boolean isNumeric(String str){
		if(str.isEmpty())return false;
		DecimalFormatSymbols currentLocaleSymbols = DecimalFormatSymbols.getInstance();
		char localeMinusSign = currentLocaleSymbols.getMinusSign();

		if ( !Character.isDigit( str.charAt( 0 ) ) && str.charAt( 0 ) != localeMinusSign ) return false;

		boolean isDecimalSeparatorFound = false;
		char localeDecimalSeparator = currentLocaleSymbols.getDecimalSeparator();

		for ( char c : str.substring( 1 ).toCharArray() )
		{
			if ( !Character.isDigit( c ) )
			{
				if ( c == localeDecimalSeparator && !isDecimalSeparatorFound )
				{
					isDecimalSeparatorFound = true;
					continue;
				}
				return false;
			}
		}
		return true;
	}
	/**
	 * @param theta Sector size in radians, theta > 6.28(2 pi) for full circle ,theta < 0 returns null.
	 * @param r Radius
	 * @param polygon true if polygon,false if polyline
	 * @return <b>Shape</b> Polygonal representation of a circle sector.
	 */

	public  static Shape genCirc(double theta,double r,boolean polygon){
		ArrayList<MyPoint> list = new ArrayList<>();
		if(theta < 0) return null;
		double limit = 2*Math.PI;

		list.add(new MyPoint(r,0d));
		for(float i = 0 ;i < theta;i+=0.05){
			if(i > limit)break;
			list.add(new MyPoint(r*Math.cos(i),r*Math.sin(i)));
		}
		list.add(new MyPoint(r*Math.cos(theta),r*Math.sin(theta)));

		if(theta < limit && polygon)list.add(new MyPoint(0,0));
		System.out.println(list.size());
		return new Shape(list);
	}
	/**
	 * Test if point is on line segement within a certain degree of tolerance;
	 * .=
	 * @param p Point to test.
	 * @param p1 Start of line segment.
	 * @param p2 End of line segement.
	 * @param tolerance The amount of tolerance.
	 * 
	 * @return Boolean true for point is on line, false if is not.
	 * 
	 * @see <a href="https://en.wikipedia.org/wiki/Distance_from_a_point_to_a_line">https://en.wikipedia.org/wiki/Distance_from_a_point_to_a_line</a>
	 */
	public static boolean PointInLineSegment(MyPoint p,MyPoint p1,MyPoint p2,float tolerance){
		if(tolerance == 0)tolerance = 0.01f;
		double a = Math.abs((p2.x-p1.x)*(p1.y-p.y)-(p1.x-p.x)*(p2.y-p1.y));
		double b = Math.sqrt(Math.pow(p2.x-p1.x,2)+Math.pow(p2.y-p1.y,2));
		if(a/b<tolerance)return true;

		return false;
	}
}
