package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import model.GameEngineImpl;
import model.interfaces.Player;

public class MenuCancel implements ActionListener{
	private GameEngineImpl gei;
	public MenuCancel(GameEngineImpl gei) {
		this.gei = gei;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//asking the user for the the player to cancel bet then grabbing the player object and using the resetBet()
		try {
			Collection<Player> players = gei.getAllPlayers();
			JComboBox<String> playerlist = new JComboBox<String>();
			for(Player p: players) 
				playerlist.addItem(p.getPlayerName());
			JOptionPane.showMessageDialog(null, playerlist, "Player to Remove?", JOptionPane.QUESTION_MESSAGE);
			String userid = null;
			for(Player p: players) {
				if(p.getPlayerName() == playerlist.getSelectedItem()) {
					userid = p.getPlayerId();
				}
			}
			Player cancelplayer = gei.getPlayer(userid);
			cancelplayer.resetBet();
		}catch(NullPointerException e2) {
			JOptionPane.showMessageDialog(null,"Invailded Selection of player to cancel bet","Cancelling Failed",JOptionPane.WARNING_MESSAGE);
		}
	}
}
