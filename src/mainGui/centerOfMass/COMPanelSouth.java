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
import math.Obj;
import math.Plane;

/**
 * The COMPanelSouth class is a panel that is used to hold obj specific fields
 * and buttons
 */
public class COMPanelSouth extends JPanel {
	private static final long serialVersionUID = 1L;

	JLabel planeCom = new JLabel("Plane Center of mass");
	JLabel planeComVal = new JLabel("");

	JLabel objName = new JLabel("Current Object");
	JLabel objMass = new JLabel("Mass");
	JLabel objCOM = new JLabel("Position");

	JTextField varName = new JTextField("NULL");
	JTextField varMass = new JTextField("NULL");
	JTextField varCOM = new JTextField("NULL");

	JButton rotate = new JButton("Rotate");
	JButton translate = new JButton("Translate");
	JButton changeCOM = new JButton("Change center of mass");

	JButton lastObj = new JButton("< Previous");
	JButton nextObj = new JButton("Next >");
	Plane plane;

	public Obj current;

	/**
	 * Construct a new instance of this class
	 * 
	 * @param frame
	 *            The frame this panel belongs to
	 */
	public COMPanelSouth(Frame frame) {
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
			return;
		}
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
		// Rotate obj
		rotate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (current != null) {
					String responce = JOptionPane.showInputDialog(null,
							"Enter angle with suffix 'r' for radians or 'd' for degrees."
									+ "\n Default is radians if nothing is suffixed. Do not use character π!",
							"Rotate", JOptionPane.QUESTION_MESSAGE);
					if (responce == null)
						return;
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
						JOptionPane.showMessageDialog(null, "Not a valid input!");
						System.out.println(responce);
					}
				} else {
					JOptionPane.showMessageDialog(null, "No object selected.", "Error", JOptionPane.ERROR_MESSAGE);
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

					JOptionPane.showMessageDialog(null, message, "Translate", JOptionPane.PLAIN_MESSAGE);

					if (!MathUtil.isNumeric(fieldX.getText()) || !MathUtil.isNumeric(fieldY.getText())) {
						JOptionPane.showMessageDialog(null, "Not a number.", "Error", JOptionPane.ERROR_MESSAGE);
					} else {
						current.translate(Double.parseDouble(fieldX.getText()), Double.parseDouble(fieldY.getText()));
					}
				} else {
					JOptionPane.showMessageDialog(null, "No object selected.", "Error", JOptionPane.ERROR_MESSAGE);
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

					JOptionPane.showMessageDialog(null, message, "Translate", JOptionPane.PLAIN_MESSAGE);

					if (!MathUtil.isNumeric(fieldX.getText()) || !MathUtil.isNumeric(fieldY.getText())) {
						JOptionPane.showMessageDialog(null, "Not a number.", "Error", JOptionPane.ERROR_MESSAGE);
					} else {
						current.shiftCOM(Double.parseDouble(fieldX.getText()), Double.parseDouble(fieldY.getText()));
					}
				} else {
					JOptionPane.showMessageDialog(null, "No object selected.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		// Select next obj
		nextObj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (current == null) {
					JOptionPane.showMessageDialog(null, "No object selected.", "Error", JOptionPane.ERROR_MESSAGE);
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
					JOptionPane.showMessageDialog(null, "No object selected.", "Error", JOptionPane.ERROR_MESSAGE);
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

		// Fixed Size so changing text doesn't change layout.
		varName.setColumns(9);
		varMass.setColumns(9);
		varCOM.setColumns(9);

		varCOM.setEditable(false);
	}
}
