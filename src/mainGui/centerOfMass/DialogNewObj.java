package mainGui.centerOfMass;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import math.MathUtil;
import math.MyPoint;
import math.Obj;
import math.Shape;

public class DialogNewObj extends JDialog {
	Dimension prefSize = new Dimension(200, 500);

	MyPoint com;
	JPanel bot = new JPanel();
	JPanel container = new JPanel();
	JPanel container2 = new JPanel();
	JTextField txtPoints = new JTextField(12);
	JTextField txtMass = new JTextField(12);

	JTextField txtCOMX = new JTextField(4);
	JTextField txtCOMY = new JTextField(4);
	JTextField txtPosX = new JTextField(4);
	JTextField txtPosY = new JTextField(4);
	IllustrationPanel p = new IllustrationPanel();
	JButton buAdd = new JButton("Add Vertex");
	JButton buRem = new JButton("Remove Vertex");
	JButton buOk = new JButton("Done");
	DialogNewVertex popup = new DialogNewVertex();
	Boolean txtManual = false;

	private ArrayList<MyPoint> vertices = new ArrayList<MyPoint>();
	Obj object = new Obj();
	boolean done;

	public DialogNewObj() {
		setTitle("AddShape");
		add(p, BorderLayout.NORTH);
		add(bot, BorderLayout.SOUTH);
		container.add(new JLabel("X"));
		container.add(txtPosX);
		container.add(new JLabel("Y"));
		container.add(txtPosY);

		container2.add(new JLabel("X"));
		container2.add(txtCOMX);
		container2.add(new JLabel("Y"));
		container2.add(txtCOMY);

		bot.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.ipadx = 1;
		c.anchor = GridBagConstraints.LINE_START;
		c.gridx = 0;
		c.gridy = 0;
		bot.add(buAdd, c);
		c.gridy = 1;
		bot.add(new JLabel("Vertices"), c);
		c.gridy++;
		bot.add(new JLabel("Mass (kg)"), c);
		c.gridy++;
		bot.add(new JLabel("Center Of Mass"), c);
		c.gridy++;
		bot.add(new JLabel("Position"), c);
		c.gridy++;
		bot.add(new JLabel(" "), c);

		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 1;
		c.gridy = 0;
		bot.add(buRem, c);
		c.anchor = GridBagConstraints.LINE_END;
		c.gridy = 1;
		bot.add(txtPoints, c);
		c.gridy++;
		bot.add(txtMass, c);
		c.gridy++;
		bot.add(container2, c);
		c.gridy++;
		bot.add(container, c);
		c.gridy++;
		bot.add(buOk, c);

		txtPoints.setEditable(false);
		buAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				popup.open();
				if (popup.x != null) {
					vertices.add(new MyPoint(popup.x, popup.y));
					updateVertices();
				}
			}

		});
		buRem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (vertices.isEmpty())
					return;
				vertices.remove(vertices.size() - 1);
				updateVertices();
			}

		});

		buOk.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				String mass = txtMass.getText();
				String comX = txtCOMX.getText();
				String comY = txtCOMY.getText();
				String PosX = txtPosX.getText();
				String PosY = txtPosY.getText();

				if (!MathUtil.isNumeric(mass)) {
					JOptionPane
							.showMessageDialog(getParent(), "Please enter a valid number(mass)!",
									"Oops", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (!MathUtil.isNumeric(comX) || !MathUtil.isNumeric(comY)) {
					JOptionPane.showMessageDialog(getParent(),
							"Please enter a valid number(fenter of mass)!", "Oops",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (!MathUtil.isNumeric(PosX) || !MathUtil.isNumeric(PosY)) {
					JOptionPane.showMessageDialog(getParent(),
							"Please enter a valid number(position)!", "Oops",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				MyPoint[] points = new MyPoint[vertices.size()];
				for (int i = 0; i < vertices.size(); i++) {
					points[i] = new MyPoint(vertices.get(i).x - com.x, vertices.get(i).y - com.y);
				}
				object = new Obj(Float.valueOf(mass), new MyPoint(Double.valueOf(PosX), Double
						.valueOf(PosY)), points);
				setVisible(false);
			}
		});

		setModal(true);
		center();
		pack();
		setResizable(false);

	}

	private void updateVertices() {
		ArrayList<MyPoint> copy = new ArrayList<MyPoint>();

		String text = "";
		if (!vertices.isEmpty()) {
			for (MyPoint po : vertices) {
				copy.add(new MyPoint(po.x, po.y));
				text = text + "(" + po.x + "," + po.y + "),";
			}
			text = text.substring(0, text.length() - 1);
		}
		txtPoints.setText(text);

		Shape ver = new Shape(copy);

		p.setShape(ver);
		if ((!txtManual || txtCOMX.getText().isEmpty()) && ver.getNPoints() > 2) {
			com = ver.findCenter();
			txtCOMX.setText("" + Math.round(com.x));
			txtCOMY.setText("" + Math.round(com.y));
			p.setCOM(com);
		}

	}

	private void center() {
		GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point center = g.getCenterPoint();
		setLocation(center.x - prefSize.width / 2, center.y - prefSize.height / 2);
	}
}
