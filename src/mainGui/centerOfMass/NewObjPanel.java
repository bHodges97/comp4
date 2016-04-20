package mainGui.centerOfMass;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import math.MyPoint;
import math.MyVector;
import math.Shape;

/**
 * The NewObjPanel class is the panel that is used to illustrate a new obj for
 * DialogNewObj
 */
public class NewObjPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	Shape s;
	double scale;
	int[] xpoints;
	int[] ypoints;
	int x = -1;
	int y = -1;

	/**
	 * Construct a new instance of this class
	 */
	public NewObjPanel() {
		setPreferredSize(new Dimension(200, 200));
		setBorder(BorderFactory.createEtchedBorder(1));
		this.setVisible(true);
		this.setFocusable(true);
		Thread repaint = new Thread() {
			public void run() {
				while (true) {
					repaint();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		repaint.start();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.black);
		if (xpoints != null)
			g2d.drawPolyline(xpoints, ypoints, xpoints.length);
		g2d.setColor(Color.red);
		g2d.fillOval(x - 1, y - 1, 3, 3);
	}

	/**
	 * Set the shape to illustrate
	 * 
	 * @param shape
	 *            the shape to illustrate
	 */
	public void setShape(Shape shape) {
		s = shape;
		MyVector r = s.getRange();
		scale = (float) (r.x > r.y ? r.x / 200 : r.y / 200) * 1.05f;

		ypoints = new int[s.getNPoints() + 1];
		xpoints = new int[s.getNPoints() + 1];
		for (int i = 0; i < s.getNPoints(); i++) {
			xpoints[i] = (int) ((s.getPoint(i).x - s.getMinX()) / scale) + 10;
			ypoints[i] = (int) (this.getHeight() - (s.getPoint(i).y - s.getMinY()) / scale) - 10;
		}
		xpoints[xpoints.length - 1] = xpoints[0];
		ypoints[ypoints.length - 1] = ypoints[0];

	}

	/**
	 * Set the new COM
	 * 
	 * @param com
	 *            the new COM
	 */
	public void setCOM(MyPoint com) {
		x = (int) ((com.x - s.getMinX()) / scale) + 10;
		y = (int) (this.getHeight() - (com.y - s.getMinY()) / scale) - 10;
	}
}
