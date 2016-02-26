package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

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
	// Border border = BorderFactory.createLineBorder(Color.BLACK, 0);
	Border border = BorderFactory.createEtchedBorder(1);

	// collision
	boolean colA;
	Var[] a;
	Var[] b;
	Var e;
	ColDiagram colDiagram;

	// CircularMotion
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
	List<String> circTextA = new ArrayList<String>();
	List<String> circTextB = new ArrayList<String>();
	Var[] circVarB;
	JButton circB;
	JPanel panelSouthS;

	// CenterOfMass
	Panel canvas = new Panel("Default");
	JPanel sidepanel = new JPanel();
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
	Var[] projVars;

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
		if (popup.topic.equals("Projectiles")) {
			initProjectiles();
		}

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);

	}

	private void initCircularMotion() {
		setLayout(new BorderLayout(5, 5));
		// Initialised panels;
		JPanel panelDiagram = new JPanel(new GridLayout());
		JPanel panelFields = new JPanel();
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

		textM.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent arg0) {
				if (textW.getText().equals("?")) {
					circVars[1].setContents("?", false);
					return;
				}
				if (!MathUtil.isNumeric(textW.getText())) {
					JOptionPane.showMessageDialog(Frame.this,
							"Must enter a number!", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (Double.parseDouble(textW.getText()) == 0) {
					JOptionPane.showMessageDialog(Frame.this,
							"Must not equal 0!", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				circVars[0].setContents(textW.getText(), true);
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				// DO NOTHING!
			}
		});
		textM.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent arg0) {
				if (textM.getText().equals("?")) {
					circVars[1].setContents("?", false);
					return;
				}
				if (!MathUtil.isNumeric(textM.getText())) {
					JOptionPane.showMessageDialog(Frame.this,
							"Must enter a number!", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (Double.parseDouble(textM.getText()) <= 0) {
					JOptionPane.showMessageDialog(Frame.this,
							"Must be greater than 0!", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				circVars[1].setContents(textM.getText(), true);
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				// DO NOTHING!
			}
		});
		textU.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent arg0) {
				if (textU.getText().equals("?")) {
					circVars[2].setContents("?", false);
					return;
				}
				if (!MathUtil.isNumeric(textU.getText())) {
					JOptionPane.showMessageDialog(Frame.this,
							"Must enter a number!", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (Math.abs(Double.parseDouble(textU.getText())) > 6.28) {
					JOptionPane.showMessageDialog(Frame.this,
							"Careful this value is greater than 2 PI");
				}
				circVars[2].setContents(textU.getText(), true);
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				// DO NOTHING!
			}
		});
		textX.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent arg0) {
				if (textX.getText().equals("?")) {
					circVars[3].setContents("?", false);
					return;
				}
				if (!MathUtil.isNumeric(textX.getText())) {
					JOptionPane.showMessageDialog(Frame.this,
							"Must enter a number!", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (Math.abs(Double.parseDouble(textX.getText())) > 6.28) {
					JOptionPane.showMessageDialog(Frame.this,
							"Careful this value is greater than 2 PI");
				}
				circVars[3].setContents(textX.getText(), true);
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				// DO NOTHING!
			}
		});
		textV.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent arg0) {
				if (textV.getText().equals("?")) {
					circVars[4].setContents("?", false);
					return;
				}
				if (!MathUtil.isNumeric(textV.getText())) {
					JOptionPane.showMessageDialog(Frame.this,
							"Must enter a number!", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				circVars[4].setContents(textV.getText(), true);
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				// DO NOTHING!
			}
		});
		textR.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent arg0) {
				if (textR.getText().equals("?")) {
					circVars[5].setContents("?", false);
					return;
				}
				if (!MathUtil.isNumeric(textR.getText())) {
					JOptionPane.showMessageDialog(Frame.this,
							"Must enter a number!", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (Double.parseDouble(textR.getText()) <= 0) {
					JOptionPane.showMessageDialog(Frame.this,
							"Must be greater than 0!", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				circVars[5].setContents(textR.getText(), true);

			}

			@Override
			public void focusGained(FocusEvent arg0) {
				// DO NOTHING!

			}
		});

		textA.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent arg0) {
				if (textA.getText().equals("?")) {
					circVars[6].setContents("?", false);
					return;
				}
				if (!MathUtil.isNumeric(textA.getText())) {
					JOptionPane.showMessageDialog(Frame.this,
							"Must enter a number!", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (Double.parseDouble(textA.getText()) <= 0) {
					JOptionPane.showMessageDialog(Frame.this,
							"Must be greater than 0!", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				circVars[6].setContents(textA.getText(), true);

			}

			@Override
			public void focusGained(FocusEvent arg0) {
				// DO NOTHING!

			}
		});

		textT.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent arg0) {
				if (textT.getText().equals("?")) {
					circVars[7].setContents("?", false);
					return;
				}
				if (!MathUtil.isNumeric(textT.getText())) {
					JOptionPane.showMessageDialog(Frame.this,
							"Must enter a number!", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (Double.parseDouble(textT.getText()) <= 0) {
					JOptionPane.showMessageDialog(Frame.this,
							"Must be greater than 0!", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				circVars[7].setContents(textT.getText(), true);

			}

			@Override
			public void focusGained(FocusEvent arg0) {
				// DO NOTHING!

			}
		});
		panelFields.setLayout(new GridBagLayout());
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
		c.gridx = 0;
		c.gridy = 0;
		panelSouthN.add(new JLabel("X:"), c);
		c.gridy++;
		panelSouthN.add(new JLabel("Y:"), c);
		c.gridy++;
		c.gridx = 1;
		panelSouthN.add(circB, c);
		c.gridy = 0;
		panelSouthN.add(circX, c);
		c.gridy++;
		panelSouthN.add(circY, c);

		circX.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent arg0) {
				if (circX.getText().equals("?")) {
					circVarB[0].setContents("?", false);
					return;
				}
				if (!MathUtil.isNumeric(circX.getText())) {
					JOptionPane.showMessageDialog(Frame.this,
							"Must enter a number!", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				circVarB[0].setContents(circX.getText(), true);
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				if (circY.getText().equals("?")) {
					circVarB[1].setContents("?", false);
					return;
				}
				if (!MathUtil.isNumeric(circY.getText())) {
					JOptionPane.showMessageDialog(Frame.this,
							"Must enter a number!", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				circVarB[1].setContents(circY.getText(), true);
			}
		});
		circY.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO: circX
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				// Do nothing.
			}
		});

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

					}

					@Override
					public void focusGained(FocusEvent e) {
						// Do nothing
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

					}

					@Override
					public void focusGained(FocusEvent e) {
						// Do nothing
					}
				});
			}
		});

		Thread update = new Thread() {
			public void run() {
				// TODO: remove dialog box.
				CircDialog form = new CircDialog(Frame.this);
				circVertical.text = circVarB;
				circVertical.force = circTextA;
				circVertical.angle = circTextB;
				circTopDown.vars = circVars;

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

		projTheta = new JTextField(9);
		projV = new JTextField(9);
		projVx = new JTextField(9);
		projVy = new JTextField(9);
		projY = new JTextField(9);
		projX = new JTextField(9);
		projT = new JTextField(9);
		projU = new JTextField(9);
		projUx = new JTextField(9);
		projUy = new JTextField(9);
		projS = new JTextField(9);
		projLabel = new JTextField(9);
		projUy = new JTextField(9);
		projSy = new JTextField(9);

		projTheta.setText("?");
		projV.setText("?");
		projVx.setText("?");
		projVy.setText("?");
		projY.setText("?");
		projX.setText("?");
		projT.setText("?");
		projU.setText("?");
		projUx.setText("?");
		projUy.setText("?");
		projS.setText("?");
		projLabel.setText("A");
		projSy.setText("?");

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

		projTheta.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				// DO NOTHING

			}

			@Override
			public void focusLost(FocusEvent arg0) {
				if (projTheta.getText().equals("?")) {
					projVars[0].setContents(projTheta.getText(), false);
					return;
				}
				if (!MathUtil.isNumeric(projTheta.getText())) {
					JOptionPane.showMessageDialog(Frame.this, "Not a number!",
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (Double.parseDouble(projTheta.getText()) > Math.PI * 2) {
					JOptionPane
							.showMessageDialog(Frame.this,
									"The value entered is greater than 2 PI.\n Make sure it is in radians.");
				}
				projVars[0].setContents(new String(projTheta.getText()), true);
			}

		});
		projV.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				// DO NOTHING

			}

			@Override
			public void focusLost(FocusEvent arg0) {
				if (projV.getText().equals("?")) {
					projVars[1].setContents(projV.getText(), false);
					return;
				}
				if (!MathUtil.isNumeric(projV.getText())) {
					JOptionPane.showMessageDialog(Frame.this, "Not a number!",
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				projVars[1].setContents(new String(projV.getText()), true);
			}

		});
		projVx.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				// DO NOTHING

			}

			@Override
			public void focusLost(FocusEvent arg0) {
				if (projVx.getText().equals("?")) {
					projVars[2].setContents(projVx.getText(), false);
					return;
				}
				if (!MathUtil.isNumeric(projVx.getText())) {
					JOptionPane.showMessageDialog(Frame.this, "Not a number!",
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				projVars[2].setContents(new String(projVx.getText()), true);
			}

		});
		projVy.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				// DO NOTHING
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				if (projVy.getText().equals("?")) {
					projVars[3].setContents(projVy.getText(), false);
					return;
				}
				if (!MathUtil.isNumeric(projVy.getText())) {
					JOptionPane.showMessageDialog(Frame.this, "Not a number!",
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				projVars[3].setContents(new String(projVy.getText()), true);
			}

		});
		projY.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				// DO NOTHING

			}

			@Override
			public void focusLost(FocusEvent arg0) {
				if (projY.getText().equals("?")) {
					projVars[4].setContents(projY.getText(), false);
					return;
				}
				if (!MathUtil.isNumeric(projY.getText())) {
					JOptionPane.showMessageDialog(Frame.this, "Not a number!",
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (Double.parseDouble(projY.getText()) < 0) {
					JOptionPane.showMessageDialog(Frame.this,
							"Must be less than 0!", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				projVars[4].setContents(new String(projY.getText()), true);
			}

		});
		projX.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				// DO NOTHING
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				if (projX.getText().equals("?")) {

					projVars[5].setContents(projX.getText(), false);
					return;
				}
				if (!MathUtil.isNumeric(projX.getText())) {
					JOptionPane.showMessageDialog(Frame.this, "Not a number!",
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (Double.parseDouble(projX.getText()) < 0) {
					JOptionPane.showMessageDialog(Frame.this,
							"Must be greater than 0!", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				projVars[5].setContents(new String(projX.getText()), true);
			}

		});
		projT.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				// DO NOTHING
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				if (projT.getText().equals("?")) {
					projVars[6].setContents(projT.getText(), false);
					return;
				}
				if (!MathUtil.isNumeric(projT.getText())) {
					JOptionPane.showMessageDialog(Frame.this, "Not a number!",
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (Double.parseDouble(projT.getText()) < 0) {
					JOptionPane.showMessageDialog(Frame.this,
							"Must be greater than 0!", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				projVars[6].setContents(new String(projT.getText()), true);
			}
		});
		projU.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				// DO NOTHING
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				if (projU.getText().equals("?")) {
					projVars[7].setContents(projU.getText(), false);
					return;
				}
				if (!MathUtil.isNumeric(projU.getText())) {
					JOptionPane.showMessageDialog(Frame.this, "Not a number!",
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				projVars[7].setContents(new String(projU.getText()), true);
			}
		});
		projUx.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				// DO NOTHING
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				if (projUx.getText().equals("?")) {
					projVars[8].setContents(projUx.getText(), false);
					return;
				}
				if (!MathUtil.isNumeric(projUx.getText())) {
					JOptionPane.showMessageDialog(Frame.this, "Not a number!",
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (Double.parseDouble(projUx.getText()) < 0) {
					JOptionPane.showMessageDialog(Frame.this,
							"Value is negative.");
					return;
				}
				projVars[8].setContents(new String(projUx.getText()), true);
			}
		});
		projUy.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				// DO NOTHING
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				if (projUy.getText().equals("?")) {
					projVars[9].setContents(projUy.getText(), false);
					return;
				}
				if (!MathUtil.isNumeric(projUy.getText())) {
					JOptionPane.showMessageDialog(Frame.this, "Not a number!",
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				projVars[9].setContents(new String(projUy.getText()), true);
			}
		});
		projS.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				// DO NOTHING
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				if (projS.getText().equals("?")) {
					projVars[10].setContents(projS.getText(), false);
					return;
				}
				if (!MathUtil.isNumeric(projS.getText())) {
					JOptionPane.showMessageDialog(Frame.this, "Not a number!",
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (Double.parseDouble(projS.getText()) < 0) {
					JOptionPane.showMessageDialog(Frame.this,
							"Value is negative.");
					return;
				}
				projVars[10].setContents(new String(projS.getText()), true);
			}
		});
		projSy.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				// DO NOTHING
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				if (projSy.getText().equals("?")) {
					projVars[12].setContents(projSy.getText(), false);
					return;
				}
				if (!MathUtil.isNumeric(projSy.getText())) {
					JOptionPane.showMessageDialog(Frame.this, "Not a number!",
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (Double.parseDouble(projSy.getText()) < 0) {
					JOptionPane.showMessageDialog(Frame.this,
							"Value is negative.");
					return;
				}
				projVars[12].setContents(new String(projSy.getText()), true);
			}
		});
		projLabel.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				// DO NOTHING
			}

			@Override
			public void focusLost(FocusEvent e) {
				projVars[11].setContents(projLabel.getText(), true);

			}
		});
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

		others.setLayout(new GridBagLayout());
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
		colDiagram = new ColDiagram(a, b, e);
		JPanel westPanel = new JPanel();
		this.add(westPanel, BorderLayout.WEST);
		this.add(colDiagram, BorderLayout.CENTER);
		JPanel fields = new JPanel();
		JPanel text = new JPanel();
		colDiagram.setBorder(border);
		fields.setBorder(border);
		text.setBorder(border);
		westPanel.setLayout(new GridLayout(0, 1));
		westPanel.add(text);
		westPanel.add(fields);

		// text;
		text.setLayout(new BorderLayout(0, 0));
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
					fieldLabel.setText(a[0].contents);
					fieldMass.setText(a[1].contents);
					fieldU.setText(a[2].contents);
					fieldV.setText(a[3].contents);
					fieldImpulse.setText(a[4].contents);
					textCurrent.setText("Currently selected object: "
							+ fieldLabel.getText());
				} else {
					fieldLabel.setText(b[0].contents);
					fieldMass.setText(b[1].contents);
					fieldU.setText(b[2].contents);
					fieldV.setText(b[3].contents);
					fieldImpulse.setText(b[4].contents);
					textCurrent.setText("Currently selected object: "
							+ fieldLabel.getText());
				}
			}
		});

		fieldE.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {

			}

			@Override
			public void focusLost(FocusEvent event) {
				if (fieldE.getText().equals("?")) {
					e.setContents(fieldE.getText(), false);
					return;
				}
				if (!MathUtil.isNumeric(fieldE.getText())) {
					JOptionPane.showMessageDialog(Frame.this, "Not a number.",
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (Double.parseDouble(fieldE.getText()) > 1
						|| Double.parseDouble(fieldE.getText()) < 0) {
					JOptionPane.showMessageDialog(Frame.this,
							"Must follow: 0 <= e <= 1", "Error",
							JOptionPane.ERROR_MESSAGE);
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
					a[0].setContents(fieldLabel.getText(), false);
				} else {
					b[0].setContents(fieldLabel.getText(), false);
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
					if (colA) {
						a[1].setContents(fieldMass.getText(), false);
					} else {
						b[1].setContents(fieldMass.getText(), false);
					}
					return;
				}
				if (!MathUtil.isNumeric(fieldMass.getText())) {
					JOptionPane.showMessageDialog(Frame.this, "Not a number.",
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				double x = Double.parseDouble(fieldMass.getText());
				if (x < 0) {
					JOptionPane.showMessageDialog(Frame.this,
							"Mass should not be negative.", "Error",
							JOptionPane.ERROR_MESSAGE);
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
				if (fieldU.getText().equals("?")) {
					if (colA) {
						a[2].setContents(fieldU.getText(), false);
					} else {
						b[2].setContents(fieldU.getText(), false);
					}
					return;
				}
				if (!MathUtil.isNumeric(fieldU.getText())) {
					JOptionPane.showMessageDialog(Frame.this, "Not a number.",
							"Error", JOptionPane.ERROR_MESSAGE);
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
				if (fieldV.getText().equals("?")) {
					if (colA) {
						a[3].setContents(fieldV.getText(), false);
					} else {
						b[3].setContents(fieldV.getText(), false);
					}
					return;
				}
				if (!MathUtil.isNumeric(fieldV.getText())) {
					JOptionPane.showMessageDialog(Frame.this, "Not a number.",
							"Error", JOptionPane.ERROR_MESSAGE);
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
				if (fieldImpulse.getText().equals("?")) {
					if (colA) {
						a[4].setContents(fieldImpulse.getText(), false);
					} else {
						b[4].setContents(fieldImpulse.getText(), false);
					}
					return;
				}
				if (!MathUtil.isNumeric(fieldImpulse.getText())) {
					JOptionPane.showMessageDialog(Frame.this, "Not a number.",
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (colA) {
					a[4].setContents(fieldImpulse.getText(), true);
				} else {
					b[4].setContents(fieldImpulse.getText(), true);
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
					colDiagram.repaint();

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
		this.add(sidepanel, BorderLayout.WEST);
		sideNorth = new sidepanelNorth(canvas.plane);
		sideSouth = new sidepanelSouth(0, canvas.plane);
		sidepanel.setPreferredSize(new Dimension(300, this.getHeight()));
		sidepanel.setBorder(border);
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
