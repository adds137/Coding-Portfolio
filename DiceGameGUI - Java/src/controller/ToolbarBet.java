package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import model.GameEngineImpl;
import model.interfaces.Player;

public class ToolbarBet implements ActionListener{
	private GameEngineImpl gei;
	public ToolbarBet(GameEngineImpl gei) {
		this.gei = gei;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//asking the user which player to place  the bet on and the amount
		try {
		Collection<Player> players = gei.getAllPlayers();
		JComboBox<String> playerlist = new JComboBox<String>();
		for(Player p: players) 
			playerlist.addItem(p.getPlayerName());
		JOptionPane.showMessageDialog(null, playerlist, "Who are you?", JOptionPane.QUESTION_MESSAGE);
		String betamount = JOptionPane.showInputDialog(null, "Bet Amount?", "Add Bet", JOptionPane.QUESTION_MESSAGE);
		String userid = null;
		
		//get the player object and run the setBet()
		for(Player p: players) {
			if(p.getPlayerName() == playerlist.getSelectedItem()) {
				userid = p.getPlayerId();
			}
		}
		Player betuser = gei.getPlayer(userid);
		betuser.setBet(Integer.parseInt(betamount));
		}catch(NumberFormatException e2) {
			JOptionPane.showMessageDialog(null,"Invaild input,please try again","Bet Failed",JOptionPane.WARNING_MESSAGE);
		}
	}
}
