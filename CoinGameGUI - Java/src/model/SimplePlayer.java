package model;

import model.enumeration.BetType;
import model.interfaces.CoinPair;
import model.interfaces.Player;

public class SimplePlayer implements Player {
	private String playerId;
	private String playerName;
	private int initialPoints;
	private int bet;
	private BetType betType;
	private CoinPair coinPair;

	public SimplePlayer(String playerName, String playerid) {
		this.playerId = playerid;
		this.playerName = playerName;
		this.initialPoints = 1000;
	}

	@Override
	public String getPlayerName() {
		// TODO Auto-generated method stub
		return playerName;
	}

	@Override
	public void setPlayerName(String playerName) {
		// TODO Auto-generated method stub
		this.playerName = playerName;
	}

	@Override
	public int getPoints() {
		// TODO Auto-generated method stub
		return initialPoints;
	}

	@Override
	public void setPoints(int points) {
		// TODO Auto-generated method stub
		initialPoints = points;
	}

	@Override
	public String getPlayerId() {
		// TODO Auto-generated method stub
		return playerId;
	}

	@Override
	public boolean setBet(int bet) {
		// TODO Auto-generated method stub
		if (bet >= 0 && bet < getPoints()) {
			this.bet = bet;
			return true;
		} else
		return false;
	}

	@Override
	public int getBet() {
		// TODO Auto-generated method stub
		return bet;
	}

	@Override
	public void setBetType(BetType betType) {
		// TODO Auto-generated method stub
		//this.betType = betType;
		if (betType == null) 
			this.betType = BetType.NO_BET;
			else
				this.betType = betType;
		}

	@Override
	public BetType getBetType() {
		// TODO Auto-generated method stub
		return betType;
	}

	@Override
	public void resetBet() {
		// TODO Auto-generated method stub
		setBet(0);
		setBetType(BetType.NO_BET);
	}

	@Override
	public CoinPair getResult() {
		// TODO Auto-generated method stub
		return coinPair;
	}

	@Override
	public void setResult(CoinPair coinPair) {
		// TODO Auto-generated method stub
		this.coinPair = coinPair;
	}

	@Override
	public String toString() {
		return String.format("PlayerID: %s, Name= %s, bet= %d, betType= %s, points= %d, RESULT .. Coin 1: %s, Coin 2: %s",
				playerId, playerName, bet, betType, initialPoints, coinPair, coinPair);
	}

}
