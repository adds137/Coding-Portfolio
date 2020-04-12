package model;

import model.enumeration.BetType;
import model.interfaces.CoinPair;
import model.interfaces.Player;

public class SimplePlayer implements Player {

	private String ID;
	private String name;
	private int points;
	private int bet;
	private BetType betType;
	private CoinPair result;

	public SimplePlayer(String ID, String name, int points) {
		this.ID = ID;
		this.name = name;
		this.points = points;
	}

	@Override
	public String getPlayerName() {
		return name;
	}

	@Override
	public void setPlayerName(String name) {
		if (name == null) {
			return;
		}
		;
		this.name = name;
	}

	@Override
	public int getPoints() {
		return points;
	}

	@Override
	public void setPoints(int points) {
		this.points = points;

	}

	@Override
	public String getPlayerId() {
		return ID;
	}

	@Override
	public boolean setBet(int bet) {
		if (bet > 0 && bet <= points) {
			this.bet = bet;
			return true;
		}
		return false;
	}

	@Override
	public int getBet() {
		return bet;
	}

	@Override
	public void setBetType(BetType betType) {
		if (betType == null) {
			this.betType = BetType.NO_BET;
		} else {
			this.betType = betType;
		}
	}

	@Override
	public BetType getBetType() {
		return betType;
	}

	@Override
	public void resetBet() {
		this.betType = BetType.NO_BET;
		this.bet = 0;
	}

	@Override
	public CoinPair getResult() {
		return result;
	}

	@Override
	public void setResult(CoinPair coinPair) {
		if (coinPair == null) {
			return;
		}
		this.result = coinPair;

	}

	@Override
	public String toString() {
		return "Player: id=" + ID + ", name=" + name + ", bet=" + bet + ", betType=" + betType + ", points=" + points;
	}

}
