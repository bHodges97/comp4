package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import circularMotion.CircDialog;
import circularMotion.CircFields;
import circularMotion.CircTopDown;
import circularMotion.CircVertical;

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
	// CircularMotion
	CircTopDown circTopDown;
	CircVertical circVertical;
	JPanel panelDiagram;
	CircFields panelFields;

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

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);

	}

	private void setTopic(String t) {
		setTitle(t);
		canvas.setTopic(t);
	}

	private void initCircularMotion() {
		panelDiagram = new JPanel();
		panelFields = new CircFields();
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

	/**
	 * MAIN METHOD
	 * 
	 * @param Args
	 */
	public static void main(String[] Args) {
		window = new Frame();
	}
}
