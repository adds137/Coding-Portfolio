package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.HashMap;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import model.GameEngineImpl;
import model.interfaces.Player;
import view.Summarypanel;

public class ToolbarBox implements ActionListener {
	private JComboBox<String> cbo;
	private HashMap<String, JPanel> map;
	private boolean first;
	private Summarypanel summary;
	private GameEngineImpl gei;

	public ToolbarBox(JComboBox<String> cbo, HashMap<String, JPanel> map, Summarypanel summary, GameEngineImpl gei) {
		this.cbo = cbo;
		this.map = map;
		this.summary = summary;
		first = true;
		this.gei = gei;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (first) {
			first = false;
			return;
		}
		
		//runs thru all the object in the hashmap and either set the visable as true or false depend on the combo box selected item
		for (String str : map.keySet()) {
			if (str == cbo.getSelectedItem()) {
				JPanel panel = map.get(str);
				panel.setVisible(true);
				
				//if its a player object set the summarypanel info accordingly
				if (str != "Main" && str != "House") {
					Collection<Player> players = gei.getAllPlayers();
					String userid = null;
					for (Player p : players) {
						if (cbo.getSelectedItem() == str) {
							userid = p.getPlayerId();
						}
					}
						Player user = gei.getPlayer(userid);
						summary.setLblplayername(str);
						summary.setLblpoint(user.getPoints());
						summary.setLblbet(user.getBet());
					}else {
						summary.setLblplayername(str);
						summary.setLblpoint(0);
						summary.setLblbet(0);
					}
				}else {
				JPanel panel = map.get(str);
				panel.setVisible(false);
			}
		}
	}
}
