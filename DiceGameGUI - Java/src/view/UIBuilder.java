package view;

import java.awt.BorderLayout;
import java.util.HashMap;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.ModelSubject;
import controller.ToolbarBox;
import model.GameEngineImpl;

@SuppressWarnings("serial")
public class UIBuilder extends JFrame {
	private JComboBox<String> cboplayer;
	private HashMap<String, JPanel> hashpanel;
	//all component needed to create the general Frame, calling other methods
	public UIBuilder() {
		super("DiceGame GUI");
		final ModelSubject subject = new ModelSubject();
		cboplayer = new JComboBox<String>();
		hashpanel = new HashMap<String, JPanel>();
		GameEngineImpl gei = new GameEngineImpl();
		gei.addGameEngineCallback(new GameEngineCallbackImpl());
		Summarypanel summary = new Summarypanel(subject);
		add(new ToolBar(cboplayer,gei,hashpanel,this,subject), BorderLayout.NORTH);
		MainMenus main = new MainMenus(gei,hashpanel,cboplayer,this,subject);
		cboplayer.addActionListener(new ToolbarBox(cboplayer, hashpanel, summary,gei));
		Playerdice house = new Playerdice("House", subject,new GameEngineCallbackImpl());
		hashpanel.put("Main", main);
		house.setVisible(false);
		hashpanel.put("House", house);
		cboplayer.addItem("House");
		cboplayer.addItem("Main");
		add(house,BorderLayout.CENTER);
		add(main, BorderLayout.CENTER);
		cboplayer.setSelectedItem("Main");
		add(summary, BorderLayout.SOUTH);
		setBounds(100,100,880,550);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
}
