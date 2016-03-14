package mainGui;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import math.Definition;
import math.Solver;
import math.Var;

public class myMenuBar extends JMenuBar {
	final Frame frame;

	/**
	 * 
	 */
	private static final long serialVersionUID = -1764943131583829011L;

	public myMenuBar(final Frame main) {
		frame = main;

		//File
		JMenu menuFile = new JMenu("File");
		menuFile.setMnemonic(KeyEvent.VK_F);
		JMenuItem saveImage = new JMenuItem("Save image", KeyEvent.VK_I);
		JMenuItem saveFile = new JMenuItem("Save file", KeyEvent.VK_F);
		menuFile.add(saveImage);
		menuFile.add(saveFile);

		final JFileChooser fileChooser = new JFileChooser();
		saveImage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (fileChooser.showSaveDialog(main) == fileChooser.APPROVE_OPTION) {
					save(fileChooser.getSelectedFile().getPath());
				}
			}
		});

		//Tools			
		JMenu menuTools = new JMenu("Tool");
		menuTools.setMnemonic(KeyEvent.VK_T);
		final JMenuItem mConverter = new JMenuItem("Degrees & radians converter", KeyEvent.VK_D);
		final JMenuItem mTrig = new JMenuItem("Trig calculator", KeyEvent.VK_T);
		menuTools.add(mConverter);
		menuTools.add(mTrig);
		final AngleConverter dialogConverter = new AngleConverter();

		//Solve
		final JMenuItem menuSolve = new JMenuItem("Solve");

		//Zoom
		JMenu menuZoom = new JMenu("Zoom");
		menuTools.setMnemonic(KeyEvent.VK_Z);
		final JMenuItem zoomIn = new JMenuItem("Zoom in");
		final JMenuItem zoomOut = new JMenuItem("Zoom out");
		final JMenuItem zoomReset = new JMenuItem("Rest zoom");
		menuZoom.add(zoomIn);
		menuZoom.add(zoomOut);
		menuZoom.add(zoomReset);

		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == menuSolve) {
					solve();
				}
				if (e.getSource() == mConverter) {
					dialogConverter.Open();
				}
				if (e.getSource() == zoomIn) {
					frame.zoom(0);
				}
				if (e.getSource() == zoomOut) {
					frame.zoom(1);
				}
				if (e.getSource() == zoomReset) {
					frame.zoom(2);
				}
			}
		};

		//AddListeners;
		menuSolve.addActionListener(listener);
		mConverter.addActionListener(listener);
		zoomIn.addActionListener(listener);
		zoomOut.addActionListener(listener);

		//Add all menu items;
		add(menuFile);
		add(menuTools);
		add(menuSolve);
		if (frame.topic.equals("Center")) {
			add(menuZoom);
		}
	}

	private void save(String pathName) {
		if (pathName.endsWith(".png")) {
			pathName.replace(".png", "");
		}
		if (frame.topic.equals("Circles")) {
			BufferedImage imgA = frame.circTopDown.getImg();
			BufferedImage imgB = frame.circVertical.getImg();
			int width = imgA.getWidth() + imgB.getWidth();
			int height = imgA.getHeight();
			BufferedImage combinedImage = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = combinedImage.createGraphics();
			g2d.fillRect(0, 0, width, height);
			g2d.drawImage(imgA, null, 0, 0);
			g2d.drawImage(imgB, null, imgA.getWidth() + 1, 0);
			g2d.dispose();
			try {
				ImageIO.write(combinedImage, "PNG", new File(pathName + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (frame.topic.equals("Center")) {
			frame.canvas.print(pathName);
		}
		if (frame.topic.equals("Collisions")) {
			frame.colDiagram.print(pathName);
		}
		if (frame.topic.equals("Projectiles")) {
			frame.projDiagram.print(pathName);
		}

	}

	/**
	 * 
	 */
	public void solve() {
		String topic = frame.topic;
		Var[] circVars = frame.circVars;
		Var[] circVarB = frame.circVars;
		Var[] projVars = frame.projVars;
		Var[] a = frame.a;
		Var[] b = frame.b;
		Var e = frame.e;

		long startTime = System.currentTimeMillis();
		if (topic.equals("Circles")) {
			int confirm = JOptionPane.showConfirmDialog(frame,
					"Attempts to find unkown variables if possible.", "Solver",
					JOptionPane.OK_CANCEL_OPTION);
			if (confirm == JOptionPane.CANCEL_OPTION || confirm == JOptionPane.CLOSED_OPTION) {
				return;
			}

			if (!circVarB[0].isKnown() && circVars[5].isKnown()) {
				circVars[5].setContents(circVarB[0].contents, false);
			}
			System.out.println("Started");
			Definition[] defs = new Definition[9];
			defs[0] = new Definition("v=r*w");
			defs[1] = new Definition("w=v/r");
			defs[2] = new Definition("f=m*a");
			defs[3] = new Definition("a=v^2/r");
			defs[4] = new Definition("a=w^2*r");
			defs[5] = new Definition("x=t*w+u");
			defs[6] = new Definition("t=x-u/t");
			defs[7] = new Definition("r=v/w");
			defs[8] = new Definition("r=v^2/a");
			Solver s = new Solver(defs, circVars);

		} else if (topic.equals("Projectiles")) {
			int confirm = JOptionPane.showConfirmDialog(frame,
					"Attempts to find unkown variables if possible.", "Solver",
					JOptionPane.OK_CANCEL_OPTION);
			if (confirm == JOptionPane.CANCEL_OPTION || confirm == JOptionPane.CLOSED_OPTION) {
				return;
			}
			if (!projVars[9].isKnown() && !projVars[8].isKnown()) {
				projVars[0].setContents(
						"" + Math.atan(projVars[9].getVal() / projVars[8].getVal()), false);
			}
			Definition[] defs = new Definition[11];
			defs[0] = new Definition("d=u*cos(a)");
			defs[1] = new Definition("e=u*sin(a)");
			defs[2] = new Definition("d=b");
			defs[3] = new Definition("b=d");
			defs[4] = new Definition("x=b*t");
			defs[5] = new Definition("e=9.8*t+c");
			defs[6] = new Definition("y=c+e/2*t+h");
			defs[7] = new Definition("y=t^2*0.5*-9.8+(e*t)");
			defs[8] = new Definition("v=(b^2)+(c^2)^1/2");
			defs[9] = new Definition("u=(d^2)+(e^2)^1/2");
			defs[10] = new Definition("v=(u^2)+0.5*a*(y-h)^1/2");
			Solver s = new Solver(defs, projVars);

		} else if (topic.equals("Collisions")) {
			//Not using solver as simultaneous equations are too complex to be represented.
			while (true) {
				double m1 = a[1].getVal();
				double m2 = b[1].getVal();
				double v1 = a[2].getVal();
				double v2 = b[2].getVal();
				double u1 = a[3].getVal();
				double u2 = b[3].getVal();
				double c = e.getVal();//c since e is used.

				// mass 1
				if (!a[1].isKnown()
						&& (b[1].isKnown() && b[2].isKnown() && b[3].isKnown() && a[3].isKnown() && a[2]
								.isKnown())) {
					a[1].setContents("" + (m2 * (v2 - u2) / (u1 - v1)), false);
				}
				// mass 2
				if (!b[1].isKnown()
						&& (a[1].isKnown() && a[2].isKnown() && a[3].isKnown() && b[3].isKnown() && b[2]
								.isKnown())) {
					b[1].setContents("" + (m1 * (u1 - v1) / (v2 - u2)), false);
				}
				// e
				if (!e.isKnown()
						&& (a[2].isKnown() && a[3].isKnown() && b[2].isKnown() && b[3].isKnown())) {
					e.setContents("" + ((v2 - v1) / (u1 - u2)), false);
					break;//Exit loop as all var must be known by now.
				}
				// v1 
				if (!a[2].isKnown() && a[2].isKnown() && b[3].isKnown() && b[3].isKnown()) {
					a[2].setContents("" + (u1 + (m2 * u2 - m2 * v2) / m1), false);//TODO: 
				}
				// v2
				if (!b[2].isKnown() && a[2].isKnown() && b[3].isKnown() && a[3].isKnown()) {
					b[2].setContents("" + (u2 - (m1 * u1 - m1 * v1) / m2), false);
				}
				// u1
				if (!a[3].isKnown() && a[2].isKnown() && b[2].isKnown() && b[3].isKnown()) {
					a[3].setContents("" + (v1 + (m2 * v2 - m2 * u2) / m1), false);
				}
				// u2
				if (!b[3].isKnown() && a[2].isKnown() && b[2].isKnown() && a[3].isKnown()) {
					b[3].setContents("" + v2 + (m1 * v1 - m1 * u1) / m2, false);
				}
				//v1 && v2
				if (!b[2].isKnown() && !a[2].isKnown() && b[3].isKnown() && a[3].isKnown()) {
					a[2].setContents("" + (u1 + (m2 * u2 - m2 * (c * (u1 - u2) + v1)) / m1), false);
					b[2].setContents("" + (u2 - (m1 * u1 - m1 * v1) / m2), false);
				}
				//u1 && u2 
				if (!b[3].isKnown() && !a[3].isKnown() && b[2].isKnown() && a[2].isKnown()) {
					b[3].setContents(
							""
									+ ((m1 * v1 / m2 - m1 / m2 * ((v2 - v1) / c) + u2 / m2 + v2) / (1 - 1 / m2)),
							false);
					a[3].setContents("" + (v1 + (m2 * v2 - m2 * u2) / m1), false);
				}

				//Escape if calc take too long
				if (System.currentTimeMillis() - startTime > 500) {
					System.out.println("Maximum time reached");
					break;
				}
			}

		} else if (topic.equals("Center")) {
			JOptionPane.showMessageDialog(frame, "Not avaliable.");
		} else if (topic.equals("Work")) {
			//TODO:Work energy power
		}
		System.out.println("Solver completed in " + (System.currentTimeMillis() - startTime)
				+ " ms");
		frame.updateFields();
	}
}
