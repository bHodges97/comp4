package mainGui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class myMenuBar extends JMenuBar {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1764943131583829011L;

	public myMenuBar(final Frame main) {

		JMenu menuFile = new JMenu("File");
		JMenu menuTools = new JMenu("Tool");
		JMenuItem menuSolve = new JMenuItem("Solve");
		menuFile.setMnemonic(KeyEvent.VK_F);
		menuTools.setMnemonic(KeyEvent.VK_T);
		add(menuFile);
		add(menuTools);
		add(menuSolve);
		JMenuItem mConveter = new JMenuItem("Degrees & radians converter", KeyEvent.VK_D);
		JMenuItem mTrig = new JMenuItem("Trig calculator", KeyEvent.VK_T);
		menuTools.add(mConveter);
		menuTools.add(mTrig);
		final AngleConverter dialogConverter = new AngleConverter();
		mConveter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogConverter.Open();
			}
		});
		menuSolve.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				main.solve();

			}
		});
	}
}
