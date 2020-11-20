package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import model.interfaces.DicePair;
import model.interfaces.Die;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.interfaces.GameEngineCallback;

public class GameEngineImpl implements GameEngine {
	private Map<String, Player> playermap;
	private List<GameEngineCallback> callbacklist;

	public GameEngineImpl() {
		playermap = new HashMap<>();
		callbacklist = new ArrayList<>();
	}

	@Override
	public void rollPlayer(Player player, int initialDelay1, int finalDelay1, int delayIncrement1, int initialDelay2,
			int finalDelay2, int delayIncrement2) {
		Random rand = new Random();
		int delay1 = initialDelay1;
		int delay2 = initialDelay2;
		Die pdie1 = new DieImpl(1, rand.nextInt((6 - 1) + 1) + 1, 6);
		Die pdie2 = new DieImpl(2, rand.nextInt((6 - 1) + 1) + 1, 6);
		DicePairImpl pdicepair = new DicePairImpl(pdie1, pdie2);
		player.setResult(pdicepair);
		while (delay1 <= finalDelay1 || delay2 <= finalDelay2) {
			for (GameEngineCallback gec : callbacklist) {
				gec.playerDieUpdate(player, pdie1, this);
				pdie1 = new DieImpl(1, rand.nextInt((6 - 1) + 1) + 1, 6);
			}
			try {
				Thread.sleep(delay1);
				delay1 += delayIncrement1;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for (GameEngineCallback gec : callbacklist) {
				gec.playerDieUpdate(player, pdie2, this);
				pdie2 = new DieImpl(2, rand.nextInt((6 - 1) + 1) + 1, 6);
			}
			try {
				Thread.sleep(delay2);
				delay2 += delayIncrement2;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for (GameEngineCallback gec : callbacklist)
			gec.playerResult(player, pdicepair, this);
	}

	@Override
	public void rollHouse(int initialDelay1, int finalDelay1, int delayIncrement1, int initialDelay2, int finalDelay2,
			int delayIncrement2) {
		Random rand = new Random();
		int delay1 = initialDelay1;
		int delay2 = initialDelay2;
		Die hdie1 = new DieImpl(1, rand.nextInt((6 - 1) + 1) + 1, 6);
		Die hdie2 = new DieImpl(2, rand.nextInt((6 - 1) + 1) + 1, 6);
		while (delay1 <= finalDelay1 || delay2 <= finalDelay2) {
			for (GameEngineCallback gec : callbacklist) {
				gec.houseDieUpdate(hdie1, this);
				hdie1 = new DieImpl(1, rand.nextInt((6 - 1) + 1) + 1, 6);;
			}
			try {
				Thread.sleep(delay1);
				delay1 += delayIncrement1;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for (GameEngineCallback gec : callbacklist) {
				gec.houseDieUpdate(hdie2, this);
				hdie2 = new DieImpl(2, rand.nextInt((6 - 1) + 1) + 1, 6);
			}
			try {
				Thread.sleep(delay2);
				delay2 += delayIncrement2;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		DicePair hdicepair = new DicePairImpl(hdie1, hdie2);
		for (GameEngineCallback gec : callbacklist)
			gec.houseResult(hdicepair, this);
		for (Player p : playermap.values()) {
			applyWinLoss(p, hdicepair);
			for (GameEngineCallback gec : callbacklist)
				gec.playerResult(p, p.getResult(), this);
		}
	}

	@Override
	public void applyWinLoss(Player player, DicePair houseResult) {
		DicePair pdicepair = player.getResult();
		if (pdicepair.getTotal() > houseResult.getTotal())
			player.setPoints(player.getBet() + player.getPoints());
		else if (pdicepair.getTotal() == houseResult.getTotal())
			player.setPoints(player.getPoints() + 0);
		else 
			player.setPoints(player.getPoints() - player.getBet());
	}

	@Override
	public void addPlayer(Player player) {
		playermap.put(player.getPlayerId(), player);
	}

	@Override
	public Player getPlayer(String id) {
		return playermap.get(id);
	}

	@Override
	public boolean removePlayer(Player player) {
		if (playermap.remove(player.getPlayerId(), player) == true)
			return true;
		else
			return false;
	}

	@Override
	public boolean placeBet(Player player, int bet) {
		if (player.getPoints() > bet) {
			player.setBet(bet);
			return true;
		}
		return false;
	}

	@Override
	public void addGameEngineCallback(GameEngineCallback gameEngineCallback) {
		callbacklist.add(gameEngineCallback);
	}

	@Override
	public boolean removeGameEngineCallback(GameEngineCallback gameEngineCallback) {
		if (callbacklist.remove(gameEngineCallback) == true)
			return true;
		else
			return false;
	}

	@Override
	public Collection<Player> getAllPlayers() {
		return Collections.unmodifiableCollection(playermap.values());
	}

}
