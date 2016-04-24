package mainGui.collision;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import mainGui.Frame;
import math.MathUtil;
import math.Var;

/**
 * The ColDiagram class is a panel that is used to illustrate the variables in
 * the collisions topic
 * 
 */
public class ColDiagram extends JPanel {

	private static final long serialVersionUID = 1L;
	Var[] a;
	Var[] b;
	Var e;
	Frame frame;

	/**
	 * Construct a new instance of this panel
	 * 
	 * @param frame
	 *            The frame this panel belongs to
	 */
	public ColDiagram(Frame window) {
		this.frame = window;
		this.a = frame.colVarA;
		this.b = frame.colVarB;
		this.e = frame.colVarE;

		addMouseListener(new MouseListener() {
			@Override
			public void mousePressed(MouseEvent e) {// Do nothing
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// Select object based on mouse click location.
				int width = getWidth();
				int height = getHeight();
				int x = e.getX();
				int y = e.getY();
				if (Math.abs(y - height / 2) < height * 0.1
						&& (x < width * 0.25 || (x > width * 0.5 && x < width * 0.75))) {
					frame.colA = true;
				} else if (Math.abs(y - height / 2) < height * 0.1
						&& (x > width * 0.25 || (x < width * 0.5 && x > width * 0.25))) {
					frame.colA = false;
				}
				frame.updateFields();
			}
		});

		setFocusable(true);
	}

	/**
	 * Write the current panel as an image to the location specified
	 * 
	 * @param path
	 *            The path to write the file to
	 */
	public void print(String path) {
		BufferedImage img = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics print = img.getGraphics();
		printAll(print);
		try {
			ImageIO.write(img, "PNG", new File(path + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(frame.color?frame.bgColor:Color.white);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		g2d.setColor(Color.black);

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

		String label = new String(a[1].isUnknown() ? a[1].label : a[1].contents);
		g2d.drawString(label + " kg", (int) (lx - lx * 0.5), (int) (ly * 1.18));
		g2d.drawString(a[0].contents, (int) (lx - lx * 0.55), (int) (ly));

		if (!a[2].isZero()) {
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
				if (a[2].isUnknown()) {
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
		label = new String(b[1].isUnknown() ? b[1].label : b[1].contents);
		g2d.drawString(label + " kg", (int) (lx * 1.45), (int) (ly * 1.18));

		if (!b[2].isZero()) {

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
				if (b[2].isUnknown()) {
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
		 */
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.drawLine((int) (d.getWidth() / 2), 0, (int) (d.getWidth() / 2), (int) d.getHeight());
		g2d.setColor(Color.black);

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
		label = new String(a[1].isUnknown() ? a[1].label : a[1].contents);
		g2d.drawString(label + " kg", (int) (lx - lx * 0.5 + c), (int) (ly * 1.18));
		g2d.drawString(a[0].contents, (int) (lx - lx * 0.55 + c), (int) (ly));

		if (!a[3].isZero()) {
			g2d.drawLine((int) (lx - lx * 0.5 + ly * 0.05 - ly * 0.1 + c), (int) (ly * 0.95),
					(int) (lx - lx * 0.5 + ly * 0.05 + ly * 0.1 + c), (int) (ly * 0.95));
			if (MathUtil.isNumeric(a[3].contents) && Double.parseDouble(a[3].contents) < 0) {
				// remove negative sign
				label = new String(a[3].contents).substring(1, a[3].contents.length());

				// Draw < arrows
				int tx = (int) (lx * 0.5 - +c);
				int ty = (int) (ly * 0.95);
				g2d.drawLine(tx, ty, (int) (tx + ly * 0.024), (int) (ty - ly * 0.024));
				g2d.drawLine(tx, ty, (int) (tx + ly * 0.024), (int) (ty + ly * 0.024));

			} else {
				if (a[3].isUnknown()) {
					label = new String(a[3].label);

				} else {
					label = new String(a[3].contents);
				}

				// Draw > arrows
				int tx = (int) (lx * 0.5 + ly * 0.15 + c);
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
		label = new String(b[1].isUnknown() ? b[1].label : b[1].contents);
		g2d.drawString(label + " kg", (int) (lx * 1.45 + c), (int) (ly * 1.18));

		if (!b[3].isZero()) {

			g2d.drawLine((int) (lx * 1.5 - ly * 0.1 + c), (int) (ly * 0.95), (int) (c + lx * 1.5 + ly * 0.1),
					(int) (ly * 0.95));
			if (MathUtil.isNumeric(b[3].contents) && Double.parseDouble(b[3].contents) < 0) {
				// removes negative sign
				label = new String(b[3].contents).substring(1, b[3].contents.length());

				// Draw < arrows
				int tx = (int) (lx + lx * 0.5 - ly * 0.1 + c);
				int ty = (int) (ly * 0.95);
				g2d.drawLine(tx, ty, (int) (tx + ly * 0.024), (int) (ty - ly * 0.024));
				g2d.drawLine(tx, ty, (int) (tx + ly * 0.024), (int) (ty + ly * 0.024));

			} else {
				if (b[3].isUnknown()) {
					label = new String(b[3].label);

				} else {
					label = new String(b[3].contents);
				}
				// Draw > arrows
				int tx = (int) (lx * 1.5 + ly * 0.1 + c);
				int ty = (int) (ly * 0.95);
				g2d.drawLine(tx, ty, (int) (tx - ly * 0.024), (int) (ty - ly * 0.024));
				g2d.drawLine(tx, ty, (int) (tx - ly * 0.024), (int) (ty + ly * 0.024));

			}
			label = label + " m/s";
		} else {
			label = "Stationary";
		}
		g2d.drawString(label, (int) (lx * 1.45) + c, (int) (ly * 0.9));

	}
}
