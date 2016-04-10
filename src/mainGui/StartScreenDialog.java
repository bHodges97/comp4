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

@SuppressWarnings("serial")
public class StartScreenDialog extends JDialog {
	private Dimension prefSize = new Dimension(500, 500);
	public String topic = "Default";

	public StartScreenDialog() {
		setPreferredSize(prefSize);
		setTitle("Topic Selection");
		setModal(true);
		center();
		this.setResizable(false);
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

		topicCircles.setActionCommand("Circles");
		topicRestitute.setActionCommand("Collisions");
		topicCenter.setActionCommand("Center");
		topicProjectiles.setActionCommand("Projectiles");
		ActionListener tpcListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				topic = e.getActionCommand();
				if (e.getActionCommand().equals("Circles")) {

				}
				if (e.getActionCommand().equals("Collisions")) {

				}
				if (e.getActionCommand().equals("Center")) {

				}
				setVisible(false);
			}
		};
		topicCircles.addActionListener(tpcListener);
		topicRestitute.addActionListener(tpcListener);
		topicCenter.addActionListener(tpcListener);
		topicProjectiles.addActionListener(tpcListener);
		this.pack();
		setAlwaysOnTop(true);
		setVisible(true);

	}

	private void center() {
		GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point center = g.getCenterPoint();
		setLocation(center.x - prefSize.width / 2, center.y - prefSize.height / 2);
	}
}
