package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import math.MyPoint;
import math.Obj;
import math.Plane;

@SuppressWarnings("serial")
public class Panel extends JPanel {
	String topic;
	Plane plane;
	float scale = 0.05f;
	int ox = 0;
	int oy = 0;
	Obj currentObj;

	public Panel(String t) {
		setBorder(BorderFactory.createLineBorder(Color.black));
		setPreferredSize(new Dimension(800, 500));
		this.setFocusable(true);
		setTopic(t);

		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				if (currentObj != null)
					currentObj.moveto(new MyPoint(e.getX(), e.getY()), scale, ox, oy);
			}

			@Override
			public void mouseMoved(MouseEvent arg0) {
				// Do nothing

			}

		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				super.mouseClicked(e);
				for (Obj s : plane.objects) {
					if (s.PointInPolygon(e.getPoint(), ox, oy, scale)) {
						currentObj = s;
						return;
					}
				}
				currentObj = null;
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				plane.update();
			}

		});

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Dimension d = getSize();
		ox = d.width / 2;
		oy = d.height / 2;

		Graphics2D g2d = (Graphics2D) g;
		g2d.drawOval(ox - 2, oy + 2, 4, 4);
		int s = (int) (1 / scale) / 4;
		for (Obj obj : plane.objects) {
			obj.prepareForPaint(ox, oy, scale);
			Polygon renderPoly = null;
			if (obj.getType() != obj.PointMass) {
				renderPoly = obj.getRenderPoly();
			}
			if (obj.getType() == obj.Polygon) {
				g2d.drawPolygon(renderPoly);
			} else if (obj.getType() == obj.Polyline) {
				g2d.drawPolyline(renderPoly.xpoints, renderPoly.ypoints, renderPoly.npoints);
			}
			g2d.fillOval(obj.getWorldX(scale, ox) - s / 2, obj.getWorldY(scale, oy) - s / 2, s, s);
		}

		update();
	}

	private void update() {
		if (plane.objects.isEmpty())
			return;
		if (topic.equals("Default")) {
		} else if (topic.equals("Center")) {
			// plane.findCofM();
		}
	}

	public void setTopic(String t) {
		this.topic = t;
		plane = new Plane(t);
	}
}
