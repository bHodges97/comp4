package mainGui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
	private static Color red = new Color(255, 204, 204);

	private boolean showPopUp = false;

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
	public void addListener(final JTextField textField, final Var var1, final Var var2, final int type) {
		textField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {

			}

			@Override
			public void focusLost(FocusEvent arg0) {
				if (textField.getText().equals("?")) {
					textField.setText("");
				} else {
					showPopUp = true;
					if (!verify(textField.getText(), var1, var2, type)) {
						textField.setBackground(red);
					} else {
						textField.setBackground(Color.white);
					}
				}
			}
		});

		textField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showPopUp = true;
				if (!verify(textField.getText(), var1, var2, type)) {
					textField.setBackground(red);
				} else {
					textField.setBackground(Color.white);
				}
			}
		});
		textField.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				showPopUp = false;
				if (!verify(textField.getText(), var1, var2, type)) {
					textField.setBackground(red);
				} else {
					textField.setBackground(Color.white);
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
	}

	public void addListener(final JTextField field, final boolean isTextA) {
		field.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				showPopUp = true;
				if (!verify(field, isTextA)) {
					field.setBackground(red);
				} else {
					field.setBackground(Color.white);
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
			}
		});
		field.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showPopUp = true;
				if (!verify(field, isTextA)) {
					field.setBackground(red);
				} else {
					field.setBackground(Color.white);
				}
			}
		});
		field.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				showPopUp = false;
				if (!verify(field, isTextA)) {
					field.setBackground(red);
				} else {
					field.setBackground(Color.white);
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
	}

	private boolean verify(final JTextField field, boolean isTextA) {
		if (!MathUtil.isNumeric(field.getText())) {
			showError("Not a number");
			return false;
		}
		if (isTextA) {
			frame.circTextA.set(frame.circF.indexOf(field), field.getText());
		} else {
			frame.circTextB.set(frame.circT.indexOf(field), field.getText());
		}
		frame.updateFields();
		return true;
	}

	/**
	 * Verifies the text
	 * 
	 * @param text
	 *            The text to verify
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
	private boolean verify(String text, Var var1, Var var2, int type) {

		// select one of the two variables
		Var v = (var2 == null && frame.colA) ? var1 : var2;

		if (text.equals("?")) {
			v.setContents(text, false);
			return false;
		}
		if (text.length() > 10) {
			showError("Input is too long!");
			return false;
		}
		if (type >= ISNUMBER && !MathUtil.isNumeric(text)) {
			showError("Not a number!");
			return false;
		}
		if (type == GREATER_THAN_ZERO && Double.parseDouble(text) < 0) {
			showError("Must be greater than or equal to 0.");
			return false;
		}
		if (type == GREATER_OR_EQUAL_TO_ZERO && Double.parseDouble(text) <= 0) {
			showError("Must be greater than 0.");
			return false;
		}
		if (type == NONE_ZERO && Double.parseDouble(text) == 0) {
			showError("Must not equal 0.");
			return false;
		}
		if (type == LESS_OR_EQUAL_TO_ZERO && Double.parseDouble(text) > 0) {
			showError("Must be less than 0.");
			return false;
		}
		if (type == ANGLE_VERIF && Math.abs(Double.parseDouble(text)) > 6.28) {
			JOptionPane.showMessageDialog(null, "This value is greater than 2 PI");
		}
		if (type == E_VERIF && (Double.parseDouble(text) > 1 || Double.parseDouble(text) < 0)) {
			showError("Must follow: 0 <= e <= 1");
			return false;
		}
		v.setContents(new String(text), true);
		return true;
	}

	/**
	 * Creates error message
	 * 
	 * @param msg
	 *            The message to show
	 */
	private void showError(String msg) {
		if (showPopUp) {
			JOptionPane.showMessageDialog(frame, msg, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
