package model;

import java.util.Objects;

import model.enumeration.CoinFace;
import model.interfaces.Coin;

public class CoinImpl implements Coin {

	private int number;
	private CoinFace face;

	public CoinImpl(int number, CoinFace coin) {
		this.number = number;
		this.face = coin;
	}

	@Override
	public int getNumber() {
		return this.number;
	}

	@Override
	public CoinFace getFace() {
		return this.face;
	}

	@Override
	public void flip() {
		if (this.face == CoinFace.HEADS) {
			this.face = CoinFace.TAILS;
		} else {
			this.face = CoinFace.HEADS;
		}

	}

	@Override
	public boolean equals(Coin coin) {
		return this.hashCode() == coin.hashCode();

	}

	@Override
	public int hashCode() {
		return Objects.hash(this.face);

	}

	@Override
	public String toString() {
		return "Coin " + number + ": " + this.face;
	}

}
