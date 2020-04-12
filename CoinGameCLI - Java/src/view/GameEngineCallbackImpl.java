package view;

import java.util.logging.Level;
import java.util.logging.Logger;

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

	@Override
	public void playerCoinUpdate(Player player, Coin coin, GameEngine engine) {
		// intermediate results logged at Level.FINE
		logger.log(Level.FINE, player.getPlayerName() + " coin " + coin.getNumber() + " flipped to " + coin.getFace());
	}

	@Override
	public void playerResult(Player player, CoinPair coinPair, GameEngine engine) {
		// final results logged at Level.INFO
		logger.log(Level.INFO, player.getPlayerName() + ", final result=" + player.getResult());
	}

	@Override
	public void spinnerCoinUpdate(Coin coin, GameEngine engine) {
		// intermediate results logged at Level.FINE
		logger.log(Level.FINE, "Spinner coin " + coin.getNumber() + " flipped to " + coin.getFace());
	}

	/**
	 * @param coinPair
	 * @param engine   INFO: Final Player Results Player: id=1, name=The Coin
	 *                 Master, bet=100, betType=COIN1, points=1100, RESULT .. Coin
	 *                 1: Heads, Coin 2: Tails Player: id=2, name=The Loser,
	 *                 bet=100, betType=COIN2, points=650, RESULT .. Coin 1: Tails,
	 *                 Coin 2: Tails Player: id=3, name=The Dabbler, bet=100,
	 *                 betType=BOTH, points=700, RESULT .. Coin 1: Heads, Coin 2:
	 *                 Heads
	 */
	@Override

	public void spinnerResult(CoinPair coinPair, GameEngine engine) {
		// final results logged at Level.INFO
		StringBuilder sb = new StringBuilder("Final Player Results");
		for (Player player : engine.getAllPlayers()) {
			sb.append("\n" + player + ", Result  ..  " + player.getResult());
		}
		logger.log(Level.INFO, sb.toString());
	}

	// TODO: implement rest of interface
}
