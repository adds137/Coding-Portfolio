package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import model.GameEngineImpl;
import model.SimplePlayer;


public class ToolbarRemoveplayer implements ActionListener {
	private JComboBox<SimplePlayer> jcb;
	private GameEngineImpl gec;

	public ToolbarRemoveplayer(JComboBox<SimplePlayer> jcb, GameEngineImpl gec) {
		// TODO Auto-generated constructor stub
		this.jcb = jcb;
		this.gec = gec;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Remove Player working");
		JOptionPane.showMessageDialog(null, jcb, "Player to Remove?", JOptionPane.QUESTION_MESSAGE);
		SimplePlayer user = (SimplePlayer) jcb.getSelectedItem();
		int index =jcb.getSelectedIndex();;
		gec.removePlayer(user);
		jcb.remove(index);
		System.out.println("user sucessfully removed");
	}

}
