package mainGui.centerOfMass;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import math.MathUtil;

/**
 * The DialogNewVertex class creates a new dialog for creating a vertex
 */
public class DialogNewVertex extends JDialog {
	private static final long serialVersionUID = 1L;

	JTextField xField = new JTextField(3);
	JTextField yField = new JTextField(3);
	JLabel labelx = new JLabel("x:");
	JLabel labely = new JLabel("y:");
	JButton confirm = new JButton("Done");
	Double x;
	Double y;

	/**
	 * Construct a new instance of this class
	 */
	public DialogNewVertex() {
		setTitle("New Vertex");
		setLayout(new FlowLayout(FlowLayout.LEFT));
		add(labelx);
		add(xField);
		add(labely);
		add(yField);
		add(confirm);
		setModal(true);
		ActionListener e = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String sx = xField.getText();
				String sy = yField.getText();
				if (MathUtil.isNumeric(sx) && MathUtil.isNumeric(sy)) {
					x = Double.parseDouble(sx);
					y = Double.parseDouble(sy);
					setVisible(false);
				} else {
					JOptionPane.showMessageDialog(getParent(), "Please enter a valid number!",
							"Oops", JOptionPane.ERROR_MESSAGE);
				}
			}
		};
		xField.addActionListener(e);
		yField.addActionListener(e);
		confirm.addActionListener(e);

		setLocationRelativeTo(getParent());
		pack();
		setResizable(false);
	}

	/**
	 * Show the dialog
	 */
	public void open() {
		xField.setText("");
		yField.setText("");
		setVisible(true);
	}
}
