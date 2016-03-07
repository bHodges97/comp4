package mainGui.projectileMotion;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.QuadCurve2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import math.MathUtil;
import math.Var;

public class ProjDiagram extends JPanel {

	Var[] v;

	public ProjDiagram(Var[] projVars) {
		v = projVars;
	}

	public void print() {
		BufferedImage img = new BufferedImage(this.getWidth(),
				this.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics print = img.getGraphics();
		printAll(print);
		try {
			ImageIO.write(img, "JPEG", new File("DiagramProjectileMotion.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		Dimension d = getSize();
		String label = "TEST";
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		g2d.setColor(Color.black);
		if (v == null || v[0] == null) {
			// Stop painting.
			return;
		}
		if (v[4].isZero()) {
			if (v[9].getVal() <= 0) {
				g2d.drawString("THIS IS NO LONGER PROJECTILE MOTION!", 10, 20);
				return;
			}
		}

		int y;
		int r = (int) (d.getHeight() * 0.03);
		int widthDivByTen = (int) (d.getWidth() / 10);

		//Draw ground.
		g2d.drawLine((int) (widthDivByTen - r), (int) (d.getHeight() * 0.8),
				(int) (widthDivByTen * 9), (int) (d.getHeight() * 0.8));

		//Draw height arrow
		if (v[4].isZero()) {
			y = (int) (d.getHeight() * 0.8 - r);
		} else {
			y = (int) (d.getHeight() * 0.4);
			MathUtil.drawArrow(g2d, (int) (widthDivByTen),
					(int) (d.getHeight() * 0.8), (int) (widthDivByTen), y + r,
					r / 2);
			MathUtil.drawArrow(g2d, (int) (widthDivByTen), y + r,
					(int) (widthDivByTen), (int) (d.getHeight() * 0.8), r / 2);
			label = new String(v[4].isKnown() ? v[4].name : v[4].contents);
			g2d.drawString(label + " m", (int) (widthDivByTen) - r,
					(int) (1.5 * y));
		}

		int ry;
		int rx = v[10].isZero() ? (int) (widthDivByTen)
				: (int) (d.getWidth() * 0.7);

		// draw end position arrows.
		if (v[12].getVal() == 0 || v[12].isKnown()) {
			ry = (int) (d.getHeight() * 0.8 - r);
		} else {
			ry = (int) (d.getHeight() * 0.6 - r);
			MathUtil.drawArrow(g2d, rx + r, (int) (d.getHeight() * 0.8),
					rx + r, ry + r, r / 2);
			MathUtil.drawArrow(g2d, rx + r, ry + r, rx + r,
					(int) (d.getHeight() * 0.8), r / 2);
			label = v[12].isKnown() ? v[12].label : v[12].contents;
			g2d.drawString(label + " m", rx + r + 2,
					(int) (((d.getHeight() * 0.8) - ry) / 2 + ry));
		}

		// draw Ovals
		g2d.fillOval((int) (widthDivByTen), y, r, r);
		g2d.fillOval((int) rx, (int) ry, r, r);

		// Draw distance arrows.
		if (!v[10].isZero()) {
			int t = ry;
			ry = (int) (d.getHeight() * 0.8 - r);
			MathUtil.drawArrow(g2d, (int) (widthDivByTen + r),
					(int) (ry + 1.5 * r), rx, (int) (ry + 1.5 * r), r);
			MathUtil.drawArrow(g2d, rx, (int) (ry + 1.5 * r),
					(int) (widthDivByTen + r), (int) (ry + 1.5 * r), r);
			label = new String(v[10].isKnown() ? v[10].label : v[10].contents);
			g2d.drawString(label + " m", rx / 2, (int) (ry + 1.9 * r));
			ry = t;
		}

		label = new String(v[11].contents);
		g2d.drawString(label, (int) (widthDivByTen), y + r / 2);

		//Control values for where the curve passes through.
		int ctrlY = 0;
		int ctrlX = (int) (((d.getWidth() * 0.7 + r / 2) - (widthDivByTen + r / 2)) / 2 + (d
				.getWidth() / 10 + r / 2));

		// draw  initial arrows
		if (v[9].getVal() > 0 || v[9].isKnown()) {
			MathUtil.drawArrow(g2d, (int) (widthDivByTen + r / 2), y,
					(int) (widthDivByTen + r / 2), y - 2 * r, r);
			ctrlY = (int) (y * 0.1 + r);
			g2d.drawArc(widthDivByTen - r, y - r, 3 * r, 3 * r, 0, (int) Math
					.toDegrees(Math.atan(d.getHeight() / d.getWidth())));
			label = new String(v[0].isKnown() ? v[0].label : v[0].contents);
			g2d.drawString(label, (int) (widthDivByTen + 2.1 * r),
					(int) (y * 1.01));
			label = new String(v[9].isKnown() ? v[9].label : v[9].contents);
			g2d.drawString(label + " m/s", (int) (widthDivByTen + r / 2),
					(int) (y * 0.94));

		} else if (v[9].getVal() < 0) {
			MathUtil.drawArrow(g2d, (int) (widthDivByTen + r / 2), y,
					(int) (widthDivByTen + r / 2), y + 3 * r, r);
			ctrlY = (int) (y * 1.2 + r);
			ctrlX *= 1.6;

			g2d.drawArc(
					widthDivByTen - r,
					y - r,
					3 * r,
					3 * r,
					0,
					(int) (Math.toDegrees(Math.atan((ctrlY - d.getHeight()
							* 0.8 - r)
							/ ctrlX))) + 10);

			label = new String(v[0].isKnown() ? v[0].label : v[0].contents);
			g2d.drawString(label, (int) (widthDivByTen + 2.1 * r),
					(int) (y * 1.07));
			label = new String(v[9].contents);
			g2d.drawString(label + " m/s", (int) (widthDivByTen + r / 2),
					(int) (y * 1.15));
		} else {
			ctrlY = y;
		}

		if (!v[8].isZero()) {
			MathUtil.drawArrow(g2d, (int) (widthDivByTen + r), y + r / 2,
					(int) (widthDivByTen + 3 * r), y + r / 2, r);
			label = new String(v[8].isKnown() ? v[8].label : v[8].contents);
			g2d.drawString(label + " m/s", (int) (widthDivByTen + 2 * r),
					(int) (y + r));
		}

		//Draw arc.
		if (!v[10].isZero()) {
			Stroke norm = g2d.getStroke();
			Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT,
					BasicStroke.JOIN_BEVEL, 0, new float[] { 8 }, 0);
			g2d.setStroke(dashed);
			QuadCurve2D.Double curve = new QuadCurve2D.Double(
					(int) (widthDivByTen) + r / 2, y + r / 2, ctrlX, ctrlY, rx
							+ r / 2, ry + r / 2);
			g2d.draw(curve);
			g2d.setStroke(norm);
		}

		// Draw second;

		if (v[3].getVal() < 0 || v[3].isKnown()) {
			MathUtil.drawArrow(g2d, rx + r / 2, ry, rx + r / 2, ry + 3 * r, r);
			label = new String(v[3].isKnown() ? v[3].label : v[3].contents);
			g2d.drawString(label + " m/s", (int) (rx - r), (int) (ry + 2 * r));
		}
		if (!v[2].isZero()) {
			MathUtil.drawArrow(g2d, rx, ry + r / 2, rx + 3 * r, ry + r / 2, r);
			label = new String(v[2].isKnown() ? v[2].label : v[2].contents);
			g2d.drawString(label + " m/s", (int) (rx + 1.5 * r), ry + r / 2);
		}

	}

}
