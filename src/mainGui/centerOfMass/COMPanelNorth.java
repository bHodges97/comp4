package mainGui.centerOfMass;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import mainGui.Frame;
import math.MathUtil;
import math.MyPoint;
import math.Obj;
import math.Plane;
import math.Shape;

public class COMPanelNorth extends JPanel {
	final JButton b1 = new JButton("Custom");
	final JButton b2 = new JButton("Rectangle");
	final JButton b3 = new JButton("Circle Sector");
	final JButton b4 = new JButton("Rod");
	final JButton b5 = new JButton("Arc");
	final JButton b6 = new JButton("PointMass");
	final DialogPointMass Dialogb6 = new DialogPointMass();
	final DialogRect Dialogb2 = new DialogRect();
	final JPanel body = new JPanel();
	final JLabel lbl1 = new JLabel("");
	final JLabel lbl2 = new JLabel("");
	final JLabel lbl3 = new JLabel("");
	final JTextField txt1 = new JTextField(9);
	final JTextField txt2 = new JTextField(9);
	final JTextField txt3 = new JTextField(9);
	Border border = BorderFactory.createEtchedBorder(1);
	Frame frame;
	Plane plane;

	DialogNewObj popupCOM = new DialogNewObj();

	public COMPanelNorth(Frame frame) {
		setBorder(border);
		this.frame = frame;
		plane = frame.panelCOM.plane;

		// Set up icons
		try {
			BufferedImage b1Icon = ImageIO.read(this.getClass().getResource("/testIcon.png"));
			b1.setIcon(new ImageIcon(b1Icon));
			BufferedImage b2Icon = ImageIO.read(this.getClass().getResource("/rectIcon.png"));
			b2.setIcon(new ImageIcon(b2Icon));
			BufferedImage b3Icon = ImageIO.read(this.getClass().getResource("/sectIcon.png"));
			b3.setIcon(new ImageIcon(b3Icon));
			BufferedImage b4Icon = ImageIO.read(this.getClass().getResource("/rodIcon.png"));
			b4.setIcon(new ImageIcon(b4Icon));
			BufferedImage b5Icon = ImageIO.read(this.getClass().getResource("/arcIcon.png"));
			b5.setIcon(new ImageIcon(b5Icon));
			BufferedImage b6Icon = ImageIO.read(this.getClass().getResource("/pointIcon.png"));
			b6.setIcon(new ImageIcon(b6Icon));

		} catch (IOException e) {
			// TODO: icons
			e.printStackTrace();
		}

		// For JOptionPane
		body.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(2, 2, 2, 2);
		body.add(lbl1, gbc);
		gbc.gridy++;
		body.add(lbl2, gbc);
		gbc.gridy++;
		body.add(lbl3, gbc);
		gbc.gridx++;
		gbc.gridy = 0;
		body.add(txt1, gbc);
		gbc.gridy++;
		body.add(txt2, gbc);
		gbc.gridy++;
		body.add(txt3, gbc);

		gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1;
		setLayout(new GridBagLayout());
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(b1, gbc);
		gbc.gridy++;
		add(b2, gbc);
		gbc.gridy++;
		add(b3, gbc);
		gbc.gridy++;
		add(b4, gbc);
		gbc.gridy++;
		add(b5, gbc);
		gbc.gridy++;
		add(b6, gbc);

		// left align
		for (Component c : getComponents()) {
			if (c instanceof JButton) {
				((JButton) c).setHorizontalAlignment(SwingConstants.LEFT);
			}
		}

		addListeners();
	}

	public void addListeners() {
		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				popupCOM = new DialogNewObj();
				popupCOM.setVisible(true);
				plane.add(popupCOM.object);
				popupCOM.dispose();
			}
		});
		b2.addActionListener(new ActionListener() {
			// rectangle
			@Override
			public void actionPerformed(ActionEvent e) {
				Dialogb2.open();
				if (Dialogb2.returnObj != null) {
					plane.add(Dialogb2.returnObj);
				}
			}
		});
		b3.addActionListener(new ActionListener() {
			// circ sector
			@Override
			public void actionPerformed(ActionEvent e) {
				// Set up text for body;
				lbl1.setText("Angle");
				lbl2.setText("Mass");
				lbl3.setText("Radius");
				JOptionPane.showMessageDialog(null, body, "Circle Sector",
						JOptionPane.QUESTION_MESSAGE);

				if (!MathUtil.isNumeric(txt1.getText()) || !MathUtil.isNumeric(txt2.getText())
						|| !MathUtil.isNumeric(txt3.getText())) {
					JOptionPane.showMessageDialog(null, "Not a number!", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				double angle = Double.parseDouble(txt1.getText());
				float mass = Float.parseFloat(txt2.getText());
				double radius = Double.parseDouble(txt3.getText());
				if (angle > 2 * Math.PI || angle <= 0) {
					JOptionPane.showMessageDialog(null, "Must be with the range 0 < a <= 2 pi",
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				plane.add(new Obj(Obj.POLYGON, new MyPoint(0, 0), MathUtil.genCirc(angle, radius,
						true), mass));
			}
		});
		b4.addActionListener(new ActionListener() {
			// rod
			@Override
			public void actionPerformed(ActionEvent e) {
				lbl1.setText("Direction");
				lbl2.setText("Magnitude");
				lbl3.setText("Mass");
				JOptionPane.showMessageDialog(null, body, "Rod", JOptionPane.QUESTION_MESSAGE);
				if (!MathUtil.isNumeric(txt1.getText()) || !MathUtil.isNumeric(txt2.getText())
						|| !MathUtil.isNumeric(txt3.getText())) {
					JOptionPane.showMessageDialog(null, "Not a number!", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				double angle = Double.parseDouble(txt1.getText());
				double magnitude = Double.parseDouble(txt2.getText());
				float mass = Float.parseFloat(txt3.getText());
				MyPoint[] points = new MyPoint[2];
				points[0] = new MyPoint(-magnitude * Math.cos(angle) / 2, -magnitude
						* Math.sin(angle) / 2);
				points[1] = new MyPoint(magnitude * Math.cos(angle) / 2, magnitude
						* Math.sin(angle) / 2);
				plane.add(new Obj(Obj.POLYLINE, new MyPoint(0, 0), new Shape(points), mass));
			}
		});
		b5.addActionListener(new ActionListener() {
			// arc
			@Override
			public void actionPerformed(ActionEvent e) {
				// Set up text for body;
				lbl1.setText("Angle");
				lbl2.setText("Mass");
				lbl3.setText("Radius");
				JOptionPane.showMessageDialog(null, body, "Circle Sector",
						JOptionPane.QUESTION_MESSAGE);

				if (!MathUtil.isNumeric(txt1.getText()) || !MathUtil.isNumeric(txt2.getText())
						|| !MathUtil.isNumeric(txt3.getText())) {
					JOptionPane.showMessageDialog(null, "Not a number!", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				double angle = Double.parseDouble(txt1.getText());
				float mass = Float.parseFloat(txt2.getText());
				double radius = Double.parseDouble(txt3.getText());
				if (angle > 2 * Math.PI || angle <= 0) {
					JOptionPane.showMessageDialog(null, "Must be with the range 0 < a <= 2 pi",
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				plane.add(new Obj(Obj.POLYLINE, new MyPoint(0, 0), MathUtil.genCirc(angle, radius,
						false), mass));
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
