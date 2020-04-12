package model.enumeration;

import java.util.ArrayList;

import model.interfaces.CoinPair;
import model.interfaces.Player;

/**
 * Provided enum type for Further Programming representing a type of Bet<br>
 * See inline comments for where you need to provide additional code
 * 
 * @author Caspar Ryan
 * 
 */
public enum BetType {
	COIN1 {
		@Override
		public void applyWinLoss(Player player, CoinPair spinnerResult) {
			// TODO implementation
			if (player.getResult().getCoin1().equals(spinnerResult.getCoin1()))
				player.setPoints(player.getBet() + player.getPoints());
			else
				player.setPoints(player.getPoints() - player.getBet());
//			if (spinnerResult.equals(spinnerResult.getCoin1()) == true)
//				player.setPoints(player.getBet() + player.getPoints());
//			else
//				player.setPoints(player.getPoints() - player.getBet());
		}
	},
	COIN2 {

		@Override
		public void applyWinLoss(Player player, CoinPair spinnerResult) {
			// TODO Auto-generated method stub
			if (player.getResult().getCoin2().equals(spinnerResult.getCoin2()))
				player.setPoints(player.getBet() + player.getPoints());
			else
				player.setPoints(player.getPoints() - player.getBet());
//			if (spinnerResult.equals(spinnerResult.getCoin2()) == true)
//				player.setPoints(player.getBet() + player.getPoints());
//			else
//				player.setPoints(player.getPoints() - player.getBet());
		}

	},
	BOTH {

		@Override
		public void applyWinLoss(Player player, CoinPair spinnerResult) {
			// TODO Auto-generated method stub
			if (player.getResult().equals(spinnerResult))
				player.setBet((player.getBet() * 2) + player.getPoints());
			else
				player.setBet(player.getPoints() + player.getBet());
//			if (spinnerResult.equals(spinnerResult.getCoin2()) == true
//					&& spinnerResult.equals(spinnerResult.getCoin1()) == true)
//				player.setBet((2 * player.getBet()) + player.getPoints());
//			else
//				player.setPoints(player.getPoints() - player.getBet());
		}
	},
	NO_BET {

		@Override
		public void applyWinLoss(Player player, CoinPair spinnerResult) {
			// TODO Auto-generated method stub
			player.resetBet();
		}
	};

	// TODO finish this class with other enum constants

	/**
	 * This method is to be overridden for each bet type<br>
	 * see assignment specification for betting odds and how win/loss is applied
	 * 
	 * @param player        - the player to apply the win/loss points balance
	 *                      adjustment
	 * @param spinnerResult - the CoinPair result of the spinner to compare to
	 */
	public static ArrayList<BetType> bettype = new ArrayList<BetType>();

	public abstract void applyWinLoss(Player player, CoinPair spinnerResult);

}