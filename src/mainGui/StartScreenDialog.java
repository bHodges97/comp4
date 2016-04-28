package mainGui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

/**
 * 
 * The StartScreenDialog class creates a popup menu for choosing the topic/
 * 
 */
public class StartScreenDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private int topic = Frame.CENTER;

	private JButton topicCircles = new JButton("Circular Motion");
	private JButton topicRestitute = new JButton("Collisons and Restitution");
	private JButton topicCentre = new JButton("Center Of Mass");
	private JButton buttonProjectiles = new JButton("Projectile Motion");
	private JButton buttonExit = new JButton("Start");
	private JTextArea textArea = new JTextArea(15, 20);
	private StringBuilder[] collection = new StringBuilder[4];
	private Color paleGreen = new Color(144, 247, 144);

	/**
	 * Construct the gui
	 */
	public StartScreenDialog() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {

		}
		setPreferredSize(new Dimension(461, 371));
		setResizable(false);
		setTitle("Topic Selection");
		setModal(true);

		setupTextArea();
		topicCircles.setFocusPainted(false);
		topicRestitute.setFocusPainted(false);
		topicCentre.setFocusPainted(false);
		buttonProjectiles.setFocusPainted(false);

		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(2, 2, 2, 2);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(new JLabel("<html><h2>Select a Topic"), gbc);
		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.gridy = 1;
		add(topicCircles, gbc);
		gbc.gridy = 2;
		add(topicRestitute, gbc);
		gbc.gridy = 3;
		add(topicCentre, gbc);
		gbc.gridy = 4;
		add(buttonProjectiles, gbc);
		gbc.gridy = 5;
		add(buttonExit, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridheight = 4;
		add(new JScrollPane(textArea), gbc);

		topicCircles.setActionCommand("" + Frame.CIRCLES);
		topicRestitute.setActionCommand("" + Frame.COLLISIONS);
		topicCentre.setActionCommand("" + Frame.CENTER + "");
		buttonProjectiles.setActionCommand("" + Frame.PROJECTILES);
		ActionListener tpcListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				topic = Integer.parseInt(e.getActionCommand());
				textArea.setText(collection[topic].toString());
				topicCircles.setBackground(Color.white);
				topicRestitute.setBackground(Color.white);
				topicCentre.setBackground(Color.white);
				buttonProjectiles.setBackground(Color.white);

				((JButton) e.getSource()).setBackground(paleGreen);
			}
		};
		topicCircles.addActionListener(tpcListener);
		topicRestitute.addActionListener(tpcListener);
		topicCentre.addActionListener(tpcListener);
		buttonProjectiles.addActionListener(tpcListener);

		buttonExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		pack();
		centre();
		setAlwaysOnTop(true);
		setVisible(true);

	}

	private void setupTextArea() {
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		topicCircles.setBackground(paleGreen);

		for (int i = 0; i < collection.length; i++) {
			collection[i] = new StringBuilder();
			try {
				InputStream resource = this.getClass().getResourceAsStream("/desc" + i + ".txt");
				BufferedReader br = new BufferedReader(new InputStreamReader(resource, "UTF-8"));
				br = new BufferedReader(new InputStreamReader(resource));
				String line = br.readLine();
				while (line != null) {
					collection[i].append(line);
					collection[i].append(System.lineSeparator());
					line = br.readLine();
				}
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		textArea.setText(collection[0].toString());
	}

	/**
	 * Centers the popup
	 */
	private void centre() {
		GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centre = g.getCenterPoint();
		setLocation(centre.x - getSize().width / 2, centre.y - getSize().height / 2);
	}

	/**
	 * @return The chosen topic
	 */
	public int getTopic() {
		return topic;
	}
}
