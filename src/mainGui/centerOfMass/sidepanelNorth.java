package mainGui.centerOfMass;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import mainGui.WrapLayout;
import math.MathUtil;
import math.MyPoint;
import math.Obj;
import math.Plane;

public class sidepanelNorth extends JPanel {
	final JButton b1 = new JButton("Custom");
	final JButton b2 = new JButton("Rectangle");
	final JButton b3 = new JButton("Circle Sector");
	final JButton b4 = new JButton("Rod");
	final JButton b5 = new JButton("arc");
	final JButton b6 = new JButton("PointMass");
	final DialogPointMass Dialogb6 = new DialogPointMass();
	final DialogRect Dialogb2 = new DialogRect();
	final JPanel body = new JPanel();
	final JLabel lbl1 = new JLabel("");
	final JLabel lbl2 = new JLabel("");
	final JTextField txt1 = new JTextField(9);
	final JTextField txt2 = new JTextField(9);
	Border border = BorderFactory.createEtchedBorder(1);
	Plane plane;

	DialogNewObj popupCofM = new DialogNewObj();

	public sidepanelNorth(final Plane plane) {
		JPanel laminars = new JPanel(new WrapLayout());
		JPanel solids = new JPanel();
		laminars.setBorder(border);
		solids.setBorder(border);
		add(laminars);

		//For JOptionPane		
		body.add(lbl1);
		body.add(txt1);
		body.add(lbl2);
		body.add(txt2);

		laminars.add(b1);
		laminars.add(b2);
		laminars.add(b3);
		laminars.add(b4);
		laminars.add(b5);
		laminars.add(b6);

		this.plane = plane;
		addListeners();
	}

	public void addListeners() {

		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				popupCofM = new DialogNewObj();
				popupCofM.setVisible(true);
				plane.add(popupCofM.object);
				popupCofM.dispose();
			}
		});
		b2.addActionListener(new ActionListener() {
			// rectangle
			@Override
			public void actionPerformed(ActionEvent e) {
				Dialogb2.open();
				plane.add(Dialogb2.returnObj);
			}
		});
		b3.addActionListener(new ActionListener() {
			// circsector
			@Override
			public void actionPerformed(ActionEvent e) {
				//Set up text for body;
				lbl1.setText("Angle");
				lbl2.setText("Mass");
				JOptionPane.showMessageDialog(null, body, "Circle Sector",
						JOptionPane.QUESTION_MESSAGE);

				if (!MathUtil.isNumeric(txt1.getText()) || !MathUtil.isNumeric(txt2.getText())) {
					JOptionPane.showMessageDialog(null, "Must be numeric.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				double angle = Double.parseDouble(txt1.getText());
				double mass = Double.parseDouble(txt2.getText());
				if (angle > 2 * Math.PI || angle <= 0) {
					JOptionPane.showMessageDialog(null, "Must be with the range 0 < a <= 2 pi",
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				plane.add(new Obj(0, new MyPoint(0, 0), MathUtil.genCirc(angle, 5d, true), mass));
			}
		});
		b4.addActionListener(new ActionListener() {
			// rod
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		b5.addActionListener(new ActionListener() {
			//arc
			@Override
			public void actionPerformed(ActionEvent e) {
				String reply = JOptionPane.showInputDialog(null,
						"Enter angle of the circle sector in radians.", "Circle Sector",
						JOptionPane.QUESTION_MESSAGE);
				if (!MathUtil.isNumeric(reply)) {
					JOptionPane.showMessageDialog(null, "Must be numeric.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				double angle = Double.parseDouble(reply);
				if (angle > 2 * Math.PI || angle <= 0) {
					JOptionPane.showMessageDialog(null, "Must be with the range 0 < a <= 2 pi",
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				plane.add(new Obj(2, new MyPoint(0, 0), MathUtil.genCirc(Math.PI, 5d, false), 0d));
			}
		});
		b6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Dialogb6.open();
				plane.add(Dialogb6.returnObj);
			}
		});
	}
}
