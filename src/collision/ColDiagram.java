package collision;

import java.awt.Dimension;
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

	public ColDiagram(Var[] a, Var[] b) {
		this.a = a;
		this.b = b;
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
		g2d.fillOval((int) (lx + lx * 0.5), ly, (int) (ly * 0.1), (int) (ly * 0.1));

		if (a == null) {
			// Skip drawing if not initialised correctly.
			return;
		}
		// Draw name rather than contents if unknown! Using String constructor
		// to avoid changing original variable.
		String label = new String(a[1].contents.equals("?") ? a[1].label : a[1].contents);
		g2d.drawString(label + " kg", (int) (lx - lx * 0.5), (int) (ly * 1.13));

		if (!a[2].contents.equals("0")) {
			g2d.drawLine((int) (lx - lx * 0.5 - ly * 0.15), (int) (ly * 0.95), (int) (lx - lx * 0.5 + ly * 0.15),
					(int) (ly * 0.95));
			if (MathUtil.isNumeric(a[2].contents) && Double.parseDouble(a[2].contents) < 0) {
				// remove negative sign
				label = new String(a[2].contents).substring(1, a[2].contents.length());
			} else {
				if (a[2].contents.equals("")) {
					label = new String(a[2].label);
				} else {
					label = new String(a[2].contents);
				}

			}

		}

	}
}
