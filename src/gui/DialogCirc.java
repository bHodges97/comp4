package gui;

import java.awt.GraphicsEnvironment;
import java.awt.Point;

import javax.swing.JDialog;

public class DialogCirc extends JDialog{
	
	public DialogCirc(){
		

		setResizable(false);
		setModal(true);
		GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point center = g.getCenterPoint();   
        setLocation(center.x-500/2, center.y-500/2);
		pack();
	}
	public void Open(){
		setVisible(true);
	}
	public void Close(){
		setVisible(false);
	}
}
