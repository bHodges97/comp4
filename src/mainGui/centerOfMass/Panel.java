package mainGui.centerOfMass;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
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

import mainGui.Frame;
import math.MyPoint;
import math.Obj;
import math.Plane;

@SuppressWarnings("serial")
public class Panel extends JPanel {
	String topic;
	public Plane plane;
	public double scale = 0.05d;
	int ox = 0;
	int oy = 0;
	public Obj currentObj;

	public void print(String path) {
		BufferedImage img = new BufferedImage(this.getWidth(), this.getHeight(),
				BufferedImage.TYPE_INT_RGB);
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
						Frame.sideSouth.setObj(currentObj);
						return;
					}
				}
				currentObj = null;
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				//plane.update();
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

		//Draw Axis
		g2d.setColor(Color.LIGHT_GRAY);
		for (int i = oy; i < getHeight(); i += 1d / scale) {
			g2d.drawLine(0, i, getWidth(), i);
		}
		for (int u = ox; u < getWidth(); u += 1d / scale) {
			g2d.drawLine(u, 0, u, getHeight());
		}
		for (int i = oy; i > 0; i -= 1d / scale) {
			g2d.drawLine(0, i, getWidth(), i);
		}
		for (int u = ox; u > 0; u -= 1d / scale) {
			g2d.drawLine(u, 0, u, getHeight());
		}

		//Draw axis labels;
		g2d.setColor(Color.GRAY);
		int counter = 0;
		for (int i = oy; i < getHeight(); i += 1d / scale) {
			if (counter != 0) {
				if (counter % Math.ceil(40 * scale) == 0) {
					FontMetrics fontMetrics = g2d.getFontMetrics();//Used to make text right aligned
					g2d.drawString("-" + counter, ox - fontMetrics.stringWidth("-" + counter) - 2,
							i + 5);
				}
			}
			counter++;
		}
		counter = 0;
		for (int u = ox; u < getWidth(); u += 1d / scale) {
			if (counter != 0) {
				if (counter % Math.ceil(40 * scale) == 0) {
					FontMetrics fontMetrics = g2d.getFontMetrics();
					g2d.drawString("" + counter, u - fontMetrics.stringWidth("" + counter) / 2,
							oy + 12);
				}
			}
			counter++;
		}
		counter = 0;
		for (int i = oy; i > 0; i -= 1d / scale) {
			if (counter != 0) {
				if (counter % Math.ceil(40 * scale) == 0) {
					FontMetrics fontMetrics = g2d.getFontMetrics();
					g2d.drawString("" + counter, ox - fontMetrics.stringWidth("" + counter) - 2,
							i + 5);
				}
			}
			counter++;
		}
		counter = 0;
		for (int u = ox; u > 0; u -= 1d / scale) {
			if (counter != 0) {
				if (counter % Math.ceil(40 * scale) == 0) {
					FontMetrics fontMetrics = g2d.getFontMetrics();
					g2d.drawString("-" + counter, u - fontMetrics.stringWidth("-" + counter) / 2,
							oy + 12);
				}
			}
			counter++;
		}

		g2d.setColor(Color.black);
		g2d.drawLine(ox, getHeight(), ox, 0);
		g2d.drawLine(0, oy, getWidth(), oy);
		g2d.drawString("0", ox - 10, oy + 15);// Skip this as it's been done in axis already.

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
				g2d.drawPolyline(renderPoly.xpoints, renderPoly.ypoints, renderPoly.npoints);
			}
			g2d.fillOval(obj.getWorldX(scale, ox) - s / 2, obj.getWorldY(scale, oy) - s / 2, s, s);
		}

		g2d.drawString("" + scale, 10, 15);//TODO: debug pls remove

		update();
	}

	private void update() {
		if (plane.objects.isEmpty())
			return;
		// plane.findCofM(); 
		//TODO : findcofm
	}

}
