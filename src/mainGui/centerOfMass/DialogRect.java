package mainGui.centerOfMass;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import math.MathUtil;
import math.MyPoint;
import math.Obj;
import math.Shape;

public class DialogRect extends JDialog {
	Dimension prefSize = new Dimension(200, 200);
	JTextField comx = new JTextField(9);
	JTextField comy = new JTextField(9);
	JTextField x = new JTextField("0");
	JTextField y = new JTextField("0");
	JTextField width = new JTextField(9);
	JTextField height = new JTextField(9);
	JTextField mass = new JTextField(9);
	JButton butDone = new JButton("Done");
	boolean filled = false;
	Obj returnObj;
	PanelRectangle panelDiagram = new PanelRectangle();

	public DialogRect() {

		placeFields();
		// TODO: fix rectangles
		butDone.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				double xval, yval, massval, w, h, cx, cy;
				// Loops through all panels inside dialog box
				for (Component panel : getContentPane().getComponents()) {
					if (panel instanceof JPanel) {

						// loop through panel components
						for (Component component : ((JPanel) panel).getComponents()) {
							if (component instanceof JTextField) {
								if (!MathUtil.isNumeric(((JTextField) component).getText())) {
									JOptionPane.showMessageDialog(null,
											"All fields must be numeric.");
									return;
								}
							}
						}
					}
				}
				xval = Double.valueOf(x.getText());
				yval = Double.valueOf(y.getText());
				massval = Double.valueOf(mass.getText());
				w = Double.valueOf(width.getText());
				h = Double.valueOf(height.getText());
				cx = Double.valueOf(comx.getText());
				cy = Double.valueOf(comy.getText());
				Shape s = new Shape(new MyPoint[] { new MyPoint(0 - cx, h - cy),
						new MyPoint(w - cx, h - cy), new MyPoint(w - cx, 0 - cy),
						new MyPoint(0 - cx, 0 - cy) });
				returnObj = new Obj(0, new MyPoint(xval + cx, yval + cy), s, massval);
				filled = true;
				close();

			}

		});

		setResizable(false);
		setModal(true);
		GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point center = g.getCenterPoint();
		setLocation(center.x - prefSize.width / 2, center.y - prefSize.height / 2);
		pack();

	}

	public void open() {
		filled = false;
		x.setText("0");
		y.setText("0");
		comx.setText("");
		comy.setText("");
		height.setText("");
		width.setText("");
		mass.setText("");
		this.setVisible(true);
	}

	public void close() {
		panelDiagram.clear();
		this.setVisible(false);
	}

	private void placeFields() {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.WEST;

		setFocusable(true);
		setTitle("AddPointMass");

		Border etchedBorder = BorderFactory.createEtchedBorder(1);

		panelDiagram.setPreferredSize(new Dimension(200, 200));
		panelDiagram.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		JPanel panelMass = new JPanel();
		JPanel panelDimension = new JPanel();
		JPanel panelPosition = new JPanel();
		JPanel panelCenter = new JPanel();
		JPanel panelDone = new JPanel();

		panelMass.setBorder(BorderFactory.createTitledBorder(etchedBorder, "Mass"));
		panelDimension.setBorder(BorderFactory.createTitledBorder(etchedBorder, "Dimensions"));
		panelPosition.setBorder(BorderFactory.createTitledBorder(etchedBorder, "Position"));
		panelCenter.setBorder(BorderFactory.createTitledBorder(etchedBorder, "Center of mass"));

		panelMass.setLayout(new GridLayout(0, 2));
		panelDimension.setLayout(new GridLayout(0, 2));
		panelPosition.setLayout(new GridLayout(0, 2));
		panelCenter.setLayout(new GridLayout(0, 2));
		panelDone.setLayout(new GridLayout(0, 2));

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(panelDiagram, gbc);
		gbc.gridy = 1;
		add(panelMass, gbc);
		gbc.gridy = 2;
		add(panelDimension, gbc);
		gbc.gridy = 3;
		add(panelPosition, gbc);
		gbc.gridy = 4;
		add(panelCenter, gbc);
		gbc.gridy = 5;
		add(panelDone, gbc);

		panelMass.add(new JLabel("Mass (kg):"));
		panelMass.add(mass);
		panelDimension.add(new JLabel("Width (m):"));
		panelDimension.add(width);
		panelDimension.add(new JLabel("Height (m):"));
		panelDimension.add(height);
		panelPosition.add(new JLabel("X (m):"));
		panelPosition.add(x);
		panelPosition.add(new JLabel("Y (m):"));
		panelPosition.add(y);
		panelCenter.add(new JLabel("X (m):"));
		panelCenter.add(comx);
		panelCenter.add(new JLabel("Y (m):"));
		panelCenter.add(comy);
		panelDone.add(new JLabel(""));// Fill space
		panelDone.add(butDone);

		width.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {
				// Do nothing
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				if (comx.getText().isEmpty()) {
					if (MathUtil.isNumeric(width.getText())) {
						comx.setText((Double.parseDouble(width.getText()) / 2) + "");
					}
				}
				panelDiagram.x = (int) Double.parseDouble(width.getText());
				panelDiagram.repaint();
			}
		});
		height.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {
				// Do nothing
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				if (comy.getText().isEmpty()) {
					if (MathUtil.isNumeric(height.getText())) {
						comy.setText((Double.parseDouble(height.getText()) / 2) + "");
					}
				}
				panelDiagram.y = (int) Double.parseDouble(height.getText());
				panelDiagram.repaint();
			}
		});
		DocumentListener docListener = new DocumentListener() {
			private void update() {
				if (MathUtil.isNumeric(width.getText()) && MathUtil.isNumeric(comx.getText())) {
					double x = Double.parseDouble(width.getText());
					double comXVal = Double.parseDouble(comx.getText());
					panelDiagram.comX = (int) comXVal;
				}
				if (MathUtil.isNumeric(height.getText()) && MathUtil.isNumeric(comy.getText())) {
					double y = Double.parseDouble(height.getText());
					double comYVal = Double.parseDouble(comy.getText());
					panelDiagram.comY = (int) (comYVal);
				}
				panelDiagram.repaint();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				update();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				update();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				// Do nothing
			}
		};
		comx.getDocument().addDocumentListener(docListener);
		comy.getDocument().addDocumentListener(docListener);

	}
}
