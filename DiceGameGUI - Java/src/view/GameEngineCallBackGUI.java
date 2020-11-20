package view;

import controller.ModelSubject;
import controller.Observer;
import model.interfaces.DicePair;
import model.interfaces.Die;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.interfaces.GameEngineCallback;

public class GameEngineCallBackGUI implements GameEngineCallback, Observer {
	private ModelSubject subject;

	//all below using observer pattern to update dice and player
	public GameEngineCallBackGUI(ModelSubject subject) {
		this.subject = subject;
	}

	@Override
	public void playerDieUpdate(Player player, Die die, GameEngine gameEngine) {
		subject.setDienum(die.getNumber(), die.getValue());
	}

	@Override
	public void houseDieUpdate(Die die, GameEngine gameEngine) {
		subject.setDienum(die.getNumber(), die.getValue());
	}

	@Override
	public void playerResult(Player player, DicePair result, GameEngine gameEngine) {
		subject.setResult(player.getPlayerName(), player.getPoints(), player.getBet());
	}

	@Override
	public void houseResult(DicePair result, GameEngine gameEngine) {
		subject.setResult("House", 0, 0);
	}

	//both not needed for the callbackGUI
	@Override
	public void update(int dienum) {
		// NULL
	}
	

	@Override
	public void updateResult(String Playername) {
		//NULL
		
	}
}
