package view;

import javax.swing.JPanel;
import view.die.*;

//enum used for all the dice values
public enum Dice {
	DIE1ONE(new DieOne()),
	DIE2ONE(new DieOne()),
	DIE1TWO(new DieTwo()),
	DIE2TWO(new DieTwo()),
	DIE1THREE(new DieThree()),
	DIE2THREE(new DieThree()),
	DIE1FOUR(new DieFour()),
	DIE2FOUR(new DieFour()),
	DIE1FIVE(new DieFive()),
	DIE2FIVE(new DieFive()),
	DIE1SIX(new DieSix()),
	DIE2SIX(new DieSix());
	private JPanel jp;
	
	//getter and setter  for jp
	private Dice(JPanel jp) {
		this.jp = jp;
	}
	
	public JPanel getJp() {
		return jp;
	}
}
