package view;

import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class CoinGUI extends JPanel {
	public CoinGUI() {
		setLayout(new GridLayout(1,2));
		ImageIcon heads = new ImageIcon("img/heads.png");
		ImageIcon tails = new ImageIcon("img/tails.png");
		JLabel lblheads = new JLabel();
		JLabel lbltails = new JLabel();
		lblheads.setIcon(heads);
		lbltails.setIcon(tails);
		add(lblheads);
		add(lbltails);
	}
}
