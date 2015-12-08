package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import math.MathUtil;
import math.Obj;

public class sidepanelSouth extends JPanel {
	
	JLabel templabel = new JLabel("PlaceHolderText");
	JButton rotate = new JButton("Rotate");
	public Obj current;
	
	public sidepanelSouth(int t){
		this.setLayout(new GridBagLayout());
		rotate.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(current != null){
					String responce = JOptionPane.showInputDialog(null, "Enter angle with suffix 'r' for radians or 'd' for degrees. \n Default is radians if nothing is suffixed. Do not use character π!", "Rotate",JOptionPane.QUESTION_MESSAGE);
					if(responce == null) return;
					char c = responce.charAt(responce.length()-1);
					if(!Character.isDigit(c)){
						responce = responce.substring(0,responce.length()-1);
					}
					if(MathUtil.isNumeric(responce)){
						double x = Double.valueOf(responce);
						boolean radians = true;
						if(c == 'D'|| c == 'd')radians = false;
						current.rotate(x,radians);
					}
					else{
						JOptionPane.showMessageDialog(null,"Not a valid input!");
						System.out.println(responce);
					}
				}
			}			
		});
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		add(templabel,c);
		c.gridy++;
		add(rotate,c);
	}
	public void setObj(Obj o){
		current = o;
	}
}
