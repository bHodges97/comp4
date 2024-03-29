package mainGui.circularMotion;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import mainGui.Frame;
import math.MathUtil;
import math.Var;

/**
 * The CircVertical class is a panel that is used to illustrate the variables in
 * the circular motion topic
 * 
 */
public class CircVertical extends JPanel {
	private static final long serialVersionUID = 1L;

	public List<String> force;
	public List<String> angle;
	public Var[] text;
	Font font;
	Font largeFont;
	Frame frame;

	/**
	 * Construct a new instance of this class
	 * 
	 * @param frame
	 *            The frame this belongs to
	 */
	public CircVertical(Frame frame) {
		this.frame = frame;
		this.setVisible(true);
		this.setFocusable(true);
		setBorder(BorderFactory.createEtchedBorder());
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

		g2d.setColor(frame.color ? frame.bgColor : Color.white);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		g2d.setColor(Color.black);
		String lbl;

		// Exit painting prematurely
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
		if (!text[0].isUnknown() && text[0].getVal() > 0) {
			ox = d.width / 4;
			x = 3 * ox;
		} else if (text[0].isUnknown() || text[0].getVal() < 0) {
			x = d.width / 4;
			ox = 3 * x;
		} else {
			return;
		}
		// Position Y
		if (!text[1].isUnknown() && text[1].getVal() > 0) {
			y = d.height / 4;
			oy = 3 * y;
		} else if (!text[1].isUnknown() && text[1].getVal() < 0) {
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
				double t = Double.parseDouble(angle.get(i));
				int dx = (int) (7 * smallR * Math.cos(t));
				int dy = -(int) (7 * smallR * Math.sin(t));
				MathUtil.drawArrow(g2d, x, y, x + dx, y + dy, smallR);
				lbl = force.get(i);
				g2d.drawString(lbl, x + dx / 2, y + dy / 2);
			}
		}
		if (font == null) {
			font = g.getFont();
			largeFont = font.deriveFont((float) (font.getSize() * 1.2));
			largeFont = largeFont.deriveFont(Font.BOLD);
		}
		Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0,
				new float[] { 9 }, 0);
		Stroke stroke = g2d.getStroke();

		// Draw origin
		g2d.setFont(largeFont);
		g2d.fillOval(x - smallR / 2, y - smallR / 2, smallR, smallR);
		g2d.drawString("O", ox, oy);

		// draw centre of rotation
		g2d.setFont(font);
		g2d.fillOval(ox - smallR / 6, y - smallR / 6, smallR / 3, smallR / 3);
		g2d.drawString("Centre of Rotation", ox - 3 * smallR, y - smallR);

		// Draw x & y lines
		g2d.setFont(font);
		g2d.setStroke(dashed);
		g2d.drawLine(ox, oy, ox, y);
		g2d.drawLine(ox, oy, x, oy);
		g2d.setStroke(stroke);
		lbl = text[0].isUnknown() ? text[0].label : text[0].contents;
		g2d.drawString(lbl + " m", (x + ox) / 2, oy);
		if (y != oy) {
			lbl = text[1].isUnknown() ? text[1].label : text[1].contents;
			g2d.drawString(lbl + " m", ox, (y + oy) / 2);
		}
	}
}
