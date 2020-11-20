package view;

import java.awt.BorderLayout;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import controller.MenuAdd;
import controller.MenuCancel;
import controller.MenuRemove;
import controller.ModelSubject;
import model.GameEngineImpl;

@SuppressWarnings("serial")
public class MainMenus extends JPanel{
	
	//all component needed to create the main panel
	public MainMenus(GameEngineImpl gei,HashMap<String, JPanel> map, JComboBox<String> jcb, JFrame frame, ModelSubject subject) {
		JLabel lbltitle = new JLabel("Main Menu");
		JButton btnadd = new JButton("Add Player");
		btnadd.addActionListener(new MenuAdd(gei, map, jcb, frame,subject));
		JButton btnremove = new JButton("Remove Player");
		btnremove.addActionListener(new MenuRemove(gei, map, jcb));
		JButton btncancel = new JButton("Cancel Bet");
		btncancel.addActionListener(new MenuCancel(gei));
		setLayout(new BorderLayout());
		JPanel headpanel = new JPanel();
		JPanel bodypanel = new JPanel();;
		headpanel.add(lbltitle, JLabel.CENTER);
		bodypanel.add(btnadd);
		bodypanel.add(btnremove);
		bodypanel.add(btncancel);
		add(headpanel, BorderLayout.NORTH);
		add(bodypanel,BorderLayout.CENTER);
	}
}
