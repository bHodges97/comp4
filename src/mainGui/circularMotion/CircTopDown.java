package mainGui.circularMotion;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import math.MathUtil;
import math.Var;

/**
 * The CircTopDown class is a panel that is used to illustrate the variables in
 * the circular motion topic
 * 
 */
public class CircTopDown extends JPanel {
	private static final long serialVersionUID = 1L;

	private Font font;
	private Font largeFont;
	private Stroke norm;
	public Var[] vars;
	public float thickness = 2f;

	/**
	 * Construct a new CircTopDown class
	 */
	public CircTopDown() {
		this.setVisible(true);
		this.setFocusable(true);
		setBorder(BorderFactory.createEtchedBorder(1));
	}

	/**
	 * Generates and returns this panel as an image
	 * 
	 * @return A BufferedImage of the current panel
	 */
	public BufferedImage getImg() {
		BufferedImage img = new BufferedImage(this.getWidth(), this.getHeight(),
				BufferedImage.TYPE_INT_RGB);
		Graphics print = img.getGraphics();
		printAll(print);
		return img;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.white);//Paint background
		g2d.fillRect(0, 0, getWidth(), getHeight());
		g2d.setColor(Color.black);
		g2d.setStroke(new BasicStroke(thickness));

		// Sets up fonts
		if (font == null) {
			font = g.getFont();
			largeFont = font.deriveFont((float) (font.getSize() * 1.5));
		}
		g2d.setFont(largeFont);
		String label;

		// Strokes		
		if (norm == null) {
			norm = g2d.getStroke();
		}
		Stroke dashed = new BasicStroke(thickness, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0,
				new float[] { 2 }, 0);

		// Draw title
		g2d.drawString("Overview", 0, 20);

		// Don't draw if not initialised.
		if (vars == null) {
			return;
		}

		//Set up dimensions
		Dimension d = this.getSize();
		int ox = d.width / 2;
		int oy = d.height / 2;
		int r = (int) (d.width / 2 * 0.9);

		//Draw big circles
		g2d.setStroke(dashed);
		g2d.drawOval(ox - r, oy - r, r * 2, r * 2);

		/*
		 * Basis of most scaling.
		 */
		int smallR = (int) (r * 0.04);

		// Draw init pos?
		double theta = vars[2].isUnknown() ? 0 : vars[2].getVal();
		g2d.drawOval((int) (ox + r * Math.sin(theta)) - smallR, (int) (oy + r * Math.cos(theta))
				- smallR, smallR * 2, smallR * 2);
		g2d.drawLine(ox, oy, (int) (ox + r * Math.sin(theta)), (int) (oy + r * Math.cos(theta)));

		// draw final pos
		g2d.setStroke(norm);
		int objX;
		int objY;

		if (vars[3] != null && !vars[3].isUnknown()) {
			theta = vars[3].getVal();
			g2d.drawString(vars[3].contents + " rad", ox + smallR, oy - smallR);
		} else {
			theta = Math.PI / 4;
			g2d.drawString("ϴ" + " rad", ox + smallR, oy - smallR);
		}
		objX = (int) (ox + r * Math.sin(theta) - smallR);
		objY = (int) (oy + r * Math.cos(theta) - smallR);
		g2d.drawOval(objX, objY, smallR * 2, smallR * 2);

		//Draw arc
		int temp = (int) (Math.toDegrees(!vars[2].isUnknown() ? vars[2].getVal() : 0));
		g2d.drawArc(ox - smallR, oy - smallR, smallR * 2, smallR * 2, temp + 270,
				(int) (Math.toDegrees(theta) - temp));
		g2d.drawLine(ox, oy, (int) (ox + r * Math.sin(theta) - smallR * Math.sin(theta)), (int) (oy
				+ r * Math.cos(theta) - smallR * Math.cos(theta)));

		/*
		 * draw tangents, swap cos and sin for normal
		 */
		int gx = (int) (objX + smallR + 3 * smallR * Math.sin(theta));
		int gy = (int) (objY + smallR + 3 * smallR * Math.cos(theta));
		int ga = (int) (smallR * 2.5 * Math.cos(theta));
		int gb = (int) (smallR * 2.5 * Math.sin(theta));
		MathUtil.drawArrow(g2d, gx - ga, gy + gb, gx + ga, gy - gb, smallR);

		gx = (int) (objX + smallR - smallR * Math.sin(theta));
		gy = (int) (objY + smallR - smallR * Math.cos(theta));
		MathUtil.drawArrow(g2d, gx, gy, (gx - ox) / 2 + ox, (gy - oy) / 2 + oy, smallR * 2);

		label = vars[6].isUnknown() ? vars[6].label : vars[6].contents;
		g2d.drawString(" " + label + " m/s²", (int) (ox + r * 0.5 * Math.sin(theta)), (int) (oy + r
				* 0.5 * Math.cos(theta)));
		label = vars[4].isUnknown() ? vars[4].label : vars[4].contents;
		g2d.drawString(label, (int) (ox + (r + smallR * 3) * Math.sin(theta)),
				(int) (oy + (r + smallR * 3) * Math.sin(theta)));

	}
}
