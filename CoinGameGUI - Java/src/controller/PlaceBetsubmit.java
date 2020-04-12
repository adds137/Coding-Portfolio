package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import model.SimplePlayer;
import model.enumeration.BetType;

public class PlaceBetsubmit implements ActionListener {
	private JTextField betint;
	private JComboBox<SimplePlayer> jcb;
	private JComboBox<BetType> bettype;

	public PlaceBetsubmit(JTextField betint, JComboBox<SimplePlayer> jcb, JComboBox<BetType> bettype) {
		// TODO Auto-generated constructor stub
		this.betint = betint;
		this.jcb = jcb;
		this.bettype = bettype;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("sumbit works");
		SimplePlayer user = (SimplePlayer) jcb.getSelectedItem();
		String stringbet = betint.getText();
		int betint = Integer.parseInt(stringbet);
		user.setBet(betint);
		BetType type = (BetType) bettype.getSelectedItem();
		user.setBetType(type);
		System.out.println("Bet Successfully placed");
		jcb.invalidate();
	}

}
