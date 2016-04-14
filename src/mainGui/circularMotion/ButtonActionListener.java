package mainGui.circularMotion;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import mainGui.Frame;
import math.MathUtil;

public class ButtonActionListener implements ActionListener {

	Frame frame;

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
	}

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

		a.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if (!MathUtil.isNumeric(a.getText())) {
					JOptionPane.showMessageDialog(frame, "Not a number!.");
					return;
				}
				frame.circTextA.set(frame.circF.indexOf(a), a.getText());
				frame.updateFields();
			}

			@Override
			public void focusGained(FocusEvent e) {
				// Do nothing
			}
		});
		a.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!MathUtil.isNumeric(a.getText())) {
					JOptionPane.showMessageDialog(frame, "Not a number!");
					return;
				}
				frame.circTextA.set(frame.circF.indexOf(a), a.getText());
				frame.updateFields();
			}
		});
		b.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if (!MathUtil.isNumeric(b.getText())) {
					JOptionPane.showMessageDialog(frame, "Not a number!");
					return;
				}
				frame.circTextB.set(frame.circT.indexOf(b), b.getText());
				frame.updateFields();
			}

			@Override
			public void focusGained(FocusEvent e) {
				// Do nothing
			}
		});
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!MathUtil.isNumeric(b.getText())) {
					JOptionPane.showMessageDialog(frame, "Not a number!");
					return;
				}
				frame.circTextB.set(frame.circT.indexOf(b), b.getText());
				frame.updateFields();
			}
		});
	}

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
