package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.QuadCurve2D;

import javax.swing.JPanel;

import math.MathUtil;
import math.Var;

public class ProjDiagram extends JPanel {

	Var[] v;

	public ProjDiagram(Var[] projVars) {
		v = projVars;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		Dimension d = getSize();

		if (v.equals(null)) {
			// Stop painting.
			return;
		}

		g2d.drawLine((int) (d.getWidth() / 10), (int) (d.getHeight() * 0.8), (int) (d.getWidth() / 10 * 9),
				(int) (d.getHeight() * 0.8));

		int y;
		int r = (int) (d.getHeight() * 0.03);
		if (v[4].contents.equals("0")) {
			y = (int) (d.getHeight() * 0.8 - r);
		} else {
			y = (int) (d.getHeight() * 0.4);
			MathUtil.drawArrow(g2d, (int) (d.getWidth() / 10 + r / 2), (int) (d.getHeight() * 0.8),
					(int) (d.getWidth() / 10 + r / 2), y + r, r);
			MathUtil.drawArrow(g2d,(int) (d.getWidth() / 10 + r / 2), y + r,(int) (d.getWidth() / 10 + r / 2), (int) (d.getHeight() * 0.8),
					 r);
		}

		QuadCurve2D.Double curve = new QuadCurve2D.Double((int) (d.getWidth() / 10) + r / 2, y + r / 2,
				(int) (d.getWidth() / 20 * 9 * 0.9) + r, y * 0.1 + r, d.getWidth() * 0.7 + r / 2,
				(int) (d.getHeight() * 0.8) - r / 2);
		g2d.draw(curve);
		g2d.fillOval((int) (d.getWidth() / 10), y, r, r);
		g2d.fillOval((int) (d.getWidth() * 0.7), (int) (d.getHeight() * 0.8 - r), r, r);
		if (v[9].getVal() > 0 || v[8].contents.equals("?")) {
			MathUtil.drawArrow(g2d, (int) (d.getWidth() / 10 + r / 2), y, (int) (d.getWidth() / 10 + r / 2), y - 2 * r,
					r);
		}
		if (!v[8].contents.equals("0")) {
			MathUtil.drawArrow(g2d, (int) (d.getWidth() / 10 + r), y + r / 2, (int) (d.getWidth() / 10 + 3 * r),
					y + r / 2, r);
		}

	}

}
