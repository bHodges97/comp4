import mainGui.Frame;
import mainGui.StartScreenDialog;

public class Main {

	/**
	 * MAIN METHOD
	 * 
	 * @param Args
	 */
	public static void main(String[] Args) {
		StartScreenDialog popup = new StartScreenDialog();
		Frame window = new Frame(popup.getTopic());
	}

}
