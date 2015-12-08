package gui;

import java.awt.*;
import javax.swing.*;

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
	Panel canvas = new Panel("Default");
	JPanel sidepanel = new JPanel();
	sidepanelNorth sideNorth;
	sidepanelSouth sideSouth = new sidepanelSouth(0);
	
	public Frame(){
		//this.setLayout();
		setExtendedState(MAXIMIZED_BOTH);		
		setTopic(popup.topic);
		this.add(canvas,BorderLayout.CENTER);		
		this.add(sidepanel,BorderLayout.EAST);
		sideNorth = new sidepanelNorth(canvas.plane);
		
		sidepanel.setPreferredSize(new Dimension(300,this.getHeight()));
		sidepanel.setBorder(BorderFactory.createLineBorder(Color.black));
		sidepanel.add(sideNorth.p,BorderLayout.NORTH);
		
		sidepanel.add(sideSouth,BorderLayout.SOUTH);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		
		Thread update  = new Thread(){
			public void run(){
				while(true){
					repaint();
					revalidate();
					sideSouth.setObj(canvas.currentObj);
					canvas.repaint();
					
					try {
						Thread.sleep(1/1000*60);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		update.start();
	}
	private void setTopic(String t){
		setTitle(t);
		canvas.setTopic(t);
	}
	public static void main(String[] Args){
		window = new Frame();
		
	}
}
