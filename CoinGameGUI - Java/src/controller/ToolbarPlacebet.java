package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import model.SimplePlayer;
import view.PlaceBetGUI;

public class ToolbarPlacebet implements ActionListener {
	private JComboBox<SimplePlayer> jcb;

	public ToolbarPlacebet(JComboBox<SimplePlayer> jcb) {
		// TODO Auto-generated constructor stub
		this.jcb = jcb;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Place bet working");
		new Thread()
		{
			@Override
			public void run() {
				new PlaceBetGUI(jcb);
			}
		}.start();
	}
}
