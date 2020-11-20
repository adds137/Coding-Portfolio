package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.HashMap;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.GameEngineImpl;
import model.interfaces.Player;
import view.Playerdice;

public class MenuRemove implements ActionListener{
	private GameEngineImpl gei;
	private HashMap<String, JPanel> map;
	private JComboBox<String> jcb;
	public MenuRemove(GameEngineImpl gei,HashMap<String, JPanel> map, JComboBox<String> jcb) {
		this.gei = gei;
		this.jcb = jcb;
		this.map = map;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		//asking the user for the the player to remove then grabbing the player object and using the resetBet()
		try {
		Collection<Player> players = gei.getAllPlayers();
		JComboBox<String> playerlist = new JComboBox<String>();
		for(Player p: players) 
			playerlist.addItem(p.getPlayerName());
		JOptionPane.showMessageDialog(null, playerlist, "Player to Cancel Bet?", JOptionPane.QUESTION_MESSAGE);
		String userid = null;
		for(Player p: players) {
			if(p.getPlayerName() == playerlist.getSelectedItem()) {
				userid = p.getPlayerId();
			}
		}
		Player removeuser = gei.getPlayer(userid);
		gei.removePlayer(removeuser);

		// finding the callback and removing it from the callback 
		Playerdice panel = (Playerdice) map.get(removeuser.getPlayerName());
		gei.removeGameEngineCallback(panel.getGec());
		
		//removing the player from both the hashmap and combobox
		map.remove(removeuser.getPlayerName());
		jcb.removeItem(removeuser.getPlayerName());
		}catch(NullPointerException e2) {
			JOptionPane.showMessageDialog(null,"Invailded Selection of player to cancel bet","Cancelling Failed",JOptionPane.WARNING_MESSAGE);
		}
	}
}
