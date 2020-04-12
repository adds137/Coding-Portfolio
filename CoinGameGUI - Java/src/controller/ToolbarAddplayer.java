package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import model.GameEngineImpl;
import model.SimplePlayer;
import view.GameEngineCallBackGUI;

public class ToolbarAddplayer implements ActionListener {
	private GameEngineImpl gameEngineGUI;
	private JComboBox<SimplePlayer> cb;
	private int id = 0;

	public ToolbarAddplayer(GameEngineImpl gameEngineGUI, JComboBox<SimplePlayer> cb, int id) {
		this.gameEngineGUI = gameEngineGUI;
		this.cb = cb;
		this.id = id;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Add working");
		String name = JOptionPane.showInputDialog(null, "Username:", "Add player", JOptionPane.QUESTION_MESSAGE);
		String stringid = String.valueOf(id);
		SimplePlayer user = new SimplePlayer(name, stringid);
		id++;
		gameEngineGUI.addPlayer(user);
		gameEngineGUI.addGameEngineCallback(new GameEngineCallBackGUI());
		cb.addItem(user);
		System.out.println(String.format("%s successfully added", name));

	}
}
