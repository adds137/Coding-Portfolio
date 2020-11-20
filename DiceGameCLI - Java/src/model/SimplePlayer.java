package model;

import model.interfaces.DicePair;
import model.interfaces.Player;

public class SimplePlayer implements Player {

	private String playerId;
	private String playerName;
	private int initialPoints;
	private int betAmount;
	private DicePair DicePair;

	public SimplePlayer(String playerId, String playerName, int initialPoints) {
		this.initialPoints = initialPoints;
		this.playerName = playerName;
		this.playerId = playerId;
		this.betAmount = 0;
	}

	@Override
	public String getPlayerName() {
		return playerName;
	}

	@Override
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	@Override
	public int getPoints() {
		return initialPoints;
	}

	@Override
	public void setPoints(int points) {
		this.initialPoints = points;
	}

	@Override
	public String getPlayerId() {
		return playerId;
	}

	@Override
	public boolean setBet(int bet) {
		if (bet > 0) {
			this.betAmount = bet;
			return true;
		} else
			return false;
	}

	@Override
	public int getBet() {
		return betAmount;
	}

	@Override
	public void resetBet() {
		this.betAmount = 0;
	}

	@Override
	public DicePair getResult() {
		return this.DicePair;
	}

	@Override
	public void setResult(DicePair rollResult) {
		this.DicePair = rollResult;
	}

	@Override
	public String toString() {
		return String.format(
				"Player: id=%s, name=%s, bet=%s, points=%s, RESULTS ... %s",
				playerId, playerName, betAmount, initialPoints,DicePair.toString());

	}
}
