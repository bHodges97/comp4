package mainGui.centreOfMass;

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

/**
 * The DialogNewObj class creates a new dialog for creating a obj
 */
public class DialogNewObj extends JDialog {
	private static final long serialVersionUID = 1L;

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
	NewObjPanel p = new NewObjPanel();
	JButton buAdd = new JButton("Add Vertex");
	JButton buRem = new JButton("Remove Vertex");
	JButton buOk = new JButton("Done");
	DialogNewVertex popup = new DialogNewVertex();
	Boolean txtManual = false;

	private ArrayList<MyPoint> vertices = new ArrayList<MyPoint>();
	Obj object = null;
	boolean done;

	/**
	 * Construct a new instance of this class
	 */
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
		bot.add(new JLabel("Centre Of Mass"), c);
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
		// Add vertex
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
		// remove vertex
		buRem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (vertices.isEmpty())
					return;
				vertices.remove(vertices.size() - 1);
				updateVertices();
			}
		});
		// Ok button
		buOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String mass = txtMass.getText();
				String comX = txtCOMX.getText();
				String comY = txtCOMY.getText();
				String PosX = txtPosX.getText();
				String PosY = txtPosY.getText();

				if (!MathUtil.isNumeric(mass)) {
					JOptionPane.showMessageDialog(getParent(), "Please enter a valid number for mass!", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (!MathUtil.isNumeric(comX) || !MathUtil.isNumeric(comY)) {
					JOptionPane.showMessageDialog(getParent(), "Please enter a valid number for centre of mass!",
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (!MathUtil.isNumeric(PosX) || !MathUtil.isNumeric(PosY)) {
					JOptionPane.showMessageDialog(getParent(), "Please enter a valid number for position!", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				MyPoint[] points = new MyPoint[vertices.size()];
				for (int i = 0; i < vertices.size(); i++) {
					points[i] = new MyPoint(vertices.get(i).x - com.x, vertices.get(i).y - com.y);
				}
				object = new Obj(Float.valueOf(mass), new MyPoint(Double.parseDouble(PosX), Double.valueOf(PosY)),
						points);
				setVisible(false);
			}
		});

		setModal(true);
		centre();
		pack();
		setResizable(false);

	}

	/**
	 * update vertices list
	 */
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

		Shape vertices = new Shape(copy);

		p.setShape(vertices);
		if ((!txtManual || txtCOMX.getText().isEmpty()) && vertices.getNPoints() > 2) {
			com = vertices.findCentre();
			txtCOMX.setText("" + Math.round(com.x));
			txtCOMY.setText("" + Math.round(com.y));
			p.setCOM(com);
		}

	}

	/**
	 * centres the gui
	 */
	private void centre() {
		GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centre = g.getCenterPoint();
		setLocation(centre.x - prefSize.width / 2, centre.y - prefSize.height / 2);
	}
}
