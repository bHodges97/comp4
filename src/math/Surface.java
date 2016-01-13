package math;

public class Surface {
	MyVector direction,offset;
	float mu;
	
	/**
	 * Constructs a new surface
	 *  
	 * @param direction The gradient of the surface
	 * @param mu The Coefficient of Friction.
	 * @param offset Vector representing the offset from 0,0
	 */
	public Surface(MyVector direction,float mu,MyVector offset){
		this.direction = direction;
		this.mu = mu;
		this.offset = offset;
	}
}
