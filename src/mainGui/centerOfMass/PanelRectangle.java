package mainGui.centerOfMass;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class PanelRectangle extends JPanel {
	int comX = -1;
	int comY = -1;
	int x;
	int y;

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		if (x == 0 || y == 0) {
			return;
		}

		int ox = getWidth() / 2;
		int oy = getHeight() / 2;
		int width = ox - 5;
		int height = oy - 5;
		if (comX == -1 && comY == -1) {
			comX = ox;
			comY = oy;
		}

		int scale = 180 / (x > y ? x : y);

		g2d.drawString(x + " m", y * scale / 2 + 8, 20);
		g2d.drawString(y + " m", 15, x * scale / 2 + 8);
		g2d.drawRect(ox - width, oy - height, x * scale + 5, y * scale + 5);
		g2d.setColor(Color.red);
		g2d.fillOval(comX * scale + 8, comY * scale + 8, 4, 4);
	}

	public void clear() {
		x = 0;
		y = 0;
	}
}
