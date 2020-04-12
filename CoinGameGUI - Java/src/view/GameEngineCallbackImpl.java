package view;

import java.util.logging.Level;
import java.util.logging.Logger;
import model.CoinImpl;
import model.CoinPairImpl;
import model.interfaces.Coin;
import model.interfaces.CoinPair;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.interfaces.GameEngineCallback;

/**
 * 
 * Skeleton implementation of GameEngineCallback showing Java logging behaviour
 * 
 * @author Caspar Ryan
 * @see view.interfaces.GameEngineCallback
 * 
 */
public class GameEngineCallbackImpl implements GameEngineCallback {
	private static final Logger logger = Logger.getLogger(GameEngineCallback.class.getName());

	public GameEngineCallbackImpl() {
		// NOTE need to also set the console to FINE in
		// %JRE_HOME%\lib\logging.properties
		logger.setLevel(Level.FINE);
	}

	public void playerCoinUpdate(Player player, Coin coin, GameEngine engine) {
		// intermediate results logged at Level.FINE
		logger.log(Level.FINE, String.format("FINE: %s coin %d flipped to %s", player.getPlayerName(), coin.getNumber(),
				coin.getFace()));
		// TODO: complete this method to log intermediate results
	}

	public void playerResult(Player player, CoinPair coinPair, GameEngine engine) {
		// final results logged at Level.INFO
		logger.log(Level.INFO, String.format("INFO: %s, final results= %s", player.getPlayerName(),
				player.getResult()));
		// TODO: complete this method to log results
	}

	@Override 
	public void spinnerCoinUpdate(Coin coin, GameEngine engine) {
		// TODO Auto-generated method stub
		logger.log(Level.FINE, String.format("FINE: Spinner coin %d flipped to %s", coin.getNumber(), coin.getFace()));
	}

	@Override
	public void spinnerResult(CoinPair coinPair, GameEngine engine) {
		// TODO Auto-generated method stub
		StringBuilder bs = new StringBuilder("Final Player Results:");
		for(Player player: engine.getAllPlayers())
			bs.append(String.format("\n %s , \n Result .. %s", player, player.getResult()));
		logger.log(Level.INFO, bs.toString());
	}

	// TODO: implement rest of interface
}
