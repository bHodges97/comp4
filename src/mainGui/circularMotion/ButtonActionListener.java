package mainGui.circularMotion;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import mainGui.Frame;
import math.MathUtil;

public class ButtonActionListener implements ActionListener {

	Frame frame;

	public ButtonActionListener(Frame frame) {
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		GridBagConstraints c = new GridBagConstraints();
		frame.circF.add(new JTextField("0", 9));
		frame.circT.add(new JTextField("0", 9));
		frame.circTextA.add("0");
		frame.circTextB.add("0");
		final JTextField a = frame.circF.get(frame.circF.size() - 1);
		final JTextField b = frame.circT.get(frame.circT.size() - 1);
		c.gridy = frame.circF.size();
		c.gridx = 0;
		c.weightx = 1;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		frame.panelSouthS.add(a, c);
		c.gridx = 1;
		frame.panelSouthS.add(b, c);

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

}
