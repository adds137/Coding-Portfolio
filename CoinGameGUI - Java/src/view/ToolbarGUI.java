package view;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import controller.Spinbutton;
import controller.ToolbarAddplayer;
import controller.ToolbarCancelbet;
import controller.ToolbarExit;
import controller.ToolbarPlacebet;
import controller.ToolbarRemoveplayer;
import model.GameEngineImpl;
import model.SimplePlayer;

@SuppressWarnings("serial")
public class ToolbarGUI extends JPanel {

	public ToolbarGUI(GameEngineImpl g) {
		setLayout(new BorderLayout());
		JMenuBar menu = new JMenuBar();
		JMenu optfile = new JMenu("File");
		JMenu optplayer = new JMenu("Player");
		JMenuItem exititem = new JMenuItem("Exit");
		JMenuItem addPlayeritem = new JMenuItem("Add Player");
		JMenuItem removePlayeritem = new JMenuItem("Remove Player");
		JMenuItem placebetitem = new JMenuItem("Place Bet");
		JMenuItem cancelbetitem = new JMenuItem("Cancel Bet");
		optfile.add(exititem);
		optplayer.add(addPlayeritem);
		optplayer.add(removePlayeritem);
		optplayer.add(placebetitem);
		optplayer.add(cancelbetitem);
		exititem.addActionListener(new ToolbarExit());
		JComboBox<SimplePlayer> playercbo = new JComboBox<SimplePlayer>();
		addPlayeritem.addActionListener(new ToolbarAddplayer(g, playercbo,0));
		removePlayeritem.addActionListener(new ToolbarRemoveplayer(playercbo, g));
		placebetitem.addActionListener(new ToolbarPlacebet(playercbo));
		cancelbetitem.addActionListener(new ToolbarCancelbet(playercbo));
		menu.add(optfile);
		menu.add(optplayer);
		add(menu, BorderLayout.NORTH);
		JButton btnspin = new JButton("Spin");
		btnspin.addActionListener(new Spinbutton());
		add(playercbo, BorderLayout.EAST);
		add(btnspin, BorderLayout.WEST);
	}
}