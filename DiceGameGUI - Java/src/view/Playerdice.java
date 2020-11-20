package view;

import java.awt.GridLayout;
import javax.swing.JPanel;
import controller.ModelSubject;
import controller.Observer;
import view.interfaces.GameEngineCallback;

@SuppressWarnings("serial")
public class Playerdice extends JPanel implements Observer {
	private String owner;
	private JPanel die1;
	private JPanel die2;
	private ModelSubject subject;
	private GameEngineCallback gec;
	
	//all component needed to create the playerdice panel
	public Playerdice(String owner, ModelSubject subject,GameEngineCallback gec) {
		this.owner = owner;
		setLayout(new GridLayout(1, 2));
		die1 = Dice.DIE1ONE.getJp();
		die2 = Dice.DIE2ONE.getJp();
		add(die1);
		add(die2);
		this.subject = subject;
		this.subject.addObserver(this);
		this.gec = gec;
		setVisible(true);
	}
	
	//getter for owner
	public String getOwner() {
		return owner;
	}
	
	//used to update draw die
	@Override
	public void update(int dienum) {
		if (dienum == 1) {
			remove(die1);
			switch (subject.getdievalue()) {
			case 1:
				die1 = Dice.DIE1ONE.getJp();
				break;
			case 2:
				die1 = Dice.DIE1TWO.getJp();
				break;
			case 3:
				die1 = Dice.DIE1THREE.getJp();
				break;
			case 4:
				die1 = Dice.DIE1FOUR.getJp();
				break;
			case 5:
				die1 = Dice.DIE1FIVE.getJp();
				break;
			case 6:
				die1 = Dice.DIE1SIX.getJp();
				break;
			}
			add(die1);
			die1.revalidate();
			die1.repaint();
		} else if (dienum == 2) {
			remove(die2);
			switch (subject.getdievalue()) {
			case 1:
				die2 = Dice.DIE2ONE.getJp();
				break;
			case 2:
				die2 = Dice.DIE2TWO.getJp();
				break;
			case 3:
				die2 = Dice.DIE2THREE.getJp();
				break;
			case 4:
				die2 = Dice.DIE2FOUR.getJp();
				break;
			case 5:
				die2 = Dice.DIE2FIVE.getJp();
				break;
			case 6:
				die2 = Dice.DIE2SIX.getJp();
				break;
			}
			add(die2);
			die2.revalidate();
			die2.repaint();
		}
	}
	
	//getter for gec
	public GameEngineCallback getGec() {
		return gec;
	}	
	
	//both not needed for the callbackGUI
	@Override
	public void updateResult(String playname) {
		//NULL
		
	}
}
