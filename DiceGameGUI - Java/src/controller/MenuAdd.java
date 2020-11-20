package controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.GameEngineImpl;
import model.SimplePlayer;
import view.GameEngineCallBackGUI;
import view.Playerdice;

public class MenuAdd implements ActionListener {
	private int id;
	private GameEngineImpl gei;
	private HashMap<String, JPanel> map;
	private JComboBox<String> jcb;
	private JFrame frame;
	private ModelSubject subject;

	public MenuAdd(GameEngineImpl gei, HashMap<String, JPanel> map, JComboBox<String> jcb, JFrame frame,
			ModelSubject subject) {
		id = 1;
		this.gei = gei;
		this.jcb = jcb;
		this.map = map;
		this.frame = frame;
		this.subject = subject;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String name;
		String points;
		SimplePlayer user = null;
		
		//asking the user for the name and point of the new player, shows error message if not vaild 
		try {
			name = JOptionPane.showInputDialog(null, "Username:", "Add player", JOptionPane.QUESTION_MESSAGE);
			points = JOptionPane.showInputDialog(null, "Starting Points", "Add player", JOptionPane.QUESTION_MESSAGE);
			user = new SimplePlayer(String.valueOf(id), name, Integer.parseInt(points));
		} catch (NumberFormatException e2) {
			JOptionPane.showMessageDialog(null,"Invailded Number Input,please try again","Adding Failed",JOptionPane.WARNING_MESSAGE);
		} catch (NullPointerException e3) {
			JOptionPane.showMessageDialog(null,"Missing Input.please try again","Adding Failed",JOptionPane.WARNING_MESSAGE);
		}
		
		//adding the player to the gei and creating a callbackGUI for it
		GameEngineCallBackGUI gecGUI = new GameEngineCallBackGUI(subject);
		gei.addGameEngineCallback(gecGUI);
		gei.addPlayer(user);
		
		//creating the Playerdice panel, adding it to the hashmap and the combobox, and adding it to the frame
		Playerdice panel = new Playerdice(user.getPlayerName(),subject, gecGUI);
		map.put(user.getPlayerName(), panel);
		jcb.addItem(user.getPlayerName());
		panel.setVisible(false);
		jcb.setSelectedItem(user.getPlayerName());
		frame.add(panel, BorderLayout.CENTER);
	}
}