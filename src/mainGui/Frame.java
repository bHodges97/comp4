package mainGui;

import java.awt.BorderLayout;
import java.awt.Component;
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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;

import mainGui.centerOfMass.COMPanel;
import mainGui.centerOfMass.COMPanelNorth;
import mainGui.centerOfMass.COMPanelSouth;
import mainGui.circularMotion.ButtonActionListener;
import mainGui.circularMotion.CircTopDown;
import mainGui.circularMotion.CircVertical;
import mainGui.collision.ColDiagram;
import mainGui.projectileMotion.ProjDiagram;
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

	String topic = "Default";
	Border border = BorderFactory.createEtchedBorder(1);
	JTextArea topicDesc;

	// collision
	boolean colA = true;
	/**
	 * 0 a <br>
	 * 1 m1 <br>
	 * 2 v1 <br>
	 * 3 u1 <br>
	 * 4 i1
	 */
	public Var[] colVarA;
	public Var[] colVarB;
	public Var colVarE;
	ColDiagram colDiagram;
	JTextField[] colField = new JTextField[6];
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
	public Var[] circVars;
	/**
	 * Magnitude list.
	 */
	public List<String> circTextA = new ArrayList<String>();
	/**
	 * Direction list. Angle begins at 3 o'clock in radians.
	 */
	public List<String> circTextB = new ArrayList<String>();
	public CircTopDown circTopDown;
	public CircVertical circVertical;

	/**
	 * wmuxvrat
	 */
	JTextField[] circText = new JTextField[8];
	JTextField circX;
	JTextField circY;
	public List<JTextField> circF = new ArrayList<JTextField>();
	public List<JTextField> circT = new ArrayList<JTextField>();
	Var[] circVarB;
	public JTextField circLblX;
	public JTextField circLblY;
	public JPanel panelSouthS;

	// CenterOfMass
	public COMPanel canvas;
	JPanel sidePanel;
	COMPanelNorth sideNorth;
	public static COMPanelSouth sideSouth;

	// projectile
	ProjDiagram projDiagram;
	/**
	 * projTheta- 0. <br>
	 * projV- 1. <br>
	 * projVx- 2. <br>
	 * projVy- 3. <br>
	 * projY- 4. <br>
	 * projX- 5. <br>
	 * projT- 6. <br>
	 * projU- 7. <br>
	 * projUx- 8. <br>
	 * projUy- 9. <br>
	 * projS- 10. <br>
	 * projLabel- 11. <br>
	 * projSy- 12
	 */
	JTextField[] projText = new JTextField[13];
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

	public Frame(String popupTopic) {
		try {//Set to os style
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		topic = popupTopic;

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				long t = System.currentTimeMillis();
				setExtendedState(MAXIMIZED_BOTH);

				MyMenuBar menu = new MyMenuBar(Frame.this);
				setJMenuBar(menu);

				setTopic(topic);
				setMinimumSize(new Dimension(640, 480));
				setDefaultCloseOperation(EXIT_ON_CLOSE);
				pack();
				setVisible(true);

				// Thread that updates gui
				Thread update = new Thread() {
					public void run() {
						try {
							Thread.sleep((long) 1000);// wait till app starts
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						while (true) {
							repaint();
							revalidate();
							for (Component c : Frame.this.getComponents()) {
								if (c instanceof JPanel) {
									c.repaint();// repaint every panel
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
				System.out.println("GUI initialised in " + (System.currentTimeMillis() - t)
						+ " milliseconds");
			}

		});

	}

	void initCircularMotion() {
		setLayout(new BorderLayout(5, 5));
		// Initialised panels;
		JPanel panelDiagram = new JPanel(new GridLayout());
		JPanel panelFields = new JPanel(new GridBagLayout());
		JPanel panelWest = new JPanel(new GridLayout(0, 1, 5, 5));
		panelSouthS = new JPanel(new GridBagLayout());
		JPanel panelSouth = new JPanel(new GridLayout(0, 1));
		JPanel panelSouthN = new JPanel(new GridBagLayout());
		// Set borders
		panelSouthS.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
				"Forces"));
		panelSouthN.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
				"Position from O"));
		panelFields.setBorder(border);
		panelSouth.setBorder(border);

		// Add panels to gui
		panelWest.add(createNotesPanel());
		panelWest.add(new JScrollPane(panelFields));
		panelWest.add(panelSouth);
		panelSouth.add(new JScrollPane(panelSouthN));
		panelSouth.add(new JScrollPane(panelSouthS));
		this.add(panelWest, BorderLayout.WEST);
		this.add(panelDiagram, BorderLayout.CENTER);

		// Add diagram panels.
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		circTopDown = new CircTopDown();
		panelDiagram.add(circTopDown);
		c.gridx++;
		circVertical = new CircVertical();
		panelDiagram.add(circVertical);

		// initiate components
		JLabel Explanation = new JLabel("Leave unknowns as \"?\".");
		for (int i = 0; i < circText.length; i++) {
			circText[i] = new JTextField("?", 9);
		}
		circX = new JTextField("?", 7);
		circY = new JTextField("?", 7);
		JButton circAddForce = new JButton("Add Force");

		// initiate variables
		circVars = new Var[8];
		circVarB = new Var[2];
		circVars[0] = new Var("w", new String(circText[0].getText()), "w");
		circVars[1] = new Var("m", new String(circText[1].getText()), "m");
		circVars[2] = new Var("u", new String(circText[2].getText()), "u");
		circVars[3] = new Var("x", new String(circText[3].getText()), "x");
		circVars[4] = new Var("v", new String(circText[4].getText()), "v");
		circVars[5] = new Var("r", new String(circText[5].getText()), "r");
		circVars[6] = new Var("a", new String(circText[6].getText()), "a");
		circVars[7] = new Var("t", new String(circText[7].getText()), "t");
		circVarB[0] = new Var("x", "?", "x");
		circVarB[1] = new Var("y", "?", "y");

		addListener(circText[0], circVars[0], 2, null);
		addListener(circText[1], circVars[1], 1, null);
		addListener(circText[2], circVars[2], 4, null);
		addListener(circText[3], circVars[3], 4, null);
		addListener(circText[4], circVars[4], -1, null);
		addListener(circText[5], circVars[5], 1, null);
		addListener(circText[6], circVars[6], 1, null);
		addListener(circText[7], circVars[7], 1, null);

		c = new GridBagConstraints();
		c.gridx = 0;
		c.weighty = 0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.gridy = 0;
		c.insets = new Insets(2, 2, 2, 2);
		c.weightx = 1;
		panelFields.add(Explanation, c);

		// Coloumn 1;
		c.gridx = 0;
		c.gridy++;
		panelFields.add(new JLabel("Start Angle"), c);
		c.gridy++;
		panelFields.add(new JLabel("End Angle"), c);
		c.gridy++;
		panelFields.add(new JLabel("Angular Velocity"), c);
		c.gridy++;
		panelFields.add(new JLabel("Tangential Velocity"), c);
		c.gridy++;
		panelFields.add(new JLabel("Mass"), c);
		c.gridy++;
		panelFields.add(new JLabel("Radius"), c);
		c.gridy++;
		panelFields.add(new JLabel("Centripetal Acceleration"), c);
		c.gridy++;
		panelFields.add(new JLabel("Time"), c);

		// Second Column
		c.gridx = 1;
		c.gridy = 1;
		panelFields.add(circText[2], c);
		c.gridy++;
		panelFields.add(circText[3], c);
		c.gridy++;
		panelFields.add(circText[0], c);
		c.gridy++;
		panelFields.add(circText[4], c);
		c.gridy++;
		panelFields.add(circText[1], c);
		c.gridy++;
		panelFields.add(circText[5], c);
		c.gridy++;
		panelFields.add(circText[6], c);
		c.gridy++;
		panelFields.add(circText[7], c);

		// Layout panelSouthN;
		circLblX = new JTextField("Sum of horizontal forces: ?");
		circLblY = new JTextField("Sum of vertical forces  : ?");
		circLblX.setEditable(false);
		circLblY.setEditable(false);
		c.weightx = 1;

		// row 1
		c.gridy = 0;
		c.gridx = 0;
		panelSouthN.add(new JLabel("X:"), c);
		c.gridx++;
		panelSouthN.add(circX, c);
		c.gridx++;
		panelSouthN.add(new JLabel("Y:"), c);
		c.gridx++;
		panelSouthN.add(circY, c);

		// row 2 & 3
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 4;
		panelSouthN.add(circLblX, c);
		c.gridy++;
		panelSouthN.add(circLblY, c);

		// last button
		c.anchor = GridBagConstraints.EAST;
		c.gridwidth = 2;
		c.gridy++;
		c.gridx = 2;
		panelSouthN.add(circAddForce, c);

		addListener(circX, circVarB[0], -1, null);
		addListener(circY, circVarB[1], -1, null);
		circAddForce.addActionListener(new ButtonActionListener(this));
	}

	void initProjectiles() {
		projVars = new Var[13];
		projDiagram = new ProjDiagram(projVars);
		JPanel sidePanel = new JPanel(new BorderLayout(0, 0));
		JPanel southPanel = new JPanel(new GridLayout(0, 1));

		sidePanel.add(createNotesPanel(), BorderLayout.NORTH);
		sidePanel.add(new JScrollPane(southPanel), BorderLayout.CENTER);
		add(projDiagram, BorderLayout.CENTER);
		add(sidePanel, BorderLayout.WEST);
		southPanel.setBorder(border);
		projDiagram.setBorder(border);

		for (int i = 0; i < projText.length; i++) {
			projText[i] = new JTextField("?", 9);
		}
		projText[11].setText("A");
		projVars[0] = new Var("a", new String(projText[0].getText()), "θ");
		projVars[1] = new Var("v", new String(projText[1].getText()), "V");
		projVars[2] = new Var("b", new String(projText[2].getText()), "Vx");
		projVars[3] = new Var("c", new String(projText[3].getText()), "Vy");
		projVars[4] = new Var("h", new String(projText[4].getText()), "Height");
		projVars[5] = new Var("z", new String(projText[5].getText()), "UNUSED");
		projVars[6] = new Var("t", new String(projText[6].getText()), "t");
		projVars[7] = new Var("u", new String(projText[7].getText()), "U");
		projVars[8] = new Var("d", new String(projText[8].getText()), "Ux");
		projVars[9] = new Var("e", new String(projText[9].getText()), "Uy");
		projVars[10] = new Var("x", new String(projText[10].getText()), "x");
		projVars[11] = new Var("", new String(projText[11].getText()), "A");
		projVars[12] = new Var("y", new String(projText[12].getText()), "y");

		addListener(projText[0], projVars[0], 4, null);
		addListener(projText[1], projVars[1], -1, null);
		addListener(projText[2], projVars[2], -1, null);
		addListener(projText[3], projVars[3], -1, null);
		addListener(projText[4], projVars[4], 0, null);
		addListener(projText[5], projVars[5], 0, null);
		addListener(projText[6], projVars[6], 0, null);
		addListener(projText[7], projVars[7], -1, null);
		addListener(projText[8], projVars[8], 0, null);
		addListener(projText[9], projVars[9], -1, null);
		addListener(projText[10], projVars[10], 0, null);
		addListener(projText[12], projVars[12], 0, null);
		addListener(projText[11], projVars[11], -2, null);

		JPanel others = new JPanel(new GridBagLayout());
		JPanel before = new JPanel(new GridBagLayout());
		before.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
				"Initial conditions"));
		JPanel after = new JPanel(new GridBagLayout());
		after.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
				"When object hits someting"));

		southPanel.add(new JScrollPane(before));
		southPanel.add(new JScrollPane(after));
		southPanel.add(new JScrollPane(others));

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
		before.add(projText[4], c);
		c.gridy++;
		before.add(projText[0], c);
		c.gridy++;
		before.add(projText[7], c);
		c.gridy++;
		before.add(projText[8], c);
		c.gridy++;
		before.add(projText[9], c);

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
		after.add(projText[6], c);
		c.gridy++;
		after.add(projText[1], c);
		c.gridy++;
		after.add(projText[2], c);
		c.gridy++;
		after.add(projText[3], c);
		c.gridy++;
		after.add(projText[10], c);
		c.gridy++;
		after.add(projText[12], c);

		c.gridx = 0;
		c.gridy = 0;
		others.add(new JLabel("Name:          "), c);
		c.gridx = 1;
		others.add(projText[11], c);
		c.gridx = 0;
		c.gridy = 1;
		others.add(new JLabel("Max distance:                "), c);
		c.gridx = 1;
		others.add(projText[6], c);
	}

	void initCollisions() {
		colVarA = new Var[5];
		colVarB = new Var[5];
		colVarE = new Var("e", "?", "e");
		colVarA[0] = new Var("a", "A", "1");
		colVarA[1] = new Var("m1", "?", "M1");
		colVarA[2] = new Var("v1", "?", "V1");
		colVarA[3] = new Var("u1", "?", "U1");
		colVarA[4] = new Var("i1", "?", "i1");
		colVarB[0] = new Var("b", "B", "2");
		colVarB[1] = new Var("m2", "?", "M2");
		colVarB[2] = new Var("v2", "?", "V2");
		colVarB[3] = new Var("u2", "?", "U2");
		colVarB[4] = new Var("i2", "?", "i2");

		// layout
		colDiagram = new ColDiagram(colVarA, colVarB, colVarE);
		JPanel westPanel = new JPanel(new GridLayout(0, 1));
		JPanel fields = new JPanel(new GridBagLayout());
		this.add(westPanel, BorderLayout.WEST);
		this.add(colDiagram, BorderLayout.CENTER);
		westPanel.add(new JScrollPane(createNotesPanel()));
		westPanel.add(new JScrollPane(fields));
		colDiagram.setBorder(border);
		fields.setBorder(border);

		JLabel textDesc = new JLabel(
				"<html>Click on diagram to select point mass. <br>Use \"?\" for unknown variables. Units used are<br> kg, m/s, Ns");

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(2, 2, 2, 2);
		gbc.weightx = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridwidth = 2;
		fields.add(textDesc, gbc);
		gbc.gridwidth = 1;

		// place each label in place
		gbc.gridx = 0;
		gbc.gridy = 1;
		fields.add(new JLabel("Coefficient of restitution"), gbc);
		gbc.gridy = 2;
		fields.add(new JLabel("Label"), gbc);
		gbc.gridy = 3;
		fields.add(new JLabel("Mass"), gbc);
		gbc.gridy = 4;
		fields.add(new JLabel("Initial velocity"), gbc);
		gbc.gridy = 5;
		fields.add(new JLabel("Final velocity"), gbc);
		gbc.gridy = 6;
		fields.add(new JLabel("Impulse"), gbc);

		// Places each textfield inplace.
		gbc.gridx = 1;
		gbc.gridy = 1;
		for (int i = 0; i < colField.length; i++) {
			colField[i] = new JTextField("?", 10);// initialise each field
			fields.add(colField[i], gbc);
			gbc.gridy++;
		}
		colField[1].setText("A");

		colDiagram.addMouseListener(new MouseListener() {
			@Override
			public void mousePressed(MouseEvent e) {// Do nothing
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// Select object based on mouse click.
				if (Math.abs(e.getY() - colDiagram.getHeight() / 2) < colDiagram.getHeight() * 0.1
						&& (e.getX() < colDiagram.getWidth() / 4 || (e.getX() > colDiagram
								.getWidth() / 2 && e.getX() < colDiagram.getWidth() * 0.75))) {
					colA = true;
				} else if (Math.abs(e.getY() - colDiagram.getHeight() / 2) < colDiagram.getHeight() * 0.1
						&& (e.getX() > colDiagram.getWidth() / 4 || (e.getX() < colDiagram
								.getWidth() / 2 && e.getX() > colDiagram.getWidth() * 0.25))) {
					colA = false;
				}
				updateFields();
			}

		});
		addListener(colField[0], colVarE, 5, colVarE);
		addListener(colField[1], colVarA[0], -2, colVarB[0]);
		addListener(colField[2], colVarA[1], 0, colVarB[1]);
		addListener(colField[3], colVarA[2], -1, colVarB[2]);
		addListener(colField[4], colVarA[3], -1, colVarB[3]);
		addListener(colField[5], colVarA[4], -1, colVarB[4]);
	}

	void initCenterOfMass() {
		canvas = new COMPanel();
		sidePanel = new JPanel(new GridBagLayout());
		this.add(canvas, BorderLayout.CENTER);
		this.add(sidePanel, BorderLayout.WEST);
		sideNorth = new COMPanelNorth(this);
		sideSouth = new COMPanelSouth(this);
		sidePanel.setBorder(border);

		GridBagConstraints c = new GridBagConstraints();
		c.weighty = 1;
		c.weightx = 1;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.NORTHEAST;
		c.gridy = 0;
		sidePanel.add(createNotesPanel(), c);
		c.gridy++;
		sidePanel.add(new JScrollPane(sideNorth), c);
		c.gridy++;
		sidePanel.add(new JScrollPane(sideSouth), c);
	}

	/**
	 * Create a panel that contains the topic's notes.
	 * 
	 * @return panel that contain's topic's notes
	 */
	private JPanel createNotesPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		String title = "";
		String path = "";
		BufferedReader br = null;
		InputStream input = null;
		topicDesc = new JTextArea();

		try {
			if (topic.equals("Circles")) {
				input = this.getClass().getResourceAsStream("/circlesNotes.txt");
				title = "Circular motion notes";
				path = "m2diagramDrawer/notes/circlesNotes.txt";
			}
			if (topic.equals("Center")) {
				input = this.getClass().getResourceAsStream("/comNotes.txt");
				title = "Center of mass notes";
				path = "m2diagramDrawer/notes/comNotes.txt";
			}
			if (topic.equals("Collisions")) {
				input = this.getClass().getResourceAsStream("/collisionNotes.txt");
				title = "Coefficient of restitution and impulse notes";
				path = "m2diagramDrawer/notes/collisionNotes.txt";
			}
			if (topic.equals("Projectiles")) {
				input = this.getClass().getResourceAsStream("/projectileNotes.txt");
				title = "Projectile motion notes";
				path = "m2/notes/projectileNotes.txt";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			File file = new File(path);
			if (file.exists() && !file.isDirectory()) {
				br = new BufferedReader(new FileReader(path));
			} else {
				System.out.println(title);
				br = new BufferedReader(new InputStreamReader(input));
			}
			String line = br.readLine();
			while (line != null) {
				topicDesc.append(line);
				topicDesc.append(System.lineSeparator());
				line = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
			topicDesc.setText("Missing text file");
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (Exception e) {
				//Do nothing
			}
		}

		JTextField topicTitle = new JTextField(title);
		topicTitle.setEditable(false);
		topicTitle.setFont(topicTitle.getFont().deriveFont(1.2f * topicTitle.getFont().getSize()));
		panel.add(topicTitle, BorderLayout.NORTH);
		panel.add(new JScrollPane(topicDesc), BorderLayout.CENTER);

		topicDesc.setRows(15);
		topicDesc.setColumns(26);
		topicDesc.setLineWrap(true);

		return panel;
	}

	/**
	 * Adds a focus listener and action listener to a component.
	 * 
	 * @param textField
	 *            Text field to add listeners to.
	 * @param var1
	 *            Variable text field is associated with.
	 * @param var2
	 *            Second variable text field is associated with if applicable.
	 * @param type
	 *            The type of verification: <br>
	 *            -2: No verification -1:<br>
	 *            -1: Error if not a number <br>
	 *            0: Error if less than or equal to zero <br>
	 *            1: Error if less than zero <br>
	 *            2: Error if equal to zero <br>
	 *            3: Error if greater than 0 <br>
	 *            4: Warning if greater than 2 pi<br>
	 *            5: Special case for e
	 * 
	 */
	private void addListener(final JTextField textField, final Var var1, final int type,
			final Var var2) {
		textField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {
				// DO NOTHING
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				addVerification(textField, var1, type, var2);
			}
		});

		textField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addVerification(textField, var1, type, var2);
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
	 *            -2: No verification -1:<br>
	 *            -1: Error if not a number <br>
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
		if (MathUtil.isNumeric(t.getText()) && t.getText().length() > 10) {
			JOptionPane.showMessageDialog(Frame.this, "Input is too long!", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (c >= -1) {
			if (!MathUtil.isNumeric(t.getText())) {
				JOptionPane.showMessageDialog(Frame.this, "Not a number!", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		if (c == 0) {
			if (Double.parseDouble(t.getText()) < 0) {
				JOptionPane.showMessageDialog(Frame.this, "Must be greater than or equal to 0.");
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
				JOptionPane.showMessageDialog(Frame.this, "Must be greater than 0.");
				return;
			}
		}
		if (c == 3) {
			if (Double.parseDouble(t.getText()) > 0) {
				JOptionPane.showMessageDialog(Frame.this, "Must be less than 0.");
				return;
			}
		}
		if (c == 4) {
			if (Math.abs(Double.parseDouble(t.getText())) > 6.28) {
				JOptionPane
						.showMessageDialog(Frame.this, "Careful this value is greater than 2 PI");
			}
		}
		if (c == 5) {
			if (Double.parseDouble(t.getText()) > 1 || Double.parseDouble(t.getText()) < 0) {
				JOptionPane.showMessageDialog(Frame.this, "Must follow: 0 <= e <= 1", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		v.setContents(new String(t.getText()), true);
	}

	/**
	 * Updates textfields to show variable contents.
	 */
	public void updateFields() {
		if (topic.equals("Projectiles")) {
			for (int i = 0; i < projText.length; i++) {
				projText[i].setText(MathUtil.round(projVars[i].contents));
			}
		}
		if (topic.equals("Circles")) {
			for (int i = 0; i < circText.length; i++) {
				circText[i].setText(MathUtil.round(circVars[i].contents));
			}
			double x = 0;
			double y = 0;
			for (int i = 0; i < circTextA.size(); i++) {
				x += Double.parseDouble(circTextA.get(i))
						* Math.cos(Double.parseDouble(circTextB.get(i)));
				y += Double.parseDouble(circTextA.get(i))
						* Math.sin(Double.parseDouble(circTextB.get(i)));
				System.out.println(x);
			}
			circX.setText(circVarB[0].contents);
			circX.setText(circVarB[1].contents);
			circLblX.setText("Sum of horizontal forces: " + MathUtil.round("" + x));
			circLblY.setText("Sum of vertical forces  : " + MathUtil.round("" + y));

		}
		if (topic.equals("Collisions")) {
			if (colA) {
				for (int i = 0; i < colVarA.length; i++) {
					colField[i + 1].setText(MathUtil.round(colVarA[i].contents));
				}
			} else {
				for (int i = 0; i < colVarB.length; i++) {
					colField[i + 1].setText(MathUtil.round(colVarB[i].contents));
				}
			}
		}
	}

	/**
	 * Sets current topic
	 * 
	 * @param topic
	 *            The new topic.
	 * 
	 */
	public void setTopic(String topic) {
		getContentPane().removeAll();
		this.topic = topic;
		setTitle(topic);
		if (topic.equals("Circles")) {
			initCircularMotion();
			circVertical.text = circVarB;
			circVertical.force = circTextA;
			circVertical.angle = circTextB;
			circTopDown.vars = circVars;
		}
		if (topic.equals("Center")) {
			initCenterOfMass();
		}
		if (topic.equals("Collisions")) {
			initCollisions();
		}
		if (topic.equals("Projectiles")) {
			initProjectiles();
		}
		try {
			Thread.sleep((long) 100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Zooms for topic center of mass.
	 * 
	 * @param option
	 *            0 : zoom in<br>
	 *            1 : zoom out<br>
	 *            2 : reset
	 */
	public void zoom(int option) {
		if (option == 0) {
			if (canvas.scale - 0.01d <= 0) {
				if (canvas.scale - 0.002d <= 0) {
					JOptionPane.showMessageDialog(Frame.this, "Max zoom reached");
					return;// No zoom;
				}
				canvas.scale -= 0.002d;// Smaller zoom;
			} else {
				canvas.scale -= 0.01d;// Standard zoom;
			}
		} else if (option == 1) {
			if (canvas.scale + 0.002d >= 0.5d) {
				JOptionPane.showMessageDialog(Frame.this, "Max zoom reached");
				return;// No zoom;
			}
			canvas.scale += 0.02d;
		} else if (option == 2) {
			canvas.scale = 0.05d;
		}
		canvas.repaint();
	}

	/**
	 * MAIN METHOD
	 * 
	 * @param Args
	 */
	public static void main(String[] Args) {
		StartScreenDialog popup = new StartScreenDialog();
		Frame window = new Frame(popup.topic);
	}
}
