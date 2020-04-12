package model.interfaces;

/**
 * <pre>
 * Assignment interface for Further Programming representing a pair of Coins in the game
 * 
 * <b>IMPORTANT:</b> To facilitate testing, your implementation of this class should be <b>model.CoinPairImpl</b>
 * i.e. a class named CoinPairImpl residing in the model package
 * The implementing class should be immutable since this interface specifies no setter methods
 * The implementing class should provide a no argument constructor which creates the two coins using the 
 * specified constructor of CoinImpl i.e.
 *    public CoinImpl(int number)
 * </pre>
 * @author Caspar Ryan
 */
public interface CoinPair
{
   /**
    * @return - the first Coin of this pair
    */
   public Coin getCoin1();

   /**
    * @return - the second Coin of this pair
    */
   public Coin getCoin2();

   /**
   * @return <pre> A human readable String that lists the face values of this CoinPair instance 
   * (see OutputTrace.pdf) 
   * 
   * HINT: You can implement this using the specified toString() method of the contained Coins
   * 
   * <b>NOTE:</b> Must use "proper naming" case i.e. First letter capitalised       
   * e.g. "Coin 1: Heads, Coin 2: Tails"</pre>
   */
   @Override
   public abstract String toString();

   /**
    * @param coinPair - another CoinPair to compare with
    * @return - true if both Coins are equal according to {@link Coin#equals(Coin)}
    */
   public abstract boolean equals(CoinPair coinPair);

   /**
    * <pre> <b>NOTE:</b> this is the java.lang.Object @Override
    * 
    * its implementation should cast and call through to the type checked method above</pre>
    * 
    * @param coinPair - another CoinPair to compare with
    * @return - true if both Coins are equal according to {@link Coin#equals(Coin)}
    */
   @Override
   public abstract boolean equals(Object coinPair);

   /**
    * <b>NOTE:</b> if equals() is true then generated hashCode should also be equal
    * 
    * @return - generated hash code (used by various JCF Collections)
    * 
    */
   @Override
   public abstract int hashCode();
}