package mainGui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import mainGui.centerOfMass.Panel;
import mainGui.centerOfMass.sidepanelNorth;
import mainGui.centerOfMass.sidepanelSouth;
import mainGui.circularMotion.CircTopDown;
import mainGui.circularMotion.CircVertical;
import mainGui.collision.ColDiagram;
import mainGui.projectileMotion.ProjDiagram;
import math.Definition;
import math.MathUtil;
import math.Solver;
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
	// Border border = BorderFactory.createLineBorder(Color.BLACK, 0);
	Border border = BorderFactory.createEtchedBorder(1);

	// collision
	boolean colA = true;
	/**
	 * 0 a <br>
	 * 1 m1 <br>
	 * 2 v1 <br>
	 * 3 u1 <br>
	 * 4 i1
	 */
	Var[] a;
	Var[] b;
	Var e;
	ColDiagram colDiagram;

	// CircularMotion
	/**
	 * Variables used in circular motion.<br>
	 * 0 = w<br>
	 * 1 = m<br>
	 * 2 = u<br>
	 * 3 = x<br>
	 * 4 = v<br>
	 * 5 = r<br>
	 * 6 = a<br>
	 * 7 = t<br>
	 */
	Var[] circVars;
	CircTopDown circTopDown;
	CircVertical circVertical;
	JTextField textW;
	JTextField textM;
	JTextField textU;
	JTextField textX;
	JTextField textV;
	JTextField textR;
	JTextField textA;
	JTextField textT;
	JTextField circX;
	JTextField circY;
	List<JTextField> circF = new ArrayList<JTextField>();
	List<JTextField> circT = new ArrayList<JTextField>();
	/**
	 * Magnitude list.
	 */
	List<String> circTextA = new ArrayList<String>();
	/**
	 * Direction list. Angle begins at 3 o'clock in radians.
	 */
	List<String> circTextB = new ArrayList<String>();
	Var[] circVarB;
	JButton circB;
	JPanel panelSouthS;
	JTextField circLblX;
	JTextField circLblY;

	// CenterOfMass
	Panel canvas = new Panel();
	JPanel sidepanel = new JPanel(new GridBagLayout());
	sidepanelNorth sideNorth;
	sidepanelSouth sideSouth;

	// projectile
	ProjDiagram projDiagram;
	JTextField projTheta;
	JTextField projV;
	JTextField projVx;
	JTextField projVy;
	JTextField projY;
	JTextField projX;
	JTextField projT;
	JTextField projU;
	JTextField projUx;
	JTextField projUy;
	JTextField projS;
	JTextField projLabel;
	JTextField projSy;

	/**
	 * 0 a theta<br>
	 * 1 v v<br>
	 * 2 b Vx<br>
	 * 3 c Vy<br>
	 * 4 h Height<br>
	 * 5 z max distamce.<br>
	 * 6 t t<br>
	 * 7 u U<br>
	 * 8 d Ux<br>
	 * 9 e Uy<br>
	 * 10 x x<br>
	 * 11 label<br>
	 * 12 y y<br>
	 */
	Var[] projVars;

	public Frame() {
		// TODO:this.setLayout();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				setExtendedState(MAXIMIZED_BOTH);
				setTopic(popup.topic);

				myMenuBar menu = new myMenuBar(Frame.this);
				setJMenuBar(menu);

				if (popup.topic.equals("Circles")) {
					initCircularMotion();
				}
				if (popup.topic.equals("Center")) {
					initCenterOfMass();
				}
				if (popup.topic.equals("Collisions")) {
					initCollisions();
				}
				if (popup.topic.equals("Projectiles")) {
					initProjectiles();
				}

				setDefaultCloseOperation(EXIT_ON_CLOSE);
				pack();
				setVisible(true);

				/*
				 * Repaint every panel used!!!
				 */
				Thread update = new Thread() {
					public void run() {
						if (popup.topic.equals("Circles")) {
							circVertical.text = circVarB;
							circVertical.force = circTextA;
							circVertical.angle = circTextB;
							circTopDown.vars = circVars;
						}

						while (true) {
							repaint();
							revalidate();
							for (Component c : Frame.this.getComponents()) {
								if (c instanceof JPanel) {
									c.repaint();
								}
							}
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
		});

	}

	private void initCircularMotion() {
		setLayout(new BorderLayout(5, 5));
		// Initialised panels;
		JPanel panelDiagram = new JPanel(new GridLayout());
		JPanel panelFields = new JPanel(new GridBagLayout());
		JPanel panelWest = new JPanel(new GridLayout(0, 1, 5, 5));
		JPanel panelNorth = new JPanel(new BorderLayout(5, 5));
		panelSouthS = new JPanel(new GridBagLayout());
		JPanel panelSouth = new JPanel(new GridLayout(0, 1));
		JPanel panelSouthN = new JPanel(new GridBagLayout());
		JScrollPane scrollSouthS = new JScrollPane(panelSouthS);
		// Set borders
		scrollSouthS.setBorder(BorderFactory.createEmptyBorder());
		panelSouthS.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), "Forces"));
		panelSouthN.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(),
				"Position from 0,0(not center of rotation)"));
		panelFields.setBorder(border);
		// panelWest.setBorder(border);
		panelSouth.setBorder(border);
		panelNorth.setBorder(border);
		// Add to it.
		panelWest.add(panelNorth);
		panelWest.add(panelFields);
		panelWest.add(panelSouth);
		panelSouth.add(panelSouthN);
		panelSouth.add(scrollSouthS);
		this.add(panelWest, BorderLayout.WEST);
		this.add(panelDiagram, BorderLayout.CENTER);

		// Add diagram panels.
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		circTopDown = new CircTopDown();
		panelDiagram.add(circTopDown);
		circTopDown.setBorder(border);
		c.gridx++;
		circVertical = new CircVertical();
		panelDiagram.add(circVertical);
		circVertical.setBorder(border);

		// Initialise topic title and desc.
		// TODO: fill out description for circularMotion.
		JTextField topicTitle = new JTextField("Circular motion notes");
		topicTitle.setEditable(false);
		topicTitle.setFont(topicTitle.getFont().deriveFont(
				1.2f * topicTitle.getFont().getSize()));
		JTextArea topicDesc = new JTextArea();
		topicDesc.setColumns(26);
		topicDesc.setLineWrap(true);
		topicDesc.setText("TO DO ADD SOME TEXT");
		panelNorth.add(topicTitle, BorderLayout.NORTH);
		panelNorth.add(topicDesc, BorderLayout.CENTER);

		JLabel Explanation = new JLabel("Leave unknowns as \"?\".");
		int n = 9;
		textW = new JTextField("?", n);
		textM = new JTextField("?", n);
		textU = new JTextField("?", n);
		textX = new JTextField("?", n);
		textV = new JTextField("?", n);
		textR = new JTextField("?", n);
		textA = new JTextField("?", n);
		textT = new JTextField("?", n);
		circX = new JTextField("?", n);
		circY = new JTextField("?", n);
		circB = new JButton("Add Force");

		circVars = new Var[8];
		circVarB = new Var[2];
		circVars[0] = new Var("w", new String(textW.getText()), "w", false);
		circVars[1] = new Var("m", new String(textM.getText()), "m", false);
		circVars[2] = new Var("u", new String(textU.getText()), "u", false);
		circVars[3] = new Var("x", new String(textX.getText()), "x", false);
		circVars[4] = new Var("v", new String(textV.getText()), "v", false);
		circVars[5] = new Var("r", new String(textR.getText()), "r", false);
		circVars[6] = new Var("a", new String(textA.getText()), "a", false);
		circVars[7] = new Var("t", new String(textT.getText()), "t", false);
		circVarB[0] = new Var("x", "?", "x", false);
		circVarB[1] = new Var("y", "?", "y", false);

		addListener(textW, circVars[0], 2, null);
		addListener(textM, circVars[1], 1, null);
		addListener(textU, circVars[2], 4, null);
		addListener(textX, circVars[3], 4, null);
		addListener(textV, circVars[4], -1, null);
		addListener(textR, circVars[5], 1, null);
		addListener(textA, circVars[6], 1, null);
		addListener(textT, circVars[7], 1, null);

		c = new GridBagConstraints();
		c.gridx = 0;
		c.weighty = 0;
		c.anchor = c.FIRST_LINE_START;
		c.gridy = 0;
		c.insets = new Insets(2, 2, 2, 2);
		c.weightx = 1;
		panelFields.add(Explanation, c);
		c.gridy++;
		c.gridx = 0;
		panelFields.add(new JLabel("Start Angle"), c);
		c.gridx = 1;
		panelFields.add(textU, c);

		c.gridy++;
		c.gridx = 0;
		panelFields.add(new JLabel("End Angle"), c);
		c.gridx = 1;
		panelFields.add(textX, c);

		c.gridy++;
		c.gridx = 0;
		panelFields.add(new JLabel("Angular Velocity"), c);
		c.gridx = 1;
		panelFields.add(textW, c);

		c.gridy++;
		c.gridx = 0;
		panelFields.add(new JLabel("Tangential Velocity"), c);
		c.gridx = 1;
		panelFields.add(textV, c);

		c.gridy++;
		c.gridx = 0;
		panelFields.add(new JLabel("Mass"), c);
		c.gridx = 1;
		panelFields.add(textM, c);

		c.gridy++;
		c.gridx = 0;
		panelFields.add(new JLabel("Radius"), c);
		c.gridx = 1;
		panelFields.add(textR, c);

		c.gridy++;
		c.gridx = 0;
		panelFields.add(new JLabel("Centripetal Acceleration"), c);
		c.gridx = 1;
		panelFields.add(textA, c);

		c.gridy++;
		c.gridx = 0;
		panelFields.add(new JLabel("Time"), c);
		c.gridx = 1;
		panelFields.add(textT, c);

		// Layout panelSouthN;
		circLblX = new JTextField("Sum of horizontal forces: ?");
		circLblY = new JTextField("Sum of vertical forces  : ?");
		circLblX.setEditable(false);
		circLblY.setEditable(false);
		c.gridx = 0;
		c.gridy = 0;
		panelSouthN.add(new JLabel("X:"), c);
		c.gridy++;
		panelSouthN.add(new JLabel("Y:"), c);
		c.gridwidth = 2;
		c.gridy++;
		panelSouthN.add(circLblX, c);
		c.gridy++;
		panelSouthN.add(circLblY, c);
		c.anchor = c.EAST;
		c.gridwidth = 1;
		c.gridy = 4;
		c.gridx = 1;
		panelSouthN.add(circB, c);
		c.gridy = 0;
		panelSouthN.add(circX, c);
		c.gridy++;
		panelSouthN.add(circY, c);
		c.anchor = c.FIRST_LINE_START;
		addListener(circX, circVarB[0], -1, null);
		addListener(circY, circVarB[1], -1, null);

		c.weighty = 1;
		c.gridy = 0;
		c.gridx = 0;
		panelSouthS.add(new JLabel("Magnitude"), c);
		c.gridx = 1;
		JLabel lbl = new JLabel("Direction.");
		lbl.setToolTipText("radians anticlock wise from 3 o'clock position");

		panelSouthS.add(lbl, c);
		circB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				GridBagConstraints c = new GridBagConstraints();
				circF.add(new JTextField("0", 9));
				circT.add(new JTextField("0", 9));
				circTextA.add("0");
				circTextB.add("0");
				final JTextField a = circF.get(circF.size() - 1);
				final JTextField b = circT.get(circT.size() - 1);
				c.gridy = circF.size();
				c.gridx = 0;
				c.weightx = 1;
				c.anchor = c.FIRST_LINE_START;
				panelSouthS.add(a, c);
				c.gridx = 1;
				panelSouthS.add(b, c);

				a.addFocusListener(new FocusListener() {
					@Override
					public void focusLost(FocusEvent e) {
						if (!MathUtil.isNumeric(a.getText())) {
							JOptionPane.showMessageDialog(Frame.this,
									"Must be numeric.");
							return;
						}
						circTextA.set(circF.indexOf(a), a.getText());
						circUpdate();
					}

					@Override
					public void focusGained(FocusEvent e) {
						// Do nothing
					}
				});
				a.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (!MathUtil.isNumeric(a.getText())) {
							JOptionPane.showMessageDialog(Frame.this,
									"Must be numeric.");
							return;
						}
						circTextA.set(circF.indexOf(a), a.getText());
						circUpdate();
					}
				});
				b.addFocusListener(new FocusListener() {
					@Override
					public void focusLost(FocusEvent e) {
						if (!MathUtil.isNumeric(b.getText())) {
							JOptionPane.showMessageDialog(Frame.this,
									"Must be numeric.");
							return;
						}
						circTextB.set(circT.indexOf(b), b.getText());
						circUpdate();
					}

					@Override
					public void focusGained(FocusEvent e) {
						// Do nothing
					}
				});
				b.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (!MathUtil.isNumeric(b.getText())) {
							JOptionPane.showMessageDialog(Frame.this,
									"Must be numeric.");
							return;
						}
						circTextB.set(circT.indexOf(b), b.getText());
						circUpdate();
					}
				});
			}
		});

	}

	private void initProjectiles() {

		projVars = new Var[13];
		projDiagram = new ProjDiagram(projVars);
		add(projDiagram, BorderLayout.CENTER);
		JPanel sidePanel = new JPanel(new GridLayout(0, 1));
		add(sidePanel, BorderLayout.WEST);
		JPanel northPanel = new JPanel(new BorderLayout(0, 0));
		JPanel southPanel = new JPanel(new GridLayout(0, 1));
		sidePanel.add(northPanel);
		sidePanel.add(southPanel);

		northPanel.setBorder(border);
		southPanel.setBorder(border);
		projDiagram.setBorder(border);

		JTextField topicTitle = new JTextField("Projectile motion notes");
		topicTitle.setEditable(false);
		topicTitle.setFont(topicTitle.getFont().deriveFont(
				1.2f * topicTitle.getFont().getSize()));
		JTextArea topicDesc = new JTextArea();
		topicDesc.setColumns(26);
		topicDesc.setLineWrap(true);
		topicDesc
				.setText("\nEquation of trajectory:\n   •y = x*tan(θ)-g*x^2/(2*v^2*cos^2(θ))\n");
		topicDesc
				.append("Acceleration:\n   •Constant acceleration of 9.8 ms^-2 downwards.\n   •No horizontal acceleration, horizontal velocity is constant.\n");
		topicDesc
				.append("Velocity\n   •Velocity in x direction is V*cos(θ) \n   •Velocity in y direction is V*sin(θ).\n");
		northPanel.add(topicTitle, BorderLayout.NORTH);
		northPanel.add(topicDesc, BorderLayout.CENTER);

		projTheta = new JTextField("?", 9);
		projV = new JTextField("?", 9);
		projVx = new JTextField("?", 9);
		projVy = new JTextField("?", 9);
		projY = new JTextField("?", 9);
		projX = new JTextField("?", 9);
		projT = new JTextField("?", 9);
		projU = new JTextField("?", 9);
		projUx = new JTextField("?", 9);
		projUy = new JTextField("?", 9);
		projS = new JTextField("?", 9);
		projLabel = new JTextField("A", 9);
		projUy = new JTextField("?", 9);
		projSy = new JTextField("?", 9);

		projVars[0] = new Var("a", new String(projTheta.getText()), "θ", false);
		projVars[1] = new Var("v", new String(projV.getText()), "V", false);
		projVars[2] = new Var("b", new String(projVx.getText()), "Vx", false);
		projVars[3] = new Var("c", new String(projVy.getText()), "Vy", false);
		projVars[4] = new Var("h", new String(projY.getText()), "Height", false);
		projVars[5] = new Var("z", new String(projX.getText()), "NOT USED",
				false);
		projVars[6] = new Var("t", new String(projT.getText()), "t", false);
		projVars[7] = new Var("u", new String(projUx.getText()), "U", false);
		projVars[8] = new Var("d", new String(projUx.getText()), "Ux", false);
		projVars[9] = new Var("e", new String(projUy.getText()), "Uy", false);
		projVars[10] = new Var("x", new String(projS.getText()), "x", false);
		projVars[11] = new Var("", new String(projLabel.getText()), "A", false);
		projVars[12] = new Var("y", new String(projSy.getText()), "y", false);

		addListener(projTheta, projVars[0], 4, null);
		addListener(projV, projVars[1], -1, null);
		addListener(projVx, projVars[2], -1, null);
		addListener(projVy, projVars[3], -1, null);
		addListener(projY, projVars[4], 0, null);
		addListener(projX, projVars[5], 0, null);
		addListener(projT, projVars[6], 0, null);
		addListener(projU, projVars[7], -1, null);
		addListener(projUx, projVars[8], 0, null);
		addListener(projUy, projVars[9], -1, null);
		addListener(projS, projVars[10], 0, null);
		addListener(projSy, projVars[12], 0, null);
		addListener(projLabel, projVars[11], -2, null);

		JPanel others = new JPanel(new GridBagLayout());
		JPanel before = new JPanel(new GridBagLayout());
		before.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), "Initial conditions"));
		JPanel after = new JPanel(new GridBagLayout());
		after.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), "When object hits someting"));

		southPanel.add(before);
		southPanel.add(after);
		southPanel.add(others);

		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(2, 2, 2, 2);
		c.anchor = GridBagConstraints.WEST;
		c.weightx = 1;

		c.gridx = 0;
		c.gridy = 0;
		before.add(new JLabel("Height:"), c);
		c.gridy++;
		before.add(new JLabel("Angle(radians)"), c);
		c.gridy++;
		before.add(new JLabel("Velocity:"), c);
		c.gridy++;
		before.add(new JLabel("Velocity(x component):"), c);
		c.gridy++;
		before.add(new JLabel("Velocity(y component):"), c);

		c.gridx = 1;
		c.gridy = 0;
		before.add(projY, c);
		c.gridy++;
		before.add(projTheta, c);
		c.gridy++;
		before.add(projU, c);
		c.gridy++;
		before.add(projUx, c);
		c.gridy++;
		before.add(projUy, c);

		c.gridx = 0;
		c.gridy = 0;
		after.add(new JLabel("Time:"), c);
		c.gridy++;
		after.add(new JLabel("Velocity:"), c);
		c.gridy++;
		after.add(new JLabel("Velocity(x component):"), c);
		c.gridy++;
		after.add(new JLabel("Velocity(y component):"), c);
		c.gridy++;
		after.add(new JLabel("X position:"), c);
		c.gridy++;
		after.add(new JLabel("Y position:"), c);

		c.gridx = 1;
		c.gridy = 0;
		after.add(projT, c);
		c.gridy++;
		after.add(projV, c);
		c.gridy++;
		after.add(projVx, c);
		c.gridy++;
		after.add(projVy, c);
		c.gridy++;
		after.add(projS, c);
		c.gridy++;
		after.add(projSy, c);

		c.gridx = 0;
		c.gridy = 0;
		others.add(new JLabel("Name:          "), c);
		c.gridx = 1;
		others.add(projLabel, c);
		c.gridx = 0;
		c.gridy = 1;
		others.add(new JLabel("Max distance:                "), c);
		c.gridx = 1;
		others.add(projX, c);

		Thread update = new Thread() {
			public void run() {

				while (true) {
					repaint();
					revalidate();
					projDiagram.repaint();

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

	private void setTopic(String t) {
		setTitle(t);
		topic = t;
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
		colDiagram = new ColDiagram(a, b, e);
		JPanel westPanel = new JPanel(new GridLayout(0, 1));
		this.add(westPanel, BorderLayout.WEST);
		this.add(colDiagram, BorderLayout.CENTER);
		JPanel fields = new JPanel(new GridBagLayout());
		JPanel text = new JPanel(new BorderLayout(0, 0));
		colDiagram.setBorder(border);
		fields.setBorder(border);
		text.setBorder(border);
		westPanel.add(text);
		westPanel.add(fields);

		// text;
		JTextField topicTitle = new JTextField(
				"Coefficient of restitution and impulse notes");
		topicTitle.setEditable(false);
		topicTitle.setFont(topicTitle.getFont().deriveFont(
				1.2f * topicTitle.getFont().getSize()));
		JTextArea topicDesc = new JTextArea();
		topicDesc
				.setText("\nNewton's experimental law:\n   •Seperation speed = e * approach speed. \n   • 0 ≤ e ≤ 1 \n   •Perfectly elastic : e = 1	Inelastic e = 0 \n");
		topicDesc
				.append("Momentum:\n   •impluse = change in momentum \n   •In a closed system, the total momentum is constant.\n   •m1 * v1 + m2 * v2 = m1 * u1 + m2 * u2");

		topicDesc.setColumns(26);
		topicDesc.setLineWrap(true);
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
		final JTextField fieldE = new JTextField("?", 10);
		final JTextField fieldLabel = new JTextField("A", 10);
		final JTextField fieldMass = new JTextField("?", 10);
		final JTextField fieldU = new JTextField("?", 10);
		final JTextField fieldV = new JTextField("?", 10);
		final JTextField fieldImpulse = new JTextField("?", 10);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(2, 2, 2, 2);
		gbc.weightx = 1;
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

		colDiagram.addMouseListener(new MouseListener() {

			// Do nothing
			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// Select object based on mouse click.
				if (Math.abs(e.getY() - colDiagram.getHeight() / 2) < colDiagram
						.getHeight() * 0.1
						&& (e.getX() < colDiagram.getWidth() / 4 || (e.getX() > colDiagram
								.getWidth() / 2 && e.getX() < colDiagram
								.getWidth() * 0.75))) {
					set(true);
				} else if (Math.abs(e.getY() - colDiagram.getHeight() / 2) < colDiagram
						.getHeight() * 0.1
						&& (e.getX() > colDiagram.getWidth() / 4 || (e.getX() < colDiagram
								.getWidth() / 2 && e.getX() > colDiagram
								.getWidth() * 0.25))) {
					set(false);
				}
			}

			private void set(boolean A) {
				if (A) {
					colA = true;
					fieldLabel.setText(a[0].contents);
					fieldMass.setText(a[1].contents);
					fieldU.setText(a[2].contents);
					fieldV.setText(a[3].contents);
					fieldImpulse.setText(a[4].contents);
					textCurrent.setText("Currently selected object: "
							+ fieldLabel.getText());
				} else {
					colA = false;
					fieldLabel.setText(b[0].contents);
					fieldMass.setText(b[1].contents);
					fieldU.setText(b[2].contents);
					fieldV.setText(b[3].contents);
					fieldImpulse.setText(b[4].contents);
					textCurrent.setText("Currently selected object: "
							+ fieldLabel.getText());
				}
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		addListener(fieldE, e, 5, e);
		addListener(fieldLabel, a[0], -2, b[0]);
		addListener(fieldMass, a[1], 0, b[1]);
		addListener(fieldU, a[2], -1, b[2]);
		addListener(fieldV, a[3], -1, b[3]);
		addListener(fieldImpulse, a[4], -1, b[4]);
	}

	private void initCenterOfMass() {
		this.add(canvas, BorderLayout.CENTER);
		this.add(sidepanel, BorderLayout.WEST);
		sideNorth = new sidepanelNorth(canvas.plane);
		sideSouth = new sidepanelSouth(0, canvas.plane);
		sidepanel.setPreferredSize(new Dimension(300, this.getHeight()));
		sidepanel.setBorder(border);

		JPanel panelNorth = new JPanel(new BorderLayout());
		// Initialise topic title and desc.
		// TODO: fill out description.
		JTextField topicTitle = new JTextField("Center of mass notes");
		topicTitle.setEditable(false);
		topicTitle.setFont(topicTitle.getFont().deriveFont(
				1.2f * topicTitle.getFont().getSize()));
		JTextArea topicDesc = new JTextArea();
		topicDesc.setColumns(26);
		topicDesc.setLineWrap(true);
		topicDesc.setText("TO DO ADD SOME TEXT");
		panelNorth.add(topicTitle, BorderLayout.NORTH);
		panelNorth.add(topicDesc, BorderLayout.CENTER);

		GridBagConstraints c = new GridBagConstraints();
		c.weighty = 1;
		c.weightx = 1;
		c.fill = c.BOTH;
		c.gridy = 0;
		sidepanel.add(panelNorth, c);
		c.gridy++;
		sidepanel.add(sideNorth.p, c);
		c.gridy++;
		sidepanel.add(sideSouth, c);
	}

	/**
	 * Adds a focus listener and action listener to a component.
	 * 
	 * @param t
	 *            Text field to add listeners to.
	 * @param v
	 *            Variable text field is associated with.
	 * @param c
	 *            The type of verification: <br>
	 *            -2: No verification -1: Error if numeric <br>
	 *            0: Error if less than or equal to zero <br>
	 *            1: Error if less than zero <br>
	 *            2: Error if equal to zero <br>
	 *            3: Error if greater than 0 <br>
	 *            4: Warning if greater than 2 pi<br>
	 *            5: Special case for e
	 * 
	 */
	private void addListener(final JTextField t, final Var var1, final int c,
			final Var var2) {
		t.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {
				// DO NOTHING
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				addVerification(t, var1, c, var2);
			}
		});

		t.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addVerification(t, var1, c, var2);
			}
		});
	}

	/**
	 * Adds verification to a text field.
	 * 
	 * @param t
	 *            Text field to add verification to.
	 * @param v
	 *            Variable text field is associated with.
	 * @param c
	 *            The type of verification: <br>
	 *            -2: No verification -1: Error if numeric <br>
	 *            0: Error if less than or equal to zero <br>
	 *            1: Error if less than zero <br>
	 *            2: Error if equal to zero <br>
	 *            3: Error if greater than 0 <br>
	 *            4: Warning if greater than 2 pi<br>
	 *            5: Special case for e
	 * 
	 */
	private void addVerification(JTextField t, Var var1, int c, Var var2) {

		Var v;
		if (var2 != null && !colA) {
			v = var2;
		} else {
			v = var1;
		}

		if (t.getText().equals("?")) {
			v.setContents(t.getText(), false);
			return;
		}

		if (c > -2) {
			if (!MathUtil.isNumeric(t.getText())) {
				JOptionPane.showMessageDialog(Frame.this, "Not a number!",
						"Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		if (c == 0) {
			if (Double.parseDouble(t.getText()) < 0) {
				JOptionPane.showMessageDialog(Frame.this,
						"Must be greater than or equal to 0.");
				return;
			}
		}
		if (c == 2) {
			if (Double.parseDouble(t.getText()) == 0) {
				JOptionPane.showMessageDialog(Frame.this, "Must not equal 0.");
				return;
			}
		}
		if (c == 1) {
			if (Double.parseDouble(t.getText()) <= 0) {
				JOptionPane.showMessageDialog(Frame.this,
						"Must be greater than 0.");
				return;
			}
		}
		if (c == 3) {
			if (Double.parseDouble(t.getText()) > 0) {
				JOptionPane.showMessageDialog(Frame.this,
						"Must be less than 0.");
				return;
			}
		}
		if (c == 4) {
			if (Math.abs(Double.parseDouble(t.getText())) > 6.28) {
				JOptionPane.showMessageDialog(Frame.this,
						"Careful this value is greater than 2 PI");
			}
		}
		if (c == 5) {
			if (Double.parseDouble(t.getText()) > 1
					|| Double.parseDouble(t.getText()) < 0) {
				JOptionPane.showMessageDialog(Frame.this,
						"Must follow: 0 <= e <= 1", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		}

		v.setContents(new String(t.getText()), true);
	}

	/**
	 * Sums up x and y components.
	 */
	private void circUpdate() {
		double x = 0;
		double y = 0;
		for (int i = 0; i < circTextA.size(); i++) {
			x += Double.parseDouble(circTextA.get(i))
					* Math.cos(Double.parseDouble(circTextB.get(i)));
			y += Double.parseDouble(circTextA.get(i))
					* Math.sin(Double.parseDouble(circTextB.get(i)));
		}
		circLblX.setText("Sum of horizontal forces: " + x);
		circLblY.setText("Sum of vertical forces  : " + y);
	}

	/**
	 * 
	 */
	public void solve() {

		long startTime = System.currentTimeMillis();
		if (topic.equals("Circles")) {
			int confirm = JOptionPane.showConfirmDialog(Frame.this,
					"Attempts to find unkown variables if possible.", "Solver",
					JOptionPane.OK_CANCEL_OPTION);
			if (confirm == JOptionPane.CANCEL_OPTION
					|| confirm == JOptionPane.CLOSED_OPTION) {
				return;
			}

			if (!circVarB[0].isKnown() && circVars[5].isKnown()) {
				circVars[5].setContents(circVarB[0].contents, false);
			}
			System.out.println("Started");
			Definition[] defs = new Definition[9];
			defs[0] = new Definition("v=r*w");
			defs[1] = new Definition("w=v/r");
			defs[2] = new Definition("f=m*a");
			defs[3] = new Definition("a=v^2/r");
			defs[4] = new Definition("a=w^2*r");
			defs[5] = new Definition("x=t*w+u");
			defs[6] = new Definition("t=x-u/t");
			defs[7] = new Definition("r=v/w");
			defs[8] = new Definition("r=v^2/a");
			Solver s = new Solver(defs, circVars);

			// Update text fields.
			textW.setText(MathUtil.round(circVars[0].contents));
			textM.setText(MathUtil.round(circVars[1].contents));
			textU.setText(MathUtil.round(circVars[2].contents));
			textX.setText(MathUtil.round(circVars[3].contents));
			textV.setText(MathUtil.round(circVars[4].contents));
			textR.setText(MathUtil.round(circVars[5].contents));
			textA.setText(MathUtil.round(circVars[6].contents));
			textT.setText(MathUtil.round(circVars[7].contents));
			circB = new JButton("Add Force");
		} else if (topic.equals("Projectiles")) {
			int confirm = JOptionPane.showConfirmDialog(Frame.this,
					"Attempts to find unkown variables if possible.", "Solver",
					JOptionPane.OK_CANCEL_OPTION);
			if (confirm == JOptionPane.CANCEL_OPTION
					|| confirm == JOptionPane.CLOSED_OPTION) {
				return;
			}
			if (!projVars[9].isKnown() && !projVars[8].isKnown()) {
				projVars[0].setContents(
						""
								+ Math.atan(projVars[9].getVal()
										/ projVars[8].getVal()), false);
			}
			Definition[] defs = new Definition[11];
			defs[0] = new Definition("d=u*cos(a)");
			defs[1] = new Definition("e=u*sin(a)");
			defs[2] = new Definition("d=b");
			defs[3] = new Definition("b=d");
			defs[4] = new Definition("x=b*t");
			defs[5] = new Definition("e=9.8*t+c");
			defs[6] = new Definition("y=c+e/2*t+h");
			defs[7] = new Definition("y=t^2*0.5*-9.8+(e*t)");
			defs[8] = new Definition("v=(b^2)+(c^2)^1/2");
			defs[9] = new Definition("u=(d^2)+(e^2)^1/2");
			defs[10] = new Definition("v=(u^2)+0.5*a*(y-h)^1/2");
			Solver s = new Solver(defs, projVars);

			projTheta.setText(MathUtil.round(projVars[0].contents));
			projV.setText(MathUtil.round(projVars[1].contents));
			projVx.setText(MathUtil.round(projVars[2].contents));
			projVy.setText(MathUtil.round(projVars[3].contents));
			projY.setText(MathUtil.round(projVars[4].contents));
			projX.setText(MathUtil.round(projVars[5].contents));
			projT.setText(MathUtil.round(projVars[6].contents));
			projU.setText(MathUtil.round(projVars[7].contents));
			projUx.setText(MathUtil.round(projVars[8].contents));
			projUy.setText(MathUtil.round(projVars[9].contents));
			projS.setText(MathUtil.round(projVars[10].contents));
			projSy.setText(MathUtil.round(projVars[12].contents));

		} else if (topic.equals("Collisions")) {

			//mass 1
			if (!a[1].isKnown()
					&& (b[1].isKnown() && b[2].isKnown() && b[3].isKnown()
							&& a[3].isKnown() && a[2].isKnown())) {
				a[1].setContents(
						"" + b[1].getVal() * (b[2].getVal() - b[3].getVal())
								/ (a[3].getVal() - a[2].getVal()), false);
			}
			//mass 2
			if (!b[1].isKnown()
					&& (a[1].isKnown() && a[2].isKnown() && a[3].isKnown()
							&& b[3].isKnown() && b[2].isKnown())) {
				b[1].setContents(
						"" + a[1].getVal() * (a[3].getVal() - a[2].getVal())
								/ (b[2].getVal() - b[3].getVal()), false);
			}
			//e
			if (!e.isKnown()
					&& (a[2].isKnown() && a[3].isKnown() && b[2].isKnown() && b[3]
							.isKnown())) {
				e.setContents(
						"" + (b[2].getVal() - a[2].getVal())
								/ (a[3].getVal() - b[3].getVal()), false);
			}

		}
		System.out.println("Solver completed in "
				+ (System.currentTimeMillis() - startTime) + " ms");
	}

	public void save(String pathName) {
		if (pathName.endsWith(".png")) {
			pathName.replace(".png", "");
		}
		if (popup.topic.equals("Circles")) {
			try {
				ImageIO.write(
						joinBufferedImage(circTopDown.getImg(),
								circVertical.getImg()), "PNG", new File(
								pathName + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (popup.topic.equals("Center")) {
			initCenterOfMass();//TODO: FIIXIXIX
		}
		if (popup.topic.equals("Collisions")) {
			colDiagram.print(pathName);
		}
		if (popup.topic.equals("Projectiles")) {
			projDiagram.print(pathName);
		}

	}

	public static BufferedImage joinBufferedImage(BufferedImage imgA,
			BufferedImage imgB) {

		int width = imgA.getWidth() + imgB.getWidth();
		int height = imgA.getHeight();
		BufferedImage combinedImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = combinedImage.createGraphics();
		Color oldColor = g2d.getColor();
		g2d.setPaint(Color.WHITE);
		g2d.fillRect(0, 0, width, height);
		g2d.drawImage(imgA, null, 0, 0);
		g2d.drawImage(imgB, null, imgA.getWidth() + 1, 0);
		g2d.dispose();
		return combinedImage;

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
