package mainGui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;

import mainGui.centreOfMass.COMPanel;
import mainGui.centreOfMass.COMPanelNorth;
import mainGui.centreOfMass.COMPanelSouth;
import mainGui.circularMotion.ButtonActionListener;
import mainGui.circularMotion.CircTopDown;
import mainGui.circularMotion.CircVertical;
import mainGui.collision.ColDiagram;
import mainGui.projectileMotion.ProjDiagram;
import math.MathUtil;
import math.Var;

/**
 * Creates main window for application
 * 
 */
public class Frame extends JFrame {
	private static final long serialVersionUID = 1L;
	private static int fieldWidth = 7;//amount of columns on textfield
	public static int CIRCLES = 0;
	public static int COLLISIONS = 1;
	public static int CENTER = 2;
	public static int PROJECTILES = 3;
	public int topic = 2;//one of the 4 above
	public boolean color = true;//Whether or not show colour in diagram
	public Color bgColor = Color.white;//background colour

	// collision
	/**
	 * 0 a <br>
	 * 1 m1 <br>
	 * 2 v1 <br>
	 * 3 u1 <br>
	 * 4 i1
	 */
	public Var[] colVarA, colVarB;
	public Var colVarE;
	public ColDiagram colDiagram;
	public JTextField[] colField = new JTextField[6];
	public boolean colA = true;//where A is selected

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
	public List<String> circTextA = new ArrayList<String>();
	public List<String> circTextB = new ArrayList<String>();
	public List<JTextField> circF = new ArrayList<JTextField>();
	public List<JTextField> circT = new ArrayList<JTextField>();
	public JLabel circLblX, circLblY;
	public CircTopDown circTopDown;
	public CircVertical circVertical;
	public JPanel circSouthS;
	public Var[] circVarB;
	public JTextField[] circField = new JTextField[8];
	public JTextField circX, circY;

	// CenterOfMass
	public COMPanel panelCOM;
	public COMPanelSouth sideSouth;

	// projectile
	/**
	 * 0 a theta<br>
	 * 1 v v<br>
	 * 2 b Vx<br>
	 * 3 c Vy<br>
	 * 4 h Height<br>
	 * 5 z max distance.<br>
	 * 6 t t<br>
	 * 7 u U<br>
	 * 8 d Ux<br>
	 * 9 e Uy<br>
	 * 10 x x<br>
	 * 11 label<br>
	 * 12 y y<br>
	 */
	public Var[] projVars;
	public ProjDiagram projDiagram;
	public JTextField[] projField = new JTextField[13];
	private Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
	JTextArea topicDesc;

	/**
	 * Construct a new instance of this class for the chosen topic
	 * 
	 * @param popupTopic
	 */
	public Frame(int popupTopic) {
		try {// Set to os style
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.getDefaults().put("Button.showMnemonics", Boolean.TRUE);
			setIconImage(new ImageIcon(getClass().getResource("/prgmIcon.png")).getImage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		topic = popupTopic;

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				long timer = System.currentTimeMillis();
				setJMenuBar(new MyMenuBar(Frame.this));
				setTopic(topic);
				setMinimumSize(new Dimension(640, 480));
				setPreferredSize(new Dimension(800, 600));
				setDefaultCloseOperation(EXIT_ON_CLOSE);
				pack();
				setVisible(true);
				setExtendedState(MAXIMIZED_BOTH);

				// Thread that updates GUI
				Thread update = new Thread() {
					public void run() {
						while (true) {
							repaint();
							// revalidate();
							for (Component c : getAllPanels(getContentPane())) {
								c.repaint();// repaint every panel
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
				// print time taken to start
				System.out.println("GUI initialised in " + (System.currentTimeMillis() - timer)
						+ " milliseconds");
			}
		});
	}

	/**
	 * Sets current topic
	 * 
	 * @param topic
	 *            The new topic.
	 * 
	 */
	public void setTopic(int topic) {
		setTitle("M2 Drawer");
		getContentPane().removeAll();
		this.topic = topic;
		if (topic == CIRCLES) {
			setTitle(getTitle() + " - Uniform Motion in a Circle");
			initCircularMotion();
			circVertical.text = circVarB;
			circVertical.force = circTextA;
			circVertical.angle = circTextB;
			circTopDown.vars = circVars;
		}
		if (topic == CENTER) {
			setTitle(getTitle() + " - Center of Mass");
			initCenterOfMass();
		}
		if (topic == COLLISIONS) {
			setTitle(getTitle() + "- Coefficient of Restitution; Impulse");
			initCollisions();
		}
		if (topic == PROJECTILES) {
			setTitle(getTitle() + "- Motion of a Projectile");
			initProjectiles();
		}
		updateFields();
	}

	/**
	 * Updates text fields to show variable contents.
	 */
	public void updateFields() {
		if (topic == PROJECTILES) {
			for (int i = 0; i < projField.length; i++) {
				projField[i].setText(MathUtil.round(projVars[i].contents, 2));
			}
		}
		if (topic == CIRCLES) {
			for (int i = 0; i < circField.length; i++) {
				circField[i].setText(MathUtil.round(circVars[i].contents, 2));
			}
			double x = 0;
			double y = 0;
			for (int i = 0; i < circTextA.size(); i++) {
				x += Double.parseDouble(circTextA.get(i))
						* Math.cos(Double.parseDouble(circTextB.get(i)));
				y += Double.parseDouble(circTextA.get(i))
						* Math.sin(Double.parseDouble(circTextB.get(i)));
			}
			circX.setText(circVarB[0].contents);
			circY.setText(circVarB[1].contents);
			circLblX.setText("Sum of horizontal forces: " + MathUtil.round("" + x, 2));
			circLblY.setText("Sum of vertical forces  : " + MathUtil.round("" + y, 2));
		}
		if (topic == COLLISIONS) {
			if (colA) {
				for (int i = 0; i < colVarA.length; i++) {
					colField[i + 1].setText(MathUtil.round(colVarA[i].contents, 2));
				}
			} else {
				for (int i = 0; i < colVarB.length; i++) {
					colField[i + 1].setText(MathUtil.round(colVarB[i].contents, 2));
				}
			}
		}
		repaint();
		revalidate();
	}

	/**
	 * Initialise and layout components for topic CIRCLES
	 */
	private void initCircularMotion() {
		// Initialised panels;
		JPanel panelDiagram = new JPanel(new GridLayout());
		JPanel panelFields = new JPanel(new GridBagLayout());
		JPanel panelWest = new JPanel(new GridLayout(0, 1));
		circSouthS = new JPanel(new GridBagLayout());
		JPanel panelSouth = new JPanel(new GridLayout(0, 1));
		JPanel panelSouthN = new JPanel(new GridBagLayout());
		// Set borders
		circSouthS.setBorder(BorderFactory.createTitledBorder(border, "Forces"));
		panelSouthN.setBorder(BorderFactory.createTitledBorder(border, "Position from O"));
		panelFields.setBorder(border);
		panelSouth.setBorder(border);

		// initiate components
		circVertical = new CircVertical(this);
		circTopDown = new CircTopDown(this);
		JLabel Explanation = new JLabel("Leave unknowns as \"?\".");
		circX = new JTextField("?", fieldWidth);
		circY = new JTextField("?", fieldWidth);
		circLblX = new JLabel("Sum of horizontal forces: ?");
		circLblY = new JLabel("Sum of vertical forces  : ?");
		JButton circAddForce = new JButton("Add Force");
		for (int i = 0; i < circField.length; i++) {
			circField[i] = new JTextField("?", fieldWidth);
		}

		// initiate variables
		circVars = Var.createVars(Var.CIRC_VARS);
		circVarB = new Var[2];
		circVarB[0] = new Var("x", "?", "x");
		circVarB[1] = new Var("y", "?", "y");

		// Add panels to GUI
		panelWest.add(createNotesPanel());
		panelWest.add(new JScrollPane(panelFields));
		panelWest.add(panelSouth);
		panelSouth.add(new JScrollPane(panelSouthN));
		panelSouth.add(new JScrollPane(circSouthS));
		this.add(panelWest, BorderLayout.WEST);
		this.add(panelDiagram, BorderLayout.CENTER);

		// Add diagram panels.
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		panelDiagram.add(circTopDown);
		c.gridx++;
		panelDiagram.add(circVertical);

		c = new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0);

		// Column 1;
		panelFields.add(Explanation, c);
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
		panelFields.add(circField[2], c);
		c.gridy++;
		panelFields.add(circField[3], c);
		c.gridy++;
		panelFields.add(circField[0], c);
		c.gridy++;
		panelFields.add(circField[4], c);
		c.gridy++;
		panelFields.add(circField[1], c);
		c.gridy++;
		panelFields.add(circField[5], c);
		c.gridy++;
		panelFields.add(circField[6], c);
		c.gridy++;
		panelFields.add(circField[7], c);

		// Layout panelSouthN;
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

		// add listeners
		ListenerAdder adder = new ListenerAdder(this);
		adder.addListeners(Frame.CIRCLES);

		circAddForce.addActionListener(new ButtonActionListener(this));
	}

	/**
	 * Initialise and layout components for topic PROJECTILES
	 */
	private void initProjectiles() {
		// initialise variables
		projVars = Var.createVars(Var.PROJ_VARS);
		projDiagram = new ProjDiagram(this);
		JPanel sidePanel = new JPanel(new GridLayout(0, 1));
		JPanel others = new JPanel(new GridBagLayout());
		JPanel before = new JPanel(new GridBagLayout());
		JPanel after = new JPanel(new GridBagLayout());
		for (int i = 0; i < projField.length; i++) {
			projField[i] = new JTextField("?", fieldWidth);
		}

		// Set borders
		before.setBorder(BorderFactory.createTitledBorder(border, "Initial conditions"));
		after.setBorder(BorderFactory.createTitledBorder(border, "When object hits someting"));

		// Add components to pane
		sidePanel.add(createNotesPanel());
		sidePanel.add(new JScrollPane(before));
		sidePanel.add(new JScrollPane(after));
		sidePanel.add(new JScrollPane(others));
		add(sidePanel, BorderLayout.WEST);
		add(projDiagram, BorderLayout.CENTER);

		GridBagConstraints c = new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0);
		// Layout out before panel
		// column 1
		before.add(new JLabel("Height:"), c);
		c.gridy++;
		before.add(new JLabel("Angle(radians)"), c);
		c.gridy++;
		before.add(new JLabel("Velocity:"), c);
		c.gridy++;
		before.add(new JLabel("Velocity(x component):"), c);
		c.gridy++;
		before.add(new JLabel("Velocity(y component):"), c);
		// column 2;
		c.gridx = 1;
		c.gridy = 0;
		before.add(projField[4], c);
		c.gridy++;
		before.add(projField[0], c);
		c.gridy++;
		before.add(projField[7], c);
		c.gridy++;
		before.add(projField[8], c);
		c.gridy++;
		before.add(projField[9], c);

		// layout panel after
		// column 1
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
		// column 2
		c.gridx = 1;
		c.gridy = 0;
		after.add(projField[6], c);
		c.gridy++;
		after.add(projField[1], c);
		c.gridy++;
		after.add(projField[2], c);
		c.gridy++;
		after.add(projField[3], c);
		c.gridy++;
		after.add(projField[10], c);
		c.gridy++;
		after.add(projField[12], c);

		// layout panel others
		c.gridx = 0;
		c.gridy = 0;
		others.add(new JLabel("Name:          "), c);
		c.gridx = 1;
		others.add(projField[11], c);
		c.gridx = 0;
		c.gridy = 1;
		others.add(new JLabel("Max distance:                "), c);
		c.gridx = 1;
		others.add(projField[6], c);

		// addListeners
		ListenerAdder adder = new ListenerAdder(this);
		adder.addListeners(PROJECTILES);
	}

	/**
	 * Initialise and layout components for topic COLLISIONS
	 */
	private void initCollisions() {
		// Initialise vars and panels
		colVarA = Var.createVars(Var.COL_VAR_A);
		colVarB = Var.createVars(Var.COL_VAR_B);
		colVarE = new Var("e", "?", "e");
		colDiagram = new ColDiagram(this);
		JPanel westPanel = new JPanel(new GridLayout(0, 1));
		JPanel fields = new JPanel(new GridBagLayout());
		JLabel textDesc = new JLabel(
				"<html>Click on diagram to select point mass. <br>Use \"?\" for unknown variables. Units used are<br> kg, m/s, Ns");

		// layout
		this.add(westPanel, BorderLayout.WEST);
		this.add(colDiagram, BorderLayout.CENTER);
		westPanel.add(createNotesPanel());
		westPanel.add(new JScrollPane(fields));

		// initialise constraints
		GridBagConstraints gbc = new GridBagConstraints(0, 0, 2, 1, 1, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0);
		fields.add(textDesc, gbc);
		// place each label in place
		gbc.gridwidth = 1;
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

		// Places each text field in place.
		gbc.gridx = 1;
		for (int i = 0; i < colField.length; i++) {
			gbc.gridy = i + 1;
			colField[i] = new JTextField("?", fieldWidth);// initialise each
															// field
			fields.add(colField[i], gbc);
		}

		// add listeners
		ListenerAdder adder = new ListenerAdder(this);
		adder.addListeners(Frame.COLLISIONS);
	}

	/**
	 * Initialise and layout components for topic CENTER
	 */
	private void initCenterOfMass() {
		panelCOM = new COMPanel(this);
		JPanel sidePanel = new JPanel(new GridBagLayout());
		this.add(panelCOM, BorderLayout.CENTER);
		this.add(sidePanel, BorderLayout.WEST);
		COMPanelNorth sideNorth = new COMPanelNorth(this);
		sideSouth = new COMPanelSouth(this);

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
		String path = "";
		BufferedReader br = null;
		InputStream input = null;
		topicDesc = new JTextArea(null, "", 15, 26);
		topicDesc.setLineWrap(true);

		if (topic == CIRCLES) {
			input = this.getClass().getResourceAsStream("/circlesNotes.txt");
			path = "m2/notes/circlesNotes.txt";
		}
		if (topic == CENTER) {
			input = this.getClass().getResourceAsStream("/comNotes.txt");
			path = "m2/notes/comNotes.txt";
		}
		if (topic == COLLISIONS) {
			input = this.getClass().getResourceAsStream("/collisionNotes.txt");
			path = "m2/notes/collisionNotes.txt";
		}
		if (topic == PROJECTILES) {
			input = this.getClass().getResourceAsStream("/projectileNotes.txt");
			path = "m2/notes/projectileNotes.txt";
		}

		try {
			File file = new File(path);
			if (file.exists()) {
				br = new BufferedReader(new FileReader(path));
			} else {
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
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (Exception e) {
				// Do nothing
			}
		}
		JTextField topicTitle = new JTextField(getTitle().replace("M2 Drawer - ", "") + " notes");
		topicTitle.setEditable(false);
		topicTitle.setFont(topicTitle.getFont().deriveFont(1.2f * topicTitle.getFont().getSize()));
		panel.add(topicTitle, BorderLayout.NORTH);
		panel.add(new JScrollPane(topicDesc), BorderLayout.CENTER);

		return panel;
	}

	/**
	 * Gets all panels with in the container
	 * 
	 * @param container
	 *            The container to get panels from
	 * @return List of panels inside the container
	 */
	private static List<Component> getAllPanels(final Container container) {
		Component[] panels = container.getComponents();
		List<Component> panelList = new ArrayList<Component>();
		for (Component panel : panels) {
			if (panel instanceof Container) {
				panelList.add(panel);
				panelList.addAll(getAllPanels((Container) panel));
			}
		}
		return panelList;
	}

}
