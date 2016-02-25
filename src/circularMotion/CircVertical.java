package circularMotion;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import math.MyVector;
import math.Obj;
import math.Surface;

public class CircVertical extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6017603251833338146L;
	public Obj obj;
	public Surface[] surface = new Surface[3];
	public MyVector Center;
	public CircDialog c;

	public CircVertical() {
		this.setVisible(true);
		this.setFocusable(true);

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.black);

		if (c == null)
			return;

		Dimension d = getSize();
		int ox = d.width / 2;
		int oy = d.height / 2;
		int smallR = d.height / 20;

		Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_BEVEL, 0, new float[] { 9 }, 0);
		// g2d.setStroke(dashed);
		g2d.fillOval(ox - smallR, oy - smallR, 2 * smallR, 2 * smallR);

	}
}
