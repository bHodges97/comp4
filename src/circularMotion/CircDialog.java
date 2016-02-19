package circularMotion;

import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import gui.Frame;
import math.Definition;
import math.MathUtil;
import math.Solver;
import math.Var;

public class CircDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6055392994194343968L;
	JButton Done = new JButton("Done");
	JLabel Explanation = new JLabel("<html>Fill in all known fields as numbers.<br>Leave unknowns blank.");
	int n = 18;
	JTextField textW = new JTextField("0", n);
	JTextField textM = new JTextField("0", n);
	JTextField textU = new JTextField("0", n);
	JTextField textX = new JTextField("0", n);
	JTextField textV = new JTextField("0", n);
	JTextField textR = new JTextField("0", n);
	JTextField textA = new JTextField("0", n);
	JTextField textT = new JTextField("0", n);

	public Definition[] defs;
	public Var[] vars = new Var[8];

	public CircDialog(Frame frame) {
		// Calls JDialog constructor,sets parent and modality.
		super(frame, true);

		placeField();
		setTitle("Input details");
		setResizable(false);
		setModal(true);

		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point center = g.getCenterPoint();
		setLocation(center.x - 500 / 2, center.y - 500 / 2);
		pack();
		initDefs();

		// Adds a generic error message.
		Done.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for (Component c : getContentPane().getComponents()) {
					if (c instanceof JTextField) {
						// Tests if each field is valid(blank or numeric)
						if (!MathUtil.isNumeric(((JTextField) c).getText()) && !((JTextField) c).getText().isEmpty()) {
							JOptionPane.showMessageDialog(getThis(),
									"One of the variables wasn't numeric or blank (" + ((JTextField) c).getText() + ")",
									"Warning", JOptionPane.ERROR_MESSAGE);
							return;
						}
					}
				}
				Close();
				Solve();
			}
		});
		// Initialise variables;
		for (Var var : vars) {
			var = new Var("", "", "");
		}

		// needs to be last for components to show properly!(setVisible(true))
		Open();
	}

	/**
	 * Initialise definitions.
	 */
	private void initDefs() {
		defs = new Definition[10];
		defs[0] = new Definition("v=r*w");
		defs[1] = new Definition("w=v/r");
		defs[2] = new Definition("f=m*a");
		defs[3] = new Definition("a=v^2/r");
		defs[4] = new Definition("a=w^2*r");
		defs[5] = new Definition("x=t*w+o");
		defs[6] = new Definition("t=x-0/t");
		defs[7] = new Definition("r=v/w");
		defs[8] = new Definition("r=v^2/a");
		defs[9] = new Definition("w=2*3.13/t");
	}

	/**
	 * Find Unknowns.
	 */
	public void Solve() {
		vars[0] = new Var("w", textW.getText(), "w");
		vars[1] = new Var("m", textM.getText(), "m");
		vars[2] = new Var("u", textU.getText(), "u");
		vars[3] = new Var("x", textX.getText(), "x");
		vars[4] = new Var("v", textV.getText(), "v");
		vars[5] = new Var("r", textR.getText(), "r");
		vars[6] = new Var("a", textA.getText(), "α");
		vars[7] = new Var("t", textT.getText(), "t");

		for (Var var : vars) {
			if (var.contents.isEmpty()) {
				var.contents = "Unknown";
			}
		}
		Solver s = new Solver(defs, vars);
		for (Var var : vars) {
			if (var.contents.equals("Unkown")) {
				s.solve(var, 0);
			}
		}
		for (Var var : vars) {
			if (!var.contents.equals("Unkown")) {
				if (var.name.equals("v")) {
					textW.setText(var.contents);
				} else if (var.name.equals("m")) {
					textM.setText(var.contents);
				} else if (var.name.equals("u")) {
					textU.setText(var.contents);
				} else if (var.name.equals("x")) {
					textX.setText(var.contents);
				} else if (var.name.equals("v")) {
					textV.setText(var.contents);
				} else if (var.name.equals("r")) {
					textR.setText(var.contents);
				} else if (var.name.equals("a")) {
					textA.setText(var.contents);
				} else if (var.name.equals("t")) {
					textT.setText(var.contents);
				}

			}
		}

	}

	/**
	 * Shows the dialog window. Clears each field.
	 */
	public void Open() {

		/*
		 * Dialog panel is used to store variables, should not be cleared.
		 * 
		 * for (Component c : this.getContentPane().getComponents()) { if (c
		 * instance of JTextField) { ((JTextField)c).setText("");
		 * 
		 * } }
		 */
		setVisible(true);
	}

	/**
	 * Hides the dialog window.
	 * 
	 */
	public void Close() {
		setVisible(false);

	}

	private void placeField() {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.anchor = c.WEST;
		c.gridy = 0;
		this.add(Explanation, c);

		c.gridy++;
		this.add(new JLabel("Start Angle"), c);
		c.gridy++;
		this.add(textU, c);

		c.gridy++;
		this.add(new JLabel("Time"), c);
		c.gridy++;
		this.add(textT, c);

		c.gridy++;
		this.add(new JLabel("End Angle"), c);
		c.gridy++;
		this.add(textX, c);

		c.gridy++;
		this.add(new JLabel("Angular Velocity"), c);
		c.gridy++;
		this.add(textW, c);

		c.gridy++;
		this.add(new JLabel("Tangential Velocity"), c);
		c.gridy++;
		this.add(textV, c);

		c.gridy++;
		this.add(new JLabel("Mass"), c);
		c.gridy++;
		this.add(textM, c);

		c.gridy++;
		this.add(new JLabel("Radius"), c);
		c.gridy++;
		this.add(textR, c);

		c.gridy++;
		this.add(new JLabel("Centripetal Acceleration"), c);
		c.gridy++;
		this.add(textA, c);

		c.gridy++;
		c.anchor = c.EAST;
		Done.setAlignmentX(RIGHT_ALIGNMENT);
		this.add(Done, c);
	}

	/**
	 * Alternative to "this" keyword.
	 * 
	 * @return this object
	 */
	public CircDialog getThis() {
		return this;
	}
}