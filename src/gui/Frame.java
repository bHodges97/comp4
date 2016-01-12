package gui;

import java.awt.*;

import javax.swing.*;

import circularMotion.*;

//TODO: Implement shapes;
//TODO: Finish add object, : add toggled fields, add check box manual input
//TODO: Shape Offset by CofM
//TODO: More topics!
//TODO:

/**Comp4 CourseWork
 * Creates main window for application
 * 
 * @author j00791
 * @version 0;
 */
public class Frame extends JFrame{
	static Frame window;
	Dialog popup = new Dialog();
	String topic = "Default";
	//CircularMotion
	CircCanvas circCanvas;
	CircDiagram circDiagram;
	
	//CenterOfMass
	Panel canvas = new Panel("Default");
	JPanel sidepanel = new JPanel();
	sidepanelNorth sideNorth;
	sidepanelSouth sideSouth;
	
	public Frame(){
		//this.setLayout();
		setExtendedState(MAXIMIZED_BOTH);		
		setTopic(popup.topic);
		
		if(popup.topic.equals("circles")){
			initCircularMotion();
		}
		if(popup.topic.equals("Center")){
			initCenterOfMass();
		}
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);

	}
	private void setTopic(String t){
		setTitle(t);
		canvas.setTopic(t);
	}
	public static void main(String[] Args){
		window = new Frame();
	}
	private void initCircularMotion(){
		circCanvas = new CircCanvas();
		this.add(circCanvas,BorderLayout.CENTER);
		circDiagram = new CircDiagram();
		this.add(circDiagram,BorderLayout.EAST);		
		
		
		
		
		Thread update  = new Thread(){
			public void run(){
				
				
				
				while(true){
					repaint();
					revalidate();
					circCanvas.repaint();
					circDiagram.repaint();
					try {
						Thread.sleep((long) 0.03);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		update.start();
		
	}
	private void initCenterOfMass(){
		this.add(canvas,BorderLayout.CENTER);		
			this.add(sidepanel,BorderLayout.EAST);
			sideNorth = new sidepanelNorth(canvas.plane);
			sideSouth = new sidepanelSouth(0,canvas.plane);		
			sidepanel.setPreferredSize(new Dimension(300,this.getHeight()));
			sidepanel.setBorder(BorderFactory.createLineBorder(Color.black));
			
			sidepanel.setLayout(new GridBagLayout());
			GridBagConstraints c =  new GridBagConstraints();
			c.weighty=1;
			c.weightx=1;
			c.fill = c.BOTH;
			c.gridy=0;
			sidepanel.add(sideNorth.p,c);
			c.gridy++;
			sidepanel.add(sideSouth,c);
			Thread update  = new Thread(){
				public void run(){
					while(true){
						repaint();
						revalidate();
						sideSouth.setObj(canvas.currentObj);
						canvas.repaint();
							
						try {
							Thread.sleep((long) 0.03);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			};
			update.start();
	}
}
