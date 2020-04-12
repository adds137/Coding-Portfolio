package model.interfaces;

import model.enumeration.CoinFace;

/**
 * <pre>
 * Assignment interface for Further Programming representing a Coin in the game
 * 
 * <b>IMPORTANT:</b> To facilitate testing, your implementation of this class should be <b>model.CoinImpl</b>
 * i.e. a class named CoinImpl residing in the model package
 * The implementing class should provide a single argument constructor which sets the coin number
 * and randomly initializes the face i.e.
 *    public CoinImpl(int number)
 * </pre>
 * @author Caspar Ryan
 */
public interface Coin
{
   /**
    * @return - the number of the coin as an int i.e. 1 for Coin 1 and 2 for Coin 2
    */
   public int getNumber();

   /**
    * @return - the upward/visible face of the coin 
    * 
    * @see model.enumeration.CoinFace
    */
   public CoinFace getFace();

   /**
    * <pre>flip the face of the coin to the opposing side i.e. heads goes to tails or tails goes to heads
    * this can be retrieved on the next call to {@link Coin#getFace()}</pre>
    */
   public void flip();

   /**
    * @param coin - another Coin to compare with
    * @return - true if the coin faces are equal
    */
   public abstract boolean equals(Coin coin);

   /**
    * <pre> <b>NOTE:</b> this is the java.lang.Object @Override
    * 
    * the implementation should cast and call through to the type checked method above</pre>
    * 
    * @param coin - another Coin to compare with
    * @return - true if the coin faces are equal
    */
   @Override
   public abstract boolean equals(Object coin);

   /**
    * <b>NOTE:</b> if equals() is true then generated hashCode should also be equal
    * 
    * @return - generated hash code (used by various JCF Collections)
    * 
    */
   @Override
   public abstract int hashCode();

   /**
   * @return <pre> A human readable String that lists the coin number and face value of this Coin instance 
   * (see OutputTrace.pdf) 
   * 
   * <b>NOTE:</b> Must use "proper naming" case i.e. First letter capitalised       
   * e.g. "Coin 1: Heads"</pre>
   */
   @Override
   public abstract String toString();
}
