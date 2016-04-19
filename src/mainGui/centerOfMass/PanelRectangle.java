package mainGui.centerOfMass;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

/**
 * The PanelRectangle class is a panel that draws a rectangle of given
 * constraints.
 * 
 */
public class PanelRectangle extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int x, y, comX = -1, comY = -1;

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		int ox = getWidth() / 2;
		int oy = getHeight() / 2;

		//Stop painting 
		if (x <= 0 || y <= 0) {
			g2d.drawString("Input dimensions in the fields below.", 5, oy);
			return;
		}

		if (comX == -1 && comY == -1) {
			comX = ox;
			comY = oy;
		}

		//Divide avaliable pixel into the x and y ratio
		int xPixels, yPixels;
		if (x > y) {
			xPixels = 185;
			yPixels = (int) (180 * ((float) y / (float) x) + 5);
		} else {
			xPixels = (int) (180 * ((float) x / (float) y) + 5);
			yPixels = 185;
		}

		g2d.drawString(x + " m", (xPixels - 5) / 2 + 10, 20);
		g2d.drawString(y + " m", 20, (yPixels - 5) / 2 + 10);
		g2d.drawRect(5, 5, xPixels, yPixels);
		g2d.setColor(Color.red);

		g2d.fillOval((int) (xPixels * ((float) comX / x)) + 2,
				(int) (yPixels * ((float) comY / y)) + 2, 4, 4);
	}

	/**
	 * Resets the panel
	 */
	public void reset() {
		x = 0;
		y = 0;
		comX = -1;
		comY = -1;
	}

	/**
	 * @param x
	 *            The x value to set
	 */
	public void setX(double x) {
		this.x = (int) x;
	}

	/**
	 * 
	 * @param y
	 *            The y value to set
	 */
	public void setY(double y) {
		this.y = (int) y;
	}

	public void setCOMX(double x) {
		this.comX = (int) x;
	}

	public void setCOMY(double y) {
		this.comY = (int) y;
	}
}
