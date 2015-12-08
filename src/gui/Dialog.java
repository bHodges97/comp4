package gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JDialog;


@SuppressWarnings("serial")
public class Dialog extends JDialog {
	private Dimension prefSize = new Dimension(500,500);
	public String topic = "Default";
	
	public Dialog(){
		setPreferredSize(prefSize);
		setTitle("Topic Selection");
		setModal(true);	
		center();
		this.setResizable(false);
		Container frame = this.getContentPane();
		frame.setLayout(new FlowLayout(FlowLayout.LEFT));
		JButton topicCircles = new JButton("circles");
		JButton topicRestitute = new JButton("Resistution");
		JButton topicWork = new JButton("Work Energy and Power");
		JButton topicCenter = new JButton("topicCenterOfMass");
		frame.add(topicCircles);
		frame.add(topicRestitute);
		frame.add(topicWork);
		frame.add(topicCenter);
		topicCircles.setActionCommand("Circles");
		topicRestitute.setActionCommand("Restitute");
		topicWork.setActionCommand("Work");
		topicCenter.setActionCommand("Center");
		ActionListener tpcListener = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				topic = e.getActionCommand();
				if(e.getActionCommand().equals("Circles")){
					
				}
				if(e.getActionCommand().equals("Restitute")){
									
				}
				if(e.getActionCommand().equals("Work")){
					
				}
				if(e.getActionCommand().equals("Center")){
					
				}
				setVisible(false);
			}
		};
		topicCircles.addActionListener(tpcListener);
		topicRestitute.addActionListener(tpcListener);
		topicWork.addActionListener(tpcListener);
		topicCenter.addActionListener(tpcListener);
		this.pack();
		setAlwaysOnTop(true);
		setVisible(true);		
		
	}
	   private void center() {
           GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
           Point center = g.getCenterPoint();   
           setLocation(center.x-prefSize.width/2, center.y-prefSize.height/2);
   }
}
