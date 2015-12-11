package gui;

import java.awt.GraphicsEnvironment;
import java.awt.GridBagLayout;
import java.awt.Point;

import javax.swing.JDialog;
import javax.swing.JTextField;

public class DialogCirc extends JDialog{
	JTextField textAngle = new JTextField(9);
	JTextField textPosX = new JTextField(9);
	JTextField textCY = new JTextField(9);
	JTextField textCX = new JTextField(9);
	JTextField textPosY = new JTextField(9);
	JTextField textRadius = new JTextField(9);
	
	public DialogCirc(){
		
		placeField();

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
	private void placeField(){
		setLayout(new GridBagLayout());
	}
}
