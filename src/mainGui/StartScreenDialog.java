package mainGui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;

/**
 * 
 * The StartScreenDialog class creates a popup menu for choosing the topic/
 * 
 */
public class StartScreenDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	private Dimension prefSize = new Dimension(500, 500);
	private String topic = Frame.CENTER;

	/**
	 * Construct the gui
	 */
	public StartScreenDialog() {
		setPreferredSize(prefSize);
		setTitle("Topic Selection");
		setModal(true);
		center();
		setResizable(false);

		Container frame = this.getContentPane();
		frame.setLayout(new FlowLayout(FlowLayout.LEFT));
		JButton topicCircles = new JButton("circles");
		JButton topicRestitute = new JButton("Collisons");
		JButton topicCenter = new JButton("topicCenterOfMass");
		JButton topicProjectiles = new JButton("Projectile Motion");

		frame.add(topicCircles);
		frame.add(topicRestitute);
		frame.add(topicCenter);
		frame.add(topicProjectiles);

		topicCircles.setActionCommand(Frame.CIRCLES);
		topicRestitute.setActionCommand(Frame.COLLISIONS);
		topicCenter.setActionCommand(Frame.CENTER);
		topicProjectiles.setActionCommand(Frame.PROJECTILES);
		ActionListener tpcListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				topic = e.getActionCommand();
				setVisible(false);
			}
		};
		topicCircles.addActionListener(tpcListener);
		topicRestitute.addActionListener(tpcListener);
		topicCenter.addActionListener(tpcListener);
		topicProjectiles.addActionListener(tpcListener);

		pack();
		setAlwaysOnTop(true);
		setVisible(true);
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
	public String getTopic() {
		return topic;
	}
}
