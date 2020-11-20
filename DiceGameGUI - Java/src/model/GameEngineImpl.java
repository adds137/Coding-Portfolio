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

public class GameEngineImpl implements GameEngine{
	private Map<String, Player> playermap;
	private List<GameEngineCallback> callbacklist;

	final int DICEFACE = 6;
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
		Die pdie1 = new DieImpl(1, rand.nextInt((DICEFACE - 1) + 1) + 1, DICEFACE);
		Die pdie2 = new DieImpl(2, rand.nextInt((DICEFACE - 1) + 1) + 1, DICEFACE);
		DicePairImpl pdicepair = new DicePairImpl(pdie1, pdie2);
		player.setResult(pdicepair);
		
		if(initialDelay1 < 0 || initialDelay2 < 0 || delayIncrement1 < 0 || delayIncrement2 < 0 || finalDelay1 < 0 || finalDelay2 < 0)
			throw new IllegalArgumentException("None of the delay params can be < 0");
		else if(finalDelay1 < initialDelay1 || finalDelay2 < initialDelay2) 
			throw new IllegalArgumentException("finalDelay < initialDelay cant happen");
		else if(delayIncrement1 > (finalDelay1 - initialDelay1) || delayIncrement2 > (finalDelay2 - initialDelay2))
			throw new IllegalArgumentException("delayIncrement > (finalDelay - initialDelay) cant happen");
		
		while (delay1 <= finalDelay1 || delay2 <= finalDelay2) {
			pdie1 = rolldiceplayer(callbacklist, pdie1, delay1, delayIncrement1, rand,player,1);
			delay1 += delayIncrement1;
			pdie2 = rolldiceplayer(callbacklist, pdie2, delay2, delayIncrement2, rand,player,2);
			delay2 += delayIncrement2;
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
		Die hdie1 = new DieImpl(1, rand.nextInt((DICEFACE - 1) + 1) + 1, DICEFACE);
		Die hdie2 = new DieImpl(2, rand.nextInt((DICEFACE - 1) + 1) + 1, DICEFACE);
		
		if(initialDelay1 < 0 || initialDelay2 < 0 || delayIncrement1 < 0 || delayIncrement2 < 0 || finalDelay1 < 0 || finalDelay2 < 0)
			throw new IllegalArgumentException("None of the delay params can be < 0");
		else if(finalDelay1 < initialDelay1 || finalDelay2 < initialDelay2) 
			throw new IllegalArgumentException("finalDelay < initialDelay cant happen");
		else if(delayIncrement1 > (finalDelay1 - initialDelay1) || delayIncrement2 > (finalDelay2 - initialDelay2))
			throw new IllegalArgumentException("delayIncrement > (finalDelay - initialDelay) cant happen");
		
		while (delay1 <= finalDelay1 || delay2 <= finalDelay2) {
			rolldicehouse(callbacklist, hdie1, delay1, delayIncrement1, rand);
			delay1 += delayIncrement1;
			rolldicehouse(callbacklist, hdie2, delay2, delayIncrement2, rand);
			delay2 += delayIncrement2;
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
	
	private Die rolldiceplayer(List<GameEngineCallback> gec,Die die,int delay, int delayIncrement, Random rand,Player p,int index) {
		for (GameEngineCallback geclist : gec) {
			geclist.playerDieUpdate(p, die, this);
			die = new DieImpl(index, rand.nextInt((DICEFACE - 1) + 1) + 1, DICEFACE);
		}
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return die;
	}
	
	private Die rolldicehouse(List<GameEngineCallback> gec,Die die,int delay, int delayIncrement, Random rand) {
		for (GameEngineCallback geclist : gec) {
			geclist.houseDieUpdate(die, this);
			die = new DieImpl(1, rand.nextInt((DICEFACE - 1) + 1) + 1, DICEFACE);
		}
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return die;
	}
}

