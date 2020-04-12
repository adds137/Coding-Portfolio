package view;

import java.awt.BorderLayout;
import javax.swing.JFrame;

import model.GameEngineImpl;

@SuppressWarnings("serial")
public class UIBuilder extends JFrame{
	
	public UIBuilder() {
		super("Assignment 2: Two-up");
		setLayout(new BorderLayout());
		final GameEngineImpl gameEngineGUI = new GameEngineImpl();
		add(new ToolbarGUI(gameEngineGUI), BorderLayout.NORTH);
		add(new CoinGUI(), BorderLayout.CENTER);
		add(new StatusbarGUI(), BorderLayout.SOUTH);
		setBounds(100,100,850,550);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
}
