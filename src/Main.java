import mainGui.Frame;
import mainGui.StartScreenDialog;

/**
 * COMP4: The computing practical project
 * 
 * Main class, contains Main method.
 * 
 * @author Benjamin Hodges(4143) Centre 51337
 *
 * @version 1.0
 */
public class Main {

	/**
	 * MAIN METHOD
	 * 
	 * @param Args
	 */
	public static void main(String[] Args) {
		StartScreenDialog popup = new StartScreenDialog();
		Frame window = new Frame(popup.getTopic());
		System.out.println(window.topic + " launched");
	}

}
