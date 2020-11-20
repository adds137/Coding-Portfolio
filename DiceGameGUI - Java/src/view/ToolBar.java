package view;

import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import controller.MenuAdd;
import controller.MenuCancel;
import controller.MenuRemove;
import controller.ModelSubject;
import controller.ToolbarBet;
import controller.ToolbarRoll;
import model.GameEngineImpl;

@SuppressWarnings("serial")
public class ToolBar extends JToolBar {
	//all component needed to create the Toolbar panel
	public ToolBar(JComboBox<String> cbo, GameEngineImpl gei, HashMap<String, JPanel> hashpanel,JFrame frame,ModelSubject subject) {
		JMenuBar menu = new JMenuBar();
		JMenu optfile = new JMenu("File");
		JButton rollbtn = new JButton("Roll");
		JMenuItem addPlayeritem = new JMenuItem("Add Player");
		JMenuItem removePlayeritem = new JMenuItem("Remove Player");
		JMenuItem cancelbetitem = new JMenuItem("Cancel Bet");
		rollbtn.addActionListener(new ToolbarRoll(cbo,gei));
		JButton betbtn = new JButton("Bet");
		betbtn.addActionListener(new ToolbarBet(gei));
		menu.add(optfile);
		optfile.add(addPlayeritem);
		optfile.add(removePlayeritem);
		optfile.add(cancelbetitem);
		addPlayeritem.addActionListener(new MenuAdd(gei, hashpanel, cbo, frame,subject));
		removePlayeritem.addActionListener(new MenuRemove(gei, hashpanel, cbo));
		cancelbetitem.addActionListener(new MenuCancel(gei));
		add(menu);
		add(rollbtn);
		add(betbtn);
		add(cbo);
	}
}
