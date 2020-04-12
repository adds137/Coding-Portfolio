package view;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.PlaceBetsubmit;
import model.SimplePlayer;
import model.enumeration.BetType;

@SuppressWarnings("serial")
public class PlaceBetGUI extends JFrame {
	public PlaceBetGUI(JComboBox<SimplePlayer> jcb) {
		super("Place Bet");
		JPanel panel = new JPanel(new GridLayout(2, 2));
		JLabel bettype = new JLabel("BetType:");
		JLabel betnum = new JLabel("Bet Amount:");
		JLabel playerpic = new JLabel("Player:");
		JButton betbtn = new JButton("Place bet");
		JTextField betnumtxt = new JTextField();
		BetType[] betvalue = { BetType.NO_BET, BetType.COIN1, BetType.COIN2, BetType.BOTH };
		JComboBox<BetType> jcbbet = new JComboBox<BetType>(betvalue);
		panel.add(bettype);
		panel.add(jcbbet);
		panel.add(betnum);
		panel.add(betnumtxt);
		panel.add(playerpic);
		panel.add(jcb);
		panel.add(betbtn);
		betbtn.addActionListener(new PlaceBetsubmit(betnumtxt, jcb,jcbbet));
		panel.setBorder(new EmptyBorder(15, 15, 15, 15));
		add(panel);
		setBounds(100,100,500,500);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setVisible(true);
	}
}
