package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import math.MathUtil;
import math.Var;

public class ColDiagram extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6979312766899117413L;
	Var[] a;
	Var[] b;
	Var e;

	public ColDiagram(Var[] a, Var[] b, Var e) {
		this.a = a;
		this.b = b;
		this.e = e;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		Dimension d = ColDiagram.this.getSize();
		int lx = (int) (d.getWidth() / 4);
		int ly = (int) (d.getHeight() / 2);

		g2d.drawLine((int) (lx - lx * 0.6), (int) (ly * 1.1), (int) (lx + lx * 0.6), (int) (ly * 1.1));
		g2d.fillOval((int) (lx - lx * 0.5), ly, (int) (ly * 0.1), (int) (ly * 0.1));
		g2d.fillOval((int) (lx + lx * 0.5 - ly * 0.05), ly, (int) (ly * 0.1), (int) (ly * 0.1));

		if (a == null) {
			// Skip drawing if not initialised correctly.
			return;
		}
		// DRAWING A

		// Draw name rather than contents if unknown! Using String constructor
		// to avoid changing original variable.

		Font font = g2d.getFont();
		g2d.setFont(font.deriveFont(font.getSize() * 1.5f));
		g2d.drawString("Before", (int) (d.getWidth() / 16), (int) (d.getHeight() / 4));
		g2d.drawString("After", (int) (d.getWidth() / 2 + d.getWidth() / 16), (int) (d.getHeight() / 4));
		g2d.setFont(font);
		if (!e.contents.equals("=")) {
			g2d.drawString("e = " + e.contents, lx, ly);
		}

		String label = new String(a[1].contents.equals("?") ? a[1].label : a[1].contents);
		g2d.drawString(label + " kg", (int) (lx - lx * 0.5), (int) (ly * 1.18));
		g2d.drawString(a[0].contents, (int) (lx - lx * 0.55), (int) (ly));

		if (!a[2].contents.equals("0")) {
			g2d.drawLine((int) (lx - lx * 0.5 + ly * 0.05 - ly * 0.1), (int) (ly * 0.95),
					(int) (lx - lx * 0.5 + ly * 0.05 + ly * 0.1), (int) (ly * 0.95));
			if (MathUtil.isNumeric(a[2].contents) && Double.parseDouble(a[2].contents) < 0) {
				// remove negative sign
				label = new String(a[2].contents).substring(1, a[2].contents.length());

				// Draw < arrows
				int tx = (int) (lx - lx * 0.5 + ly * 0.05 - ly * 0.1);
				int ty = (int) (ly * 0.95);
				g2d.drawLine(tx, ty, (int) (tx + ly * 0.04 * 0.6), (int) (ty - ly * 0.04 * 0.6));
				g2d.drawLine(tx, ty, (int) (tx + ly * 0.04 * 0.6), (int) (ty + ly * 0.04 * 0.6));

			} else {
				if (a[2].contents.equals("?")) {
					label = new String(a[2].label);

				} else {
					label = new String(a[2].contents);
				}

				// Draw > arrows
				int tx = (int) (lx - lx * 0.5 + ly * 0.05 + ly * 0.1);
				int ty = (int) (ly * 0.95);
				g2d.drawLine(tx, ty, (int) (tx - ly * 0.04 * 0.6), (int) (ty - ly * 0.04 * 0.6));
				g2d.drawLine(tx, ty, (int) (tx - ly * 0.04 * 0.6), (int) (ty + ly * 0.04 * 0.6));

			}
			label = label + " m/s";
		} else {
			label = "Stationary";
		}
		g2d.drawString(label, (int) (lx - lx * 0.5), (int) (ly * 0.9));

		// DRAWING B

		g2d.drawString(b[0].contents, (int) (lx * 1.4), (int) (ly));
		label = new String(b[1].contents.equals("?") ? b[1].label : b[1].contents);
		g2d.drawString(label + " kg", (int) (lx * 1.45), (int) (ly * 1.18));

		if (!b[2].contents.equals("0")) {

			g2d.drawLine((int) (lx + lx * 0.5 - ly * 0.1), (int) (ly * 0.95), (int) (lx + lx * 0.5 + ly * 0.1),
					(int) (ly * 0.95));
			if (MathUtil.isNumeric(b[2].contents) && Double.parseDouble(b[2].contents) < 0) {
				// removes negative sign
				label = new String(b[2].contents).substring(1, b[2].contents.length());
				int tx = (int) (lx + lx * 0.5 - ly * 0.1);
				int ty = (int) (ly * 0.95);

				// Draw < arrows
				g2d.drawLine(tx, ty, (int) (tx + ly * 0.04 * 0.6), (int) (ty - ly * 0.04 * 0.6));
				g2d.drawLine(tx, ty, (int) (tx + ly * 0.04 * 0.6), (int) (ty + ly * 0.04 * 0.6));

			} else {
				if (b[2].contents.equals("?")) {
					label = new String(b[2].label);

				} else {
					label = new String(b[2].contents);
				}

				// Draw > arrows
				int tx = (int) (lx + lx * 0.5 + ly * 0.1);
				int ty = (int) (ly * 0.95);
				g2d.drawLine(tx, ty, (int) (tx - ly * 0.04 * 0.6), (int) (ty - ly * 0.04 * 0.6));
				g2d.drawLine(tx, ty, (int) (tx - ly * 0.04 * 0.6), (int) (ty + ly * 0.04 * 0.6));

			}
			label = label + " m/s";
		} else {
			label = "Stationary";
		}
		g2d.drawString(label, (int) (lx * 1.45), (int) (ly * 0.9));

		/*
		 * DRAW RIGHT HALF!
		 * 
		 * Same as previous code except translated by c;
		 * 
		 */
		g2d.drawLine((int) (d.getWidth() / 2), 0, (int) (d.getWidth() / 2), (int) d.getHeight());
		lx = (int) (d.getWidth() / 4);
		ly = (int) (d.getHeight() / 2);
		int c = 2 * lx;

		g2d.drawLine((int) (lx - lx * 0.6 + c), (int) (ly * 1.1), (int) (lx + lx * 0.6 + c), (int) (ly * 1.1));
		g2d.fillOval((int) (lx - lx * 0.5 + c), ly, (int) (ly * 0.1), (int) (ly * 0.1));
		g2d.fillOval((int) (lx + lx * 0.5 - ly * 0.05 + c), ly, (int) (ly * 0.1), (int) (ly * 0.1));

		if (!e.contents.equals("=")) {
			g2d.drawString("e = " + e.contents, lx + c, ly);
		}

		// DRAWING A

		// Draw name rather than contents if unknown! Using String constructor
		// to avoid changing original variable.
		label = new String(a[1].contents.equals("?") ? a[1].label : a[1].contents);
		g2d.drawString(label + " kg", (int) (lx - lx * 0.5 + c), (int) (ly * 1.18));
		g2d.drawString(a[0].contents, (int) (lx - lx * 0.55 + c), (int) (ly));

		if (!a[3].contents.equals("0")) {
			g2d.drawLine((int) (lx - lx * 0.5 + ly * 0.05 - ly * 0.1 + c), (int) (ly * 0.95),
					(int) (lx - lx * 0.5 + ly * 0.05 + ly * 0.1 + c), (int) (ly * 0.95));
			if (MathUtil.isNumeric(a[3].contents) && Double.parseDouble(a[3].contents) < 0) {
				// remove negative sign
				label = new String(a[3].contents).substring(1, a[3].contents.length());

				// Draw < arrows
				int tx = (int) (lx - lx * 0.5 + ly * 0.05 - ly * 0.1 + c);
				int ty = (int) (ly * 0.95);
				g2d.drawLine(tx, ty, (int) (tx + ly * 0.04 * 0.6), (int) (ty - ly * 0.04 * 0.6));
				g2d.drawLine(tx, ty, (int) (tx + ly * 0.04 * 0.6), (int) (ty + ly * 0.04 * 0.6));

			} else {
				if (a[3].contents.equals("?")) {
					label = new String(a[3].label);

				} else {
					label = new String(a[3].contents);
				}

				// Draw > arrows
				int tx = (int) (lx - lx * 0.5 + ly * 0.05 + ly * 0.1 + c);
				int ty = (int) (ly * 0.95);
				g2d.drawLine(tx, ty, (int) (tx - ly * 0.04 * 0.6), (int) (ty - ly * 0.04 * 0.6));
				g2d.drawLine(tx, ty, (int) (tx - ly * 0.04 * 0.6), (int) (ty + ly * 0.04 * 0.6));

			}
			label = label + " m/s";
		} else {
			label = "Stationary";
		}
		g2d.drawString(label, (int) (lx - lx * 0.5 + c), (int) (ly * 0.9));

		// DRAWING B

		g2d.drawString(b[0].contents, (int) (lx * 1.4 + c), (int) (ly));
		label = new String(b[1].contents.equals("?") ? b[1].label : b[1].contents);
		g2d.drawString(label + " kg", (int) (lx * 1.45 + c), (int) (ly * 1.18));

		if (!b[3].contents.equals("0")) {

			g2d.drawLine((int) (lx + lx * 0.5 - ly * 0.1 + c), (int) (ly * 0.95), (int) (c + lx + lx * 0.5 + ly * 0.1),
					(int) (ly * 0.95));
			if (MathUtil.isNumeric(b[3].contents) && Double.parseDouble(b[3].contents) < 0) {
				// removes negative sign
				label = new String(b[3].contents).substring(1, b[3].contents.length());

				// Draw < arrows
				int tx = (int) (lx + lx * 0.5 - ly * 0.1 + c);
				int ty = (int) (ly * 0.95);
				g2d.drawLine(tx, ty, (int) (tx + ly * 0.04 * 0.6), (int) (ty - ly * 0.04 * 0.6));
				g2d.drawLine(tx, ty, (int) (tx + ly * 0.04 * 0.6), (int) (ty + ly * 0.04 * 0.6));

			} else {
				if (b[3].contents.equals("?")) {
					label = new String(b[3].label);

				} else {
					label = new String(b[3].contents);
				}
				// Draw > arrows
				int tx = (int) (lx + lx * 0.5 + ly * 0.1 + c);
				int ty = (int) (ly * 0.95);
				g2d.drawLine(tx, ty, (int) (tx - ly * 0.04 * 0.6), (int) (ty - ly * 0.04 * 0.6));
				g2d.drawLine(tx, ty, (int) (tx - ly * 0.04 * 0.6), (int) (ty + ly * 0.04 * 0.6));

			}
			label = label + " m/s";
		} else {
			label = "Stationary";
		}
		g2d.drawString(label, (int) (lx * 1.45) + c, (int) (ly * 0.9));

	}
}
