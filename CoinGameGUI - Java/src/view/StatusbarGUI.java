package view;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class StatusbarGUI extends JPanel {
	public StatusbarGUI() {
		setLayout(new GridLayout(1, 3));
		JLabel statusleft = new JLabel("1st place");
		JLabel statusmiddle = new JLabel("1st place points", SwingConstants.CENTER);
		JLabel statusright = new JLabel("total spins left",SwingConstants.RIGHT);
		Border border = BorderFactory.createLineBorder(Color.black);
		statusleft.setBorder(border);
		statusmiddle.setBorder(border);
		statusright.setBorder(border);
		add(statusleft);
		add(statusmiddle);
		add(statusright);
	}
}
