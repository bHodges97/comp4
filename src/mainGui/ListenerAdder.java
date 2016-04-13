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
	 *            The type of verification: <br>
	 *            -2: No verification -1:<br>
	 *            -1: Error if not a number <br>
	 *            0: Error if less than or equal to zero <br>
	 *            1: Error if less than zero <br>
	 *            2: Error if equal to zero <br>
	 *            3: Error if greater than 0 <br>
	 *            4: Warning if greater than 2 pi<br>
	 *            5: Special case for e
	 * 
	 */
	public void addListener(final JTextField textField, final Var var1, final int type, final Var var2) {
		textField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {
				if (textField.getText().equals("?")) {
					textField.setText("");
				}
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				addVerification(textField, var1, type, var2);
			}
		});

		textField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addVerification(textField, var1, type, var2);
			}
		});
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
	 *            The type of verification: <br>
	 *            -2: No verification -1:<br>
	 *            -1: Error if not a number <br>
	 *            0: Error if less than or equal to zero <br>
	 *            1: Error if less than zero <br>
	 *            2: Error if equal to zero <br>
	 *            3: Error if greater than 0 <br>
	 *            4: Warning if greater than 2 pi<br>
	 *            5: Special case for e
	 * 
	 */
	public void addVerification(JTextField textField, Var var1, int type, Var var2) {

		Var v = (var2 != null && !frame.colA) ? var2 : var1;

		if (textField.getText().equals("?")) {
			v.setContents(textField.getText(), false);
			return;
		}
		if (textField.getText().length() > 10) {
			showErrorMsg("Input is too long!");
			return;
		}
		if (type >= -1 && !MathUtil.isNumeric(textField.getText())) {
			showErrorMsg("Not a number!");
			return;
		}
		if (type == 0 && Double.parseDouble(textField.getText()) < 0) {
			showErrorMsg("Must be greater than or equal to 0.");
			return;
		}
		if (type == 1 && Double.parseDouble(textField.getText()) <= 0) {
			showErrorMsg("Must be greater than 0.");
			return;
		}
		if (type == 2 && Double.parseDouble(textField.getText()) == 0) {
			showErrorMsg("Must not equal 0.");
			return;
		}
		if (type == 3 && Double.parseDouble(textField.getText()) > 0) {
			showErrorMsg("Must be less than 0.");
			return;
		}
		if (type == 4 && Math.abs(Double.parseDouble(textField.getText())) > 6.28) {
			showErrorMsg("Careful this value is greater than 2 PI");
		}
		if (type == 5 && Double.parseDouble(textField.getText()) > 1 || Double.parseDouble(textField.getText()) < 0) {
			showErrorMsg("Must follow: 0 <= e <= 1");
			return;
		}
		v.setContents(new String(textField.getText()), true);
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
