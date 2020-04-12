package model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import model.enumeration.BetType;
import model.enumeration.CoinFace;
import model.interfaces.Coin;
import model.interfaces.CoinPair;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.GameEngineCallbackImpl;
import view.interfaces.GameEngineCallback;

public class GameEngineImpl implements GameEngine {
	private List<GameEngineCallback> callbackList;
	private Map<String, Player> playersList;

	public GameEngineImpl() {
		// TODO Auto-generated constructor stub
		playersList = new HashMap<>();
		callbackList = new ArrayList<>();
	}

	@Override
	public void spinPlayer(Player player, int initialDelay1, int finalDelay1, int delayIncrement1, int initialDelay2,
			int finalDelay2, int delayIncrement2) throws IllegalArgumentException {
		// TODO Auto-generated method stub
			int PlayerCoin1 = ThreadLocalRandom.current().nextInt(0, CoinFace.values().length - 1);
			int PlayerCoin2 = ThreadLocalRandom.current().nextInt(0, CoinFace.values().length - 1);
			Coin playercoin1 = new CoinImpl(1, CoinFace.values()[PlayerCoin1]);
			Coin playercoin2 = new CoinImpl(2, CoinFace.values()[PlayerCoin2]);
			CoinPair playercp = new CoinPairImpl(playercoin1, playercoin2);
			if (initialDelay1 < 0 || finalDelay1 < 0 || delayIncrement1 < 0)
				throw new IllegalArgumentException("ERROR: Delays cant be 0");
			if (finalDelay1 < initialDelay1 || delayIncrement1 > finalDelay1 - initialDelay1)
				throw new IllegalArgumentException("ERROR: delay parameter dont make mathematical sense");
			for (int i = initialDelay1; i < finalDelay1; i += delayIncrement1) {
				try {
					Thread.sleep(i);
				} catch (InterruptedException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				playercoin1.flip();
				for (GameEngineCallback gec : callbackList)
					gec.playerCoinUpdate(player, playercoin1, this);
			}
			if (initialDelay2 < 0 || finalDelay2 < 0 || delayIncrement2 < 0)
				throw new IllegalArgumentException("ERROR: Delays cant be 0");
			if (finalDelay2 < initialDelay2 || delayIncrement2 > finalDelay2 - initialDelay2)
				throw new IllegalArgumentException("ERROR: delay parameter dont make mathematical sense");
			for (int i = initialDelay2; i < finalDelay2; i += delayIncrement2) {
				try {
					Thread.sleep(i);
				} catch (InterruptedException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				playercoin2.flip();
				for (GameEngineCallback gec : callbackList)
					gec.playerCoinUpdate(player, playercoin2, this);
			}
			player.setResult(playercp);
			for (GameEngineCallback gec: callbackList)
				gec.playerResult(player, player.getResult(), this);
//		CoinImpl pcoin1 = new CoinImpl(1);
//		CoinImpl pcoin2 = new CoinImpl(2);
//		int delay = initialDelay1;
//		while (delay <= finalDelay1 || delay <= finalDelay2) {
//			pcoin1.flip();
//			for (GameEngineCallback g : callbackList) {
//				g.playerCoinUpdate(player, pcoin1, this.engine);
//			}
//			pcoin2.flip();
//			for (GameEngineCallback g : callbackList) {
//				g.playerCoinUpdate(player, pcoin2, this.engine);
//			}
//			try {
//				try {
//					Thread.sleep(delay);
//				} catch (InterruptedException e) {
//				}
//			} catch (IllegalArgumentException e) {
//				System.out.println("Error delay parms/logic not right");
//				e.printStackTrace();
//			}
//			delay = delay + delayIncrement1;
//		}
//		for (GameEngineCallback g : callbackList)
//			g.playerResult(player, player.getResult(), this.engine);
	}
	@Override
	public void spinSpinner(int initialDelay1, int finalDelay1, int delayIncrement1, int initialDelay2, int finalDelay2,
			int delayIncrement2) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		int SpinCoin1 = ThreadLocalRandom.current().nextInt(0, CoinFace.values().length - 1);
		int SpinCoin2 = ThreadLocalRandom.current().nextInt(0, CoinFace.values().length - 1);
		Coin spincoin1 = new CoinImpl(1, CoinFace.values()[SpinCoin1]);
		Coin spincoin2 = new CoinImpl(2, CoinFace.values()[SpinCoin2]);
		CoinPairImpl Spincp = new CoinPairImpl(spincoin1, spincoin2);
		if (initialDelay1 < 0 || finalDelay1 < 0 || delayIncrement1 < 0)
			throw new IllegalArgumentException("ERROR: Delays cant be 0");
		if (finalDelay1 < initialDelay1 || delayIncrement1 > finalDelay1 - initialDelay1)
			throw new IllegalArgumentException("ERROR: delay parameter dont make mathematical sense");
		for (int i = initialDelay1; i < finalDelay1; i += delayIncrement1) {
			try {
				Thread.sleep(i);
			} catch (InterruptedException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			spincoin1.flip();
			for (GameEngineCallback gec: callbackList)
				gec.spinnerCoinUpdate(spincoin1, this);
		}
		if (initialDelay2 < 0 || finalDelay2 < 0 || delayIncrement2 < 0)
			throw new IllegalArgumentException("ERROR: Delays cant be 0");
		if (finalDelay2 < initialDelay2 || delayIncrement2 > finalDelay2 - initialDelay2)
			throw new IllegalArgumentException("ERROR: delay parameter dont make mathematical sense");
		for (int i = initialDelay2; i < finalDelay2; i += delayIncrement2) {
			try {
				Thread.sleep(i);
			} catch (InterruptedException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			spincoin2.flip();
			for (GameEngineCallback gec: callbackList)
				gec.spinnerCoinUpdate(spincoin2, this);
		}
		applyBetResults(Spincp);
		for (GameEngineCallback gec: callbackList)
			gec.spinnerResult(Spincp, this);
		for (Player p: playersList.values())
			p.resetBet();
//		CoinImpl scoin1 = new CoinImpl(1);
//		CoinImpl scoin2 = new CoinImpl(2);
//		int delay = initialDelay1;
//		Player spinner = null;
//		try {
//			while (delay <= finalDelay1 || delay <= finalDelay2) {
//				scoin1.flip();
//				for (GameEngineCallback g : callbackList) {
//					g.spinnerCoinUpdate(scoin1, this.engine);
//				}
//				scoin2.flip();
//				for (GameEngineCallback g : callbackList) {
//					g.spinnerCoinUpdate(scoin2, this.engine);
//				}
//				try {
//					Thread.sleep(delay);
//				} catch (InterruptedException e) {
//				}
//				delay = delay + delayIncrement1;
//			}
//		} catch (IllegalArgumentException e) {
//			System.out.println("Error delay parms/logic not right");
//		}
//		for (GameEngineCallback g : callbackList)
//			g.spinnerResult(spinner.getResult(), this.engine);

	}

	@Override
	public void applyBetResults(CoinPair spinnerResult) {
		// TODO Auto-generated method stub
//		for (Player p: playersList.values())
//			p.getBetType().applyWinLoss(p, spinnerResult);
		for (Player p : playersList.values()) {
			p.getBetType().applyWinLoss(p, spinnerResult);
	}
	}

	@Override
	public void addPlayer(Player player) {
		// TODO Auto-generated method stub
		playersList.put(player.getPlayerId(), player);
	}

	@Override
	public Player getPlayer(String id) {
		// TODO Auto-generated method stub
		return playersList.get(id);
	}

	@Override
	public boolean removePlayer(Player player) {
		// TODO Auto-generated method stub
		return playersList.remove(player, player.getPlayerId());
	}

	@Override
	public void addGameEngineCallback(GameEngineCallback gameEngineCallback) {
		// TODO Auto-generated method stub
		callbackList.add(gameEngineCallback);
	}

	@Override
	public boolean removeGameEngineCallback(GameEngineCallback gameEngineCallback) {
		// TODO Auto-generated method stub
		return callbackList.remove(gameEngineCallback);
	}

	@Override
	public Collection<Player> getAllPlayers() {
		// TODO Auto-generated method stub
		return Collections.unmodifiableCollection(playersList.values());
	}

	@Override
	public boolean placeBet(Player player, int bet, BetType betType) {
		// TODO Auto-generated method stub
		if (betType != null && player.setBet(bet)) {
			player.setBetType(betType);
			return true;
		}
		else
			return false;
	}
}
