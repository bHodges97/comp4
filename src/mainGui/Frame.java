package mainGui;

import java.awt.BorderLayout;
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
	public boolean colA = true;

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
	public CircTopDown circTopDown;
	public CircVertical circVertical;
	Var[] circVarB;
	JTextField[] circText = new JTextField[8];
	JTextField circX;
	JTextField circY;
	public List<JTextField> circF = new ArrayList<JTextField>();
	public List<JTextField> circT = new ArrayList<JTextField>();
	public JLabel circLblX;
	public JLabel circLblY;
	public JPanel circSouthS;

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
	Var[] projVars;
	ProjDiagram projDiagram;
	JTextField[] projText = new JTextField[13];

	public Frame(String popupTopic) {
		try {// Set to os style
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		topic = popupTopic;

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				long timer = System.currentTimeMillis();
				setExtendedState(MAXIMIZED_BOTH);// FullScreen
				setJMenuBar(new MyMenuBar(Frame.this));
				setTopic(topic);
				setMinimumSize(new Dimension(640, 480));
				setDefaultCloseOperation(EXIT_ON_CLOSE);
				pack();
				setVisible(true);

				// Thread that updates GUI
				Thread update = new Thread() {
					public void run() {
						while (true) {
							repaint();
							revalidate();
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
				System.out.println("GUI initialised in " + (System.currentTimeMillis() - timer) + " milliseconds");
			}
		});
	}

	public static List<Component> getAllPanels(final Container container) {
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

	void initCircularMotion() {
		// Initialised panels;
		JPanel panelDiagram = new JPanel(new GridLayout());
		JPanel panelFields = new JPanel(new GridBagLayout());
		JPanel panelWest = new JPanel(new GridLayout(0, 1, 5, 5));
		circSouthS = new JPanel(new GridBagLayout());
		JPanel panelSouth = new JPanel(new GridLayout(0, 1));
		JPanel panelSouthN = new JPanel(new GridBagLayout());
		// Set borders
		circSouthS.setBorder(BorderFactory.createTitledBorder(border, "Forces"));
		panelSouthN.setBorder(BorderFactory.createTitledBorder(border, "Position from O"));
		panelFields.setBorder(border);
		panelSouth.setBorder(border);

		// initiate components
		circVertical = new CircVertical();
		circTopDown = new CircTopDown();
		JLabel Explanation = new JLabel("Leave unknowns as \"?\".");
		circX = new JTextField("?", 7);
		circY = new JTextField("?", 7);
		circLblX = new JLabel("Sum of horizontal forces: ?");
		circLblY = new JLabel("Sum of vertical forces  : ?");
		JButton circAddForce = new JButton("Add Force");
		for (int i = 0; i < circText.length; i++) {
			circText[i] = new JTextField("?", 9);
		}

		// initiate variables
		circVars = Var.initVars(circVars, "circVars");
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

		c = new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL,
				new Insets(2, 2, 2, 2), 0, 0);

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
		adder.addListener(circText[0], circVars[0], 2, null);
		adder.addListener(circText[1], circVars[1], 1, null);
		adder.addListener(circText[2], circVars[2], 4, null);
		adder.addListener(circText[3], circVars[3], 4, null);
		adder.addListener(circText[4], circVars[4], -1, null);
		adder.addListener(circText[5], circVars[5], 1, null);
		adder.addListener(circText[6], circVars[6], 1, null);
		adder.addListener(circText[7], circVars[7], 1, null);
		adder.addListener(circX, circVarB[0], -1, null);
		adder.addListener(circY, circVarB[1], -1, null);
		circAddForce.addActionListener(new ButtonActionListener(this));
	}

	void initProjectiles() {
		// initialise variables
		projVars = Var.initVars(projVars, "projVars");
		projDiagram = new ProjDiagram(projVars);
		JPanel sidePanel = new JPanel(new BorderLayout(0, 0));
		JPanel southPanel = new JPanel(new GridLayout(0, 1));
		JPanel others = new JPanel(new GridBagLayout());
		JPanel before = new JPanel(new GridBagLayout());
		JPanel after = new JPanel(new GridBagLayout());
		for (int i = 0; i < projText.length; i++) {
			projText[i] = new JTextField("?", 9);
		}

		// Set borders
		southPanel.setBorder(border);
		projDiagram.setBorder(border);
		before.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Initial conditions"));
		after.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "When object hits someting"));

		// Add components to pane
		sidePanel.add(createNotesPanel(), BorderLayout.NORTH);
		sidePanel.add(new JScrollPane(southPanel), BorderLayout.CENTER);
		add(projDiagram, BorderLayout.CENTER);
		add(sidePanel, BorderLayout.WEST);
		southPanel.add(new JScrollPane(before));
		southPanel.add(new JScrollPane(after));
		southPanel.add(new JScrollPane(others));

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
		before.add(projText[4], c);
		c.gridy++;
		before.add(projText[0], c);
		c.gridy++;
		before.add(projText[7], c);
		c.gridy++;
		before.add(projText[8], c);
		c.gridy++;
		before.add(projText[9], c);

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

		// layout panel others
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

		// addListeners
		ListenerAdder adder = new ListenerAdder(this);
		adder.addListener(projText[0], projVars[0], 4, null);
		adder.addListener(projText[1], projVars[1], -1, null);
		adder.addListener(projText[2], projVars[2], -1, null);
		adder.addListener(projText[3], projVars[3], -1, null);
		adder.addListener(projText[4], projVars[4], 0, null);
		adder.addListener(projText[5], projVars[5], 0, null);
		adder.addListener(projText[6], projVars[6], 0, null);
		adder.addListener(projText[7], projVars[7], -1, null);
		adder.addListener(projText[8], projVars[8], 0, null);
		adder.addListener(projText[9], projVars[9], -1, null);
		adder.addListener(projText[10], projVars[10], 0, null);
		adder.addListener(projText[12], projVars[12], 0, null);
		adder.addListener(projText[11], projVars[11], -2, null);
	}

	void initCollisions() {
		// Initialise vars and panels
		colVarA = Var.initVars(colVarA, "colVarA");
		colVarB = Var.initVars(colVarB, "colVarB");
		colVarE = new Var("e", "?", "e");
		colDiagram = new ColDiagram(this);
		JPanel westPanel = new JPanel(new GridLayout(0, 1));
		JPanel fields = new JPanel(new GridBagLayout());
		JLabel textDesc = new JLabel(
				"<html>Click on diagram to select point mass. <br>Use \"?\" for unknown variables. Units used are<br> kg, m/s, Ns");

		// layout
		this.add(westPanel, BorderLayout.WEST);
		this.add(colDiagram, BorderLayout.CENTER);
		westPanel.add(new JScrollPane(createNotesPanel()));
		westPanel.add(new JScrollPane(fields));
		colDiagram.setBorder(border);
		fields.setBorder(border);

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
			colField[i] = new JTextField("?", 10);// initialise each field
			fields.add(colField[i], gbc);
		}

		// add listeners
		ListenerAdder adder = new ListenerAdder(this);
		adder.addListener(colField[0], colVarE, 5, colVarE);
		adder.addListener(colField[1], colVarA[0], -2, colVarB[0]);
		adder.addListener(colField[2], colVarA[1], 0, colVarB[1]);
		adder.addListener(colField[3], colVarA[2], -1, colVarB[2]);
		adder.addListener(colField[4], colVarA[3], -1, colVarB[3]);
		adder.addListener(colField[5], colVarA[4], -1, colVarB[4]);
	}

	void initCenterOfMass() {
		panelCOM = new COMPanel(this);
		JPanel sidePanel = new JPanel(new GridBagLayout());
		this.add(panelCOM, BorderLayout.CENTER);
		this.add(sidePanel, BorderLayout.WEST);
		COMPanelNorth sideNorth = new COMPanelNorth(this);
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
		String path = "";
		BufferedReader br = null;
		InputStream input = null;
		topicDesc = new JTextArea(null, "", 15, 26);
		topicDesc.setLineWrap(true);

		if (topic.equals("Circles")) {
			input = this.getClass().getResourceAsStream("/circlesNotes.txt");
			path = "m2/notes/circlesNotes.txt";
		}
		if (topic.equals("Center")) {
			input = this.getClass().getResourceAsStream("/comNotes.txt");
			path = "m2/notes/comNotes.txt";
		}
		if (topic.equals("Collisions")) {
			input = this.getClass().getResourceAsStream("/collisionNotes.txt");
			path = "m2/notes/collisionNotes.txt";
		}
		if (topic.equals("Projectiles")) {
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
		topicDesc.setText("\nEquation of trajectory:\n •y = x*tan(θ)-g*x^2/(2*v^2*cos^2(θ))\n");
		topicDesc.append(
				"Acceleration:\n •Constant acceleration of 9.8 ms^-2 downwards.\n •No horizontal acceleration, horizontal velocity is constant.\n");
		topicDesc.append("Velocity\n •Velocity in x direction is V*cos(θ) \n •Velocity in y direction is V*sin(θ).\n");

		JTextField topicTitle = new JTextField(getTitle() + " notes");
		topicTitle.setEditable(false);
		topicTitle.setFont(topicTitle.getFont().deriveFont(1.2f * topicTitle.getFont().getSize()));
		panel.add(topicTitle, BorderLayout.NORTH);
		panel.add(new JScrollPane(topicDesc), BorderLayout.CENTER);

		return panel;
	}

	/**
	 * Updates text fields to show variable contents.
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
				x += Double.parseDouble(circTextA.get(i)) * Math.cos(Double.parseDouble(circTextB.get(i)));
				y += Double.parseDouble(circTextA.get(i)) * Math.sin(Double.parseDouble(circTextB.get(i)));
				System.out.println(x);
			}
			circX.setText(circVarB[0].contents);
			circY.setText(circVarB[1].contents);
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
		if (topic.equals("Circles")) {
			initCircularMotion();
			circVertical.text = circVarB;
			circVertical.force = circTextA;
			circVertical.angle = circTextB;
			circTopDown.vars = circVars;
			setTitle("Uniform Motion in a Circle");
		}
		if (topic.equals("Center")) {
			initCenterOfMass();
			setTitle("Center of Mass");
		}
		if (topic.equals("Collisions")) {
			initCollisions();
			setTitle("Coefficient of Restitution; Impulse");
		}
		if (topic.equals("Projectiles")) {
			initProjectiles();
			setTitle("Motion of a Projectile");
		}
		updateFields();
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
