package mainGui.centreOfMass;

import java.awt.Dimension;
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

/**
 * The DialogPointMass class creates a new dialog for creating a new pointmass
 */
public class DialogPointMass extends JDialog {
	private static final long serialVersionUID = 1L;

	Dimension prefSize = new Dimension(200, 200);
	JTextField x = new JTextField(5);
	JTextField y = new JTextField(5);
	JTextField mass = new JTextField(10);
	JButton butDone = new JButton("Done");
	boolean filled = false;
	Obj returnObj = null;

	/**
	 * Construct a new instance of this class
	 */
	public DialogPointMass() {
		returnObj = new Obj();
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 0, 5);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.WEST;

		setFocusable(true);
		setTitle("AddPointMass");

		add(new JLabel("x:"), gbc);
		gbc.gridy++;
		add(new JLabel("y:"), gbc);
		gbc.gridy++;
		add(new JLabel("mass:"), gbc);

		gbc.gridx++;
		gbc.gridy = 0;
		add(x, gbc);
		gbc.gridy++;
		add(y, gbc);
		gbc.gridy++;
		add(mass, gbc);
		gbc.gridy++;
		add(butDone, gbc);

		butDone.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (MathUtil.isNumeric(x.getText())) {
					double xval = Double.parseDouble(x.getText());
					if (MathUtil.isNumeric(y.getText())) {
						double yval = Double.parseDouble(y.getText());
						if (MathUtil.isNumeric(mass.getText())) {
							float massval = Float.valueOf(mass.getText());
							if (massval != 0) {
								returnObj = new Obj(Obj.POINTMASS, new MyPoint(xval, yval), null, massval);
								filled = true;
								close();
								return;
							}
						}
					}
				}
				returnObj = null;
				JOptionPane.showMessageDialog(null, "Something is not right.");
			}

		});

		setResizable(false);
		setModal(true);
		GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centre = g.getCenterPoint();
		setLocation(centre.x - prefSize.width / 2, centre.y - prefSize.height / 2);
		pack();

	}

	/**
	 * show the dialog
	 */
	public void open() {
		filled = false;
		x.setText("");
		y.setText("");
		mass.setText("");
		this.setVisible(true);
	}

	/**
	 * hide the dialog
	 */
	public void close() {
		returnObj = null;
		this.setVisible(false);
	}

}
