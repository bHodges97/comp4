package circularMotion;

import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import math.MathUtil;
import math.Obj;

public class CircDialog extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6055392994194343968L;
	JButton Done = new JButton("Done");
	JLabel Explanation = new JLabel("<html>Fill in all known fields as numbers.<br>Leave unknowns blank.");
	int n = 18;
	JTextField textAngularV = new JTextField(n);
	JTextField textMass = new JTextField(n);
	JTextField textStart = new JTextField(n);
	JTextField textEnd = new JTextField(n);
	JTextField textVelocity = new JTextField(n);
	JTextField textRadius = new JTextField(n);
	JTextField textAccCent = new JTextField(n);
	JTextField textTime = new JTextField(n);

	Double mass,v,angularv,theta,startTheta,r;


	public CircDialog(){
		Open();
		placeField();
		setTitle("Input details");
		setResizable(false);
		setModal(true);
		GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point center = g.getCenterPoint();
		setLocation(center.x-500/2, center.y-500/2);
		pack();
		Done.addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) {
						for (Component c : getContentPane().getComponents()) {
							if (c instanceof JTextField) {
								//Tests if each field is valid(blank or numeric)
								if(!MathUtil.isNumeric(((JTextField)c).getText()) && !((JTextField)c).getText().isEmpty()) {
									JOptionPane.showMessageDialog(getThis(),
											"One of the variables wasn't numeric or blank ("+((JTextField)c).getText()+")",
											"Warning", JOptionPane.ERROR_MESSAGE);
									return;
								}
							}
						}

						for (Component c : getContentPane().getComponents()) {
							if (c instanceof JTextField && ((JTextField)c).getText().isEmpty()) {
								if(c == textAccCent){
									if(!textRadius.getText().isEmpty()){
										if(!textVelocity.getText().isEmpty()){
											textAccCent.setText(""+
													// v^2/r
													Math.pow(Double.parseDouble(textVelocity.getText()),2)/
													Double.parseDouble(textRadius.getText())
													);

										}else if(!textAngularV.getText().isEmpty()){
											textAccCent.setText(""+
													// omega^2*r
													Math.pow(Double.parseDouble(textAngularV.getText()),2)*
													Double.parseDouble(textRadius.getText())
													);
										}
									}
								}
								if(c == textAngularV){
									if(!textRadius.getText().isEmpty() && !textVelocity.getText().isEmpty()){
										textAngularV.setText(""+
												//v/r
												Double.parseDouble(textVelocity.getText())/
												Double.parseDouble(textRadius.getText())
												);
									}
								}
								if(c == textVelocity){
									if(!textRadius.getText().isEmpty() && !textAngularV.getText().isEmpty()){
										textAngularV.setText(""+
												//v/r
												Double.parseDouble(textAngularV.getText())*
												Double.parseDouble(textRadius.getText())
												);
									}
								}

							}
						}





					}
				}
				);
	}
	public void Open(){
		for (Component c : this.getContentPane().getComponents()) {
			if (c instanceof JTextField) {
				((JTextField)c).setText("");

			}
		}
		setVisible(true);
	}
	public void Close(){
		setVisible(false);


	}
	private void placeField(){
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.anchor = c.WEST;
		c.gridy = 0;
		this.add(Explanation,c);
		c.gridy++;
		this.add(new JLabel("Start Angle"),c);
		c.gridy++;
		this.add(textStart,c);
		c.gridy++;
		this.add(new JLabel("Time"),c);
		c.gridy++;
		this.add(textTime,c);
		c.gridy++;
		this.add(new JLabel("End Angle"),c);
		c.gridy++;
		this.add(textEnd,c);
		c.gridy++;
		this.add(new JLabel("Angular Velocity"),c);
		c.gridy++;
		this.add(textAngularV,c);
		c.gridy++;
		this.add(new JLabel("Mass"),c);
		c.gridy++;
		this.add(textMass,c);
		c.gridy++;
		this.add(new JLabel("Radius"),c);
		c.gridy++;
		this.add(textRadius,c);
		c.gridy++;
		this.add(new JLabel("Centripetal Acceleration"),c);
		c.gridy++;
		this.add(textAccCent,c);
		c.gridy++;
		c.anchor = c.EAST;
		Done.setAlignmentX(RIGHT_ALIGNMENT);
		this.add(Done,c);
	}
	/**
	 * Alternative to "this" keyword.
	 * returns this object
	 */
	public CircDialog getThis(){
		return this;
	}
}