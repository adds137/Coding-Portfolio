package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import model.GameEngineImpl;
import model.interfaces.Player;

public class ToolbarRoll implements ActionListener{
	private JComboBox<String> cbo;
	private GameEngineImpl gei;
	public ToolbarRoll(JComboBox<String> cbo,GameEngineImpl gei) {
		this.cbo = cbo;
		this.gei = gei;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// on a new thread check which player it is and roll for that particular player and then changes the dice
		new Thread() {
			@Override
			public void run() {	
				Collection<Player> players = gei.getAllPlayers();
				String user = (String) cbo.getSelectedItem();
				if(user != "House" && user != "Main") {
					String userid = null;
					for(Player p: players) {
						if(p.getPlayerName() == user) {
							userid = p.getPlayerId();
						}
					}	
				Player rollplayer = gei.getPlayer(userid);
				
				//check if get is valid
				if(rollplayer.getBet() > 0)
					gei.rollPlayer(rollplayer, 100, 1000, 100, 50, 500, 50);
				else
					JOptionPane.showMessageDialog(null,"No bet has been placed for this player","Roll Failed",JOptionPane.WARNING_MESSAGE);
				
				//if selected is House then roll house else it is on main so error message
				}else if(user == "House")
					gei.rollHouse(100, 1000, 100, 50, 500, 50);
				else
					JOptionPane.showMessageDialog(null,"Cant roll on Main Panel","Roll Failed",JOptionPane.WARNING_MESSAGE);
			}
		}.start();
	}
}
