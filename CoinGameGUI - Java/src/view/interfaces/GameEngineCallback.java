package view.interfaces;

import model.interfaces.Coin;
import model.interfaces.CoinPair;
import model.interfaces.GameEngine;
import model.interfaces.Player;

/**
 * Assignment interface for Further Programming to notify client of GameEngine events<br>
 * i.e. the state of the flipped coins in the air and when they have landed and stopped
 * 
 * @author Caspar Ryan
 */
public interface GameEngineCallback
{
   /**
    * called as the Player's coins turn during a flip<br>
    * use this to update your GUI or log to console
    * 
    * @param player - the Player who flipped
    * @param coin - coinPair representing the current state of the Player's coins 
    * @param engine - a convenience reference to the engine so the receiver can call methods if necessary
    * @see model.interfaces.GameEngine
    */
   public void playerCoinUpdate(Player player, Coin coin, GameEngine engine);

   /**
    * called as the Spinner's coins turn during a flip<br>
    * use this to update your GUI or log to console
    * 
    * @param coin - coinPair representing the current state of the Spinner's coins 
    * @param engine - a convenience reference to the engine so the receiver can call methods if necessary
    * @see model.interfaces.GameEngine
    */
   public void spinnerCoinUpdate(Coin coin, GameEngine engine);

   /**
    * called when the Player's coins have landed and stopped turning<br>
    * 
    * @param player - the Player who flipped
    * @param coinPair - the state of the Player's coins when landed and stopped
    * @param engine - a convenience reference to the engine so the receiver can call methods if necessary
    * @see model.interfaces.GameEngine
    */
   public void playerResult(Player player, CoinPair coinPair, GameEngine engine);

   /**
    * called when the Spinner's coins have landed and stopped turning<br>
    * 
    * @param coinPair - the state of the Spinner's coins when landed and stopped
    * @param engine - a convenience reference to the engine so the receiver can call methods if necessary
    * @see model.interfaces.GameEngine
    */
   public void spinnerResult(CoinPair coinPair, GameEngine engine);
}
