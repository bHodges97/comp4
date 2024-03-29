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
	 * Add listeners to the window based on topic.
	 * 
	 * @param topic
	 *            The topic to add listeners for
	 */
	public void addListeners(int topic) {
		if (topic == Frame.PROJECTILES) {
			JTextField[] projText = frame.projField;
			Var[] projVars = frame.projVars;
			addListener(projText[0], projVars[0], null, ListenerAdder.ANGLE_VERIF);
			addListener(projText[1], projVars[1], null, ListenerAdder.ISNUMBER);
			addListener(projText[2], projVars[2], null, ListenerAdder.ISNUMBER);
			addListener(projText[3], projVars[3], null, ListenerAdder.ISNUMBER);
			addListener(projText[4], projVars[4], null, ListenerAdder.GREATER_THAN_ZERO);
			addListener(projText[5], projVars[5], null, ListenerAdder.GREATER_THAN_ZERO);
			addListener(projText[6], projVars[6], null, ListenerAdder.GREATER_THAN_ZERO);
			addListener(projText[7], projVars[7], null, ListenerAdder.ISNUMBER);
			addListener(projText[8], projVars[8], null, ListenerAdder.GREATER_THAN_ZERO);
			addListener(projText[9], projVars[9], null, ListenerAdder.ISNUMBER);
			addListener(projText[10], projVars[10], null, ListenerAdder.GREATER_THAN_ZERO);
			addListener(projText[12], projVars[12], null, ListenerAdder.GREATER_THAN_ZERO);
			addListener(projText[11], projVars[11], null, ListenerAdder.NO_VERIF);
		} else if (topic == Frame.CIRCLES) {
			JTextField[] circText = frame.circField;
			Var[] circVars = frame.circVars;
			addListener(circText[0], circVars[0], null, ListenerAdder.NONE_ZERO);
			addListener(circText[1], circVars[1], null, ListenerAdder.GREATER_OR_EQUAL_TO_ZERO);
			addListener(circText[2], circVars[2], null, ListenerAdder.ANGLE_VERIF);
			addListener(circText[3], circVars[3], null, ListenerAdder.ANGLE_VERIF);
			addListener(circText[4], circVars[4], null, ListenerAdder.ISNUMBER);
			addListener(circText[5], circVars[5], null, ListenerAdder.GREATER_OR_EQUAL_TO_ZERO);
			addListener(circText[6], circVars[6], null, ListenerAdder.GREATER_OR_EQUAL_TO_ZERO);
			addListener(circText[7], circVars[7], null, ListenerAdder.GREATER_OR_EQUAL_TO_ZERO);
			addListener(frame.circX, frame.circVarB[0], null, ListenerAdder.ISNUMBER);
			addListener(frame.circY, frame.circVarB[1], null, ListenerAdder.ISNUMBER);
		} else if (topic == Frame.COLLISIONS) {
			JTextField[] colField = frame.colField;
			Var[] colVarA = frame.colVarA;
			Var[] colVarB = frame.colVarB;
			addListener(colField[0], frame.colVarE, frame.colVarE, ListenerAdder.E_VERIF);
			addListener(colField[1], colVarA[0], colVarB[0], ListenerAdder.NO_VERIF);
			addListener(colField[2], colVarA[1], colVarB[1], ListenerAdder.GREATER_THAN_ZERO);
			addListener(colField[3], colVarA[2], colVarB[2], ListenerAdder.ISNUMBER);
			addListener(colField[4], colVarA[3], colVarB[3], ListenerAdder.ISNUMBER);
			addListener(colField[5], colVarA[4], colVarB[4], ListenerAdder.ISNUMBER);
		}

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
	private void addListener(final JTextField textField, final Var var1, final Var var2, final int type) {
		textField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {
				if (textField.getText().equals("?")) {
					textField.setText("");
				}
			}

			@Override
			public void focusLost(FocusEvent arg0) {

				showPopUp = true;
				if (!verify(textField.getText(), var1, var2, type)) {
					textField.setBackground(red);
				} else {
					textField.setBackground(Color.white);
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

		if (text.equals("?") || text.isEmpty()) {
			v.setContents("?", false);
			return true;
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
