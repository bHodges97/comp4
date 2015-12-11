package math;

import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

public class MathUtil {
	/**
	 * Checks if a string is numeric. Blank strings are not numeric.
	 * 
	 * @param str The string to be tested.
	 * @return boolean true for numerical strings false is otherwire.
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
}
