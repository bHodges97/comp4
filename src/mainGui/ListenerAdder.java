package mainGui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import math.MathUtil;
import math.Var;

public class ListenerAdder {
	Frame frame;

	public static int NO_VERIF = 0;
	public static int ISNUMBER = 1;
	public static int GREATER_THAN_ZERO = 2;
	public static int GREATER_OR_EQUAL_TO_ZERO = 3;
	public static int NONE_ZERO = 4;
	public static int LESS_OR_EQUAL_TO_ZERO = 5;
	public static int ANGLE_VERIF = 6;
	public static int E_VERIF = 7;

	public ListenerAdder(Frame frame) {
		this.frame = frame;
	}

	/**
	 * Adds a focus listener and action listener to a component.
	 * 
	 * @param textField
	 *            Text field to add listeners to.
	 * @param var1
	 *            Variable text field is associated with.
	 * @param var2
	 *            Second variable text field is associated with if applicable.
	 * @param type
	 *            The type of verification, one of following: NO_VERIF,
	 *            ISNUMBER_VERIF, GREATER_THAN_ZERO, GREATER_OR_EQUAL_TO_ZERO,
	 *            NOT_ZERO, LESS_OR_EQUAL_TO_ZERO, ANGLE_VERIF, E_VERIF,
	 * 
	 */
	public void addListener(final JTextField textField, final Var var1, final Var var2,
			final int type) {
		textField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {
				if (textField.getText().equals("?")) {
					textField.setText("");
				}
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				verify(textField, var1, var2, type);
			}
		});

		textField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				verify(textField, var1, var2, type);
			}
		});
	}

	/**
	 * Verifies the content in the textfield
	 * 
	 * @param textField
	 *            Text field to add listeners to.
	 * @param var1
	 *            Variable text field is associated with.
	 * @param var2
	 *            Second variable text field is associated with if applicable.
	 * @param type
	 *            The type of verification, one of following: NO_VERIF,
	 *            ISNUMBER_VERIF, GREATER_THAN_ZERO, GREATER_OR_EQUAL_TO_ZERO,
	 *            NOT_ZERO, LESS_OR_EQUAL_TO_ZERO, ANGLE_VERIF, E_VERIF,
	 * 
	 * 
	 */
	private void verify(String text, Var var1, Var var2, int type) {

		Var v = (var2 != null && !frame.colA) ? var2 : var1;

		if (text.equals("?")) {
			v.setContents(text, false);
			return;
		}
		if (text.length() > 10) {
			showErrorMsg("Input is too long!");
			return;
		}
		if (type >= ISNUMBER && !MathUtil.isNumeric(text)) {
			showErrorMsg("Not a number!");
			return;
		}
		if (type == GREATER_THAN_ZERO && Double.parseDouble(text) < 0) {
			showErrorMsg("Must be greater than or equal to 0.");
			return;
		}
		if (type == GREATER_OR_EQUAL_TO_ZERO && Double.parseDouble(text) <= 0) {
			showErrorMsg("Must be greater than 0.");
			return;
		}
		if (type == NONE_ZERO && Double.parseDouble(text) == 0) {
			showErrorMsg("Must not equal 0.");
			return;
		}
		if (type == LESS_OR_EQUAL_TO_ZERO && Double.parseDouble(text) > 0) {
			showErrorMsg("Must be less than 0.");
			return;
		}
		if (type == ANGLE_VERIF && Math.abs(Double.parseDouble(text)) > 6.28) {
			showErrorMsg("Careful this value is greater than 2 PI");
		}
		if (type == E_VERIF && Double.parseDouble(text) > 1 || Double.parseDouble(text) < 0) {
			showErrorMsg("Must follow: 0 <= e <= 1");
			return;
		}
		v.setContents(new String(text), true);
	}

	/**
	 * Creates error message
	 * 
	 * @param msg
	 *            The message to show
	 */
	private void showErrorMsg(String msg) {
		JOptionPane.showMessageDialog(frame, msg, "Error", JOptionPane.ERROR_MESSAGE);
	}
}
