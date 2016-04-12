package mainGui;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import math.Definition;
import math.Solver;
import math.Var;

public class MyMenuBar extends JMenuBar {
	final Frame frame;

	/**
	 * 
	 */
	private static final long serialVersionUID = -1764943131583829011L;

	public MyMenuBar(final Frame main) {
		frame = main;
		final JFileChooser imageChooser = new JFileChooser();
		final AngleConverter dialogConverter = new AngleConverter();
		final JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("m2 files (*.m2s)", "m2s");
		fileChooser.setFileFilter(filter);

		// File
		JMenu menuFile = new JMenu("File");
		menuFile.setMnemonic(KeyEvent.VK_F);
		final JMenuItem saveImage = new JMenuItem("Export image", KeyEvent.VK_I);
		final JMenuItem saveFile = new JMenuItem("Save as...", KeyEvent.VK_F);
		final JMenuItem loadFile = new JMenuItem("Open File...", KeyEvent.VK_O);
		final JMenuItem saveNotes = new JMenuItem("Save Notes", KeyEvent.VK_T);
		menuFile.add(loadFile);
		menuFile.add(saveFile);
		menuFile.add(saveImage);
		menuFile.add(saveNotes);

		// Topic
		JMenu menuTopic = new JMenu("Topic");
		menuFile.setMnemonic(KeyEvent.VK_T);
		final JMenuItem initCirc = new JMenuItem("Circular Motion", KeyEvent.VK_M);
		final JMenuItem initCOM = new JMenuItem("Center of mass", KeyEvent.VK_O);
		final JMenuItem initColl = new JMenuItem("Collisions", KeyEvent.VK_C);
		final JMenuItem initProj = new JMenuItem("Projectiles", KeyEvent.VK_P);
		menuTopic.add(initCirc);
		menuTopic.add(initCOM);
		menuTopic.add(initColl);
		menuTopic.add(initProj);

		// Tools
		JMenu menuTools = new JMenu("Tools");
		menuTools.setMnemonic(KeyEvent.VK_T);
		final JMenuItem mConverter = new JMenuItem("Degrees & radians converter", KeyEvent.VK_D);
		final JMenuItem mTrig = new JMenuItem("Trig calculator", KeyEvent.VK_T);
		menuTools.add(mConverter);
		menuTools.add(mTrig);

		// Solve
		final JMenuItem menuSolve = new JMenuItem("Solve");

		// Zoom
		JMenu menuZoom = new JMenu("Zoom");
		menuTools.setMnemonic(KeyEvent.VK_Z);
		final JMenuItem zoomIn = new JMenuItem("Zoom in");
		final JMenuItem zoomOut = new JMenuItem("Zoom out");
		final JMenuItem zoomReset = new JMenuItem("Reset zoom");
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
				if (e.getSource() == saveImage) {
					if (imageChooser.showSaveDialog(main) == JFileChooser.APPROVE_OPTION) {
						exportImage(imageChooser.getSelectedFile().getPath());
					}
				}
				if (e.getSource() == loadFile) {
					if (imageChooser.showOpenDialog(main) == JFileChooser.APPROVE_OPTION) {
						load(imageChooser.getSelectedFile().getPath());
					}
				}
				if (e.getSource() == saveFile) {
					if (imageChooser.showSaveDialog(main) == JFileChooser.APPROVE_OPTION) {
						save(imageChooser.getSelectedFile().getPath());
					}
				}
				if (e.getSource() == saveNotes) {
					saveNotes();
				}
				if (e.getSource() == initCirc) {
					frame.setTopic("Circles");
				}
				if (e.getSource() == initCOM) {
					frame.setTopic("Center");
				}
				if (e.getSource() == initColl) {
					frame.setTopic("Collisions");
				}
				if (e.getSource() == initProj) {
					frame.setTopic("Projectiles");
				}
			}

		};

		// AddListeners;
		menuSolve.addActionListener(listener);
		mConverter.addActionListener(listener);
		zoomIn.addActionListener(listener);
		zoomOut.addActionListener(listener);
		zoomReset.addActionListener(listener);
		loadFile.addActionListener(listener);
		saveImage.addActionListener(listener);
		saveFile.addActionListener(listener);
		saveNotes.addActionListener(listener);
		initCirc.addActionListener(listener);
		initCOM.addActionListener(listener);
		initColl.addActionListener(listener);
		initProj.addActionListener(listener);

		// Add all menu items;
		add(menuFile);
		add(menuTools);
		add(menuTopic);
		add(menuSolve);
		if (frame.topic.equals("Center")) {
			add(menuZoom);
		}
	}

	private void exportImage(String pathName) {
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

	private void load(String pathName) {
		Object savedItem = null;
		try {
			FileInputStream fout = new FileInputStream(pathName);
			ObjectInputStream oos = new ObjectInputStream(fout);
			savedItem = oos.readObject();
			oos.close();

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Failed to save!\n" + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		if (savedItem == null) {
			throw new IllegalArgumentException("File not found");
		}
		Object[] saves = (Object[]) savedItem;
		String topic = ((String) saves[0]);
		if (topic.equals("Circles")) {
			frame.circVars = (Var[]) saves[1];
			frame.circVarB = (Var[]) saves[2];
			frame.circTextA = (List<String>) saves[3];
			frame.circTextB = (List<String>) saves[4];
			frame.circX = (JTextField) saves[5];
			frame.circY = (JTextField) saves[6];
			frame.updateFields();
		}
		if (topic.equals("Center")) {
			savedItem = frame.canvas.plane;
		}
		if (topic.equals("Collisions")) {
			savedItem = new Object[] { frame.colVarA, frame.colVarB, frame.colVarE };
		}
		if (topic.equals("Projectiles")) {
			savedItem = frame.projVars;
		}
		JOptionPane.showMessageDialog(null, "Loaded successfully!");
	}

	private void save(String pathName) {
		Object savedItem = null;
		if (pathName.endsWith(".m2s")) {
			pathName.replace(".m2s", "");
		}
		if (frame.topic.equals("Circles")) {
			//savedItem = new Object[] { "Circles", frame.circVars, frame.circVarB, frame.circTextA,
			//	frame.circTextB, frame.circX, frame.circY };
		}
		if (frame.topic.equals("Center")) {
			//savedItem = new Object[] { "Center", frame.canvas.plane };
		}
		if (frame.topic.equals("Collisions")) {
			//savedItem = new Object[] { "Collision", frame.colVarA, frame.colVarB, frame.colVarE };
		}
		if (frame.topic.equals("Projectiles")) {
			//savedItem = new Object[] { "Center", frame.projVars };
		}

		try {
			FileOutputStream fout = new FileOutputStream(pathName + ".m2s");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(savedItem);
			oos.close();
			JOptionPane.showMessageDialog(null, "Saved successfully!");
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Failed to save!\n" + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
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
		Var[] a = frame.colVarA;
		Var[] b = frame.colVarB;
		Var e = frame.colVarE;

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
			// Not using solver as simultaneous equations are too complex to be
			// represented.
			while (true) {
				double m1 = a[1].getVal();
				double m2 = b[1].getVal();
				double v1 = a[2].getVal();
				double v2 = b[2].getVal();
				double u1 = a[3].getVal();
				double u2 = b[3].getVal();
				double c = e.getVal();// c since e is used.

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
					break;// Exit loop as all var must be known by now.
				}
				// v1
				if (!a[2].isKnown() && a[2].isKnown() && b[3].isKnown() && b[3].isKnown()) {
					a[2].setContents("" + (u1 + (m2 * u2 - m2 * v2) / m1), false);
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
				// v1 && v2
				if (!b[2].isKnown() && !a[2].isKnown() && b[3].isKnown() && a[3].isKnown()) {
					a[2].setContents("" + (u1 + (m2 * u2 - m2 * (c * (u1 - u2) + v1)) / m1), false);
					b[2].setContents("" + (u2 - (m1 * u1 - m1 * v1) / m2), false);
				}
				// u1 && u2
				if (!b[3].isKnown() && !a[3].isKnown() && b[2].isKnown() && a[2].isKnown()) {
					b[3].setContents(
							""
									+ ((m1 * v1 / m2 - m1 / m2 * ((v2 - v1) / c) + u2 / m2 + v2) / (1 - 1 / m2)),
							false);
					a[3].setContents("" + (v1 + (m2 * v2 - m2 * u2) / m1), false);
				}

				// Escape if calc take too long
				if (System.currentTimeMillis() - startTime > 500) {
					System.out.println("Maximum time reached");
					break;
				}
			}

		} else if (topic.equals("Center")) {
			JOptionPane.showMessageDialog(frame, "Not avaliable.");
		}
		System.out.println("Solver completed in " + (System.currentTimeMillis() - startTime)
				+ " ms");
		frame.updateFields();
	}

	private void saveNotes() {
		JTextArea textArea = frame.topicDesc;
		String path = "";

		if (frame.topic.equals("Circles")) {
			path = "m2diagramDrawer/notes/circlesNotes";
		}
		if (frame.topic.equals("Center")) {
			path = "m2diagramDrawer/notes/comNotes";
		}
		if (frame.topic.equals("Collisions")) {
			path = "m2diagramDrawer/notes/collisionNotes";
		}
		if (frame.topic.equals("Projectiles")) {
			path = "m2diagramDrawer/notes/projectileNotes";
		}
		Writer writer = null;
		try {
			FileOutputStream fout = new FileOutputStream(path);
			writer = new BufferedWriter(new OutputStreamWriter(fout));
			writer.write(textArea.getText());
			JOptionPane.showMessageDialog(null, "Notes saved");
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Failed to save notes!", "Error",
					JOptionPane.ERROR_MESSAGE);
		} finally {
			try {
				writer.close();
			} catch (Exception e) {
			}
		}
	}
}
