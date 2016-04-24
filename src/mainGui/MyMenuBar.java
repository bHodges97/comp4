package mainGui;

import java.awt.Color;
import java.awt.Component;
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

import javax.imageio.ImageIO;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

import mainGui.circularMotion.ButtonActionListener;
import math.Definition;
import math.Plane;
import math.Solver;
import math.Var;

/**
 * The MyMenuBar class creates a menubar for the gui and handles the menu
 * events.
 * 
 * 
 */
public class MyMenuBar extends JMenuBar {
	private static final long serialVersionUID = 11L;
	AngleConverter dialogConverter;
	JFileChooser imageChooser, fileChooser;
	JMenuItem saveImage, saveFile, loadFile, saveNotes;
	JMenuItem initCirc, initCOM, initColl, initProj;
	JMenuItem mConverter, mTrig;
	JMenuItem toggleColor, setBackground;
	JMenuItem menuSolve;
	JMenuItem zoomIn, zoomOut, zoomReset;

	final Frame frame;

	/**
	 * Construct a new menubar for the Frame
	 * 
	 * @param main
	 *            The frame to add the menubar to
	 */
	public MyMenuBar(final Frame main) {
		frame = main;
		dialogConverter = new AngleConverter();
		imageChooser = new JFileChooser();
		fileChooser = new JFileChooser();
		FileNameExtensionFilter filterFile = new FileNameExtensionFilter("m2 files (*.m2)", "m2");
		FileNameExtensionFilter filterImage = new FileNameExtensionFilter("PNG files (*.png)", "png");
		imageChooser.setFileFilter(filterImage);
		fileChooser.setFileFilter(filterFile);

		// File
		JMenu menuFile = new JMenu("File");
		menuFile.setMnemonic(KeyEvent.VK_F);
		saveImage = new JMenuItem("Export image", KeyEvent.VK_I);
		saveFile = new JMenuItem("Save as...", KeyEvent.VK_S);
		loadFile = new JMenuItem("Open File...", KeyEvent.VK_E);
		saveNotes = new JMenuItem("Save Notes", KeyEvent.VK_N);
		menuFile.add(loadFile);
		menuFile.add(saveFile);
		menuFile.add(saveImage);
		menuFile.add(saveNotes);

		// Topic
		JMenu menuTopic = new JMenu("Topic");
		menuFile.setMnemonic(KeyEvent.VK_T);
		initCirc = new JMenuItem("Circular Motion", KeyEvent.VK_C);
		initCOM = new JMenuItem("Center of mass", KeyEvent.VK_M);
		initColl = new JMenuItem("Collisons and Restitution", KeyEvent.VK_R);
		initProj = new JMenuItem("Projectile Motion", KeyEvent.VK_P);

		menuTopic.add(initCirc);
		menuTopic.add(initCOM);
		menuTopic.add(initColl);
		menuTopic.add(initProj);

		// Tools
		JMenu menuMaths = new JMenu("Maths");
		menuMaths.setMnemonic(KeyEvent.VK_T);
		mConverter = new JMenuItem("Degrees & radians converter", KeyEvent.VK_D);
		mTrig = new JMenuItem("Trig calculator", KeyEvent.VK_T);
		menuSolve = new JMenuItem("Solve", KeyEvent.VK_S);
		menuMaths.add(mConverter);
		menuMaths.add(mTrig);
		menuMaths.add(menuSolve);

		// Image
		JMenu menuImage = new JMenu("Image");
		toggleColor = new JMenuItem("Toggle colours", KeyEvent.VK_T);
		setBackground = new JMenuItem("Background colour", KeyEvent.VK_B);
		menuImage.add(toggleColor);
		menuImage.add(setBackground);

		// Zoom
		JMenu menuZoom = new JMenu("Zoom");
		menuMaths.setMnemonic(KeyEvent.VK_Z);
		zoomIn = new JMenuItem("Zoom in");
		zoomOut = new JMenuItem("Zoom out");
		zoomReset = new JMenuItem("Reset zoom");
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
					frame.panelCOM.zoom(0);
				}
				if (e.getSource() == zoomOut) {
					frame.panelCOM.zoom(1);
				}
				if (e.getSource() == zoomReset) {
					frame.panelCOM.zoom(2);
				}
				if (e.getSource() == saveImage) {
					if (imageChooser.showSaveDialog(main) == JFileChooser.APPROVE_OPTION) {
						exportImage(imageChooser.getSelectedFile().getPath());
					}
				}
				if (e.getSource() == loadFile) {
					if (fileChooser.showOpenDialog(main) == JFileChooser.APPROVE_OPTION) {
						load(fileChooser.getSelectedFile().getPath());
					}
				}
				if (e.getSource() == saveFile) {
					if (fileChooser.showSaveDialog(main) == JFileChooser.APPROVE_OPTION) {
						save(fileChooser.getSelectedFile().getPath());
					}
				}
				if (e.getSource() == saveNotes) {
					saveNotes();
				}
				if (e.getSource() == initCirc) {
					frame.setTopic(Frame.CIRCLES);
				}
				if (e.getSource() == initCOM) {
					frame.setTopic(Frame.CENTER);
				}
				if (e.getSource() == initColl) {
					frame.setTopic(Frame.COLLISIONS);
				}
				if (e.getSource() == initProj) {
					frame.setTopic(Frame.PROJECTILES);
				}
				if (e.getSource() == toggleColor) {
					toggleColor();
				}
				if (e.getSource() == setBackground) {
					setBackground();
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
		toggleColor.addActionListener(listener);
		setBackground.addActionListener(listener);

		// Add all menu items;
		add(menuFile);
		add(menuMaths);
		add(menuTopic);
		add(menuImage);
		if (frame.topic == Frame.CENTER) {
			add(menuZoom);
		}
	}

	/**
	 * Export the diagram as a png
	 * 
	 * @param pathName
	 *            The destination to export to
	 */
	private void exportImage(String pathName) {
		if (pathName.endsWith(".png")) {
			pathName.replace(".png", "");
		}
		if (frame.topic == Frame.CIRCLES) {
			BufferedImage imgA = frame.circTopDown.getImg();
			BufferedImage imgB = frame.circVertical.getImg();
			int width = imgA.getWidth() + imgB.getWidth();
			int height = imgA.getHeight();
			BufferedImage combinedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
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
		if (frame.topic == Frame.CENTER) {
			frame.panelCOM.print(pathName);
		}
		if (frame.topic == Frame.COLLISIONS) {
			frame.colDiagram.print(pathName);
		}
		if (frame.topic == Frame.PROJECTILES) {
			frame.projDiagram.print(pathName);
		}
	}

	/**
	 * Load the file at the specified location.
	 * 
	 * @param pathName
	 *            The path of the file to load
	 */
	private void load(String pathName) {

		Object savedItem = null;
		try {
			FileInputStream fout = new FileInputStream(pathName);
			ObjectInputStream oos = new ObjectInputStream(fout);
			savedItem = oos.readObject();
			oos.close();

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "<html>Failed to load!<br>" + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		try {
			Object[] saves = (Object[]) savedItem;
			int topic = ((int) saves[0]);
			frame.setTopic(topic);
			if (topic == Frame.CIRCLES) {
				frame.circVars = (Var[]) saves[1];
				frame.circVarB = (Var[]) saves[2];
				Component[] fields = (Component[]) saves[3];
				// this class has the right method.
				new ButtonActionListener(frame).loadFields(fields);
			} else if (topic == Frame.CENTER) {
				frame.panelCOM.plane = (Plane) saves[1];
			} else if (topic == Frame.COLLISIONS) {
				frame.colVarA = (Var[]) saves[1];
				frame.colVarB = (Var[]) saves[2];
				frame.colVarE = (Var) saves[3];
			} else if (topic == Frame.PROJECTILES) {
				frame.projVars = (Var[]) saves[1];
			} else {
				throw new ClassCastException("No matching topic");
			}
			frame.updateFields();
			JOptionPane.showMessageDialog(null, "Loaded successfully!");
		} catch (ClassCastException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "<html>File is corrupted!<br>" + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Saves the conditions of the current program.
	 * 
	 * @param pathName
	 *            The path to save too
	 */
	private void save(String pathName) {
		Object savedItem = null;
		if (frame.topic == Frame.CIRCLES) {
			savedItem = new Object[] { Frame.CIRCLES, frame.circVars, frame.circVarB,
					frame.circSouthS.getComponents() };
		}
		if (frame.topic == Frame.CENTER) {
			savedItem = new Object[] { Frame.CENTER, frame.panelCOM.plane };
		}
		if (frame.topic == Frame.COLLISIONS) {
			savedItem = new Object[] { Frame.COLLISIONS, frame.colVarA, frame.colVarB, frame.colVarE };
		}
		if (frame.topic == Frame.PROJECTILES) {
			savedItem = new Object[] { Frame.PROJECTILES, frame.projVars };
		}

		try {
			FileOutputStream fout = new FileOutputStream(pathName.replace(".m2", "") + ".m2");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(savedItem);
			oos.close();
			JOptionPane.showMessageDialog(null, "Saved successfully!");
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "<html>Failed to save!<br>" + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Attempts to sovle
	 */
	public void solve() {
		int topic = frame.topic;
		Var[] circVars = frame.circVars;
		Var[] circVarB = frame.circVars;
		Var[] projVars = frame.projVars;
		Var[] a = frame.colVarA;
		Var[] b = frame.colVarB;
		Var e = frame.colVarE;

		long startTime = System.currentTimeMillis();
		if (topic == Frame.CIRCLES) {
			int confirm = JOptionPane.showConfirmDialog(frame, "Attempts to find unkown variables if possible.",
					"Solver", JOptionPane.OK_CANCEL_OPTION);
			if (confirm == JOptionPane.CANCEL_OPTION || confirm == JOptionPane.CLOSED_OPTION) {
				return;
			}

			if (!circVarB[0].isUnknown() && circVars[5].isUnknown()) {
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
			defs[6] = new Definition("t=x-u/w");
			defs[7] = new Definition("r=v/w");
			defs[8] = new Definition("r=v^2/a");
			Solver s = new Solver(defs, circVars);

		} else if (topic == Frame.PROJECTILES) {
			int confirm = JOptionPane.showConfirmDialog(frame, "Attempts to find unkown variables if possible.",
					"Solver", JOptionPane.OK_CANCEL_OPTION);
			if (confirm == JOptionPane.CANCEL_OPTION || confirm == JOptionPane.CLOSED_OPTION) {
				return;
			}
			if (!projVars[9].isUnknown() && !projVars[8].isUnknown()) {
				projVars[0].setContents("" + Math.atan(projVars[9].getVal() / projVars[8].getVal()), false);
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

		} else if (topic == Frame.COLLISIONS) {
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
				if (!a[1].isUnknown() && (b[1].isUnknown() && b[2].isUnknown() && b[3].isUnknown() && a[3].isUnknown()
						&& a[2].isUnknown())) {
					a[1].setContents("" + (m2 * (v2 - u2) / (u1 - v1)), false);
				}
				// mass 2
				if (!b[1].isUnknown() && (a[1].isUnknown() && a[2].isUnknown() && a[3].isUnknown() && b[3].isUnknown()
						&& b[2].isUnknown())) {
					b[1].setContents("" + (m1 * (u1 - v1) / (v2 - u2)), false);
				}
				// e
				if (!e.isUnknown() && (a[2].isUnknown() && a[3].isUnknown() && b[2].isUnknown() && b[3].isUnknown())) {
					e.setContents("" + ((v2 - v1) / (u1 - u2)), false);
					break;// Exit loop as all var must be known by now.
				}
				// v1
				if (!a[2].isUnknown() && a[2].isUnknown() && b[3].isUnknown() && b[3].isUnknown()) {
					a[2].setContents("" + (u1 + (m2 * u2 - m2 * v2) / m1), false);
				}
				// v2
				if (!b[2].isUnknown() && a[2].isUnknown() && b[3].isUnknown() && a[3].isUnknown()) {
					b[2].setContents("" + (u2 - (m1 * u1 - m1 * v1) / m2), false);
				}
				// u1
				if (!a[3].isUnknown() && a[2].isUnknown() && b[2].isUnknown() && b[3].isUnknown()) {
					a[3].setContents("" + (v1 + (m2 * v2 - m2 * u2) / m1), false);
				}
				// u2
				if (!b[3].isUnknown() && a[2].isUnknown() && b[2].isUnknown() && a[3].isUnknown()) {
					b[3].setContents("" + v2 + (m1 * v1 - m1 * u1) / m2, false);
				}
				// v1 && v2
				if (!b[2].isUnknown() && !a[2].isUnknown() && b[3].isUnknown() && a[3].isUnknown()) {
					a[2].setContents("" + (u1 + (m2 * u2 - m2 * (c * (u1 - u2) + v1)) / m1), false);
					b[2].setContents("" + (u2 - (m1 * u1 - m1 * v1) / m2), false);
				}
				// u1 && u2
				if (!b[3].isUnknown() && !a[3].isUnknown() && b[2].isUnknown() && a[2].isUnknown()) {
					b[3].setContents("" + ((m1 * v1 / m2 - m1 / m2 * ((v2 - v1) / c) + u2 / m2 + v2) / (1 - 1 / m2)),
							false);
					a[3].setContents("" + (v1 + (m2 * v2 - m2 * u2) / m1), false);
				}

				// Escape if calc take too long
				if (System.currentTimeMillis() - startTime > 500) {
					System.out.println("Maximum time reached");
					break;
				}
			}

		} else if (topic == Frame.CENTER) {
			JOptionPane.showMessageDialog(frame, "Not avaliable.");
		}
		System.out.println("Solver completed in " + (System.currentTimeMillis() - startTime) + " ms");
		frame.updateFields();
	}

	/**
	 * Saves the notes file to the folder m2/notes/ in the program location
	 */
	private void saveNotes() {
		JTextArea textArea = frame.topicDesc;
		String path = "";

		if (frame.topic == Frame.CIRCLES) {
			path = "m2/notes/circlesNotes.txt";
		}
		if (frame.topic == Frame.CENTER) {
			path = "m2/notes/comNotes.txt";
		}
		if (frame.topic == Frame.COLLISIONS) {
			path = "m2/notes/collisionNotes.txt";
		}
		if (frame.topic == Frame.PROJECTILES) {
			path = "m2/notes/projectileNotes.txt";
		}
		// Create file if none exists.
		File file = new File(path);
		if (!file.exists()) {
			try {
				file.getParentFile().mkdirs();
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// Overwrite the file in the selected path
		Writer writer = null;
		try {
			FileOutputStream fout = new FileOutputStream(file, false);
			writer = new BufferedWriter(new OutputStreamWriter(fout));
			writer.write(textArea.getText());
			JOptionPane.showMessageDialog(null, "Notes saved");
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Failed to save notes!", "Error", JOptionPane.ERROR_MESSAGE);
		} finally {
			try {
				writer.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * Toggle black and white
	 */
	private void toggleColor() {
		frame.color = !frame.color;
	}

	/**
	 * Set background colour
	 */
	private void setBackground() {
		Color newColor = JColorChooser.showDialog(null, "Choose a color", Color.white);
		frame.bgColor = newColor;
		setBackground.setBackground(newColor);
	}
}
