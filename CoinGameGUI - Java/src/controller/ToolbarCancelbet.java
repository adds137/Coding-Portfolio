package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import model.SimplePlayer;

public class ToolbarCancelbet implements ActionListener{
	private JComboBox<SimplePlayer> jcb;
	public ToolbarCancelbet(JComboBox<SimplePlayer> jcb) {
		// TODO Auto-generated constructor stub
		this.jcb = jcb;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Cancel Bet working");
		JOptionPane.showMessageDialog(null, jcb, "Player to Remove Bet From?", JOptionPane.QUESTION_MESSAGE);
		SimplePlayer user = (SimplePlayer) jcb.getSelectedItem();
		user.resetBet();
		System.out.println("Bet Canceled");
		jcb.invalidate();
	}

}
