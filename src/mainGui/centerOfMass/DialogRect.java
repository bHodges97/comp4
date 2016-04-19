package mainGui.centerOfMass;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
	Obj returnObj;
	PanelRectangle panelDiagram = new PanelRectangle();
	boolean xFilled = false, yFilled = false;

	DocumentListener docListener = new DocumentListener() {
		@Override
		public void changedUpdate(DocumentEvent e) {
			updatePanel();
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			updatePanel();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			updatePanel();
		}
	};

	KeyListener keyListener = new KeyListener() {
		@Override
		public void keyPressed(KeyEvent e) {
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

		@Override
		public void keyTyped(KeyEvent e) {
			if (e.getSource() == comx) {
				xFilled = true;
			}
			if (e.getSource() == comy) {
				yFilled = true;
			}
		}
	};

	public DialogRect() {

		placeFields();
		butDone.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				double xval, yval, w, h, cx, cy;
				float massval;
				// Loops through all panels inside dialog box
				for (Component panel : getContentPane().getComponents()) {
					if (panel instanceof JPanel) {
						// loop through panel components
						for (Component component : ((JPanel) panel).getComponents()) {
							if (component instanceof JTextField) {
								if (!MathUtil.isNumeric(((JTextField) component).getText())) {
									JOptionPane.showMessageDialog(null, "All fields must be numeric.");
									return;
								}
							}
						}
					}
				}
				xval = Double.parseDouble(x.getText());
				yval = Double.parseDouble(y.getText());
				massval = Float.parseFloat(mass.getText());
				w = Double.parseDouble(width.getText());
				h = Double.parseDouble(height.getText());
				cx = Double.parseDouble(comx.getText());
				cy = Double.parseDouble(comy.getText());
				Shape s = new Shape(new MyPoint[] { new MyPoint(0 - cx, h - cy), new MyPoint(w - cx, h - cy),
						new MyPoint(w - cx, 0 - cy), new MyPoint(0 - cx, 0 - cy) });
				returnObj = new Obj(Obj.POLYGON, new MyPoint(xval + cx, yval + cy), s, massval);
				dispose();
			}
		});

		width.getDocument().addDocumentListener(docListener);
		height.getDocument().addDocumentListener(docListener);
		comx.getDocument().addDocumentListener(docListener);
		comy.getDocument().addDocumentListener(docListener);
		comx.addKeyListener(keyListener);
		comy.addKeyListener(keyListener);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				width.getDocument().removeDocumentListener(docListener);
				height.getDocument().removeDocumentListener(docListener);
				comx.getDocument().removeDocumentListener(docListener);
				comy.getDocument().removeDocumentListener(docListener);
			}
		});

		setResizable(false);
		setModal(true);
		GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point center = g.getCenterPoint();
		setLocation(center.x - prefSize.width / 2, center.y - prefSize.height / 2);
		pack();
		setVisible(true);
	}

	private void placeFields() {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.WEST;

		setFocusable(true);
		setTitle("AddPointMass");

		Border etchedBorder = BorderFactory.createEtchedBorder(1);

		JPanel panelMass = new JPanel(new GridLayout(0, 2));
		JPanel panelDimension = new JPanel(new GridLayout(0, 2));
		JPanel panelPosition = new JPanel(new GridLayout(0, 2));
		JPanel panelCenter = new JPanel(new GridLayout(0, 2));
		JPanel panelDone = new JPanel(new GridLayout(0, 2));

		panelMass.setBorder(BorderFactory.createTitledBorder(etchedBorder, "Mass"));
		panelDimension.setBorder(BorderFactory.createTitledBorder(etchedBorder, "Dimensions"));
		panelPosition.setBorder(BorderFactory.createTitledBorder(etchedBorder, "Position"));
		panelCenter.setBorder(BorderFactory.createTitledBorder(etchedBorder, "Center of mass"));

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

	}

	private void updatePanel() {
		// Cast to Double then int to fix number format exception
		if (MathUtil.isNumeric(width.getText())) {
			panelDiagram.setX(Double.parseDouble(width.getText()));
		}
		if (MathUtil.isNumeric(height.getText())) {
			panelDiagram.setY(Double.parseDouble(height.getText()));
		}
		if (MathUtil.isNumeric(comx.getText())) {
			panelDiagram.setCOMX(Double.parseDouble(comx.getText()));
		}
		if (MathUtil.isNumeric(comy.getText())) {
			panelDiagram.setCOMY(Double.parseDouble(comy.getText()));
		}
		if (!xFilled && MathUtil.isNumeric(width.getText())) {
			comx.getDocument().removeDocumentListener(docListener);
			comx.setText((Double.parseDouble(width.getText()) / 2) + "");
			comx.getDocument().addDocumentListener(docListener);
		}
		if (!yFilled && MathUtil.isNumeric(height.getText())) {
			comy.getDocument().removeDocumentListener(docListener);
			comy.setText((Double.parseDouble(height.getText()) / 2) + "");
			comy.getDocument().addDocumentListener(docListener);
		}

		// Repaint panel
		panelDiagram.repaint();
	}
}
