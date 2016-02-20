package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import circularMotion.CircDialog;
import circularMotion.CircTopDown;
import circularMotion.CircVertical;
import math.MathUtil;
import math.Var;

/**
 * Comp4 CourseWork Creates main window for application
 * 
 * @author j00791
 * @version 0;
 */
public class Frame extends JFrame {
	/**
	 * Don't plan on serialising but conform anyway.
	 */
	private static final long serialVersionUID = 1L;
	static Frame window;
	Dialog popup = new Dialog();
	String topic = "Default";

	// collision
	boolean colA;
	Var[] a;
	Var[] b;
	Var e;
	ColDiagram diagram;

	// CircularMotion
	CircTopDown circTopDown;
	CircVertical circVertical;
	JPanel panelDiagram;
	JPanel panelFields;

	// CenterOfMass
	Panel canvas = new Panel("Default");
	JPanel sidepanel = new JPanel();
	sidepanelNorth sideNorth;
	sidepanelSouth sideSouth;

	public Frame() {
		// this.setLayout();
		setExtendedState(MAXIMIZED_BOTH);
		setTopic(popup.topic);

		if (popup.topic.equals("Circles")) {
			initCircularMotion();
		}
		if (popup.topic.equals("Center")) {
			initCenterOfMass();
		}
		if (popup.topic.equals("Collisions")) {
			initCollisions();
		}

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);

	}

	private void setTopic(String t) {
		setTitle(t);
		canvas.setTopic(t);
	}

	private void initCollisions() {
		a = new Var[5];
		b = new Var[5];
		e = new Var("e", "?", "e", false);
		a[0] = new Var("a", "A", "1", false);
		a[1] = new Var("m1", "?", "M1", false);
		a[2] = new Var("v1", "?", "V1", false);
		a[3] = new Var("u1", "?", "U1", false);
		a[4] = new Var("i1", "?", "i1", false);
		b[0] = new Var("b", "B", "2", false);
		b[1] = new Var("m2", "?", "M2", false);
		b[2] = new Var("v2", "?", "V2", false);
		b[3] = new Var("u2", "?", "U2", false);
		b[4] = new Var("i2", "?", "i2", false);

		// layout
		diagram = new ColDiagram(a, b, e);
		JPanel westPanel = new JPanel();
		this.add(westPanel, BorderLayout.WEST);
		this.add(diagram, BorderLayout.CENTER);
		JPanel fields = new JPanel();
		JPanel text = new JPanel();
		diagram.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		fields.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		text.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		westPanel.setLayout(new GridLayout(0, 1, 0, 0));
		westPanel.add(text);
		westPanel.add(fields);

		// text;
		text.setLayout(new BorderLayout(0, 0));
		JTextField topicTitle = new JTextField("Coefficient of Restitution; Impulse");
		topicTitle.setEditable(false);
		topicTitle.setFont(topicTitle.getFont().deriveFont(1.2f * topicTitle.getFont().getSize()));
		JTextArea topicDesc = new JTextArea();
		topicDesc.setEditable(false);
		topicDesc.setText(
				"\nNewton's experimental law:\n   •Seperation speed = e * approach speed. \n   • 0 ≤ e ≤ 1 \n   •Perfectly elastic : e = 1	Inelastic e = 0 \n");
		topicDesc.append(
				"Momentum:\n   •impluse = change in momentum \n   •In a closed system, the total momentum is constant.\n   •m1 * v1 + m2 * v2 = m1 * u1 + m2 * u2");
		text.add(topicTitle, BorderLayout.NORTH);
		text.add(topicDesc, BorderLayout.CENTER);

		// fields
		final JLabel textCurrent = new JLabel("Currently selected object: A.");
		JLabel textDesc = new JLabel(
				"<html>Click on diagram to select point mass. <br>Use \"?\" for unknown variables. Units used are<br> kg, m/s, Ns");
		JLabel textE = new JLabel("Coefficient of restitution");
		JLabel textLabel = new JLabel("Label");
		JLabel textMass = new JLabel("Mass");
		JLabel textU = new JLabel("Initial velocity");
		JLabel textV = new JLabel("Final velocity");
		JLabel textImpulse = new JLabel("Impulse");
		final JTextField fieldE = new JTextField(10);
		final JTextField fieldLabel = new JTextField(10);
		final JTextField fieldMass = new JTextField(10);
		final JTextField fieldU = new JTextField(10);
		final JTextField fieldV = new JTextField(10);
		final JTextField fieldImpulse = new JTextField(10);
		fieldE.setText("?");
		fieldLabel.setText("A");
		fieldMass.setText("?");
		fieldU.setText("?");
		fieldV.setText("?");
		fieldImpulse.setText("?");

		fields.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(2, 2, 2, 2);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		fields.add(textCurrent, gbc);
		gbc.gridwidth = 2;
		gbc.gridx = 0;
		gbc.gridy = 1;
		fields.add(textDesc, gbc);

		gbc.gridwidth = 1;

		gbc.gridx = 1;
		gbc.gridy = 2;
		fields.add(fieldE, gbc);
		gbc.gridy = 3;
		fields.add(fieldLabel, gbc);
		gbc.gridy = 4;
		fields.add(fieldMass, gbc);
		gbc.gridy = 5;
		fields.add(fieldU, gbc);
		gbc.gridy = 6;
		fields.add(fieldV, gbc);
		gbc.gridy = 7;
		fields.add(fieldImpulse, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		fields.add(textE, gbc);
		gbc.gridy = 3;
		fields.add(textLabel, gbc);
		gbc.gridy = 4;
		fields.add(textMass, gbc);
		gbc.gridy = 5;
		fields.add(textU, gbc);
		gbc.gridy = 6;
		fields.add(textV, gbc);
		gbc.gridy = 7;
		fields.add(textImpulse, gbc);

		diagram.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// Do nothing

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// Do nothing

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// Do nothing

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// Do nothing

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// Select object based on mouse click.
				if (Math.abs(e.getY() - diagram.getHeight() / 2) < diagram.getHeight() * 0.1
						&& (e.getX() < diagram.getWidth() / 4
								|| (e.getX() > diagram.getWidth() / 2 && e.getX() < diagram.getWidth() * 0.75))) {
					set(true);
				} else if (Math.abs(e.getY() - diagram.getHeight() / 2) < diagram.getHeight() * 0.1
						&& (e.getX() > diagram.getWidth() / 4
								|| (e.getX() < diagram.getWidth() / 2 && e.getX() > diagram.getWidth() * 0.25))) {
					set(false);

				}

			}

			private void set(boolean A) {
				if (A) {
					fieldLabel.setText(a[0].contents);
					fieldMass.setText(a[1].contents);
					fieldU.setText(a[2].contents);
					fieldV.setText(a[3].contents);
					fieldImpulse.setText(a[4].contents);
					textCurrent.setText("Currently selected object: " + fieldLabel.getText());
				} else {
					fieldLabel.setText(b[0].contents);
					fieldMass.setText(b[1].contents);
					fieldU.setText(b[2].contents);
					fieldV.setText(b[3].contents);
					fieldImpulse.setText(b[4].contents);
					textCurrent.setText("Currently selected object: " + fieldLabel.getText());
				}
			}
		});

		fieldE.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {

			}

			@Override
			public void focusLost(FocusEvent event) {
				if (!MathUtil.isNumeric(fieldE.getText())) {
					JOptionPane.showMessageDialog(Frame.this, "Not a number.");
					return;
				}
				if (Double.parseDouble(fieldE.getText()) > 1 || Double.parseDouble(fieldE.getText()) < 0) {
					JOptionPane.showMessageDialog(Frame.this, "Must follow: 0 <= e <= 1");
					return;
				}
				e.setContents(fieldE.getText(), true);
				colUpdate();
			}

		});
		fieldLabel.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {

			}

			@Override
			public void focusLost(FocusEvent e) {
				if (colA) {
					a[0].setContents(fieldLabel.getText(), true);
				} else {
					b[0].setContents(fieldLabel.getText(), true);
				}
			}

		});
		fieldMass.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {

			}

			@Override
			public void focusLost(FocusEvent arg0) {
				if (fieldMass.getText().equals("?")) {
					return;
				}
				if (!MathUtil.isNumeric(fieldMass.getText())) {
					JOptionPane.showMessageDialog(Frame.this, "Not a number.");
					return;
				}
				double x = Double.parseDouble(fieldMass.getText());
				if (x < 0) {
					JOptionPane.showMessageDialog(Frame.this, "Mass should not be negative.");
					return;
				}

				if (colA) {
					a[1].setContents(fieldMass.getText(), true);
				} else {
					b[1].setContents(fieldMass.getText(), true);
				}
				colUpdate();
			}
		});

		fieldU.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				// Do nothing.

			}

			@Override
			public void focusLost(FocusEvent e) {
				if (!MathUtil.isNumeric(fieldU.getText())) {
					JOptionPane.showMessageDialog(Frame.this, "Not a number.");
					return;
				}
				if (colA) {
					a[2].setContents(fieldU.getText(), true);
				} else {
					b[2].setContents(fieldU.getText(), true);
				}
				colUpdate();
			}

		});
		fieldV.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				// Do nothing.

			}

			@Override
			public void focusLost(FocusEvent e) {
				if (!MathUtil.isNumeric(fieldV.getText())) {
					JOptionPane.showMessageDialog(Frame.this, "Not a number.");
					return;
				}
				if (colA) {
					a[3].setContents(fieldV.getText(), true);
				} else {
					b[3].setContents(fieldV.getText(), true);
				}
				colUpdate();
			}

		});
		fieldImpulse.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				// Do nothing.

			}

			@Override
			public void focusLost(FocusEvent e) {
				if (!MathUtil.isNumeric(fieldImpulse.getText())) {
					JOptionPane.showMessageDialog(Frame.this, "Not a number.");
					return;
				}
				if (colA) {
					a[0].setContents(fieldImpulse.getText(), true);
				} else {
					b[0].setContents(fieldImpulse.getText(), true);
				}
				colUpdate();
			}

		});

		/*
		 * Repaint every panel used!!!
		 */
		Thread update = new Thread() {
			public void run() {

				while (true) {
					repaint();
					revalidate();
					diagram.repaint();

					try {
						Thread.sleep((long) 100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		update.start();

	}

	private void initCircularMotion() {
		panelDiagram = new JPanel();
		panelFields = new JPanel();
		panelDiagram.setLayout(new GridLayout());
		panelDiagram.setBorder(BorderFactory.createLineBorder(Color.black));
		this.add(panelFields, BorderLayout.WEST);
		this.add(panelDiagram, BorderLayout.CENTER);

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		circTopDown = new CircTopDown();
		panelDiagram.add(circTopDown);
		c.gridx++;
		circVertical = new CircVertical();
		panelDiagram.add(circVertical);

		Thread update = new Thread() {
			public void run() {
				CircDialog form = new CircDialog(Frame.this);
				circVertical.c = form;
				circTopDown.c = form;

				while (true) {
					repaint();
					revalidate();
					circTopDown.repaint();
					circVertical.repaint();
					try {
						Thread.sleep((long) 100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		update.start();

	}

	private void initCenterOfMass() {
		this.add(canvas, BorderLayout.CENTER);
		this.add(sidepanel, BorderLayout.EAST);
		sideNorth = new sidepanelNorth(canvas.plane);
		sideSouth = new sidepanelSouth(0, canvas.plane);
		sidepanel.setPreferredSize(new Dimension(300, this.getHeight()));
		sidepanel.setBorder(BorderFactory.createLineBorder(Color.black));
		sidepanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.weighty = 1;
		c.weightx = 1;
		c.fill = c.BOTH;
		c.gridy = 0;
		sidepanel.add(sideNorth.p, c);
		c.gridy++;
		sidepanel.add(sideSouth, c);
		Thread update = new Thread() {
			public void run() {
				while (true) {
					repaint();
					revalidate();
					sideSouth.setObj(canvas.currentObj);
					canvas.repaint();

					try {
						Thread.sleep((long) 50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		update.start();
	}

	private void colUpdate() {
		// TODO: MATH LOGIC STUFF
	}

	/**
	 * MAIN METHOD
	 * 
	 * @param Args
	 */
	public static void main(String[] Args) {
		window = new Frame();
	}
}
