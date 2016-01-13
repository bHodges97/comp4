package circularMotion;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class CircCanvas extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6017603251833338146L;

	public CircCanvas(){
		setPreferredSize(new Dimension(200,200));
		setBorder(BorderFactory.createLineBorder(Color.black));
		this.setVisible(true);
		this.setFocusable(true);
	}
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(Color.black);
		
		Dimension d = getSize();
		int ox = d.width/2;
		int oy = d.height/2;
		
		
	}
}
