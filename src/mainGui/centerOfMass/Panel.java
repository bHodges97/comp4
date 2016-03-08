package mainGui.centerOfMass;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

import math.MyPoint;
import math.Obj;
import math.Plane;

@SuppressWarnings("serial")
public class Panel extends JPanel {
	String topic;
	public Plane plane;
	float scale = 0.05f;
	int ox = 0;
	int oy = 0;
	public Obj currentObj;

	public void print(String path) {
		BufferedImage img = new BufferedImage(this.getWidth(),
				this.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics print = img.getGraphics();
		printAll(print);
		try {
			ImageIO.write(img, "PNG", new File(path + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Panel() {
		setBorder(BorderFactory.createEtchedBorder(1));
		setPreferredSize(new Dimension(800, 500));
		this.setFocusable(true);
		plane = new Plane();

		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				if (currentObj != null)
					currentObj.moveto(new MyPoint(e.getX(), e.getY()), scale,
							ox, oy);
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
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		g2d.setColor(Color.black);
		g2d.drawOval(ox - 2, oy + 2, 4, 4);
		if (plane == null) {
			return;
		}
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
				g2d.drawPolyline(renderPoly.xpoints, renderPoly.ypoints,
						renderPoly.npoints);
			}
			g2d.fillOval(obj.getWorldX(scale, ox) - s / 2,
					obj.getWorldY(scale, oy) - s / 2, s, s);
		}

		update();
	}

	private void update() {
		if (plane.objects.isEmpty())
			return;
		// plane.findCofM();
	}

}
