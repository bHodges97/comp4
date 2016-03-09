package mainGui.centerOfMass;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import math.MathUtil;
import math.MyPoint;
import math.Obj;
import math.Shape;

public class DialogRect extends JDialog {
	Dimension prefSize = new Dimension(200, 200);
	JTextField comx = new JTextField("0");
	JTextField comy = new JTextField("0");
	JTextField x = new JTextField(7);
	JTextField y = new JTextField(7);
	JTextField width = new JTextField(7);
	JTextField height = new JTextField(7);
	JTextField mass = new JTextField(7);
	JButton butDone = new JButton("Done");
	boolean filled = false;
	Obj returnObj = new Obj();

	public DialogRect() {

		placeFields();

		butDone.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				double xval, yval, massval, w, h, cx, cy;

				while (true) {
					if (MathUtil.isNumeric(x.getText())
							&& MathUtil.isNumeric(y.getText())) {
						xval = Double.valueOf(x.getText());
						yval = Double.valueOf(y.getText());
						if (xval == 0 && yval == 0)
							break;
					} else
						break;

					if (MathUtil.isNumeric(mass.getText())) {
						massval = Double.valueOf(mass.getText());
					} else
						break;

					if (MathUtil.isNumeric(width.getText())
							&& MathUtil.isNumeric(height.getText())) {
						w = Double.valueOf(width.getText());
						h = Double.valueOf(height.getText());
					} else
						break;

					if (MathUtil.isNumeric(comx.getText())
							&& MathUtil.isNumeric(comy.getText())) {
						cx = Double.valueOf(comx.getText());
						cy = Double.valueOf(comy.getText());
					} else if ((comy.getText() != null)
							&& comy.getText() != null) {
						cx = w / 2;
						cy = h / 2;
					} else
						break;

					Shape s = new Shape(new MyPoint[] {
							new MyPoint(0 - cx, w - cy),
							new MyPoint(h - cx, w - cy),
							new MyPoint(h - cx, 0 - cy),
							new MyPoint(0 - cx, 0 - cy) });
					returnObj = new Obj(0, new MyPoint(xval + cx, yval + cy),
							s, 0);
					filled = true;
					close();
					return;
				}
				JOptionPane.showMessageDialog(null, "Something is not right.");

			}

		});

		setResizable(false);
		setModal(true);
		GraphicsEnvironment g = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		Point center = g.getCenterPoint();
		setLocation(center.x - prefSize.width / 2, center.y - prefSize.height
				/ 2);
		pack();

	}

	public void open() {
		filled = false;
		x.setText("");
		y.setText("");
		comx.setText("");
		comy.setText("");
		height.setText("");
		width.setText("");
		mass.setText("");
		this.setVisible(true);
	}

	public void close() {
		this.setVisible(false);
	}

	private void placeFields() {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 0, 5);
		gbc.fill = gbc.BOTH;
		gbc.anchor = gbc.WEST;

		setFocusable(true);
		setTitle("AddPointMass");

		add(new JLabel("Width:"), gbc);
		gbc.gridy++;
		add(new JLabel("x:"), gbc);
		gbc.gridy++;
		add(new JLabel("Center of mass(if not at center)"), gbc);
		gbc.gridy++;
		add(new JLabel("x:"), gbc);
		gbc.gridy++;
		add(new JLabel("mass:"), gbc);

		gbc.gridx++;
		gbc.gridy = 0;
		add(width, gbc);
		gbc.gridy++;
		add(x, gbc);
		gbc.gridy += 2;
		add(comx, gbc);
		gbc.gridy++;
		add(mass, gbc);

		gbc.gridx++;
		gbc.gridy = 0;
		add(new JLabel("Height:"), gbc);
		gbc.gridy++;
		add(new JLabel("y:"), gbc);
		gbc.gridy += 2;
		add(new JLabel("y:"), gbc);

		gbc.gridx++;
		gbc.gridy = 0;
		add(height, gbc);
		gbc.gridy++;
		add(y, gbc);
		gbc.gridy += 2;
		add(comy, gbc);
		gbc.gridy += 2;
		add(butDone, gbc);
	}

}
