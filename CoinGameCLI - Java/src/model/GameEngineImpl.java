package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import model.enumeration.BetType;
import model.enumeration.CoinFace;
import model.interfaces.Coin;
import model.interfaces.CoinPair;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.interfaces.GameEngineCallback;

public class GameEngineImpl implements GameEngine {

	private Map<String, Player> players;
	private List<GameEngineCallback> callbacks;

	public GameEngineImpl() {
		players = new HashMap<>();
		callbacks = new ArrayList<>();
	}

	@Override
	public void spinPlayer(Player player, int initialDelay1, int finalDelay1, int delayIncrement1, int initialDelay2,
			int finalDelay2, int delayIncrement2) throws IllegalArgumentException {
		checkDelayValues(initialDelay1, delayIncrement1, finalDelay1);

		int Coin1 = generateRandomCoinFace();
		int Coin2 = generateRandomCoinFace();

		Coin coin1 = new CoinImpl(1, CoinFace.values()[Coin1]);
		Coin coin2 = new CoinImpl(2, CoinFace.values()[Coin2]);

		CoinPairImpl coinPair = new CoinPairImpl(coin1, coin2);

		spinPlayerCoin(initialDelay1, finalDelay1, delayIncrement1, coin1, player);
		spinPlayerCoin(initialDelay2, finalDelay2, delayIncrement2, coin2, player);

		player.setResult(coinPair);

		playerCoinResult(player);

	}

	private void playerCoinUpdate(Player player, Coin coin) {
		for (GameEngineCallback gameEngineCallback : callbacks) {
			gameEngineCallback.playerCoinUpdate(player, coin, this);
		}
	}

	private void playerCoinResult(Player player) {
		for (GameEngineCallback gameEngineCallback : callbacks) {
			gameEngineCallback.playerResult(player, player.getResult(), this);
		}
	}

	private int generateRandomCoinFace() {
		return ThreadLocalRandom.current().nextInt(0, CoinFace.values().length - 1);
	}

	private void checkDelayValues(int initialDelay1, int delayIncrement1, int finalDelay1)
			throws IllegalArgumentException {
		if (initialDelay1 < 0 || finalDelay1 < 0 || delayIncrement1 < 0) {
			throw new IllegalArgumentException("Invalid Delay");
		}
		if (finalDelay1 < initialDelay1 || delayIncrement1 > finalDelay1 - initialDelay1) {
			throw new IllegalArgumentException("Invalid");
		}
	}

	@Override
	public void spinSpinner(int initialDelay1, int finalDelay1, int delayIncrement1, int initialDelay2, int finalDelay2,
			int delayIncrement2) throws IllegalArgumentException {
		int Coin1 = generateRandomCoinFace();
		int Coin2 = generateRandomCoinFace();

		Coin coin1 = new CoinImpl(1, CoinFace.values()[Coin1]);
		Coin coin2 = new CoinImpl(2, CoinFace.values()[Coin2]);

		CoinPairImpl coinPair = new CoinPairImpl(coin1, coin2);
		spinSpinnerCoin(initialDelay1, finalDelay1, delayIncrement1, coin1);
		spinSpinnerCoin(initialDelay2, finalDelay2, delayIncrement2, coin2);

		applyBetResults(coinPair);

		spinnerResult(coinPair);

		for (Player player : players.values()) {
			player.resetBet();
		}
	}

	private void spinPlayerCoin(int initialDelay1, int finalDelay1, int delayIncrement1, Coin coin1, Player player) {
		checkDelayValues(initialDelay1, delayIncrement1, finalDelay1);

		for (int i = initialDelay1; i < finalDelay1; i += delayIncrement1) {
			try {
				Thread.sleep(i);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			coin1.flip();
			playerCoinUpdate(player, coin1);
		}
	}

	private void spinSpinnerCoin(int initialDelay1, int finalDelay1, int delayIncrement1, Coin coin1) {
		checkDelayValues(initialDelay1, delayIncrement1, finalDelay1);

		for (int i = initialDelay1; i < finalDelay1; i += delayIncrement1) {
			try {
				Thread.sleep(i);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			coin1.flip();
			spinCoinUpdate(coin1);

		}
	}

	private void spinCoinUpdate(Coin coin1) {
		for (GameEngineCallback gameEngineCallback : callbacks) {
			gameEngineCallback.spinnerCoinUpdate(coin1, this);
		}
	}

	private void spinnerResult(CoinPair coinPair) {
		for (GameEngineCallback gameEngineCallback : callbacks) {
			gameEngineCallback.spinnerResult(coinPair, this);
		}
	}

	@Override
	public void applyBetResults(CoinPair spinnerResult) {
		for (Player player : players.values()) {
			player.getBetType().applyWinLoss(player, spinnerResult);
		}
	}

	@Override
	public void addPlayer(Player player) {
		if (player == null) {
			return;
		}
		players.put(player.getPlayerId(), player);
	}

	@Override
	public Player getPlayer(String id) {
		return players.get(id);
	}

	@Override
	public boolean removePlayer(Player player) {
		return players.remove(player.getPlayerId()) != null;
	}

	@Override
	public void addGameEngineCallback(GameEngineCallback gameEngineCallback) {
		if (gameEngineCallback == null) {
			return;
		}
		callbacks.add(gameEngineCallback);

	}

	@Override
	public boolean removeGameEngineCallback(GameEngineCallback gameEngineCallback) {
		return callbacks.remove(gameEngineCallback);
	}

	@Override
	public Collection<Player> getAllPlayers() {
		return Collections.unmodifiableCollection(players.values());
	}

	@Override
	public boolean placeBet(Player player, int bet, BetType betType) {
		if (betType != null && player.setBet(bet)) {
			player.setBetType(betType);
			return true;
		}
		player.setBetType(BetType.NO_BET);
		player.setBet(0);
		return false;
	}

}
