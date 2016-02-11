package circularMotion;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class CircCanvas extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6017603251833338146L;
	public CircDialog c;

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

		if(c==null)return;

		Dimension d = this.getSize();
		int ox = d.width/2;
		int oy = d.height/2;
		int r = (int) (d.height/2*0.9);
		Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{2}, 0);
		g2d.setStroke(dashed);
		g2d.drawOval(ox-r,oy-r,r*2,r*2);


		int smallR = (int)(r*0.1);
		//Draw init pos?
		g2d.drawOval(ox-smallR/2,oy+r-smallR/2,smallR,smallR);
		//draw final pos?
		if(c.vars[6] != null && !c.vars[6].contents.equals("Unkown")){
			g2d.drawOval((int)(ox+r*Math.sin(c.vars[6].getVal())),(int)(oy+r*Math.cos(c.vars[6].getVal())),smallR,smallR);
		}
		//draw arrows?
		//draw labels?

	}
}
