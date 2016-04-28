package mainGui;

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

/**
 * 
 * The StartScreenDialog class creates a popup menu for choosing the topic/
 * 
 */
public class StartScreenDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private Dimension prefSize = new Dimension(990, 380);
	private int topic = Frame.CENTER;

	private JButton topicCircles = new JButton("Circular Motion");
	private JButton topicRestitute = new JButton("Collisons and Restitution");
	private JButton topicCentre = new JButton("Center Of Mass");
	private JButton topicProjectiles = new JButton("Projectile Motion");
	private JTextArea textCircles = new JTextArea(15, 20);
	private JTextArea textRestitute = new JTextArea(15, 20);
	private JTextArea textCentre = new JTextArea(15, 20);
	private JTextArea textProjectiles = new JTextArea(15, 20);

	/**
	 * Construct the gui
	 */
	public StartScreenDialog() {
		setPreferredSize(prefSize);
		setTitle("Topic Selection");
		setModal(true);
		center();
		setResizable(false);

		setLayout(new GridBagLayout());

		setupTextFields();

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(2, 2, 2, 2);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 4;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.BOTH;
		add(new JLabel("<html><h2>Select a Topic"), gbc);
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 1;
		add(topicCircles, gbc);
		gbc.gridx = 1;
		add(topicRestitute, gbc);
		gbc.gridx = 2;
		add(topicCentre, gbc);
		gbc.gridx = 3;
		add(topicProjectiles, gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		add(new JScrollPane(textCircles), gbc);
		gbc.gridx = 1;
		add(new JScrollPane(textRestitute), gbc);
		gbc.gridx = 2;
		add(new JScrollPane(textCentre), gbc);
		gbc.gridx = 3;
		add(new JScrollPane(textProjectiles), gbc);

		topicCircles.setActionCommand("" + Frame.CIRCLES);
		topicRestitute.setActionCommand("" + Frame.COLLISIONS);
		topicCentre.setActionCommand("" + Frame.CENTER + "");
		topicProjectiles.setActionCommand("" + Frame.PROJECTILES);
		ActionListener tpcListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				topic = Integer.parseInt(e.getActionCommand());
				setVisible(false);
			}
		};
		topicCircles.addActionListener(tpcListener);
		topicRestitute.addActionListener(tpcListener);
		topicCentre.addActionListener(tpcListener);
		topicProjectiles.addActionListener(tpcListener);

		pack();
		setAlwaysOnTop(true);
		setVisible(true);

	}

	private void setupTextFields() {
		textCircles.setEditable(false);
		textRestitute.setEditable(false);
		textCentre.setEditable(false);
		textProjectiles.setEditable(false);
		textCircles.setLineWrap(true);
		textRestitute.setLineWrap(true);
		textCentre.setLineWrap(true);
		textProjectiles.setLineWrap(true);

		JTextArea[] collection = { textCircles, textRestitute, textCentre, textProjectiles };
		for (int i = 0; i < collection.length; i++) {
			try {
				InputStream resource = this.getClass().getResourceAsStream("/desc" + i + ".txt");
				BufferedReader br = new BufferedReader(new InputStreamReader(resource));
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
	}

	/**
	 * Centers the popup
	 */
	private void center() {
		GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point center = g.getCenterPoint();
		setLocation(center.x - prefSize.width / 2, center.y - prefSize.height / 2);
	}

	/**
	 * @return The chosen topic
	 */
	public int getTopic() {
		return topic;
	}
}
