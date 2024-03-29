package mainGui.circularMotion;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTextField;

import mainGui.Frame;
import mainGui.ListenerAdder;

/**
 * The ButtonActionListener class handles ActionEvent thrown when the user
 * clicks the Add Force button
 * 
 */
public class ButtonActionListener implements ActionListener {

	Frame frame;

	/**
	 * Constructs a new action listener
	 * 
	 * @param frame
	 *            The frame this listener belongs to
	 */
	public ButtonActionListener(Frame frame) {
		this.frame = frame;
		frame.circLblX.setText("Sum of horizontal forces: ?");
		frame.circLblY.setText("Sum of vertical forces  : ?");

		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.weightx = 1;
		c.weighty = 1;
		c.gridy = 0;
		c.gridx = 0;
		frame.circSouthS.add(new JLabel("Magnitude"), c);
		c.gridx = 1;
		JLabel lbl = new JLabel("Direction.");
		lbl.setToolTipText("radians anticlock wise from 3 o'clock position");
		frame.circSouthS.add(lbl, c);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		addField("0", "0");
		frame.revalidate();
	}

	/**
	 * Add the parameters into the gui
	 * 
	 * @param fieldA
	 *            The first field
	 * @param fieldB
	 *            The second field
	 */
	private void addField(String fieldA, String fieldB) {
		GridBagConstraints c = new GridBagConstraints();
		frame.circF.add(new JTextField(fieldA, 9));
		frame.circT.add(new JTextField(fieldB, 9));
		frame.circTextA.add(fieldA);
		frame.circTextB.add(fieldB);
		final JTextField a = frame.circF.get(frame.circF.size() - 1);
		final JTextField b = frame.circT.get(frame.circT.size() - 1);
		c.gridy = frame.circF.size();
		c.gridx = 0;
		c.weightx = 1;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		frame.circSouthS.add(a, c);
		c.gridx = 1;
		frame.circSouthS.add(b, c);

		// add listeners
		ListenerAdder adder = new ListenerAdder(frame);
		adder.addListener(a, true);
		adder.addListener(b, false);
	}

	/**
	 * Add all fields in the parameter to the panel
	 * 
	 * @param fields
	 *            The fields to add
	 */
	public void loadFields(Component[] fields) {
		int i = 0;
		String temp = "";
		for (Component c : fields) {
			if (c instanceof JTextField) {
				if (i == 1) {
					i = 0;
					addField(temp, ((JTextField) c).getText());
				} else {
					i = 1;
					temp = ((JTextField) c).getText();
				}
			}
		}
	}

}
