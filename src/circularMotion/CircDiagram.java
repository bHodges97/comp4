package circularMotion;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import math.*;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class CircDiagram extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6017603251833338146L;
	public Obj obj;
	public Surface[] surface= new Surface[3];
	public MyVector Center;
	public CircDialog c;


	public CircDiagram(){
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

		Dimension d = getSize();
		int ox = d.width/2;
		int oy = d.height/2;

		Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
		g2d.setStroke(dashed);
		g2d.drawOval(0,0,50,50);


	}
}