package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import math.MathUtil;
import math.MyPoint;
import math.Obj;

public class DialogPointMass extends JDialog{
	Dimension prefSize = new Dimension(200,200);
	JTextField x = new JTextField(5);
	JTextField y = new JTextField(5);
	JTextField mass = new JTextField(10);
	JButton butDone = new JButton("Done");
	boolean filled = false;
	Obj returnObj = new Obj();
	
	public DialogPointMass(){
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.insets = new Insets(5,5,0,5);
		gbc.fill = gbc.BOTH;
		gbc.anchor = gbc.WEST;
		
		setFocusable(true);
		setTitle("AddPointMass");
		
		
		add(new JLabel("x:"),gbc);
		gbc.gridy++;
		add(new JLabel("y:"),gbc);
		gbc.gridy++;
		add(new JLabel("mass:"),gbc);
		
		gbc.gridx++;
		gbc.gridy=0;
		add(x,gbc);
		gbc.gridy++;
		add(y,gbc);
		gbc.gridy++;
		add(mass,gbc);
		gbc.gridy++;
		add(butDone,gbc);
		
		
		butDone.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(MathUtil.isNumeric(x.getText())){
					double xval = Double.valueOf(x.getText());
					if(MathUtil.isNumeric(y.getText())){
						double yval = Double.valueOf(y.getText());
						if(MathUtil.isNumeric(mass.getText())){
							double massval = Double.valueOf(mass.getText());
							if(massval>=0){
								//TODO: mass < 0?
								returnObj = new Obj(returnObj.PointMass, new MyPoint(xval,yval),null, massval, 1f);
								filled = true;
								close();
								return;
							}
						}
					}
				}
				JOptionPane.showMessageDialog(null,"Something is not right.");
				
			}
			
		});
		
		setResizable(false);
		setModal(true);
		GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point center = g.getCenterPoint();   
        setLocation(center.x-prefSize.width/2, center.y-prefSize.height/2);
		pack();
		
	}
	public void open(){
		filled = false;
		x.setText("");
		y.setText("");
		mass.setText("");
		this.setVisible(true);
	}
	public void close(){
		this.setVisible(false);
	}
	

}
