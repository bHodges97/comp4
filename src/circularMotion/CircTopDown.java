package circularMotion;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class CircTopDown extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6017603251833338146L;
	public CircDialog c;
	private Font font;
	private Font largeFont;
	private Stroke norm;

	public CircTopDown() {
		setBorder(BorderFactory.createLineBorder(Color.black));
		this.setVisible(true);
		this.setFocusable(true);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.black);

		/*
		 * Sets up fonts; Might not be used
		 */
		if (font == null) {
			font = g.getFont();
			largeFont = font.deriveFont((float) (font.getSize() * 1.5));
		}
		g2d.setFont(largeFont);

		/*
		 * Strokes
		 */
		if (norm == null) {
			norm = g2d.getStroke();
		}
		Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 2 }, 0);

		/*
		 * Draw title
		 */
		g2d.drawString("Overview", 0, 20);

		/*
		 * Don't draw if not initialised.
		 */
		if (c == null) {
			return;
		}

		/*
		 * Dimension
		 */
		Dimension d = this.getSize();
		int ox = d.width / 2;
		int oy = d.height / 2;
		int r = (int) (d.height / 2 * 0.7);

		g2d.setStroke(dashed);
		g2d.drawOval(ox - r, oy - r, r * 2, r * 2);

		/*
		 * Basis of most scaling.
		 */
		int smallR = (int) (r * 0.04);

		// Draw init pos?
		if (c.vars[2] != null && !c.vars[2].contents.equals("Unkown")) {
			g2d.drawOval((int) (ox + r * Math.sin(c.vars[2].getVal())) - smallR,
					(int) (oy + r * Math.cos(c.vars[2].getVal())) - smallR, smallR * 2, smallR * 2);
			g2d.drawLine(ox, oy, (int) (ox + r * Math.sin(c.vars[2].getVal())),
					(int) (oy + r * Math.cos(c.vars[2].getVal())));
		} else {
			// Should never occur.
			g2d.drawOval((int) (ox + r) - smallR, (int) (oy + r) - smallR, smallR * 2, smallR * 2);
			g2d.drawLine(ox, oy, ox, oy + r);
		}

		// draw final pos?
		g2d.setStroke(norm);
		int objX;
		int objY;
		double theta;
		if (c.vars[3] != null && !c.vars[3].contents.equals("Unkown")) {
			theta = c.vars[3].getVal();
			g2d.drawString(c.vars[3].contents + " rad", ox + smallR, oy - smallR);
		} else {
			theta = Math.PI / 4;
			g2d.drawString("ϴ" + " rad", ox + smallR, oy - smallR);
		}
		objX = (int) (ox + r * Math.sin(theta));
		objY = (int) (oy + r * Math.cos(theta));
		g2d.drawOval(objX - smallR, objY - smallR, smallR * 2, smallR * 2);
		g2d.drawArc(ox - smallR, oy - smallR, smallR * 2, smallR * 2, 270, (int) (Math.toDegrees(theta)));
		g2d.drawLine(ox, oy, (int) (ox + r * Math.sin(theta)), (int) (oy + r * Math.cos(theta)));

		/*
		 * draw tangents, swap cos and sin for normal
		 */
		int gx = (int) (objX + 2 * smallR * Math.sin(theta));
		int gy = (int) (objY + 2 * smallR * Math.cos(theta));
		int ga = (int) (smallR * 2.5 * Math.cos(theta));
		int gb = (int) (smallR * 2.5 * Math.sin(theta));
		g2d.drawLine(gx - ga, gy + gb, gx + ga, gy - gb);
		g2d.drawLine(gx + ga, gy - gb, gx + ga - (int) (15 * Math.cos(Math.PI / 6 - theta)),
				(int) (gy - gb - 15 * Math.sin(Math.PI / 6 - theta)));
		g2d.drawLine(gx + ga, gy - gb, gx + ga - (int) (15 * Math.cos(-Math.PI / 6 - theta)),
				(int) (gy - gb - 15 * Math.sin(-Math.PI / 6 - theta)));

		// draw labels?

		g2d.fillOval(ox - 1, oy - 1, 3, 3);
		g2d.drawString("O", ox, oy);
		g2d.drawString("Test", objX + smallR, objY - smallR);

	}
}
