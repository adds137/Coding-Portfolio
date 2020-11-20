package model;

import java.util.Objects;
import model.interfaces.DicePair;
import model.interfaces.Die;

public class DicePairImpl implements DicePair {
	private Die die1;
	private Die die2;

	public DicePairImpl(Die die1, Die die2) {
		this.die1 = die1;
		this.die2 = die2;
	}

	@Override
	public Die getDie1() {
		return die1;
	}

	@Override
	public Die getDie2() {
		return die2;
	}

	@Override
	public int getTotal() {
		return die1.getValue() + die2.getValue();
	}

	@Override
	public boolean equals(DicePair dicePair) {
		return this.hashCode() == dicePair.hashCode();
	}

	@Override
	public int hashCode() {
		return Objects.hash(die1, die2);
	}

	@Override
	public int compareTo(DicePair dicePair) {
		if (getTotal() == dicePair.getTotal())
			return 1;
		else
			return 0;
	}

	@Override
	public String toString() {
		return String.format("Dice 1: %s, Dice 2: %s .. Total: %d", die1.getValue(), die2.getValue(), getTotal());

	}

}
