package mainGui.centerOfMass;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import mainGui.Frame;
import math.MathUtil;
import math.MyPoint;
import math.Obj;
import math.Plane;

/**
 * The COMPanelSouth class is a panel that is used to hold obj specific fields
 * and buttons
 */
public class COMPanelSouth extends JPanel {
	private static final long serialVersionUID = 1L;

	Plane plane;
	JLabel planeCom = new JLabel("Plane Center of mass");
	JLabel planeComVal = new JLabel("");

	JLabel objName = new JLabel("Current Object");
	JLabel objMass = new JLabel("Mass");
	JLabel objCOM = new JLabel("Position");

	JTextField varName = new JTextField(9);
	JTextField varMass = new JTextField(9);
	JTextField varCOM = new JTextField(9);

	JButton rotate = new JButton("Rotate");
	JButton translate = new JButton("Translate");
	JButton changeCOM = new JButton("Change center of mass");
	JButton lastObj = new JButton("< Previous");
	JButton nextObj = new JButton("Next >");
	JButton delete = new JButton("Delete");

	public Obj current;
	Frame frame;

	/**
	 * Construct a new instance of this class
	 * 
	 * @param frame
	 *            The frame this panel belongs to
	 */
	public COMPanelSouth(Frame frame) {
		this.frame = frame;
		this.plane = frame.panelCOM.plane;
		setLayout();
		addActionListeners();
	}

	/**
	 * Sets the current Obj
	 * 
	 * @param obj
	 *            The new current Obj
	 */
	public void setObj(Obj obj) {
		current = obj;
		updateFields();
	}

	/**
	 * Update the fields
	 */
	public void updateFields() {
		if (current == null) {
			varName.setText("NULL");
			varMass.setText("NULL");
			varCOM.setText("NULL");
			varName.setEditable(false);
			varMass.setEditable(false);
			varCOM.setEditable(false);
			return;
		}
		varCOM.setEditable(true);
		varName.setText(current.getName());
		varMass.setText(current.getMass() + "");
		// truncated
		varCOM.setText(Math.floor(current.getCOM().x * 100) / 100 + "," + Math.floor(current.getCOM().y * 100) / 100);
	}

	/**
	 * Adds the action listeners;
	 */
	private void addActionListeners() {
		// Sets name
		varName.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (current != null) {
					current.setName(varName.getText());
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		// Sets mass
		varMass.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (current != null && MathUtil.isNumeric(varMass.getText())) {
					current.setMass(varMass.getText());
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		// Sets pos
		varCOM.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (current != null && varCOM.getText().matches("-?[0-9]+(\\.[0-9]+)?,-?[0-9]+(\\.[0-9]+)?")) {
					String[] text = varCOM.getText().split(",");
					double x = Double.parseDouble(text[0]);
					double y = Double.parseDouble(text[1]);
					current.moveto(new MyPoint(x, y));
					updateFields();
					System.out.println(x + " " + y);
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		// Rotate obj
		rotate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (current != null) {
					String responce = JOptionPane.showInputDialog(frame,
							"Enter angle with suffix 'r' for radians or 'd' for degrees."
									+ "\n Default is radians if nothing is suffixed. Do not use character π!",
							"Rotate", JOptionPane.QUESTION_MESSAGE);
					if (responce == null) {
						return;
					}
					char c = responce.charAt(responce.length() - 1);
					if (!Character.isDigit(c)) {
						responce = responce.substring(0, responce.length() - 1);
					}
					if (MathUtil.isNumeric(responce)) {
						double x = Double.parseDouble(responce);
						boolean radians = true;
						if (c == 'D' || c == 'd') {
							radians = false;
						}
						current.rotate(x, radians, current.getCOM());
					} else {
						showErrorMsg("Not a valid input!");
						System.out.println(responce);
					}
				} else {
					showErrorMsg("No object selected.");
				}
			}
		});
		// Translate obj
		translate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (current != null) {
					JPanel message = new JPanel(new GridBagLayout());
					GridBagConstraints gbc = new GridBagConstraints();
					JTextField fieldX = new JTextField("0", 9);
					JTextField fieldY = new JTextField("0", 9);
					gbc.gridx = 0;
					gbc.gridy = 0;
					gbc.gridwidth = 2;
					gbc.anchor = GridBagConstraints.FIRST_LINE_END;
					message.add(new JLabel("<html>Enter X and Y coordinates to translate by."), gbc);
					gbc.gridwidth = 1;
					gbc.gridy = 1;
					message.add(new JLabel("X"), gbc);
					gbc.gridy = 2;
					message.add(new JLabel("Y"), gbc);

					gbc.gridx = 1;
					message.add(fieldY, gbc);
					gbc.gridy = 1;
					message.add(fieldX, gbc);

					JOptionPane.showMessageDialog(frame, message, "Translate", JOptionPane.PLAIN_MESSAGE);

					if (!MathUtil.isNumeric(fieldX.getText()) || !MathUtil.isNumeric(fieldY.getText())) {
						showErrorMsg("Not a number.");
					} else {
						current.translate(Double.parseDouble(fieldX.getText()), Double.parseDouble(fieldY.getText()));
					}
				} else {
					showErrorMsg("No object selected.");
				}
			}
		});
		// Shift the center of mass
		changeCOM.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (current != null) {
					JPanel message = new JPanel(new GridBagLayout());
					GridBagConstraints gbc = new GridBagConstraints();
					JTextField fieldX = new JTextField("0", 9);
					JTextField fieldY = new JTextField("0", 9);
					gbc.gridx = 0;
					gbc.gridy = 0;
					gbc.gridwidth = 2;
					gbc.anchor = GridBagConstraints.FIRST_LINE_END;
					message.add(
							new JLabel(
									"<html>Enter new X,Y coordinates of the center <br>of mass relative to the old one."),
							gbc);
					gbc.gridwidth = 1;
					gbc.gridy = 1;
					message.add(new JLabel("X"), gbc);
					gbc.gridy = 2;
					message.add(new JLabel("Y"), gbc);

					gbc.gridx = 1;
					message.add(fieldY, gbc);
					gbc.gridy = 1;
					message.add(fieldX, gbc);

					JOptionPane.showMessageDialog(frame, message, "Translate", JOptionPane.PLAIN_MESSAGE);

					if (!MathUtil.isNumeric(fieldX.getText()) || !MathUtil.isNumeric(fieldY.getText())) {
						showErrorMsg("Not a number.");
					} else {
						current.shiftCOM(Double.parseDouble(fieldX.getText()), Double.parseDouble(fieldY.getText()));
					}
				} else {
					showErrorMsg("No object selected.");
				}
			}
		});
		// Select next obj
		nextObj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (current == null) {
					showErrorMsg("No object selected.");
					return;
				}
				System.out.println(plane.objects.indexOf(current) + " " + plane.objects.size());
				if (plane.objects.indexOf(current) < plane.objects.size() - 1) {
					current = plane.objects.get(plane.objects.indexOf(current) + 1);
				} else {
					current = plane.objects.get(0);
				}
				updateFields();
			}
		});
		// Select last obj
		lastObj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (current == null) {
					showErrorMsg("No object selected.");
					return;
				}
				System.out.println(plane.objects.indexOf(current) + " " + plane.objects.size());
				if (plane.objects.indexOf(current) > 0) {
					current = plane.objects.get(plane.objects.indexOf(current) - 1);
				} else {
					current = plane.objects.get(plane.objects.size() - 1);
				}
				updateFields();
			}
		});
		// delete current obj
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (current == null) {
					showErrorMsg("No object selected.");
					return;
				}
				// Confirm user choice
				if (JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this", "Confirm",
						JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
					return;
				}
				plane.remove(current);
				updateFields();
			}
		});

	}

	/**
	 * Shows an error message
	 * 
	 * @param msg
	 *            The error message
	 */
	private void showErrorMsg(String msg) {
		JOptionPane.showMessageDialog(frame, msg, "Error", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Place components in the correct position.
	 */
	private void setLayout() {
		this.setLayout(new GridBagLayout());
		setBorder(BorderFactory.createEtchedBorder(1));
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.weightx = 1.0;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(1, 20, 5, 20);
		// First Column
		add(lastObj, c);
		c.gridy++;
		add(objName, c);
		c.gridy++;
		add(objMass, c);
		c.gridy++;
		add(objCOM, c);

		// Second column
		c.gridx++;
		c.gridy = 0;
		add(nextObj, c);
		c.gridy++;
		add(varName, c);
		c.gridy++;
		add(varMass, c);
		c.gridy++;
		add(varCOM, c);

		// Add buttons
		c.gridx = 0;
		c.gridy++;
		c.gridwidth = 2;
		add(rotate, c);
		c.gridy++;
		add(translate, c);
		c.gridy++;
		add(changeCOM, c);
		c.gridy++;
		add(delete, c);

		updateFields();
	}
}
