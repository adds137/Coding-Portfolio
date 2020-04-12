package model.interfaces;

import java.util.Collection;

import model.enumeration.BetType;
import view.interfaces.GameEngineCallback;

/**
 * Assignment interface for Further Programming providing main model functionality
 * for this "Two-Up" style Coin game
 * 
 * @author Caspar Ryan
 */
public interface GameEngine
{
   /**
    * <pre>spin the coins progressing from the initialDelays to the finalDelays
    * in increments specified by the delayIncrements
    * 
    * <b>NOTE:</b> All delays are in milliseconds (ms)
    * 
    * 1. coins are initialised at their random starting face
    * 2. for each coin start at initialDelay then increment the delays for each coin on each iteration
    * 3. call GameEngineCallback.playerCoinUpdate(...) for each coin separately
    * 4. continue until both delays {@literal >=} finalDelay 
    * 5. call GameEngineCallback.playerResult(...) to finish
    * 
    * <b>See Also:</b>
    *  {@link view.interfaces.GameEngineCallback#playerCoinUpdate(Player, Coin, GameEngine)}
    *  {@link view.interfaces.GameEngineCallback#playerResult(Player, CoinPair, GameEngine)}
    * 
    * @param player 
    *             the Player to spin for
    * @param initialDelay1
    *            the starting delay in ms between updates for coin 1
    *            (reflects the speed at which this coin is spinning in the air)
    * @param finalDelay1
    *            the final delay in ms between updates when the coin has landed and stopped
    * @param delayIncrement1
    *            how much coin 1 slows down (i.e. delay gets longer) after each individual tumble
    *            i.e. each iteration until current delay {@literal >} finalDelay
    * @param initialDelay2 
    *            same as initialDelay1 but for coin 2 
    * @param finalDelay2
    *            same as finalDelay1 but for coin 2 
    * @param delayIncrement2 
    *            same as delayIncrement1 but for coin 2 
    * </pre>    
    * @throws IllegalArgumentException thrown when: <UL>
    * <LI> if any of the delay params are < 0
    * <LI> either of the finalDelay < initialDelay
    * <LI> either of the delayIncrement > (finalDelay - initialDelay)
    * </UL>
    *     
    * NOTE: for assignment 1 you can assume the values passed for the delays are the same for coins 1 and 2
    * and therefore optionally use only the first three delay parameters
    * since event handling and/or threads are suggested to implement different spinning speeds
    * which will be implemented in assignment 2 
    */
   public abstract void spinPlayer(Player player, int initialDelay1, int finalDelay1,
      int delayIncrement1, int initialDelay2, int finalDelay2, int delayIncrement2)
      throws IllegalArgumentException;

   /** <pre>as above but spinning for the Spinner and calling the appropriate spinner callbacks
    * 
    * @param initialDelay1
    *            the starting delay in ms between updates for coin 1
    *            (reflects the speed at which this coin is spinning in the air)
    * @param finalDelay1
    *            the final delay in ms between updates when the coin has landed and stopped
    * @param delayIncrement1
    *            how much coin 1 slows down (i.e. delay gets longer) after each individual tumble
    *            i.e. each iteration until current delay {@literal >} finalDelay
    * @param initialDelay2 
    *            same as initialDelay1 but for coin 2 
    * @param finalDelay2
    *            same as finalDelay1 but for coin 2 
    * @param delayIncrement2 
    *            same as delayIncrement1 but for coin 2</pre>     
    * @throws IllegalArgumentException thrown when: <UL>
    * <LI> if any of the delay params are < 0
    * <LI> either of the finalDelay < initialDelay
    * <LI> either of the delayIncrement > (finalDelay - initialDelay)
    * </UL>
    * 
    * <b>See Also:</b>
    * {@link view.interfaces.GameEngineCallback#spinnerCoinUpdate(Coin, GameEngine)}
    * {@link view.interfaces.GameEngineCallback#spinnerResult(CoinPair, GameEngine)}
    */
   public abstract void spinSpinner(int initialDelay1, int finalDelay1, int delayIncrement1,
      int initialDelay2, int finalDelay2, int delayIncrement2) throws IllegalArgumentException;

   /**
    * Iterate through players and apply win/loss point balances (via {@link BetType#applyWinLoss(Player, CoinPair)})
    * 
    * @param spinnerResult - the winning CoinPair as passed to GameEngineCallback.spinnerResult(...)
    */
   public abstract void applyBetResults(CoinPair spinnerResult);

   /**
    *  <b>NOTE:</b> playerID is unique and if another player with same id is added 
    *  it replaces the previous player
    *  
    * @param player - to add to game
    */
   public abstract void addPlayer(Player player);

   /**
    * @param id - id of player to retrieve (null if not found)
    * @return - the Player or null if Player does not exist
    */
   public abstract Player getPlayer(String id);

   /**
    * @param player - to remove from game
    * @return - true if the player existed and was removed
    */
   public abstract boolean removePlayer(Player player);

   /**
    * @param gameEngineCallback
    * <pre> a client specific implementation of GameEngineCallback used to perform display updates etc.
    * Callbacks should be called in the order they were added
    * <b>NOTE:</b> you will use a different implementation of the GameEngineCallback 
    *       for the console (assignment 1) and GUI (assignment 2) versions
    * </pre>
    * @see view.interfaces.GameEngineCallback
    */
   public abstract void addGameEngineCallback(GameEngineCallback gameEngineCallback);

   /**
    * @param gameEngineCallback - instance to be removed if no longer needed
    * @return true - if the gameEngineCallback existed
    * @see view.interfaces.GameEngineCallback
    */
   public abstract boolean removeGameEngineCallback(GameEngineCallback gameEngineCallback);

   /**
    * @return - an unmodifiable collection (or a shallow copy) of all Players
    * @see model.interfaces.Player
    */
   public abstract Collection<Player> getAllPlayers();

   /**
    * the implementation should make appropriate calls on the Player to place the bet and set bet type
    * 
    * @param player - the Player who is placing the bet
    * @param bet - the bet in points
    * @param betType - the type of bet (see spec for allowable BetType enum constants)
    * @return true - if bet is greater than 0 and player had sufficient points to place the bet OTHERWISE
    *         false - in which case the bet is set to 0 and betType set to NO_BET
    */
   public abstract boolean placeBet(Player player, int bet, BetType betType);
}