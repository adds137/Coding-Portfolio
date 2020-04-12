package model;

import java.util.Objects;

import model.enumeration.BetType;
import model.enumeration.CoinFace;
import model.interfaces.Coin;

public class CoinImpl implements Coin {
	private int number;
	private CoinFace cface;
	
	public CoinImpl(int number, CoinFace face) {
		this.number = number;
		this.cface = cface;
	}

	@Override
	public int getNumber() {
		// TODO Auto-generated method stub
		return number;
	}

	@Override
	public CoinFace getFace() {
		// TODO Auto-generated method stub
		return cface;
	}

	@Override
	public void flip() {
		// TODO Auto-generated method stub
		if (cface == CoinFace.TAILS) 
			cface = CoinFace.HEADS;
		else 
			cface = CoinFace.TAILS;
	}

	@Override
	public boolean equals(Coin coin) {
		// TODO Auto-generated method stub
		return hashCode() == cface.hashCode();
	}

	public int hashCode() {
		return Objects.hash(this.cface);
	}

	@Override
	public String toString() {
		return String.format("Coin %s : %s", number, cface);
	}

}
