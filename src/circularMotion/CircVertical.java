package circularMotion;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.List;

import javax.swing.JPanel;

import math.MathUtil;
import math.Var;

public class CircVertical extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6017603251833338146L;
	public List<String> force;
	public List<String> angle;
	public Var[] text;

	public CircVertical() {
		this.setVisible(true);
		this.setFocusable(true);

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.black);
		String lbl;

		if (text == null) {
			return;
		}

		Dimension d = getSize();

		int ox = d.width / 2;
		int oy = d.height / 2;
		int x;
		int y;
		int smallR = d.height / 80;

		// Position X coords.
		if (!text[0].contents.equals("?") && text[0].getVal() > 0) {
			ox = d.width / 4;
			x = 3 * ox;
		} else if (text[0].contents.equals("?") || text[0].getVal() < 0) {
			x = d.width / 4;
			ox = 3 * x;
		} else {
			return;
		}
		// Position Y
		if (!text[1].contents.equals("?") && text[1].getVal() > 0) {
			y = d.height / 4;
			oy = 3 * y;
		} else if (text[1].getVal() < 0) {
			oy = d.height / 4;
			y = 3 * oy;
		} else {
			oy = d.height / 2;
			y = d.height / 2;
		}

		// Loop through each force
		if (force != null && angle != null) {

			for (int i = 0; i < force.size(); i++) {
				if (force.get(i).equals("0")) {
					continue;
				}
				double t = Double.valueOf(angle.get(i));
				System.out.println(t);
				int dx = (int) (7 * smallR * Math.cos(t));
				int dy = -(int) (7 * smallR * Math.sin(t));
				System.out.println(dx);
				MathUtil.drawArrow(g2d, x, y, x + dx, y + dy, smallR);
				lbl = force.get(i);
				g2d.drawString(lbl, 0, 0);
			}
		}

		Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_BEVEL, 0, new float[] { 9 }, 0);
		Stroke stroke = g2d.getStroke();
		g2d.fillOval(x - smallR, y - smallR, 2 * smallR, 2 * smallR);
		g2d.fillOval(ox - smallR / 6, y - smallR / 6, smallR / 3, smallR / 3);
		g2d.drawString("Center of Rotation", ox - 3 * smallR, y - smallR);
		g2d.setStroke(dashed);
		g2d.drawLine(ox, oy, ox, y);
		g2d.drawLine(ox, oy, x, oy);
		g2d.setStroke(stroke);

		lbl = text[0].contents.equals("?") ? text[0].label : text[0].contents;
		g2d.drawString(lbl + " m", (x + ox) / 2, oy);
		if (y != oy) {
			lbl = text[1].contents.equals("?") ? text[1].label
					: text[1].contents;
			g2d.drawString(lbl + " m", ox, (y + oy) / 2);
		}
	}
}
