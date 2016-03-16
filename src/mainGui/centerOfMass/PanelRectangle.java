package mainGui.centerOfMass;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class PanelRectangle extends JPanel {
	int comX = -1;
	int comY = -1;

	public void update(double x, double y) {
		comX = (int) x;
		comY = (int) y;
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		int ox = getWidth() / 2;
		int oy = getHeight() / 2;
		int width = ox - 5;
		int height = oy - 5;
		if (comX == -1 && comY == -1) {
			comX = ox;
			comY = oy;
		}

		g2d.drawRect(ox - width, oy - height, width * 2, height * 2);
		g2d.setColor(Color.red);
		g2d.fillOval(comX - 2, comY - 2, 4, 4);
	}
}
