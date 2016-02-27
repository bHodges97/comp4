package mainGui.centerOfMass;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import math.MathUtil;
import math.MyPoint;
import math.Obj;
import math.Plane;

public class sidepanelSouth extends JPanel {

	JLabel planeCom = new JLabel("Plane Center of mass");
	JLabel planeComVal = new JLabel("");

	JLabel objName = new JLabel("Current Object");
	JLabel objMass = new JLabel("Mass");
	JLabel objCofm = new JLabel("Center of mass");
	JLabel objRest = new JLabel("Coefficient of resitution");
	JLabel objVelo = new JLabel("Velocity");
	JLabel objVelX = new JLabel("Velocity(x component)");
	JLabel objVelY = new JLabel("Velocity(y component)");
	JLabel objAcce = new JLabel("Acceleration");
	JLabel objAccX = new JLabel("Acceleration(x component)");
	JLabel objAccY = new JLabel("Acceleration(y component)");

	JTextField varName = new JTextField("NULL");
	JTextField varMass = new JTextField("NULL");
	JTextField varCofm = new JTextField("NULL");
	JTextField varRest = new JTextField("NULL");
	JTextField varVelo = new JTextField("NULL");
	JTextField varVelX = new JTextField("NULL");
	JTextField varVelY = new JTextField("NULL");
	JTextField varAcce = new JTextField("NULL");
	JTextField varAccX = new JTextField("NULL");
	JTextField varAccY = new JTextField("NULL");

	JButton rotate = new JButton("Rotate");
	public Obj current;

	public sidepanelSouth(int t, Plane plane) {

		setLayout();
		addActionListeners();
	}

	public void setObj(Obj o) {
		current = o;
		updateFields();
	}

	private void updateFields() {
		if (current == null)
			return;
		varName.setText(current.getName());
		varMass.setText(current.getMass() + "");
		// truncated
		varCofm.setText(Math.floor(current.getCOM().x * 100) / 100 + "," + Math.floor(current.getCOM().y * 100) / 100);
		varRest.setText(current.getRest() + "");
	}

	/**
	 * Adds the actionlisteners;
	 */
	private void addActionListeners() {
		rotate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (current != null) {
					String responce = JOptionPane.showInputDialog(null,
							"Enter angle with suffix 'r' for radians or 'd' for degrees. \n Default is radians if nothing is suffixed. Do not use character π!",
							"Rotate", JOptionPane.QUESTION_MESSAGE);
					if (responce == null)
						return;
					char c = responce.charAt(responce.length() - 1);
					if (!Character.isDigit(c)) {
						responce = responce.substring(0, responce.length() - 1);
					}
					if (MathUtil.isNumeric(responce)) {
						double x = Double.valueOf(responce);
						boolean radians = true;
						if (c == 'D' || c == 'd')
							radians = false;
						current.rotate(x, radians, new MyPoint(0, 0));
					} else {
						JOptionPane.showMessageDialog(null, "Not a valid input!");
						System.out.println(responce);
					}
				}
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
		c.anchor = c.NORTHWEST;
		c.fill = c.HORIZONTAL;
		c.weightx = 1.0;
		c.weighty = 0;

		c.gridx = 0;
		c.gridy = 0;
		add(objName, c);
		c.gridy++;
		add(objMass, c);
		c.gridy++;
		add(objCofm, c);
		c.gridy++;
		add(objRest, c);
		c.gridy++;
		add(objVelo, c);
		c.gridy++;
		add(objVelX, c);
		c.gridy++;
		add(objVelY, c);
		c.gridy++;
		add(objAcce, c);
		c.gridy++;
		add(objAccX, c);
		c.gridy++;
		add(objAccY, c);
		c.gridx++;
		c.gridy = 0;
		add(varName, c);
		c.gridy++;
		add(varMass, c);
		c.gridy++;
		add(varCofm, c);
		c.gridy++;
		add(varRest, c);
		c.gridy++;
		add(varVelo, c);
		c.gridy++;
		add(varVelX, c);
		c.gridy++;
		add(varVelY, c);
		c.gridy++;
		add(varAcce, c);
		c.gridy++;
		add(varAccX, c);
		c.gridy++;
		add(varAccY, c);
		c.gridy++;
		add(rotate, c);
		/*
		 * Fixed Size so changing text doesn't change layout.
		 */
		varName.setColumns(9);
		varMass.setColumns(9);
		varCofm.setColumns(9);
		varRest.setColumns(9);
		varVelo.setColumns(9);
		varVelX.setColumns(9);
		varVelY.setColumns(9);
		varAcce.setColumns(9);
		varAccX.setColumns(9);
		varAccY.setColumns(9);
	}
}
