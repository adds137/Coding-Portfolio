package model;

import java.util.Objects;

import model.interfaces.Coin;
import model.interfaces.CoinPair;

public class CoinPairImpl implements CoinPair {

	private Coin coin1;
	private Coin coin2;

	public CoinPairImpl(Coin coin1, Coin coin2) {
		this.coin1 = coin1;
		this.coin2 = coin2;
	}

	@Override
	public Coin getCoin1() {
		// TODO Auto-generated method stub
		return this.coin1;
	}

	@Override
	public Coin getCoin2() {
		// TODO Auto-generated method stub
		return this.coin2;
	}

	@Override
	public boolean equals(CoinPair coinPair) {
		// TODO Auto-generated method stub
		return this.hashCode() == coinPair.hashCode();
	}

	public int hashCode() {
		return Objects.hash(coin1, coin2);
	}

	@Override
	public String toString() {
		return String.format("%s , %s", coin1, coin2);
	}

}
