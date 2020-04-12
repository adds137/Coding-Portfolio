package model;

import java.util.Objects;

import model.interfaces.Coin;
import model.interfaces.CoinPair;

public class CoinPairImpl implements CoinPair {

	private Coin coin1;
	private Coin coin2;

	public CoinPairImpl(Coin Coin1, Coin Coin2) {
		this.coin1 = Coin1;
		this.coin2 = Coin2;

	}

	@Override
	public Coin getCoin1() {
		return this.coin1;
	}

	@Override
	public Coin getCoin2() {
		return this.coin2;
	}

	@Override
	public boolean equals(CoinPair coinPair) {
		return this.hashCode() == coinPair.hashCode();
	}

	@Override
	public int hashCode() {
		return Objects.hash(coin1, coin2);
	}

	@Override
	public String toString() {
		return coin1 + ", " + coin2;
	}
}
