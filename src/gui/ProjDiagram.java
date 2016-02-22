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
		String label = "TEST";
		if (v == null || v[0] == null) {
			// Stop painting.
			return;
		}
		if (v[4].contents.equals("0")) {
			if (v[9].getVal() <= 0) {
				g2d.drawString("THIS IS NO LONGER PROJECTILE MOTION!", 10, 20);
				return;
			}
		}

		g2d.drawLine((int) (d.getWidth() / 10), (int) (d.getHeight() * 0.8), (int) (d.getWidth() / 10 * 9),
				(int) (d.getHeight() * 0.8));

		int y;
		int r = (int) (d.getHeight() * 0.03);
		if (v[4].contents.equals("0")) {
			y = (int) (d.getHeight() * 0.8 - r);
		} else {
			y = (int) (d.getHeight() * 0.4);
			MathUtil.drawArrow(g2d, (int) (d.getWidth() / 10), (int) (d.getHeight() * 0.8), (int) (d.getWidth() / 10),
					y + r, r / 2);
			MathUtil.drawArrow(g2d, (int) (d.getWidth() / 10), y + r, (int) (d.getWidth() / 10),
					(int) (d.getHeight() * 0.8), r / 2);
			label = new String(v[4].contents.equals("?") ? v[4].name : v[4].contents);
			g2d.drawString(label + " m", (int) (d.getWidth() / 10) - r, (int) (1.5 * y));
		}
		int ry;
		int rx = v[10].contents.equals("0") ? (int) (d.getWidth() / 10) : (int) (d.getWidth() * 0.7);

		// draw right arrows.
		if (v[12].getVal() == 0 || v[12].contents.equals("?")) {
			ry = (int) (d.getHeight() * 0.8 - r);
		} else {
			ry = (int) (d.getHeight() * 0.6 - r);
			MathUtil.drawArrow(g2d, rx + r, (int) (d.getHeight() * 0.8), rx + r, ry + r, r / 2);
			MathUtil.drawArrow(g2d, rx + r, ry + r, rx + r, (int) (d.getHeight() * 0.8), r / 2);
			label = v[12].contents.equals("?") ? v[12].label : v[12].contents;
			g2d.drawString(label + " m", rx + r + 2, (int) (((d.getHeight() * 0.8) - ry) / 2 + ry));
		}

		// draw Ovals
		g2d.fillOval((int) (d.getWidth() / 10), y, r, r);
		g2d.fillOval((int) rx, (int) ry, r, r);

		// draw bottom arrows.
		if (!v[10].contents.equals("0")) {
			int t = ry;
			ry = (int) (d.getHeight() * 0.8 - r);
			MathUtil.drawArrow(g2d, (int) (d.getWidth() / 10 + r), (int) (ry + 1.5 * r), rx, (int) (ry + 1.5 * r), r);
			MathUtil.drawArrow(g2d, rx, (int) (ry + 1.5 * r), (int) (d.getWidth() / 10 + r), (int) (ry + 1.5 * r), r);
			label = new String(v[10].contents.equals("?") ? v[10].label : v[10].contents);
			g2d.drawString(label + " m", rx / 2, (int) (ry + 1.9 * r));
			ry = t;
		}

		label = new String(v[11].contents);
		g2d.drawString(label, (int) (d.getWidth() / 10), y + r / 2);

		int ctrlY = 0;
		int ctrlX = (int) (((d.getWidth() * 0.7 + r / 2) - (d.getWidth() / 10 + r / 2)) / 2
				+ (d.getWidth() / 10 + r / 2));
		// draw arrows
		if (v[9].getVal() > 0 || v[9].contents.equals("?")) {
			MathUtil.drawArrow(g2d, (int) (d.getWidth() / 10 + r / 2), y, (int) (d.getWidth() / 10 + r / 2), y - 2 * r,
					r);
			ctrlY = (int) (y * 0.1 + r);
			g2d.drawArc((int) (d.getWidth() / 10) - r, y - r, 3 * r, 3 * r, 0,
					(int) Math.toDegrees(Math.atan(d.getHeight() / d.getWidth())));
			label = new String(v[0].contents.equals("?") ? v[0].label : v[0].contents);
			g2d.drawString(label, (int) (d.getWidth() / 10 + 2.1 * r), (int) (y * 1.01));
			label = new String(v[9].contents.equals("?") ? v[9].label : v[9].contents);
			g2d.drawString(label + " m/s", (int) (d.getWidth() / 10 + r / 2), (int) (y * 0.94));

		} else if (v[9].getVal() < 0) {
			MathUtil.drawArrow(g2d, (int) (d.getWidth() / 10 + r / 2), y, (int) (d.getWidth() / 10 + r / 2), y + 3 * r,
					r);
			ctrlY = (int) (y * 1.2 + r);
			ctrlX *= 1.6;
			g2d.drawArc((int) (d.getWidth() / 10) - r, y - r, 3 * r, 3 * r, 0,
					(int) (Math.toDegrees(Math.atan((ctrlY - d.getHeight() * 0.8 - r) / ctrlX))) + 10);
			label = new String(v[0].contents.equals("?") ? v[0].label : v[0].contents);
			g2d.drawString(label, (int) (d.getWidth() / 10 + 2.1 * r), (int) (y * 1.07));
			label = new String(v[9].contents);
			g2d.drawString(label + " m/s", (int) (d.getWidth() / 10 + r / 2), (int) (y * 1.15));
		} else {
			ctrlY = y;
		}
		if (!v[8].contents.equals("0")) {
			MathUtil.drawArrow(g2d, (int) (d.getWidth() / 10 + r), y + r / 2, (int) (d.getWidth() / 10 + 3 * r),
					y + r / 2, r);
			label = new String(v[8].contents.equals("?") ? v[8].label : v[8].contents);
			g2d.drawString(label + " m/s", (int) (d.getWidth() / 10 + 2 * r), (int) (y + r));
		}
		if (!v[10].contents.equals("0")) {
			QuadCurve2D.Double curve = new QuadCurve2D.Double((int) (d.getWidth() / 10) + r / 2, y + r / 2, ctrlX,
					ctrlY, rx + r / 2, ry + r / 2);
			g2d.draw(curve);
		}

		// Draw second;

		if (v[3].getVal() < 0 || v[3].contents.equals("?")) {
			MathUtil.drawArrow(g2d, rx + r / 2, ry, rx + r / 2, ry + 3 * r, r);
			label = new String(v[3].contents.equals("?") ? v[3].label : v[3].contents);
			g2d.drawString(label + " m/s", (int) (rx - r), (int) (ry + 2 * r));
		}
		if (!v[2].contents.equals("0")) {
			MathUtil.drawArrow(g2d, rx, ry + r / 2, rx + 3 * r, ry + r / 2, r);
			label = new String(v[2].contents.equals("?") ? v[2].label : v[2].contents);
			g2d.drawString(label + " m/s", (int) (rx + 1.5 * r), ry + r / 2);
		}

	}

}
