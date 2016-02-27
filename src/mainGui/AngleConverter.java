package mainGui;

import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import math.MathUtil;

public class AngleConverter extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8781753606284159308L;
	JTextField a = new JTextField("0", 10);
	JTextField b = new JTextField("0", 10);
	JButton done = new JButton("Close");
	boolean editing = false;
	DocumentListener docB;
	DocumentListener docA;

	public AngleConverter(Frame frame) {
		placeField();
		setTitle("Degrees & radians converter");
		setResizable(false);
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
		setLocation(g.getCenterPoint().x - 500 / 2, g.getCenterPoint().y - 500 / 2);
		pack();
		done.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Close();
			}
		});
		docA = new DocumentListener() {
			private void update() {
				Runnable update = new Runnable() {

					@Override
					public void run() {
						b.getDocument().removeDocumentListener(docB);
						// TODO Auto-generated method stub
						if (MathUtil.isNumeric(a.getText())) {
							String t = ("" + Math.toRadians(Double.parseDouble(a.getText())));
							if (t.length() - t.indexOf(".") > 4) {
								t = t.substring(0, t.indexOf(".") + 4);
							}
							b.setText(t);
						} else {
							b.setText("");
						} // TODO: ILLEGAL STATE EXCEPTION!
						b.getDocument().addDocumentListener(docB);

					}
				};
				SwingUtilities.invokeLater(update);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				update();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				update();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				update();
			}
		};

		docB = new DocumentListener() {
			private void update() {
				Runnable update = new Runnable() {
					public void run() {
						a.getDocument().removeDocumentListener(docA);
						;
						if (MathUtil.isNumeric(b.getText())) {
							String t = ("" + Math.toDegrees(Double.parseDouble(b.getText())));
							if (t.length() - t.indexOf(".") > 4) {
								t = t.substring(0, t.indexOf(".") + 4);
							}
							a.setText(t);
						} else {
							a.setText("");
						}
						a.getDocument().addDocumentListener(docA);
					}
				};
				SwingUtilities.invokeLater(update);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				update();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				update();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				update();
			}
		};
		a.getDocument().addDocumentListener(docA);
		b.getDocument().addDocumentListener(docB);
	}

	/**
	 * Shows the dialog window. Clears each field.
	 */
	public void Open() {
		a.setText("0");
		b.setText("0");
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
		c.insets = new Insets(2, 2, 2, 2);
		c.gridy = 0;
		c.gridx = 0;
		add(new JLabel("Degrees: "), c);
		c.gridx++;
		add(a, c);
		c.gridx++;
		add(new JLabel("Radians: "), c);
		c.gridx++;
		add(b, c);
		c.gridy++;
		add(done, c);
	}
}
