package mainGui.centerOfMass;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import math.MathUtil;
import math.MyPoint;
import math.Obj;
import math.Plane;

public class sidepanelNorth extends JScrollPane {
	public JPanel p = new JPanel();
	public final JButton b1 = new JButton("test");
	public final JButton b2 = new JButton("Rectangle");
	public final JButton b3 = new JButton("Circle Sector");
	public final JButton b4 = new JButton("Rod");
	public final JButton b5 = new JButton("arc");
	public final JButton b6 = new JButton("PointMass");
	public final DialogPointMass Dialogb6 = new DialogPointMass();
	public final DialogRect Dialogb2 = new DialogRect();

	DialogNewObj popupCofM = new DialogNewObj();

	public sidepanelNorth(final Plane plane) {
		JScrollPane scrollFrame = new JScrollPane(p);
		p.setAutoscrolls(true);
		p.setPreferredSize(new Dimension(300, 300));
		p.setBorder(BorderFactory.createEtchedBorder(1));
		p.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridy = 0;
		gbc.gridx = 0;
		p.add(b1, gbc);
		gbc.gridx++;
		p.add(b2, gbc);
		gbc.gridx++;
		p.add(b3, gbc);
		gbc.gridy++;
		gbc.gridx = 0;
		p.add(b4, gbc);
		gbc.gridx++;
		p.add(b5, gbc);
		gbc.gridx++;
		p.add(b6, gbc);
		gbc.gridy++;
		gbc.gridx = 0;

		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				popupCofM = new DialogNewObj();
				popupCofM.setVisible(true);
				plane.add(popupCofM.object);
				popupCofM.dispose();
			}
		});
		b2.addActionListener(new ActionListener() {
			// rectangle
			@Override
			public void actionPerformed(ActionEvent e) {
				Dialogb2.open();
				plane.add(Dialogb2.returnObj);
			}
		});
		b3.addActionListener(new ActionListener() {
			// circsector
			@Override
			public void actionPerformed(ActionEvent e) {
				plane.add(new Obj(2, new MyPoint(0, 0), MathUtil.genCirc(Math.PI, 5d, false), 0d, 0f));

			}
		});
		b4.addActionListener(new ActionListener() {
			// rod
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		b5.addActionListener(new ActionListener() {
			// arc
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		b6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Dialogb6.open();
				plane.add(Dialogb6.returnObj);
			}
		});

		p.setVisible(true);

	}
}
